package uk.me.uohiro.ssia.model;

import java.util.List;
import java.util.Objects;

public class Employee {

	private String name;
	private List<String> books;
	private List<String> roles;

	public Employee(String name, List<String> books, List<String> roles) {
		this.name = name;
		this.books = books;
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getBooks() {
		return books;
	}

	public void setBooks(List<String> books) {
		this.books = books;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(books, name, roles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(books, other.books) && Objects.equals(name, other.name)
				&& Objects.equals(roles, other.roles);
	}
	
}
