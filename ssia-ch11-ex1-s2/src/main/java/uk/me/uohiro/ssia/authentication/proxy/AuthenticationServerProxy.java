package uk.me.uohiro.ssia.authentication.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import uk.me.uohiro.ssia.authentication.model.User;

@Component
public class AuthenticationServerProxy {

	@Autowired
	private RestTemplate rest;
	
	@Value("${auth.server.base.url}")
	private String baseUrl;
	
	public void sendAuth(String username, String password) {
		String url = baseUrl + "/user/auth";
		
		User body = new User();
		body.setUsername(username);
		body.setPassword(password);
		
		HttpEntity<User> request = new HttpEntity<User>(body);
		
		rest.postForEntity(url, request, Void.class);
	}
	
	public boolean sendOTP(String username, String code) {
		String url = baseUrl + "/otp/check";
		
		User body = new User();
		body.setUsername(username);
		body.setCode(code);
		
		HttpEntity<User> request = new HttpEntity<User>(body);
		
		ResponseEntity<Void> response = rest.postForEntity(url, request, Void.class);
		
		return response.getStatusCode().equals(HttpStatus.OK);
	}
	
}
