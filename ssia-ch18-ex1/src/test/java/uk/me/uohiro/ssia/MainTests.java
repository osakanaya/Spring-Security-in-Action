package uk.me.uohiro.ssia;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;

import uk.me.uohiro.ssia.entities.Workout;
import uk.me.uohiro.ssia.repositories.WorkoutRepository;
import uk.me.uohiro.ssia.service.WorkoutService;

@SpringBootTest
@EnableAutoConfiguration(
	exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
	}
)
public class MainTests {

	@Autowired
	private WorkoutService workoutService;
	
	@MockBean
	private WorkoutRepository workoutRepository;
	
	@Test
	public void testWorkoutServiceWithNoUser() {
		Workout w = new Workout();
		
		assertThrows(AuthenticationException.class, () -> workoutService.saveWorkout(w));
	}
	
	@Test
	@WithMockUser(username = "bill")
	public void testSaveWorkoutWorksOnlyForAuthenticatedUser() {
		Workout w = new Workout();
		w.setUser("bill");
		
		workoutService.saveWorkout(w);
	}
	
}
