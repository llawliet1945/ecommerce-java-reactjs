package com.myusufalpian.projectecommerce.securities;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface WebSecurityConfiguration {
    void configure(HttpSecurity httpSecurity) throws Exception;
}
