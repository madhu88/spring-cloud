package com.zuuldemo.filters;

import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;

@Component
public class FilterUtils {

	public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "tmx-auth-token";
    public static final String USER_ID        = "tmx-user-id";
    public static final String ORG_ID         = "tmx-org-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";
    
    public String getCorrelationId() {
    	RequestContext requestContext = RequestContext.getCurrentContext();
    	if (null != requestContext.getRequest().getHeader(CORRELATION_ID)) {
    		return requestContext.getRequest().getHeader(CORRELATION_ID);
    	} else {
    		return requestContext.getZuulRequestHeaders().get(CORRELATION_ID);
    	}
    }
    
    public void setCorrelationId(String correlationId) {
    	RequestContext requestContext = RequestContext.getCurrentContext();
    	requestContext.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }
    
    public String getOrgId() {
    	RequestContext requestContext = RequestContext.getCurrentContext();
    	if (null != requestContext.getRequest().getHeader(ORG_ID)) {
    		return requestContext.getRequest().getHeader(ORG_ID);
    	} else {
    		return requestContext.getZuulRequestHeaders().get(ORG_ID);
    	}
    }
    
    public void setOrgId(String orgId) {
    	RequestContext requestContext = RequestContext.getCurrentContext();
    	requestContext.addZuulRequestHeader(ORG_ID, orgId);
    }
    
    public final String getUserId() {
    	RequestContext requestContext = RequestContext.getCurrentContext();
    	if (null != requestContext.getRequest().getHeader(USER_ID)) {
    		return requestContext.getRequest().getHeader(USER_ID);
    	} else {
    		return requestContext.getZuulRequestHeaders().get(USER_ID);
    	}
    }
    
    public void setUserId(String userId) {
    	RequestContext requestContext = RequestContext.getCurrentContext();
    	requestContext.addZuulRequestHeader(USER_ID, userId);    	
    }
    
    public final String getAuthToken() {
    	RequestContext requestContext = RequestContext.getCurrentContext();
    	return requestContext.getRequest().getHeader(AUTH_TOKEN);
    }
    
    public String getServiceId() {
    	RequestContext requestContext = RequestContext.getCurrentContext();
    	if (null == requestContext.get("serviceId")) return "";
    	return requestContext.get("serviceId").toString();
    }
}
