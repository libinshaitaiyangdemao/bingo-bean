package com.jd.bingo.bean.mapper.utils;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: libin29
 * @createdate: 2018/11/28 13:41
 * @lastdate:
 */
public class ConcurrentLocalStore<K, V> {
    /**
     * 资源状态类型，加载中
     */
    private static final int SOURCE_STATUS_LOADING = 0;
    /**
     * 资源加载状态，加载完成
     */
    private static final int SOURCE_STATUS_LOADED = 1;

    public interface Loader<K, V> {
        V load(K key);
    }

    private static class Trunk<V> {
        /**
         * 因为只有一个线程会修改status的值，即初始化对象的线程，其他线程都是监听，所以不涉及到线程安全，只考虑指令重排即可
         */
//        private AtomicInteger status;
        private volatile int status;
        private V value;

        public Trunk() {
//            status = new AtomicInteger(0);
            status = 0;
        }

        public void setStatus(int status) {
//            this.status.set(status);
            this.status = status;
        }

        public int getStatus() {
//            return this.status.get();
            return status;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private ConcurrentMap<K, Trunk<V>> store;
    private Loader<K, V> loader;

    public ConcurrentLocalStore(Loader<K, V> loader) {
        this.loader = loader;
        this.store = new ConcurrentHashMap<>();
    }

    /**
     * 获取值
     *
     * @param k
     * @return
     * @update 2018-12-04 原方法内部使用递归调用，等待获取值，该方案可能导致递归过深，栈溢出，修改为while循环
     */
    public V get(K k) {
        while (true) {
            Trunk<V> trunk = store.get(k);
            if (Objects.isNull(trunk)) {
                trunk = new Trunk<>();
                if (Objects.isNull(store.putIfAbsent(k, trunk))) {
                    try {
                        trunk.setValue(loader.load(k));
                        trunk.setStatus(SOURCE_STATUS_LOADED);
                        return trunk.getValue();
                    } catch (Exception e) {
                        //创建失败，移除，并返回空
                        store.remove(k, trunk);
                        throw new RuntimeException(e);
                    }
                }
            }else{
                if (trunk.getStatus() == SOURCE_STATUS_LOADED) {
                    //加载完成
                    return trunk.getValue();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 存储
     *
     * @param k
     * @param v
     */
    public void put(K k, V v) {
        Trunk<V> trunk = new Trunk<>();
        trunk.setValue(v);
        trunk.setStatus(SOURCE_STATUS_LOADED);
        store.put(k, trunk);
    }
}
