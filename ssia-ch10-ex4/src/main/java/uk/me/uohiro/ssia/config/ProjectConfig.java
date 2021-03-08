package uk.me.uohiro.ssia.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors(c -> {
			CorsConfigurationSource source = request -> {
				CorsConfiguration config = new CorsConfiguration();

				List<String> allowedOrigins = new ArrayList<>();
				allowedOrigins.add("http://localhost:8080");
				config.setAllowedOrigins(allowedOrigins);

				List<String> allowedMethods = new ArrayList<>();
				allowedMethods.add("GET");
				allowedMethods.add("POST");
				allowedMethods.add("PUT");
				allowedMethods.add("DELETE");
				config.setAllowedMethods(allowedMethods);
				
				return config;
			};
			
			c.configurationSource(source);
		});
		
		http.csrf().disable();
		
		http.authorizeRequests().anyRequest().permitAll();
	}
}
