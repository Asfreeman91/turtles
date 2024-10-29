package turtles.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import turtles.entity.Species;

public interface SpeciesDao extends JpaRepository<Species, Long> {

}
