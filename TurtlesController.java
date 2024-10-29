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

	@PostMapping("/{categoryId}/species")
	@ResponseStatus(code = HttpStatus.CREATED)
	public SpeciesData createSpecies(@PathVariable Long categoryId, @RequestBody SpeciesData speciesData) {
		log.info("Creating species {}", speciesData);
		return turtlesService.saveSpecies(speciesData);
	}

	@PutMapping("/{speciesId}")
	public SpeciesData updateSpecies(@PathVariable Long speciesId, @RequestBody SpeciesData speciesData) {
		speciesData.setSpeciesId(speciesId);
		log.info("Updating species {}", speciesData);
		return turtlesService.saveSpecies(speciesData);
	}

	@GetMapping
	public List<SpeciesData> retrieveAllSpecies() {

		log.info("Retrieving all species.");

		return turtlesService.retrieveAllSpecies();
	}

	@GetMapping("/{speciesId}")
	public SpeciesData retrieveASpeciesById(@PathVariable Long speciesId) {

		log.info("Retrieving a species with ID={}", speciesId);

		return turtlesService.retrieveASpeciesById(speciesId);
	}

	@DeleteMapping("/{speciesId}")
	public Map<String, String> deleteSpeciesById(@PathVariable Long speciesId) {

		log.info("Deleting a species with ID={}", speciesId);

		turtlesService.deleteSpeciesById(speciesId);

		return Map.of("message", "Species with the ID=" + speciesId + " has been deleted.");
	}

	@PostMapping("/{speciesId}/category")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CategoryData addCategoryToSpecies(@PathVariable Long speciesId, @RequestBody CategoryData categoryData) {

		log.info("Adding a category {} with ID={}", categoryData, speciesId);

		return turtlesService.saveCategory(speciesId, categoryData);
	}

	@PostMapping("/{speciesId}/location")
	@ResponseStatus(code = HttpStatus.CREATED)

	public LocationData addLocation(@PathVariable Long speciesId, @RequestBody LocationData locationData) {

		log.info("Adding a location {} with ID={}", locationData, speciesId);

		return turtlesService.saveLocation(speciesId, locationData);
	}
}