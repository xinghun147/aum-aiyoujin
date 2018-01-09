package com.hjgj.aiyoujin.core.config;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AtomicIntegerConfig {

	@Bean
	public AtomicInteger atomicIntegerBean(){
		return new AtomicInteger(0);
	}
}
