package com.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hystrix.service.HystrixDemoService;

@RestController
@RequestMapping(value="/greet")
public class HystrixDemoController {
	
	@Autowired
	private HystrixDemoService hystrixDemoService;
	
	@GetMapping(value="/user/{name}", produces=MediaType.APPLICATION_JSON_VALUE)
	public String greetHello(@PathVariable("name") String name) {
	System.out.println("Start of greetHello");
		return hystrixDemoService.sayHello(name);
	}

}
