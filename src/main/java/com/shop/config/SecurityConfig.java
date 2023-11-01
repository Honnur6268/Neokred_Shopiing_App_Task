package com.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.shop.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String URL = "/api/shop";
	private static final String[] PUBLIC_URL = { URL + "/products", URL + "/customers", URL + "/cart", "/v3/**",
			"/swagger-ui/**" };

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((request) -> request.requestMatchers(PUBLIC_URL).permitAll()
				.requestMatchers(HttpMethod.POST, "/placeorder").hasAnyRole("USER", "ADMIN").anyRequest()
				.authenticated());
		http.formLogin();
		http.httpBasic();

		http.csrf().disable();

		return http.build();
	}

	@Autowired
	public void configureUsers(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

}
