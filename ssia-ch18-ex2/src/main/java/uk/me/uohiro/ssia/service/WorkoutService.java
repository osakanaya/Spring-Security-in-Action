package uk.me.uohiro.ssia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import uk.me.uohiro.ssia.entities.Workout;
import uk.me.uohiro.ssia.repositories.WorkoutRepository;

@Service
public class WorkoutService {

	@Autowired
	private WorkoutRepository workoutRepository;

	@PreAuthorize("#workout.user == authentication.name and #oauth2.hasScope('fitnessapp')")
	public void saveWorkout(Workout workout) {
		workoutRepository.save(workout);
	}
	
	public List<Workout> findWorkouts() {
		return workoutRepository.findAllByUser();
	}
	
	public void deleteWorkout(Integer id) {
		workoutRepository.deleteById(id);
	}
}
