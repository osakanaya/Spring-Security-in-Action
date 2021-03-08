package uk.me.uohiro.ssia.csrf;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import uk.me.uohiro.ssia.entities.Token;
import uk.me.uohiro.ssia.repositories.JpaTokenRepository;

public class CustomCsrfTokenRepository implements CsrfTokenRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomCsrfTokenRepository.class);
	
	@Autowired
	private JpaTokenRepository jpaTokenRepository;
	
	@Override
	public CsrfToken generateToken(HttpServletRequest request) {
		String uuid = UUID.randomUUID().toString();
		
		logger.info("Generating CSRF token: " + uuid);
		
		return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
	}

	@Override
	public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
		String identifier = request.getHeader("X-IDENTIFIER");
		
		Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);
		
		if (existingToken.isPresent()) {
			
			logger.info("CSRF token found for identifider: " + identifier + ", so update it");
			
			Token token = existingToken.get();
			token.setToken(csrfToken.getToken());
		} else {
			logger.info("CSRF token not found for identifider: " + identifier + ", so create it");

			Token token = new Token();
			token.setToken(csrfToken.getToken());
			token.setIdentifier(identifier);
			
			jpaTokenRepository.save(token);
		}
	}

	@Override
	public CsrfToken loadToken(HttpServletRequest request) {
		String identifier = request.getHeader("X-IDENTIFIER");

		logger.info("Loading CSRF token for identifider: " + identifier);

		Optional<Token> existingToken = jpaTokenRepository.findTokenByIdentifier(identifier);
		
		if (existingToken.isPresent()) {
			logger.info("CSRF token found for identifider: " + identifier);

			Token token = existingToken.get();
			
			return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
		}

		logger.info("CSRF token not found for identifider: " + identifier);

		return null;
	}
}
