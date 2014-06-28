package com.tasteofdata.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登陆授权
 * auth=Option（user为空代表没登陆，user不为空代表用户已登陆）
 * auth=Required 只允许已登录用户操作，未登陆用户会跳转到登陆页
 * @author wwj
 */
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)  // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
public @interface LoginAuth {
	enum Auth{
		Required,		//必须登陆
		Option,			//可选的
	}
	
	public Auth auth() default Auth.Option;
}
