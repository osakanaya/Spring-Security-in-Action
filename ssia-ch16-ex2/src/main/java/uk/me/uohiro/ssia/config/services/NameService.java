package uk.me.uohiro.ssia.config.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class NameService {

	private Map<String, List<String>> secretNames = new HashMap<String, List<String>>();
	
	@PreAuthorize("#name == authentication.principal.username")
	public List<String> getSecretNames(String name) {
		return secretNames.get(name);
	}
	
	@PostConstruct
	public void populateSecretNames() {
		
		List<String> names1 = new ArrayList<String>();
		names1.add("Energico");
		names1.add("Perfeto");
		
		List<String> names2 = new ArrayList<String>();
		names2.add("Fantastico");
		
		secretNames.put("natalie", names1);
		secretNames.put("emma", names2);
	}
}
