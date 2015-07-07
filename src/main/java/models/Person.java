package main.java.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE) 
public class Person {

	@Id
	@Column(name="id", nullable=false, updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="firstName", nullable=false)
	@NotNull
	@Size(max=20)
	private String firstName;

	@Column(name="lastName", nullable=false)
	@NotNull
	@Size(max=20)
	private String lastName;
	
	Person(){
		
	}
		
	public Person( Long id, String firstName, String lastName)
	{
		this.id = id;
		
		this.firstName = firstName;
		
		this.lastName = lastName;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	@JsonProperty("id")
	public Long getId() {
		return id;
	}
	
	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}
	
	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}
	
}
