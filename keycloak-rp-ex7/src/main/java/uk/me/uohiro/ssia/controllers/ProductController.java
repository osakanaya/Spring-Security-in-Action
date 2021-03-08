package uk.me.uohiro.ssia.controllers;

import java.net.URI;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import uk.me.uohiro.ssia.security.KeycloakService;

@Controller
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	private static final String baseUrl = "http://localhost:9091";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private KeycloakService keycloakService;
	
	@GetMapping("/hello")
	public String hello(OAuth2AuthenticationToken authentication, Model model, HttpServletRequest request) throws ServletException {
		
		String userId = authentication.getName();

		// アクセストークンの有効期限が満了していないことをチェックする（有効期限切れの場合は、リフレッシュトークンによる再発行を行う）
		try {
			keycloakService.updateAccessTokenIfPossible(authentication);
		} catch (Exception e) {
			// 例外がスローされた、ということは、リフレッシュトークンの有効期限も切れていることになる（＝Keycloakセッションが終了）
			logger.info("Refresh Token expired. The user is forced to log out.");
			
			// この場合は、強制ログアウトし、トップページに戻る
			request.logout();
			return "redirect:/";
		}
		
		// 呼び出し先のエンドポイントにトークンを渡す必要があるため、そのトークンの値を取得する
		OAuth2AccessToken accessToken = keycloakService.getAccessToken(userId);

		String accessTokenValue = accessToken.getTokenValue();
		logger.info(accessTokenValue);

		String url = baseUrl + "/hello";
		RequestEntity<Void> requestEntity = RequestEntity.get(URI.create(url))
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenValue)
				.build();

		String response = restTemplate.exchange(requestEntity, String.class).getBody();
		
		model.addAttribute("message", response);

		return "hello";
	}

	@GetMapping("/products")
	public String getProducts(OAuth2AuthenticationToken authentication, Model model, @AuthenticationPrincipal OAuth2User principal, HttpServletRequest request) throws Exception {

		logger.info(principal.toString());

		String userId = authentication.getName();

		// アクセストークンの有効期限が満了していないことをチェックする（有効期限切れの場合は、リフレッシュトークンによる再発行を行う）
		try {
			keycloakService.updateAccessTokenIfPossible(authentication);
		} catch (Exception e) {
			// 例外がスローされた、ということは、リフレッシュトークンの有効期限も切れていることになる（＝Keycloakセッションが終了）
			logger.info("Refresh Token expired. The user is forced to log out.");
			
			// この場合は、強制ログアウトし、トップページに戻る
			request.logout();
			return "redirect:/";
		}

		// 呼び出し先のエンドポイントにトークンを渡す必要があるため、そのトークンの値を取得する

		// 呼び出し先のエンドポイントにトークンを渡す必要があるため、そのトークンの値を取得する
		OAuth2AccessToken accessToken = keycloakService.getAccessToken(userId);

		String accessTokenValue = accessToken.getTokenValue();
		logger.info(accessTokenValue);

		String url = baseUrl + "/list";
		RequestEntity<Void> requestEntity = RequestEntity.get(URI.create(url))
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenValue)
				.build();

		List<String> products = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<String>>() {}).getBody();

		model.addAttribute("products", products);

		return "products";
	}
	
}
