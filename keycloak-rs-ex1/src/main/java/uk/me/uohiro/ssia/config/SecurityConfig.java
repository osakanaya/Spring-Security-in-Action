package uk.me.uohiro.ssia.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.mvcMatchers(HttpMethod.GET, "/hello").hasAuthority("SCOPE_hello")
			.mvcMatchers(HttpMethod.GET, "/list").hasAuthority("SCOPE_user")
			.mvcMatchers(HttpMethod.GET, "/").permitAll()
			.anyRequest().authenticated();
		
		http.oauth2ResourceServer().jwt();
	}
}
