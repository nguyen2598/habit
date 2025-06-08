package com.habittracke.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;

import java.util.Locale;

@Configuration
public class I18nConfig {

    // Bean để đọc các file messages_*.properties
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/language/lang"); // tên file message
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);// trong TH ko tim thay ma dc dinh nghia se tra ve thong diep mac dinh
        // vi du : ko tim thay error.unknown trong file lang thi se tra ve "error.unknown"
        return messageSource;
    }

    // Bean để lưu locale của user trong session
    @Bean
    public LocaleContextResolver localeContextResolver() {
        var resolver = new AcceptHeaderLocaleContextResolver();
        resolver.setDefaultLocale(Locale.forLanguageTag("vi"));// ngon ngu mac dinh
        return resolver;
    }
}
