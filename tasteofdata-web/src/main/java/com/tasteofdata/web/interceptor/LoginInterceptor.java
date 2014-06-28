package com.tasteofdata.web.interceptor;

import com.tasteofdata.land.module.support.cache.CacheService;
import com.tasteofdata.land.module.user.entry.User;
import com.tasteofdata.web.annotation.LoginAuth;
import com.tasteofdata.web.annotation.LoginAuth.Auth;
import com.tasteofdata.web.util.LoginUtil;
import com.tasteofdata.web.util.SysServletHandle;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登陆拦截器
 * @author wwj
 */
public class LoginInterceptor implements MethodInterceptor{
    private static final Logger logger = Logger.getLogger(LoginInterceptor.class);
    private final static String loginUrl = "/login";

    @Autowired
    private CacheService cacheService;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
        HttpServletRequest request = SysServletHandle.getServletRequest();
        HttpServletResponse response = SysServletHandle.getServletResponse();
		
		LoginAuth annotation = method.getAnnotation(LoginAuth.class);
		//添加了LoginAuth注解，请求拦截器
		if(null!=annotation){
			Auth auth = annotation.auth();
			User user = LoginUtil.getCacheLoginUserForFilter(request,response,cacheService);
			if(null==user){
				if(auth.equals(Auth.Required)){
                    if(logger.isDebugEnabled()) {
                        logger.debug("未登陆用户，跳转到登陆页面,path="+request.getServletPath());
                    }
					response.sendRedirect(request.getContextPath() + loginUrl);
				}
			}else {
                //已经登陆用户，设置用户登陆attribute
                LoginUtil.setLoginUser(request,user);
                Object[] arguments = invocation.getArguments();
                if (null != arguments) {
                    for (int i = arguments.length - 1; i >= 0; i--) {
                        if (arguments[i] instanceof User) {
                            arguments[i] = user;
                            break;
                        }
                    }
                }
            }
		}
		return invocation.proceed();
	}
	
	
	 
}
