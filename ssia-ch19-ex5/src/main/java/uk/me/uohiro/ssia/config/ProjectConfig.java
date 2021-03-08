package uk.me.uohiro.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableReactiveMethodSecurity
public class ProjectConfig {

	@Bean
	public ReactiveUserDetailsService userDetailsService() {
		UserDetails user1 = User.withUsername("john")
				.password("12345")
				.roles("ADMIN")
				.build();

		UserDetails user2 = User.withUsername("bill")
				.password("12345")
				.roles("REGULAR_MANAGER")
				.build();
		
		MapReactiveUserDetailsService service = new MapReactiveUserDetailsService(user1, user2);
		
		return service;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
