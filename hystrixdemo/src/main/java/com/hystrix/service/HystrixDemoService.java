package com.hystrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hystrix.config.HystrixDemoConfig;
import com.hystrix.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class HystrixDemoService {
	
	@Autowired
	private HystrixDemoConfig hystrixDemoService;
	
	//the default timeout value for hystrix thread wait is 1000ms
	//@HystrixCommand(fallbackMethod="fallBackSayHello")
	@HystrixCommand(fallbackMethod="fallBackSayHello", 
			commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds" , value = "2000")})
	public String sayHello(final String name) {
		System.out.println("Start of say hello");
		System.out.println("HystrixDemoService tmx-correlation-id = " + UserContextHolder.getContext().getCorrelationId());
		System.out.println("ThreadWaitTime = " +  hystrixDemoService.getThreadWaitTime());
		try {
			Thread.sleep(hystrixDemoService.getThreadWaitTime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Able to print");
		return "{\"Hello\" : \"" + name + "\"}";
	}
	
	public String fallBackSayHello(final String name) {
		System.out.println("Start fallBackSayHello");
		System.out.println("HystrixDemoService tmx-correlation-id = " + UserContextHolder.getContext().getCorrelationId());
		System.out.println("Able to print");
		return "{\"Hello\" : \" Looks like some issue please try later\"}";
	}
}
