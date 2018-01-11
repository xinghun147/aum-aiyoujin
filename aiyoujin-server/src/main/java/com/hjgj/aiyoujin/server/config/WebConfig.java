package com.hjgj.aiyoujin.server.config;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.base.Charsets;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringHttpMessageConverter=new StringHttpMessageConverter(Charsets.UTF_8);
		stringHttpMessageConverter.setWriteAcceptCharset(false);
		converters.add(stringHttpMessageConverter);
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter=new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig=new FastJsonConfig();
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		converters.add(fastJsonHttpMessageConverter);
		super.configureMessageConverters(converters);
	}
    
//    
//    @Bean
//    public FilterRegistrationBean adminPjaxFilter(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();  
//        filterRegistrationBean.setFilter(new OncePerRequestFilter() {
//			protected void doFilterInternal(HttpServletRequest request,
//					HttpServletResponse response, FilterChain filterChain)
//					throws ServletException, IOException {
//				boolean isPjax=false;
//				String pjaxHeader=request.getHeader("X-PJAX");
//				if(StringUtils.equals(pjaxHeader, "true")){
//					isPjax=true;
//				}else{
//					String pjaxParameter=request.getParameter("_pjax");
//					if(StringUtils.isNotBlank(pjaxParameter)){
//						isPjax=true;
//					}
//				}
//				request.setAttribute("_isPjax", isPjax);
//                filterChain.doFilter(request, response);
//			}
//		});
//        filterRegistrationBean.setEnabled(true);
//        filterRegistrationBean.addUrlPatterns("/admin/*");
//        return filterRegistrationBean;
//    }
//    
}
