package com.example.recipes.web;

import com.example.recipes.recipe.Recipe;
import com.example.recipes.recipe.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {

  private final RecipeService recipes;

  // LIST
  @GetMapping
  public String list(@RequestParam(required = false) String q, Model model){
    model.addAttribute("q", q);
    model.addAttribute("recipes", recipes.search(q));
    return "recipes/list";
  }

  // DETAIL
  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model){
    model.addAttribute("recipe", recipes.get(id));
    return "recipes/detail";
  }

  // CREATE FORM (GET) -> /recipes/new
  @GetMapping("/new")
  public String newForm(Model model){
    model.addAttribute("recipe", new Recipe());
    model.addAttribute("mode", "create");
    return "recipes/form"; // templates/recipes/form.html
  }

  // CREATE SUBMIT (POST) -> /recipes
  @PostMapping
  public String create(@Valid @ModelAttribute("recipe") Recipe recipe,
                       BindingResult br,
                       @AuthenticationPrincipal UserDetails principal){
    if (br.hasErrors()) return "recipes/form";

    if (principal != null && (recipe.getAuthorName() == null || recipe.getAuthorName().isBlank())) {
      recipe.setAuthorName(principal.getUsername());
    }
    if (principal == null && (recipe.getAuthorName() == null || recipe.getAuthorName().isBlank())) {
      recipe.setAuthorName("Guest");
    }

    Recipe saved = recipes.create(recipe);
    return "redirect:/recipes/" + saved.getId();
  }

  // EDIT FORM (GET) -> /recipes/{id}/edit
  @GetMapping("/{id}/edit")
  public String edit(@PathVariable Long id, Model model){
    model.addAttribute("recipe", recipes.get(id));
    model.addAttribute("mode", "edit");
    return "recipes/form";
  }

  // UPDATE SUBMIT (POST) -> /recipes/{id}
  @PostMapping("/{id}")
  public String update(@PathVariable Long id,
                       @Valid @ModelAttribute("recipe") Recipe data,
                       BindingResult br,
                       @AuthenticationPrincipal UserDetails principal){
    if (br.hasErrors()) return "recipes/form";

    if (principal != null && (data.getAuthorName() == null || data.getAuthorName().isBlank())) {
      data.setAuthorName(principal.getUsername());
    }
    recipes.update(id, data);
    return "redirect:/recipes/" + id + "?updated";
  }

  // DELETE -> /recipes/{id}/delete
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id){
    recipes.delete(id);
    return "redirect:/recipes?deleted";
  }
}
