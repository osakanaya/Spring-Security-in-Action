package uk.me.uohiro.ssia.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationLoggingFilter implements Filter {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationLoggingFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		String requestId = httpRequest.getHeader("Request-Id");
		
		logger.info("Successfully authenticated request with id " + requestId);
		
		chain.doFilter(request, response);
	}

}
