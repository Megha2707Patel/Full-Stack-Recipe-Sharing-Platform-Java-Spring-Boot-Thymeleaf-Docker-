package com.example.recipes.recipe;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

  private final RecipeRepository repo;

  public List<Recipe> latest() {
    return repo.findAllByOrderByCreatedAtDesc();
  }

  public List<Recipe> latest(int limit) {
    return repo.findAllByOrderByCreatedAtDesc(PageRequest.of(0, Math.max(1, limit)));
  }

  public List<Recipe> search(String q){
    return (q == null || q.isBlank())
        ? latest()
        : repo.findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(q);
  }

  public Recipe get(Long id){
    return repo.findById(id).orElseThrow();
  }

  @Transactional
  public Recipe create(Recipe r){ return repo.save(r); }

  @Transactional
  public Recipe update(Long id, Recipe data){
    Recipe r = get(id);
    r.setTitle(data.getTitle());
    r.setDescription(data.getDescription());
    r.setIngredients(data.getIngredients());
    r.setSteps(data.getSteps());
    r.setImageUrl(data.getImageUrl());
    r.setAuthorName(data.getAuthorName());
    return r; // JPA dirty checking will persist on commit
  }

  @Transactional
  public void delete(Long id){ repo.deleteById(id); }
}
