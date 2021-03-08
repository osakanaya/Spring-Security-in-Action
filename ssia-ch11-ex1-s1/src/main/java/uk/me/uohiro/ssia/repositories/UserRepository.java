package uk.me.uohiro.ssia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.me.uohiro.ssia.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findUserByUsername(String username);
}
