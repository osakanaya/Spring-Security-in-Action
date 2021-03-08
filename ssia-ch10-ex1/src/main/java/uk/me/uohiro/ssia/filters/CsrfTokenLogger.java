package uk.me.uohiro.ssia.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;

public class CsrfTokenLogger implements Filter {

	private static Logger logger = LoggerFactory.getLogger(CsrfTokenLogger.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		Object o = request.getAttribute("_csrf");
		CsrfToken token = (CsrfToken)o;
		
		logger.info("CSRF token " + token.getToken());
		
		chain.doFilter(request, response);
	}
}
