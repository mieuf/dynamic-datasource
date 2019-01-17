package org.base.mieuf.platform.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DynamicDataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static Lock lock = new ReentrantLock();

    private static int dataSource1 = 0;

    private static int dataSource2 = 0;

    private static int dataSource3 = 0;

    /**
     * Maintain variable for every thread, to avoid effect other thread
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = ThreadLocal.withInitial(DataSourceKey.DATA_SOURCE_1::name);

//    public static void main(String[] args) {
//        System.out.println(DynamicDataSourceContextHolder.CONTEXT_HOLDER.get());
//        DynamicDataSourceContextHolder.CONTEXT_HOLDER.set("123");
//        System.out.println(DynamicDataSourceContextHolder.CONTEXT_HOLDER.get());
//        DynamicDataSourceContextHolder.CONTEXT_HOLDER.remove();
//        DynamicDataSourceContextHolder.CONTEXT_HOLDER.remove();
//        System.out.println(DynamicDataSourceContextHolder.CONTEXT_HOLDER.get());
//    }

    /**
     * All DataSource List
     */
    public static List<Object> dataSourceKeys = new ArrayList<>();

    /**
     * The constant dealerDataSourceKeys.
     */
    public static List<Object>  dataSource1Keys = new ArrayList<>();

    /**
     * The constant crawlerDataSourceKeys.
     */
    public static List<Object> dataSource2Keys = new ArrayList<>();

    /**
     * The constant ecarDataSourceKeys.
     */
    public static List<Object> dataSource3Keys = new ArrayList<>();

    /**
     * To switch DataSource
     *
     * @param key the key
     */
    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    /**
     * Use master data source.
     */
    public static void useDataSource1() {
        lock.lock();
        try {
            int datasourceKeyIndex = dataSource1 % dataSource1Keys.size();
            CONTEXT_HOLDER.set(String.valueOf(dataSource1Keys.get(datasourceKeyIndex)));
            dataSource1++;
        } catch (Exception e) {
            logger.error("Switch datasource failed, error message is {}", e.getMessage());
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Use master data source.
     */
    public static void useDataSource2() {
        lock.lock();
        try {
            int datasourceKeyIndex = dataSource2 % dataSource2Keys.size();
            CONTEXT_HOLDER.set(String.valueOf(dataSource2Keys.get(datasourceKeyIndex)));
            dataSource2++;
        } catch (Exception e) {
            logger.error("Switch slave datasource failed, error message is {}", e.getMessage());
            useDataSource1();
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Use master data source.
     */
    public static void useDataSource3() {
        lock.lock();
        try {
            int datasourceKeyIndex = dataSource3 % dataSource3Keys.size();
            CONTEXT_HOLDER.set(String.valueOf(dataSource3Keys.get(datasourceKeyIndex)));
            dataSource3++;
        } catch (Exception e) {
            logger.error("Switch slave datasource failed, error message is {}", e.getMessage());
            useDataSource1();
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Get current DataSource
     *
     * @return data source key
     */
    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * To set DataSource as default
     */
    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * Check if give DataSource is in current DataSource list
     *
     * @param key the key
     * @return boolean boolean
     */
    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }
}
