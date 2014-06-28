package com.tasteofdata.sys.core.db.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * @author wwj
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
	private DataSourceKey dataSourceKey;
	@Override
	protected Object determineCurrentLookupKey() {
		String key = "";
        try{
            key = dataSourceKey.getKey();
            if(logger.isDebugEnabled()){
                logger.debug("get dynamic data source key ["+ key+"]");
            }
        }catch(Exception e){
            logger.error("get dynamic data soure key exception", e);
            throw new RuntimeException("get data source key fail", e);
        }
        return key;
	}
	public void setDataSourceKey(DataSourceKey dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}
}
