package uk.me.uohiro.ssia.security;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class CustomSessionDestroyedEventListener implements ApplicationListener<SessionDestroyedEvent> {

	@Autowired
	private KeycloakService keycloakService;

	// ログアウトしたとき、セッションタイムアウトしたときのイベントリスナを実装する（Keycloakのログアウトエンドポイントにアクセスし、セッションを終了する処理を付加する）
	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {

		final Stream<String> users = event
				.getSecurityContexts()
				.stream()
				.map(SecurityContext::getAuthentication)
				.filter(OAuth2AuthenticationToken.class::isInstance)
				.map(OAuth2AuthenticationToken.class::cast)
				.map(OAuth2AuthenticationToken::getPrincipal)
				.map(OAuth2User::getName)
				.distinct();

		users.forEach(keycloakService::logout);
	}
}
