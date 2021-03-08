package uk.me.uohiro.ssia.authentication.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import uk.me.uohiro.ssia.authentication.OtpAuthentication;
import uk.me.uohiro.ssia.authentication.UsernamePasswordAuthentication;

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationManager manager;
	
	@Value("${jwt.signing.key}")
	private String signingKey;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String username = request.getHeader("username");
		String password = request.getHeader("password");
		String code = request.getHeader("code");
		
		if (code == null) {
			Authentication a = new UsernamePasswordAuthentication(username, password);
			manager.authenticate(a);
		} else {
			Authentication a = new OtpAuthentication(username, code);
			a = manager.authenticate(a);
			
			SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
			
			Map<String, String> claims = new HashMap<>();
			claims.put("username", username);
			
			String jwt = Jwts.builder().setClaims(claims).signWith(key).compact();
			
			response.setHeader("Authorization", jwt);
		}

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return !request.getServletPath().contentEquals("/login");
	}
}
