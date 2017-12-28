package com.hjgj.aiyoujin.config;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

import net.unicon.cas.client.configuration.CasClientConfigurerAdapter;
import net.unicon.cas.client.configuration.EnableCasClient;

@Configuration
@EnableCasClient
public class CasConfig extends CasClientConfigurerAdapter{
//	    public void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
//	        super.configureAuthenticationFilter(authenticationFilter);
//	        authenticationFilter.getInitParameters().put("authenticationRedirectStrategyClass", "com.patterncat.CustomAuthRedirectStrategy");
//	    }
    void configureValidationFilter(FilterRegistrationBean validationFilter) {           
        validationFilter.getInitParameters().put("millisBetweenCleanUps", "120000");
    }        
    void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
        authenticationFilter.getInitParameters().put("artifactParameterName", "casTicket");
        authenticationFilter.getInitParameters().put("serviceParameterName", "targetService");
    } 
}
