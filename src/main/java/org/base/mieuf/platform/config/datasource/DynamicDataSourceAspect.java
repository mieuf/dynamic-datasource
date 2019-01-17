package org.base.mieuf.platform.config.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @Date 2019/1/16  9:18
 * @Description 切面，动态处理数据源
 **/
@Aspect
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    //后缀1
    private final static String suffix1 = "suffix1";
    //后缀2
    private final static String suffix2 = "suffix2";
    //后缀3
    private final static String suffix3 = "suffix3";



    /**
     * Dao aspect.
     * 织入切点 只能匹配一级
     * org.base.mieuf.project.*.dao 切不到dao层，因为project到dao有2层
     */
    @Pointcut("execution( * org.base.mieuf.project.bussiness.*.dao..*.*(..))")
    public void daoAspect() {
        logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    /**
     * Switch DataSource
     *
     * @param point the point
     */
    @Before("daoAspect()")
    public void switchDataSource(JoinPoint point) {
        logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++");
        String s = defineMethodType(point.getSignature().getName());

        switch (s){
            case suffix1:
                DynamicDataSourceContextHolder.useDataSource1();
                logger.debug("Switch DataSource to [{}] in Method [{}]",
                        DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
                break;
            case suffix2:
                DynamicDataSourceContextHolder.useDataSource2();
                logger.debug("Switch DataSource to [{}] in Method [{}]",
                        DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
                break;
            case suffix3:
                DynamicDataSourceContextHolder.useDataSource3();
                logger.debug("Switch DataSource to [{}] in Method [{}]",
                        DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
                break;
                //默认不切换数据源
            default:break;
        }
    }

    /**
     * Restore DataSource
     *
     * @param point the point
     */
    @After("daoAspect())")
    public void restoreDataSource(JoinPoint point) {
        logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++");
        DynamicDataSourceContextHolder.clearDataSourceKey();
        logger.debug("Restore DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }


    /**
     * Judge if method is to query hive
     *
     * @param methodName
     * @return
     */
    private String defineMethodType(String methodName) {
        if (methodName.contains(suffix1)) {
            return suffix1;
        }
        if (methodName.contains(suffix2)) {
            return suffix1;
        }
        if (methodName.contains(suffix3)) {
            return suffix1;
        }
        return "defaults";
    }

}