package com.hjgj.aiyoujin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({@PropertySource(value = "classpath:config/config.properties"),
	@PropertySource(value = "file:/etc/hjgjconf/aiyoujin-server/config.properties", ignoreResourceNotFound = true)
})
//@ImportResource({"classpath:spring/dubbo-api.xml"})//"classpath:spring/schedule.xml",
public class ServerApplication extends SpringBootServletInitializer {
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(this);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServerApplication.class, args);
    }
    
}
