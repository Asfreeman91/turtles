package turtles.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import turtles.entity.Category;
import turtles.entity.Location;
import turtles.entity.Species;

@Data
@NoArgsConstructor
public class SpeciesData {

	private Long speciesId;
	private String name;
	private String characteristics;
	private Set<LocationData> locations = new HashSet<>();

	public SpeciesData(Species species) {
		speciesId = species.getSpeciesId();
		name = species.getName();
		characteristics = species.getCharacteristics();

		for (Location location : species.getLocations()) {
			this.locations.add(new LocationData(location));
		}

	}

	public SpeciesData(Long speciesId, String name, String characteristics) {
		this.speciesId = speciesId;
		this.name = name;
		this.characteristics = characteristics;
	}

	public Species toSpecies() {
		Species species = new Species();

		species.setSpeciesId(speciesId);
		species.setName(name);
		species.setCharacteristics(characteristics);

		for (LocationData locationData : locations) {
			species.getLocations().add(locationData.toLocation());
		}

		return species;
	}

	@Data
	@NoArgsConstructor
	public static class LocationData {
		private Long locationId;
		private String country;

		public LocationData(Location location) {
			this.locationId = location.getLocationId();
			this.country = location.getCountry();

		}

		public Location toLocation() {
			Location location = new Location();

			location.setLocationId(locationId);
			location.setCountry(country);

			return location;

		}

	}

	@Data
	@NoArgsConstructor
	public static class CategoryData {
		private Long categoryId;
		private String familyName;
		private Set<LocationData> locations = new HashSet<>();
		private Set<SpeciesData> species = new HashSet<>();

		public CategoryData(Category category) {
			this.categoryId = category.getCategoryId();
			this.familyName = category.getFamilyName();

			for (Location location : category.getLocations()) {
				locations.add(new LocationData(location));
			}
			for (Species specimen : category.getSpecies()) {
				species.add(new SpeciesData(specimen));
			}
		}

		public Category toCategory() {
			Category category = new Category();

			category.setCategoryId(categoryId);
			category.setFamilyName(familyName);

			return category;
		}

	}

}
