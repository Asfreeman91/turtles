package turtles.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Species {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long speciesId;
	
	
	private String name;
	
	
	private String characteristics;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn (name = "category_id", nullable = false)
	private Category category;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "species_location",
			joinColumns = @JoinColumn (name = "species_id"),
			inverseJoinColumns = @JoinColumn(name = "location_id"))
	private Set<Location> locations = new HashSet<>();
	
	
	

}
