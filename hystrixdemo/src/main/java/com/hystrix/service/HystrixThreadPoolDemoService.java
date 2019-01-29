package com.hystrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hystrix.config.HystrixDemoConfig;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class HystrixThreadPoolDemoService {

	@Autowired
	private HystrixDemoConfig hystrixDemoConfig;
	
	@HystrixCommand(fallbackMethod = "printThreadPoolDemoFallBack",
			threadPoolKey = "printThreadPoolDemoPool",
			threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "10"),
					@HystrixProperty(name = "maxQueueSize", value = "5")
				},
			commandProperties = {
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),	
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "25"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
					@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
				})
	public String printThreadPoolDemo(final String input) {
		return "{\"printThreadPoolDemo printing \":\"" + input + "\"}";
	}
	
	public String printThreadPoolDemoFallBack(final String input) {
		return "{\"printThreadPoolDemoFallBack printing \":\"" + input + "\"}";
	}
}
