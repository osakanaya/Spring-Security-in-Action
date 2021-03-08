package uk.me.uohiro.ssia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	public ProjectConfig(
		@Value("${myoauth2app.client_id}") String clientId,
		@Value("${myoauth2app.client_secret}") String clientSecret) {
		
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	
	private String clientId;
	private String clientSecret;
	
	private ClientRegistration clientRegistration() {
		return CommonOAuth2Provider.GITHUB
				.getBuilder("github")
				.clientId(clientId)
				.clientSecret(clientSecret)
				.build();

	}
	
	private ClientRegistrationRepository clientRepository() {
		ClientRegistration registration = clientRegistration();
		
		return new InMemoryClientRegistrationRepository(registration);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.oauth2Login(c -> {
			c.clientRegistrationRepository(clientRepository());
		});
		
		http.authorizeRequests().anyRequest().authenticated();
	}
}
