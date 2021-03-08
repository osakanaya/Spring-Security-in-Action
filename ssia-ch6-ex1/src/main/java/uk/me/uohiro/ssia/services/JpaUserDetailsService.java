package uk.me.uohiro.ssia.services;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import uk.me.uohiro.ssia.entities.User;
import uk.me.uohiro.ssia.model.CustomUserDetails;
import uk.me.uohiro.ssia.repositories.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Supplier<UsernameNotFoundException> s = 
				() -> new UsernameNotFoundException("Problem during authentication!");
				
		User u = userRepository.findUserByUsername(username).orElseThrow(s);
		
		return new CustomUserDetails(u);
	}

}
