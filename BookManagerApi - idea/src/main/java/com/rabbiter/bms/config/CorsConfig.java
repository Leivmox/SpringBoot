package com.rabbiter.bms.config;

import com.rabbiter.bms.utils.PathUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//Spring Boot 配置类，用于配置跨域资源共享（CORS, Cross-Origin Resource Sharing）。
// CORS 是一种机制，用来允许浏览器的客户端从不同源的服务器请求资源。
// 在默认情况下，浏览器会阻止跨域请求，因此 CORS 配置对于前后端分离的应用非常重要，因为它允许浏览器从不同的域名访问服务器上的资源。
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
                .allowedOriginPatterns("*")
                //放行哪些请求方式
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //.allowedMethods("*") //或者放行全部
                //暴露哪些原始请求头部信息
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        用于将本地文件系统的路径映射为 URL 路径，使 Spring Boot 可以通过 HTTP 请求访问指定目录下的静态资源文件。
//        具体来说，它为应用的静态资源（例如图片或文件）提供一个外部访问路径。
        String winPath = PathUtils.getClassLoadRootPath() + "/src/main/resources/static/files/";

        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/files/**").
                addResourceLocations("file:" + winPath);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
