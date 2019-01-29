package com.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hystrix.service.HystrixDemoService;
import com.hystrix.service.HystrixThreadPoolDemoService;

@RestController
@RequestMapping(value="/greet")
public class HystrixDemoController {
	
	@Autowired
	private HystrixDemoService hystrixDemoService;
	
	@Autowired
	private HystrixThreadPoolDemoService hystrixThreadPoolDemoService;
	
	@GetMapping(value="/user/{name}", produces=MediaType.APPLICATION_JSON_VALUE)
	public String greetHello(@PathVariable("name") String name) {
		System.out.println("Start of greetHello");
		return hystrixDemoService.sayHello(name);
	}
	
	@GetMapping(value="/printinput/{input}", produces=MediaType.APPLICATION_JSON_VALUE)
	public String printThreadPoolDemo(@PathVariable("input") String input) {
		System.out.println("Start of printThreadPoolDemo");
		return hystrixThreadPoolDemoService.printThreadPoolDemo(input);
	}

}
