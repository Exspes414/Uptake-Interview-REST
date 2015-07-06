package main.java.models;

import main.java.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepositiory extends JpaRepository<Person, Long> {
}
