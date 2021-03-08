package uk.me.uohiro.ssia.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class SecurityContextFactory implements WithSecurityContextFactory<MockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(MockCustomUser annotation) {
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("read"));
		
		String clientId = "client_id";
		
		Map<String, Object> attributes = new HashMap<>();
		attributes.put(clientId, clientId);
		
		OAuth2User principal = new DefaultOAuth2User(authorities, attributes, clientId);
		
		Authentication a = new OAuth2AuthenticationToken(principal, authorities, clientId);
		context.setAuthentication(a);

		return context;
	}
}
