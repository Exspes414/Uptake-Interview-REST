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


@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE) 
public class Family {

	@OneToMany
	private Set<Person> members = new HashSet<>();
	
	@Id
	@Column(name="id", nullable=false, updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", nullable=false)
	@NotNull
	@Size(max=20)
	private String name;
	
	Family(){	
	}
	
	public Family(Long id, String name){
		this.id = id;
		this.name = name;
	}
	
	@JsonProperty("members")
	public Set<Person> getMembers() {
		return members;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}
	
}
