package com.licensesservice.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.licensesservice.utils.UserContext;
import com.licensesservice.utils.UserContextHolder;

@Component
public class UserContextFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		
		UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
		UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
		UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
		UserContextHolder.getContext().setOrgId(httpServletRequest.getHeader(UserContext.ORG_ID));
		UserContextHolder.getContext().setAuthorization(httpServletRequest.getHeader(UserContext.AUTHORIZATION));
		
		logger.debug("test debug " + UserContextHolder.getContext().getCorrelationId());
		logger.info("test info " + UserContextHolder.getContext().getCorrelationId());
		logger.debug("*** Authorization = " + UserContextHolder.getContext().getAuthorization());
		
		System.out.println("UserContextFilter tmx-correlation-id = " + UserContextHolder.getContext().getCorrelationId());
		
		logger.debug("**** Adding reponse filter ****");		
		
		httpServletResponse.setHeader(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
		
		filterChain.doFilter(servletRequest, servletResponse);		
	}

}
