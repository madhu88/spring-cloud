package com.hystrix.service;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HystrixDemoService {
	
	@HystrixCommand(fallbackMethod="fallBackSayHello")
	public String sayHello(final String name) {
		System.out.println("Start of say hello");
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Able to print");
		return "{\"Hello\" : \"" + name + "\"}";
	}
	
	public String fallBackSayHello(final String name) {
		System.out.println("Start fallBackSayHello");		
		System.out.println("Able to print");
		return "{\"Hello\" : \" Looks like some issue please try later\"}";
	}
}
