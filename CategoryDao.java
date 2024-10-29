package turtles.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import turtles.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {

}
