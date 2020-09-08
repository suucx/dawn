package com._54year.dawn.auth.config;

import com._54year.dawn.core.enums.DawnBasicResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * 服务资源安全配置
 *
 * @author Andersen
 */
@Configuration
@EnableResourceServer
@Slf4j
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint((request, response, authException) -> {
					log.error(authException.getMessage(), authException);
					response.sendError(DawnBasicResultCode.UNAUTHORIZED.getCode(), DawnBasicResultCode.UNAUTHORIZED.getMessage());
				}
			)
			.and()
			.authorizeRequests()
			.antMatchers("/oauth/**", "test/**", "/user/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.httpBasic();
	}
}
