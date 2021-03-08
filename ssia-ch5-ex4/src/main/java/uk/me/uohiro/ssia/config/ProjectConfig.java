package uk.me.uohiro.ssia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import uk.me.uohiro.ssia.handlers.CustomAuthenticationFailureHandler;
import uk.me.uohiro.ssia.handlers.CustomAuthenticationSuccessHandler;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler authenticationFailureHandler;
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		
		UserDetails user1 = User.withUsername("john")
				.password("12345")
				.authorities("read")
				.build();
		
		UserDetails user2 = User.withUsername("mary")
				.password("12345")
				.authorities("write")
				.build();
		
		userDetailsService.createUser(user1);
		userDetailsService.createUser(user2);
		
		return userDetailsService;
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.formLogin().defaultSuccessUrl("/home", true);
		
		http.formLogin()
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler)
		.and()
			.httpBasic();
		
		http.authorizeRequests().anyRequest().authenticated();
	}

}
