package com.tasteofdata.sys.core.db.datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * DATA source key 
 * 数据源
 * @author wwj
 *
 */
public class DataSourceKey {
	private Logger logger = Logger.getLogger(DataSourceKey.class);
	private static final ThreadLocal<String> data_source_key = new ThreadLocal<String>();
	private String writeKey;			//写KEY
	private List<String> readKeyList = Collections.synchronizedList(new ArrayList<String>());
	private Random random = new Random();
	
	/**
	 * 配置文件配置设置readkeylist
	 * @param readKeyList
	 */
	public void setReadKeyList(List<String> readKeyList){
		this.readKeyList = readKeyList;
	}
	
	/**
	 * 配置文件配置写KEY
	 * @param writeKey
	 */
	public void setWriteKey(String writeKey) {
		this.writeKey = writeKey;
	}
	
	/**
	 * 设置写key
	 */
	@SuppressWarnings("static-access")
	public void setwriteKey(){
		if(logger.isDebugEnabled())
			logger.debug("add data source write key:"+writeKey);
		this.data_source_key.set(writeKey);
	}
	
	/**
	 * 随机设置读key
	 */
	@SuppressWarnings("static-access")
	public void setReadKey(){
		String readKey = readKeyList.get(random.nextInt(readKeyList.size()));
		if(logger.isDebugEnabled())
			logger.debug("add data source read key :" + readKey);
		this.data_source_key.set(readKey);
	}
	
	@SuppressWarnings("static-access")
	public String getKey(){
		String key = this.data_source_key.get();
		if(logger.isDebugEnabled())
			logger.debug("get data source key :" + key);
		return key;
	}
}
