package main.java.models;

import main.java.models.Family;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Used by JPA
 */
public interface FamilyRepository extends JpaRepository<Family, Long>{

}
