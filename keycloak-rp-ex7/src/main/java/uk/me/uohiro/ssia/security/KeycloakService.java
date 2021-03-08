package uk.me.uohiro.ssia.security;

import java.net.URI;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.DefaultRefreshTokenTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2RefreshTokenGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KeycloakService {

	private static final Logger logger = LoggerFactory.getLogger(KeycloakService.class);
	
	// 汚いけどハードコードする（spring.security.oauth2.client.registration配下のキーに相当）
	private static final String CLIENT_REGISTRTION_ID = "keycloak-dev";
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	public OAuth2AuthorizedClient getAuthorizedClient(String clientRegistrationId, String userId) {
		return this.authorizedClientService
				.loadAuthorizedClient(clientRegistrationId, userId);
	}
	
	public OAuth2RefreshToken getRefreshToken(String userId) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(CLIENT_REGISTRTION_ID, userId);
		
        return authorizedClient.getRefreshToken();
	}
	
	public OAuth2AccessToken getAccessToken(String userId) {
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(CLIENT_REGISTRTION_ID, userId);
		
        return authorizedClient.getAccessToken();
	}
	
	public void updateAccessTokenIfPossible(Authentication authentication) {
		
		String userId = authentication.getName();
		
		// アクセストークンの期限切れをチェックする
		if (isAccessTokenExpired(userId)) {
			// アクセストークンは期限切れ→リフレッシュトークンを使ってアクセストークンの再取得を実行
			OAuth2AccessTokenResponse tokenResponse = getRefreshedToken(userId);

			// 既存のアクセストークンを削除する
			logger.info("Trying to delete existing authorizedClient...");

			OAuth2AuthorizedClient currentAuthorizedClient 
	        	= this.getAuthorizedClient(CLIENT_REGISTRTION_ID, userId);
	        
			ClientRegistration clientRegistration = currentAuthorizedClient.getClientRegistration();

			authorizedClientService.removeAuthorizedClient(CLIENT_REGISTRTION_ID, userId);
			
			// 取得したアクセストークンを保存する
			logger.info("Trying to save new authorizedClient...");

			OAuth2AuthorizedClient newAuthorizedClient = new OAuth2AuthorizedClient(
					clientRegistration, userId,
				    tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());
			
	        authorizedClientService.saveAuthorizedClient(newAuthorizedClient, authentication);
		}
	}
	
	private OAuth2AccessTokenResponse getRefreshedToken(String userId) {
		
		logger.info("Trying to get new AccessToken using RefeshToken...");

		OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(CLIENT_REGISTRTION_ID, userId);

        OAuth2RefreshTokenGrantRequest tokenRequest =
        	    new OAuth2RefreshTokenGrantRequest(authorizedClient.getClientRegistration(),
        	    		authorizedClient.getAccessToken(),
        	    		authorizedClient.getRefreshToken());
        
		DefaultRefreshTokenTokenResponseClient client = new DefaultRefreshTokenTokenResponseClient();
		
		return client.getTokenResponse(tokenRequest);
	}
	
	private boolean isAccessTokenExpired(String userId) {
		
		OAuth2AccessToken accessToken = this.getAccessToken(userId);

		Instant now = Instant.now();
		
		logger.info("Access Token Expires At: " + accessToken.getExpiresAt().toString() + ", Now: " + now.toString());
		
		if (accessToken.getExpiresAt().isBefore(Instant.now())) {
			logger.info("Access Token expired.");
			return true;
		}
		
		// アプリケーションのリクエスト処理に5秒かかると想定
		// ※ハードコードして汚いけど
		if (accessToken.getExpiresAt().isBefore(Instant.now().plusSeconds(5L))) {
			logger.info("Access Token is expected to expire.");
			return true;
		}
		
		return false;
	}
	
	public void logout(String userId) {

        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(CLIENT_REGISTRTION_ID, userId);

		/*
		 * Keycloakに対してセッションの終了（ログアウト）を要求する
		 */

        // ログアウトエンドポイント
        // ハードコードして汚いけど、暫定的に
    	String logoutEndpointUri = "http://localhost:8080/auth/realms/master/protocol/openid-connect/logout";

        String clientId = authorizedClient.getClientRegistration().getClientId();
        String clientSecret = authorizedClient.getClientRegistration().getClientSecret();
        String refreshTokenValue = getRefreshToken(userId).getTokenValue();

        logger.info(logoutEndpointUri);
        logger.info(clientId);
        logger.info(clientSecret);
        logger.info(refreshTokenValue);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("refresh_token", refreshTokenValue);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        
        RequestEntity<MultiValueMap<String, String>> requestEntity
        	= new RequestEntity<>(params, headers, HttpMethod.POST, URI.create(logoutEndpointUri));

        restTemplate.exchange(requestEntity, String.class);
        
        /*
         * クライアントが管理しているトークンを破棄する
         */
        authorizedClientService.removeAuthorizedClient(CLIENT_REGISTRTION_ID, userId);

	}
	
}
