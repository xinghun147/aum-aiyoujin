package com.hjgj.aiyoujin.admin.config;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@EnableCasClient
@PropertySources({@PropertySource(value = "classpath:config/config.properties"),
        @PropertySource(value = "file:/etc/hjgjconf/aiyoujin-admin/config.properties", ignoreResourceNotFound = true)
})
@ImportResource({"classpath:spring/dubbo-api.xml"})
public class BootConfig {
}
