package com.example.recipes.web;

import com.example.recipes.recipe.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final RecipeService recipes;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "RecipeShare");
        model.addAttribute("trending", recipes.latest(6)); // top 6
        return "index";
    }
}
