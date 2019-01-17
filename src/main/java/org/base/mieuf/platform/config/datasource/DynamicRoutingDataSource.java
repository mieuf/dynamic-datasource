package org.base.mieuf.platform.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceKey = DynamicDataSourceContextHolder.getDataSourceKey();
        logger.info("Current DataSource is [{}]", dataSourceKey );
        return dataSourceKey;
    }
}