package com.licensesservice;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
/*import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;*/
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.ClientHttpRequestInterceptor;
/*import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;*/
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import com.licensesservice.config.LicensesServiceConfig;
/*import com.licensesservice.events.model.OrganizationChangeModel;*/
import com.licensesservice.interceptor.UserContextInterceptor;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableResourceServer
public class LicensesserviceApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(LicensesserviceApplication.class);
	
	@Autowired
	private LicensesServiceConfig licensesServiceConfig;
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {		
		RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
		redisConfiguration.setHostName(licensesServiceConfig.getRedisServer());
		redisConfiguration.setPort(licensesServiceConfig.getRedisPort());
		return new JedisConnectionFactory(redisConfiguration);
	}
	
	@Bean
	public RedisTemplate<String, Object> getRedisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}		
	
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
	
	/*@Bean
	public OAuth2ClientContext oauth2ClientContext() {
		return new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());
	}
	
	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
			OAuth2ProtectedResourceDetails details) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.getAuthentication().getName();
		logger.debug("**** name " + securityContext.getAuthentication().getName());
		logger.debug("**** creds " + securityContext.getAuthentication().getCredentials());
		return new OAuth2RestTemplate(details, oauth2ClientContext);
	}*/
	
	public static void main(String[] args) {
		SpringApplication.run(LicensesserviceApplication.class, args);
	}

}

