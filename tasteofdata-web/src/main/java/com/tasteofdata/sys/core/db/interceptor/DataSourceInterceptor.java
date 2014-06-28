package com.tasteofdata.sys.core.db.interceptor;

import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.util.PatternMatchUtils;

import com.tasteofdata.sys.core.db.datasource.DataSourceKey;

public class DataSourceInterceptor implements MethodInterceptor {
	private static final Logger logger = Logger.getLogger(DataSourceInterceptor.class);
	private Map<String,String> attributes;
	// 数据源key的存储控制器
    private DataSourceKey dataSourceKey;
    
    protected boolean isMatch(String methodName, String mappedName) {
		return PatternMatchUtils.simpleMatch(mappedName, methodName);
	}
    
    @Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String methodName = invocation.getMethod().getName();

		String matchAttr = this.attributes.get(methodName);
		if (matchAttr == null) {
			// Look for most specific name match.
			String bestNameMatch = null;
			for (String mappedName : this.attributes.keySet()) {
				if (isMatch(methodName, mappedName)
						&& (bestNameMatch == null || bestNameMatch.length() <= mappedName.length())) {
					matchAttr = this.attributes.get(mappedName);
					bestNameMatch = mappedName;
				}
			}
		}
		
		if(logger.isDebugEnabled())
        	logger.debug(invocation.getClass().getName()+"."+invocation.getMethod().getName()+" match attr:"+matchAttr);
		
		if("READ".equalsIgnoreCase(matchAttr)){
			dataSourceKey.setReadKey();
		}else if("WRITE".equalsIgnoreCase(matchAttr)){
			dataSourceKey.setwriteKey();
		}else{
			logger.warn(invocation.getClass().getName()
					+ "."+ invocation.getMethod().getName()
					+ "  match attr["+ matchAttr+ "] "
					+ "没有匹配,这里将为该方法设置一个read source key，如果执行insert、update操作，"
					+ "将可能会出错。请最好修改spring dataSourceIntercepto配置或修改按规范修改方法名");
			dataSourceKey.setReadKey();
		}
		return invocation.proceed();
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void setDataSourceKey(DataSourceKey dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}
}
