package com.tasteofdata.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysServletHandle {
	private static final ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> localResponse = new ThreadLocal<HttpServletResponse>();
	
	public static void setServletRequest(HttpServletRequest request){
		localRequest.set(request);
	}
	public static void setServletResponse(HttpServletResponse response){
		localResponse.set(response);
	}
	
	public static HttpServletRequest getServletRequest(){
		return	localRequest.get();
	}
	
	public static HttpServletResponse getServletResponse(){
		return localResponse.get();
	}
}
