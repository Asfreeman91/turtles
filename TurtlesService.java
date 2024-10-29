package turtles.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import turtles.controller.model.SpeciesData;
import turtles.controller.model.SpeciesData.CategoryData;
import turtles.controller.model.SpeciesData.LocationData;
import turtles.dao.CategoryDao;
import turtles.dao.LocationDao;
import turtles.dao.SpeciesDao;
import turtles.entity.Category;
import turtles.entity.Location;
import turtles.entity.Species;

@Service
public class TurtlesService {

	@Autowired
	private SpeciesDao speciesDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private LocationDao locationDao;

	@Transactional(readOnly = false)
	public SpeciesData saveSpecies(SpeciesData speciesData, Long categoryId) {
		Species species = speciesData.toSpecies();
		Species dbSpecies = speciesDao.save(species);

		return new SpeciesData(dbSpecies);
	}

	@Transactional(readOnly = true)
	public List<SpeciesData> retrieveAllSpecies() {
		List<Species> speciesList = speciesDao.findAll();

		List<SpeciesData> result = new LinkedList<>();

		for (Species species : speciesList) {
			SpeciesData speciesData = new SpeciesData(species);

			speciesData.getCategory();
			speciesData.setCategory(null);
			speciesData.getLocations().clear();

			result.add(speciesData);
		}
		return result;
	}

	@Transactional(readOnly = true)
	public SpeciesData retrieveASpeciesById(Long speciesId) {
		return new SpeciesData(findSpeciesById(speciesId));
	}

	private Species findSpeciesById(Long speciesId) {
		return speciesDao.findById(speciesId)
				.orElseThrow(() -> new NoSuchElementException("Species with ID=" + speciesId + " was not found."));
	}

	@Transactional(readOnly = false)
	public void deleteSpeciesById(Long speciesId) {
		Species species = findSpeciesById(speciesId);
		speciesDao.delete(species);

	}

	@Transactional(readOnly = false)
	public CategoryData saveCategory(Long speciesId, CategoryData categoryData) {
		
		Species species = findSpeciesById(speciesId);
		Long categoryId = categoryData.getCategoryId();
		Category speciesCategory = findOrCreateCategory(speciesId, categoryId);

		copyCategoryFields(speciesCategory, categoryData);

		speciesCategory.getSpecies().add(species);
		species.setCategory(speciesCategory);
		Category dbCategory = categoryDao.save(speciesCategory);
		return new CategoryData(dbCategory);

	}

	private void copyCategoryFields(Category speciesCategory, CategoryData categoryData) {
		speciesCategory.setCategoryId(categoryData.getCategoryId());
		speciesCategory.setName(categoryData.getName());

	}

	private Category findOrCreateCategory(Long speciesId, Long categoryId) {

		if (Objects.isNull(categoryId)) {
			return new Category();

		}

		return findCategoryById(speciesId, categoryId);
	}

	private Category findCategoryById(Long speciesId, Long categoryId) {
		Category category = categoryDao.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException("Category with ID=" + categoryId + " was not found."));

		boolean found = false;

		for (Species species : category.getSpecies()) {
			if (species.getSpeciesId() == speciesId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"The category with ID=" + categoryId + " is not associated with the species with ID=" + speciesId);
		}
		return category;
	}

	public LocationData saveLocation(Long speciesId, LocationData locationData, Long categoryId) {
		Species species = findSpeciesById(speciesId);
		Long locationId = locationData.getLocationId();
		Location location = findOrCreateLocation(speciesId, locationId);

		copyLocationFields(location, locationData);

		location.getSpecies().add(species);
		species.getLocations().add(location);

		Location dbLocation = locationDao.save(location);
		return new LocationData(dbLocation);
	}

	private void copyLocationFields(Location location, LocationData locationData) {
		location.setLocationId(location.getLocationId());
		location.setCountry(location.getCountry());

	}

	private Location findOrCreateLocation(Long speciesId, Long locationId) {
		if (Objects.isNull(locationId)) {
			return new Location();

		}

		return findLocationById(speciesId, locationId);
	}

	private Location findLocationById(Long speciesId, Long locationId) {
		Location location = locationDao.findById(locationId)
				.orElseThrow(() -> new NoSuchElementException("Location with ID=" + locationId + " was not found."));

		boolean found = false;

		for (Species species : location.getSpecies()) {
			if (species.getSpeciesId() == speciesId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"The location with ID=" + locationId + " is not associated with the species with ID=" + speciesId);
		}
		return location;
	}

}
