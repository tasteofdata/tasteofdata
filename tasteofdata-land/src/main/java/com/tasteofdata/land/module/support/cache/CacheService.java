package com.tasteofdata.land.module.support.cache;

import java.io.Serializable;


public interface CacheService {
	/**
	 * 
	 * @param <T>
	 * @param key
	 * @return
	 */
	<T extends Serializable> T get(String key);
	
	/**
	 * 存,设置超时时间
	 * @param key
	 * @param value
	 * @param exp
	 * @return
	 */
	<T extends Serializable> boolean set(String key, T value, int seconds);
	
	/**
	 * 获得string
	 * @param key
	 * @return
	 */
	String getString(String key);
	
	/**
	 * set string
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	boolean setString(String key,String value,int seconds);
	
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	boolean delete(String key);
}
