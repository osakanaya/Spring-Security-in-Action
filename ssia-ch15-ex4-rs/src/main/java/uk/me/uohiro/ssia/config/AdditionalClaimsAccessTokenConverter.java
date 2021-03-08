package uk.me.uohiro.ssia.config;

import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class AdditionalClaimsAccessTokenConverter extends JwtAccessTokenConverter {

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> map) {

		OAuth2Authentication authentication = super.extractAuthentication(map);
		authentication.setDetails(map);
		
		return authentication;
	}

}
