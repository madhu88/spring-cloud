package com.hystrix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class HystrixDemoConfig {

	@Value("${thread.wait.time.ms}")
	private Long threadWaitTime;

	public Long getThreadWaitTime() {
		return threadWaitTime;
	}
		
}
