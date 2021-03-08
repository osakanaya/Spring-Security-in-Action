package uk.me.uohiro.ssia.config.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import uk.me.uohiro.ssia.model.Employee;

@Service
public class BookService {

	private Map<String, Employee> records = new HashMap<String, Employee>();
	
	@PostAuthorize("returnObject.roles.contains('reader')")
	public Employee getBookDetails(String name) {
		return records.get(name);
	}
	
	@PostConstruct
	public void populateRecords() {
		List<String> emp1_books = new ArrayList<String>();
		emp1_books.add("Karamazov Brothers");
		
		List<String> emp1_roles = new ArrayList<String>();
		emp1_roles.add("accountant");
		emp1_roles.add("reader");
		
		Employee emp1 = new Employee("Emma Thompson", emp1_books, emp1_roles);
		
		List<String> emp2_books = new ArrayList<String>();
		emp2_books.add("Beautiful Paris");
		
		List<String> emp2_roles = new ArrayList<String>();
		emp2_roles.add("researcher");
		
		Employee emp2 = new Employee("Natalie Parker", emp2_books, emp2_roles);
		
		records.put("emma", emp1);
		records.put("natalie", emp2);
	}
}
