package com.licensesservice;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.licensesservice.interceptor.UserContextInterceptor;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LicensesserviceApplication {
	
	 private static final Logger logger = LoggerFactory.getLogger(LicensesserviceApplication.class);
	
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (null != interceptors) {
			interceptors.add(new UserContextInterceptor());
			restTemplate.setInterceptors(interceptors);
		} else {
			restTemplate.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		}
		logger.debug("Added the interseptor");
		return restTemplate;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LicensesserviceApplication.class, args);
	}

}

