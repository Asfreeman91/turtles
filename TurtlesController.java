package turtles.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import turtles.controller.model.SpeciesData;
import turtles.controller.model.SpeciesData.CategoryData;
import turtles.controller.model.SpeciesData.LocationData;
import turtles.service.TurtlesService;

@RestController
@RequestMapping("/turtles")
@Slf4j
public class TurtlesController {

	@Autowired
	private TurtlesService turtlesService;

	@PostMapping("/category/{categoryId}/species")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SpeciesData createSpecies(@PathVariable Long categoryId, @RequestBody SpeciesData speciesData) {
		log.info("Creating species {} with category ID={}", speciesData, categoryId);
		return turtlesService.saveSpecies(speciesData, categoryId);
	}

	@PutMapping("/category/{categoryId}/species/{speciesId}")
	public SpeciesData updateSpecies(@PathVariable Long speciesId, @PathVariable Long categoryId,
			@RequestBody SpeciesData speciesData) {
		speciesData.setSpeciesId(speciesId);
		log.info("Updating species {} with category ID={}", speciesData, categoryId);
		return turtlesService.saveSpecies(speciesData, categoryId);
	}

	@GetMapping("/species")
	public List<SpeciesData> retrieveAllSpecies() {

		log.info("Retrieving all species.");

		return turtlesService.retrieveAllSpecies();
	}

	//Retrieves all species of a taxonomic family
	@GetMapping("/category/{categoryId}/species")
	public List<SpeciesData> retrieveAllSpeciesByCategory() {

		log.info("Retrieving all species.");

		return turtlesService.retrieveAllSpecies();
	}

	@GetMapping("/species/{speciesId}")		
	public SpeciesData retrieveASpeciesById(@PathVariable Long speciesId) {

		log.info("Retrieving a species with ID={}", speciesId);

		return turtlesService.retrieveASpeciesById(speciesId);
	}

	@DeleteMapping("/species/{speciesId}")
	public Map<String, String> deleteSpeciesById(@PathVariable Long speciesId) {

		log.info("Deleting a species with ID={}", speciesId);

		turtlesService.deleteSpeciesById(speciesId);

		return Map.of("message", "Species with the ID=" + speciesId + " has been deleted.");
	}

	@PostMapping("/category")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CategoryData addCategory(@RequestBody CategoryData categoryData) {

		log.info("Adding a category {}", categoryData);

		return turtlesService.saveCategory(categoryData);
	}

	//Retrieves all taxonomic families of turtles 
	@GetMapping("/category")
	public List<CategoryData> retrieveAllCategories() {

		log.info("Retrieving all categories.");

		return turtlesService.retrieveAllCategories();
	}

	@PostMapping("/species/{speciesId}/category/{categoryId}/location")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LocationData addLocation(@PathVariable Long speciesId, @PathVariable Long categoryId,
			@RequestBody LocationData locationData) {

		log.info("Adding a location {} for species with ID={} and category with ID={}", locationData, speciesId,
				categoryId);

		return turtlesService.saveLocation(speciesId, categoryId, locationData);
	}

	@DeleteMapping("/location/{locationId}")
	public Map<String, String> deleteLocation(@PathVariable Long locationId) {

		log.info("Deleting a location with ID={}", locationId);

		turtlesService.deleteLocation(locationId);

		return Map.of("message", "Location with the ID=" + locationId + " has been deleted.");
	}

	@PutMapping("/species/{speciesId}/category/{categoryId}/location/{locationId}")
	public LocationData updateLocation(@PathVariable Long speciesId, @PathVariable Long categoryId,
			@PathVariable Long locationId, @RequestBody LocationData locationData) {
		locationData.setLocationId(locationId);
		log.info("Updating location {} with ID={}", locationData, locationId);
		return turtlesService.saveLocation(categoryId, locationId, locationData);
	}
}