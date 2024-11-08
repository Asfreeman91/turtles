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
		Category category = findCategoryById(categoryId);

		Species species = findOrCreateSpecies(speciesData.getSpeciesId());
		setFieldsInSpecies(species, speciesData);

		species.setCategory(category);
		category.getSpecies().add(species);

		Species dbSpecies = speciesDao.save(species);
		return new SpeciesData(dbSpecies);
	}

	private void setFieldsInSpecies(Species species, SpeciesData speciesData) {
		species.setName(speciesData.getName());
		species.setCharacteristics(speciesData.getCharacteristics());
	}

	private Species findOrCreateSpecies(Long speciesId) {
		Species species;

		if (Objects.isNull(speciesId)) {
			species = new Species();
		} else {
			species = findSpeciesById(speciesId);
		}
		return species;
	}

	@Transactional(readOnly = true)
	public List<SpeciesData> retrieveAllSpecies() {
		List<Species> speciesList = speciesDao.findAll();

		List<SpeciesData> result = new LinkedList<>();

		for (Species species : speciesList) {
			SpeciesData speciesData = new SpeciesData(species);

			speciesData.getCharacteristics();
			speciesData.getLocations();

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
	public CategoryData saveCategory(CategoryData categoryData) {

		Long categoryId = categoryData.getCategoryId();
		Category speciesCategory = findOrCreateCategory(categoryId, categoryData.getFamilyName());

		setFieldsInCategory(speciesCategory, categoryData);

		return new CategoryData(categoryDao.save(speciesCategory));

	}

	private Category findOrCreateCategory(Long categoryId, String familyName) {
		Category category;

		if (Objects.isNull(categoryId)) {
			category = new Category();
		} else {
			category = findCategoryById(categoryId);
		}
		return category;
	}

	private Category findCategoryById(Long categoryId) {
		return categoryDao.findById(categoryId)
				.orElseThrow(() -> new NoSuchElementException("Category with ID=" + categoryId + "was not found."));
	}

	private void setFieldsInCategory(Category speciesCategory, CategoryData categoryData) {
		speciesCategory.setFamilyName(categoryData.getFamilyName());

	}

	@Transactional(readOnly = false)
	public LocationData saveLocation(Long speciesId, Long categoryId, LocationData locationData) {

		Species species = findSpeciesById(speciesId);
		Category category = findCategoryById(categoryId);
		Location location = findOrCreateLocation(locationData.getLocationId());

		setFieldsInLocation(location, locationData, species, category);
		species.getLocations().add(location);
		category.getLocations().add(location);

		return new LocationData(locationDao.save(location));
	}

	private void setFieldsInLocation(Location location, LocationData locationData, Species species, Category category) {
		location.setCountry(locationData.getCountry());

	}

	private Location findOrCreateLocation(Long locationId) {
		Location location;

		if (Objects.isNull(locationId)) {
			location = new Location();
		} else {
			location = findLocationById(locationId);
		}

		return location;
	}

	private Location findLocationById(Long locationId) {
		return locationDao.findById(locationId)
				.orElseThrow(() -> new NoSuchElementException("Location with ID=" + locationId + " was not found."));
	}

	@Transactional(readOnly = false)
	public void deleteLocation(Long locationId) {
		Location location = findLocationById(locationId);
		locationDao.delete(location);

	}

	@Transactional(readOnly = true)
	public List<CategoryData> retrieveAllCategories() {
		List<Category> categories = categoryDao.findAll();

		List<CategoryData> result = new LinkedList<>();

		for (Category category : categories) {
			CategoryData categoryData = new CategoryData(category);

			categoryData.getSpecies().clear();

			categoryData.getFamilyName();

			result.add(categoryData);
		}
		return result;
	}

}
