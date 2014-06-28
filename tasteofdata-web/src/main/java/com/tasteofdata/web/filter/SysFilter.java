package com.tasteofdata.web.filter;

import com.tasteofdata.web.util.SysServletHandle;
import com.tasteofdata.web.util.WebUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter{
	private static final Logger logger = Logger.getLogger(SysFilter.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        //请求信息
        String ip = WebUtils.getIpAddr(httpRequest);
        String path = httpRequest.getServletPath();
        logger.info("ip[" + ip + "],path[" + httpRequest.getContextPath() + path + "]");
        
        //设置全局HttpRequest
        SysServletHandle.setServletRequest(httpRequest);
		SysServletHandle.setServletResponse(httpResponse);
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
