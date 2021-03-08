package uk.me.uohiro.ssia.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.me.uohiro.ssia.entities.Otp;
import uk.me.uohiro.ssia.entities.User;
import uk.me.uohiro.ssia.repositories.OtpRepository;
import uk.me.uohiro.ssia.repositories.UserRepository;
import uk.me.uohiro.ssia.utils.GenerateCodeUtil;

@Service
@Transactional
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OtpRepository otpRepository;
	
	public void addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public void auth(User user) {
		Optional<User> optional = userRepository.findUserByUsername(user.getUsername());
		
		if (optional.isPresent()) {
			User registered = optional.get();
			
			if (passwordEncoder.matches(user.getPassword(), registered.getPassword())) {
				renewOtp(registered);
			} else {
				throw new BadCredentialsException("Bad credentials.");
			}
		} else {
			throw new BadCredentialsException("Bad credentials.");
		}
	}
	
	public boolean check(Otp otpToValidate) {
		Optional<Otp> optional = otpRepository.findOtpByUsername(otpToValidate.getUsername());
		
		if (optional.isPresent()) {
			Otp otp = optional.get();
			if (otpToValidate.getCode().equals(otp.getCode())) {
				return true;
			}
		} 
		
		return false;
	}

	private void renewOtp(User user) {
		
		String code = GenerateCodeUtil.generateCode();
		
		Optional<Otp> userOtp = otpRepository.findOtpByUsername(user.getUsername());
		if (userOtp.isPresent()) {
			Otp otp = userOtp.get();
			otp.setCode(code);
		} else {
			Otp otp = new Otp();
			otp.setUsername(user.getUsername());
			otp.setCode(code);
			
			otpRepository.save(otp);
		}
	}
}
