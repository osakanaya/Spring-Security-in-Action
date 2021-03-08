package uk.me.uohiro.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import uk.me.uohiro.ssia.csrf.CustomCsrfTokenRepository;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public CsrfTokenRepository customTokenRepository() {
		return new CustomCsrfTokenRepository();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf(c -> {
			String pattern = "/ciao";
			String httpMethod = HttpMethod.POST.name();
			RegexRequestMatcher matcher = new RegexRequestMatcher(pattern, httpMethod);
			c.ignoringRequestMatchers(matcher);
			
			c.csrfTokenRepository(customTokenRepository());
			
		});
		
		http.authorizeRequests().anyRequest().permitAll();
	}
}
