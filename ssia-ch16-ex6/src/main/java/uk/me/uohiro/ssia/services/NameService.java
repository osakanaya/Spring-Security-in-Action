package uk.me.uohiro.ssia.services;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class NameService {

	@Secured("ROLE_ADMIN")
	public String getName1() {
		return "Fantastico1";
	}
	
	@RolesAllowed("ROLE_ADMIN")
	public String getName2() {
		return "Fantastico2";
	}
	
}
