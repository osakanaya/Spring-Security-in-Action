package uk.me.uohiro.ssia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uk.me.uohiro.ssia.entities.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Integer>{
	
	@Query("SELECT w FROM Workout w WHERE w.user = ?#{authentication.name}")
	List<Workout> findAllByUser();

}
