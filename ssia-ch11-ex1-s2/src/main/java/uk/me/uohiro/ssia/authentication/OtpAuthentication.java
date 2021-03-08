package uk.me.uohiro.ssia.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class OtpAuthentication extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	public OtpAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	public OtpAuthentication(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
}
