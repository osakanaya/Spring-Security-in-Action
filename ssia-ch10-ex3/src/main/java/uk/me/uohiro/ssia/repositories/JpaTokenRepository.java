package uk.me.uohiro.ssia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.me.uohiro.ssia.entities.Token;

public interface JpaTokenRepository extends JpaRepository<Token, Integer> {
	
	Optional<Token> findTokenByIdentifier(String identifier);
	
}
