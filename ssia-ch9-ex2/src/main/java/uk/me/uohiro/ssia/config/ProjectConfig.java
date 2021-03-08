package uk.me.uohiro.ssia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import uk.me.uohiro.ssia.filters.StaticKeyAuthenticationFilter;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private StaticKeyAuthenticationFilter filter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.addFilterAt(filter, BasicAuthenticationFilter.class)
			.authorizeRequests().anyRequest().permitAll();
	}
}
