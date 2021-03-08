package uk.me.uohiro.ssia;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import uk.me.uohiro.ssia.entities.Authority;
import uk.me.uohiro.ssia.entities.User;
import uk.me.uohiro.ssia.entities.enums.EncryptionAlgorithm;
import uk.me.uohiro.ssia.repositories.ProductRepository;
import uk.me.uohiro.ssia.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(
	exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
	}
)
class MainTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private SCryptPasswordEncoder sCryptPasswordEncoder;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private ProductRepository productRepository;
	
	@Test
	public void loggingInWithWrongUser() throws Exception {
		mvc.perform(formLogin()).andExpect(unauthenticated());
	}
	
	@Test
	@WithMockUser(username = "mary", password = "12345")
	public void skipAuthenticationTest() throws Exception {
		mvc.perform(get("/main"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Hello, mary!")));
	}
	
	@Test
	public void testAuthenticationwithValidUser_BCRYPT() throws Exception {
		User mockUser = new User();
		mockUser.setUsername("mary");
		mockUser.setPassword(bCryptPasswordEncoder.encode("12345"));
		mockUser.setAlgorithm(EncryptionAlgorithm.BCRYPT);
		
		Authority authority = new Authority();
		authority.setName("read");
		authority.setUser(mockUser);
		
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		mockUser.setAuthorities(authorities);
		
		when(userRepository.findUserByUsername("mary")).thenReturn(Optional.of(mockUser));
		
		mvc.perform(formLogin().user("mary").password("12345"))
			.andExpect(authenticated());
		
	}
	
	@Test
	public void testAuthenticationwithValidUser_SCRYPT() throws Exception {
		User mockUser = new User();
		mockUser.setUsername("mary");
		mockUser.setPassword(sCryptPasswordEncoder.encode("12345"));
		mockUser.setAlgorithm(EncryptionAlgorithm.SCRYPT);
		
		Authority authority = new Authority();
		authority.setName("read");
		authority.setUser(mockUser);
		
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		mockUser.setAuthorities(authorities);
		
		when(userRepository.findUserByUsername("mary")).thenReturn(Optional.of(mockUser));
		
		mvc.perform(formLogin().user("mary").password("12345"))
			.andExpect(authenticated());
		
	}

	@Test
	public void testAuthenticationWithInexistentUser() throws Exception {
		
		when(userRepository.findUserByUsername("mary"))
			.thenReturn(Optional.empty());
		
		mvc.perform(formLogin().user("mary").password("12345"))
			.andExpect(unauthenticated());
	}
	
	@Test
	public void testAuthenticationWithInvalidPassword() throws Exception {
		User mockUser = new User();
		mockUser.setUsername("mary");
		mockUser.setPassword(bCryptPasswordEncoder.encode("55555"));
		mockUser.setAlgorithm(EncryptionAlgorithm.BCRYPT);
		
		Authority authority = new Authority();
		authority.setName("read");
		authority.setUser(mockUser);
		
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		mockUser.setAuthorities(authorities);
		
		when(userRepository.findUserByUsername("mary")).thenReturn(Optional.of(mockUser));
		
		mvc.perform(formLogin().user("mary").password("12345"))
			.andExpect(unauthenticated());
	}
	
}
