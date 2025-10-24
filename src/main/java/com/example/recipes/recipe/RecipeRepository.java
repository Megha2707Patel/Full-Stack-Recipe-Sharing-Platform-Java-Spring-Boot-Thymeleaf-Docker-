package com.example.recipes.recipe;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
  List<Recipe> findAllByOrderByCreatedAtDesc();
  List<Recipe> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String q);
  List<Recipe> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
