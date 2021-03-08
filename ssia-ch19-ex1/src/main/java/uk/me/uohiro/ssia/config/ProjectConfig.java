package uk.me.uohiro.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ProjectConfig {

	@Bean
	public ReactiveUserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("john")
				.password("12345")
				.authorities("read")
				.build();
		
		MapReactiveUserDetailsService service = new MapReactiveUserDetailsService(user);
		
		return service;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
}
