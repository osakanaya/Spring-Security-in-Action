package uk.me.uohiro.ssia.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationLoggingFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationLoggingFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestId = request.getHeader("Request-Id");
		
		logger.info("Successfully authenticated request with id " + requestId);
		
		filterChain.doFilter(request, response);
		
	}

}
