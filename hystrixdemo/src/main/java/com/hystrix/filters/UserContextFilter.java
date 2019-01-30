package com.hystrix.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hystrix.utils.UserContext;
import com.hystrix.utils.UserContextHolder;

@Component
public class UserContextFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		
		UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
		UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
		UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
		UserContextHolder.getContext().setOrgId(httpServletRequest.getHeader(UserContext.ORG_ID));
		
		logger.debug("test debug " + UserContextHolder.getContext().getCorrelationId());
		logger.info("test info " + UserContextHolder.getContext().getCorrelationId());
		
		System.out.println("UserContextFilter tmx-correlation-id = " + UserContextHolder.getContext().getCorrelationId());
		
		filterChain.doFilter(servletRequest, servletResponse);		
	}

}
