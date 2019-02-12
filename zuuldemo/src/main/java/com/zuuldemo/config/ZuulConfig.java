package com.zuuldemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ZuulConfig {

	@Value("${organizationservice.v2.getname.url}")
	private String getNameURL;
	
	@Value("${organizationservice.v2.greet.url}")
	private String greetURL;

	public String getGetNameURL() {
		return getNameURL;
	}

	public String getGreetURL() {
		return greetURL;
	}
}
