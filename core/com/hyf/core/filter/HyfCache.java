package com.hyf.core.filter;
/**
 * 
 * @author : wangle
 * @date:2014-5-1
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HyfCache {
	private static Map<String, CacheItem> cacheMap = new HashMap<String, CacheItem>();
	private static long clearTime = 0; 
	public synchronized static Object getCache(String type, String key){
		String cacheKey = type + "::" + key;
		Object cacheValue = null;
		clearExpired();
		if(cacheMap.containsKey(cacheKey)){
			CacheItem item = cacheMap.get(cacheKey);
			long now = (new Date()).getTime();
			if(item.expireTime < now){
				cacheValue = null;
				cacheMap.remove(cacheKey);
		     }
			else{
				cacheValue = item.value;
			}
		}
		else{
		    cacheValue = null;
		}
		return cacheValue;
	}
	
	public synchronized static void putCache(String type, String key, Object value){
        	putCache(type, key, value, 1800);
	}
	
	public synchronized static void putCache(String type, String key, Object value, int seconds){
		String cacheKey = type + "::" + key;
		CacheItem cacheValue = new CacheItem(key, value, seconds * 1000);
		cacheMap.put(cacheKey, cacheValue);
	}
	
	public synchronized static boolean containsKey(String type, String key){
	      return getCache(type, key) == null;
	}
	
	private synchronized static void clearExpired(){
		long now = new Date().getTime();
		if(clearTime == 0 || now - clearTime > 120000){
			List<String> removeKeys = new ArrayList<String>();
			for(String key : cacheMap.keySet()){
				if(cacheMap.get(key).expireTime < now)
			    	removeKeys.add(key);
			}
			for(String key : removeKeys)
				cacheMap.remove(key);
			
			clearTime = new Date().getTime();
		}
	}
	
	
	public synchronized static void removeKey(String type, String key){
		String cacheKey = type + "::" + key;
		if(cacheMap.containsKey(cacheKey)){
			cacheMap.remove(cacheKey);
		}
	}
	
}



class CacheItem{
	public CacheItem(String key, Object value, long expire){
		this.key = key;
		this.value = value;
		this.expireTime = (new Date()).getTime() + expire;
	}
	public String key;
	public Object value;
	public long expireTime;
}
