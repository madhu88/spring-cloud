package com.hystrix.utils;

import org.springframework.util.Assert;

public class UserContextHolder {

	private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();
	
	public static final UserContext getContext() {
		UserContext context = userContext.get();
		
		if (null == context) {
			context = createEmptyContext();
			userContext.set(context);			
		}
		
		return context;
	}
	
	public static final void setContext(UserContext context) {
		Assert.notNull(context, "Only non-null UserContext instances are permitted");
		userContext.set(context);
	}
	
	private static final UserContext createEmptyContext() {
		return new UserContext();
	}
}
