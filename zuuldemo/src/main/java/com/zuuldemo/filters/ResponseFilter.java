package com.zuuldemo.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ResponseFilter extends ZuulFilter {

	private static final int FILTER_ORDER = 1;
	private static final boolean SHOULD_FILTER = true;
	private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);
	
	@Autowired
	FilterUtils filterUtils;
	
	@Override
	public boolean shouldFilter() {
		return SHOULD_FILTER;
	}
	@Override
	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		
		logger.debug("****Adding the correlationIf to outbound headers. {}", filterUtils.getCorrelationId());
		
		requestContext.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());
		
		logger.debug("****Completing outgoing request for {}.", requestContext.getRequest().getRequestURI());
		
		return null;
	}
	@Override
	public String filterType() {		
		return FilterUtils.POST_FILTER_TYPE;
	}
	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}

}
