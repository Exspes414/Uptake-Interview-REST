package main.java.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model representing a Family, which is a named collection, of Person objects
 */
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE) 
public class Family {

	/**
	 * Collection of Person objects who are the members of the family
	 */
	@OneToMany
	private Set<Person> members = new HashSet<>();
	
	/**
	 * Family unique Id used as its identity in the database
	 */
	@Id
	@Column(name="id", nullable=false, updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Family name
	 */
	@Column(name="name", nullable=false)
	@NotNull
	@Size(max=20)
	private String name;
	
	/**
	 * Used by JPA and Hibernate
	 */
	Family(){	
	}
	
	/**
	 * Family object constructor
	 * @param id The family Id
	 * @param name The family name
	 */
	public Family(Long id, String name){
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Getter for the internal members set
	 * @return
	 */
	@JsonProperty("members")
	public Set<Person> getMembers() {
		return members;
	}
	
	/**
	 * Setter for the Family Id
	 * @param id The value to set the family Id to
	 */
	public void setId(Long id){
		this.id = id;
	}

	/**
	 * Getter for the family Id
	 * @return The family Id
	 */
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	/**
	 * Getter for the family name
	 * @return The family name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	
}
