package com.walther.microservices.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class ResourceMessageConfiguration implements WebMvcConfigurer {

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(("classpath:messages"));
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
    @Bean
    public SessionLocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver= new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return  lci;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
    }
}
