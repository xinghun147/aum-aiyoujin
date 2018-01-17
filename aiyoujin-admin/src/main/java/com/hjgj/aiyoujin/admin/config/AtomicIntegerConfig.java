package com.hjgj.aiyoujin.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class AtomicIntegerConfig {

    @Bean
    public AtomicInteger atomicIntegerBean() {
        return new AtomicInteger(0);
    }
}
