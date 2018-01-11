package com.hjgj.aiyoujin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import net.unicon.cas.client.configuration.EnableCasClient;
@SpringBootApplication
@EnableCasClient
@PropertySources({@PropertySource(value = "classpath:config/config.properties"),
	@PropertySource(value = "file:/etc.hjgj.aiyoujin.admin.conf/admin/config.properties", ignoreResourceNotFound = true)
})
@ImportResource({"classpath:spring/dubbo-api.xml"})
public class AdminApplication extends SpringBootServletInitializer {
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(this);
    }
	
//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer() {
//	   return (container -> {
//	        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//	        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//	        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//
//	        container.addErrorPages(error401Page, error404Page, error500Page);
//	   });
//	}

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdminApplication.class, args);
    }
    
}
