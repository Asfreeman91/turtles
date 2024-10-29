package turtles.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import turtles.entity.Location;

public interface LocationDao extends JpaRepository<Location, Long> {

}
