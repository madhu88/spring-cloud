package com.licensesservice.interceptor;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.licensesservice.utils.UserContext;
import com.licensesservice.utils.UserContextHolder;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		HttpHeaders httpHeaders = request.getHeaders();
		
		httpHeaders.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
		httpHeaders.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());
		httpHeaders.add(UserContext.AUTHORIZATION, UserContextHolder.getContext().getAuthorization());
		
		return execution.execute(request, body);
	}	

}
