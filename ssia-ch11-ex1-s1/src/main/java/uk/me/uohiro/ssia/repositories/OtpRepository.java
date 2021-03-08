package uk.me.uohiro.ssia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.me.uohiro.ssia.entities.Otp;

public interface OtpRepository extends JpaRepository<Otp, String> {
	Optional<Otp> findOtpByUsername(String username);
}
