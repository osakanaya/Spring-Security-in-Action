package uk.me.uohiro.ssia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.me.uohiro.ssia.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findUserByUsername(String u);

}
