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
	private Category category;

	public SpeciesData(Species species) {
		this.speciesId = species.getSpeciesId();
		this.name = species.getName();
		this.characteristics = species.getCharacteristics();
		this.category = species.getCategory();

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
		private Set<CategoryData> categories = new HashSet<>();

		public LocationData(Location location) {
			this.locationId = location.getLocationId();
			this.country = location.getCountry();

		}

		public Location toLocation() {
			Location location = new Location();

			location.setLocationId(locationId);
			location.setCountry(country);

			for (CategoryData categoryData : categories) {
				location.getCategories().add(categoryData.toCategory());
			}

			return location;
		}
	}

	@Data
	@NoArgsConstructor
	public static class CategoryData {
		private Long categoryId;
		private String name;
		private Set<SpeciesData> species = new HashSet<>();
		private Set<LocationData> location = new HashSet<>();

		public CategoryData(Category category) {
			this.categoryId = category.getCategoryId();
			this.name = category.getName();

			for (Species species : category.getSpecies()) {
				this.species.add(new SpeciesData(species));
			}

			for (Location location : category.getLocations()) {
				this.location.add(new LocationData(location));
			}
		}

		public Category toCategory() {
			Category category = new Category();

			category.setCategoryId(categoryId);
			category.setName(name);

			return category;

		}
	}

}