package uk.me.uohiro.ssia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

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
	
	@SuppressWarnings("deprecation")
	private ClientRegistration clientRegistration() {
//		return ClientRegistration.withRegistrationId("github")
//					.clientId(clientId)
//					.clientSecret(clientSecret)
//					.scope(new String[] {"read:user"})
//					.authorizationUri("https://github.com/login/oauth/authorize")
//					.tokenUri("https://github.com/login/oauth/access_token")
//					.userInfoUri("https://api.github.com/user")
//					.userNameAttributeName("id")
//					.clientName("GitHub")
//					.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//					.redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
//					.build();
		
		return CommonOAuth2Provider.GITHUB
				.getBuilder("github")
				.clientId(clientId)
				.clientSecret(clientSecret)
				.build();

	}
	
	@Bean
	public ClientRegistrationRepository clientRepository() {
		ClientRegistration registration = clientRegistration();
		
		return new InMemoryClientRegistrationRepository(registration);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.oauth2Login();
		
		http.authorizeRequests().anyRequest().authenticated();
	}
}
