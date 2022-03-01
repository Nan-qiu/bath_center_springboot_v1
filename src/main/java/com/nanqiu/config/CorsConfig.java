package com.nanqiu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedOrigin("http://localhost:8080/");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",config);
        return  new CorsFilter(configurationSource);
    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**") //所有接口
//                .allowedOrigins("*") //允许跨域的域名，可以用*表示允许任何域名使用
//                .allowedHeaders("*") //允许任何请求头
//                //.exposedHeaders("token") //允许自定义请求头
//                .allowedMethods("*") //允许如何方法
//                //.allowCredentials(true) //带上cookie
//                .maxAge(3600); //表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
//    }
}
