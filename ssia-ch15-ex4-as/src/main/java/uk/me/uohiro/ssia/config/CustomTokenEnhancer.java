package uk.me.uohiro.ssia.config;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
		
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("generatedInZone", ZoneId.systemDefault().toString());
		
		token.setAdditionalInformation(info);
		
		return token;
	}

}
