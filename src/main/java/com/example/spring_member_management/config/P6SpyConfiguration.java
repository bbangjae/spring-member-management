package com.example.spring_member_management.config;

import com.example.spring_member_management.formatter.P6SpyFormatter;
import com.p6spy.engine.spy.P6SpyOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;


@Configuration
public class P6SpyConfiguration {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(P6SpyFormatter.class.getName());
    }
}
