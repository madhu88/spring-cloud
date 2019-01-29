package com.hystrix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class HystrixDemoConfig {

	@Value("${thread.wait.time.ms}")
	private Long threadWaitTime;
	
	@Value("${printThreadPoolDemoPool.coreSize}")
	private String printThreadPoolDemoPool_coreSize;
	
	@Value("${printThreadPoolDemoPool.maxQueueSize}")
	private String printThreadPoolDemoPool_maxQueueSize;

	public String getPrintThreadPoolDemoPool_coreSize() {
		return printThreadPoolDemoPool_coreSize;
	}

	public String getPrintThreadPoolDemoPool_maxQueueSize() {
		return printThreadPoolDemoPool_maxQueueSize;
	}
	
	public Long getThreadWaitTime() {
		return threadWaitTime;
	}		
}
