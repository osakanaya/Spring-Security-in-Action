package uk.me.uohiro.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class UserManagementConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		
		UserDetails user = User.withUsername("john")
				.password("12345")
				.authorities("read")
				.build();
		
		userDetailsService.createUser(user);
		
		return userDetailsService;
	}
	
	@SuppressWarnings("deprecation")
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
