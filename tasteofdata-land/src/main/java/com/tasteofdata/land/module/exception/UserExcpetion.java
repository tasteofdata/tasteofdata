package com.tasteofdata.land.module.exception;

public class UserExcpetion extends Exception{
	public static final long serialVersionUID = 8984493257505446640L;
	public static final int SYSTEM_ERROR = 1;		//系统异常
	public static final int DB_ERROR = 10;		//数据库异常
	public static final int USERNAME_NOTEXIST = 1000; 	//用户名不存在
	public static final int PASSWORD_ERROR = 1001;		//密码错误
	public static final  int USERNAME_EXISTED = 1100;	//用户已经存在
	public int code;
	public UserExcpetion(int code){
		this.code = code;
	}
}
