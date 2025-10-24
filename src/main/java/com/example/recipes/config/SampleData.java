package com.example.recipes.config;

import com.example.recipes.recipe.Recipe;            // ✅ correct import
import com.example.recipes.recipe.RecipeRepository;  // ✅ correct import
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleData {

  @Bean
  CommandLineRunner seed(RecipeRepository repo){
    return args -> {
      if (repo.count() > 0) return;

      repo.save(Recipe.builder()
        .title("Creamy Paneer Tikka Wrap")
        .description("Soft tortillas filled with grilled paneer, crunchy veggies, and tangy yogurt-mint sauce.")
        .ingredients(String.join("\n","Paneer 250g","Greek yogurt 1/2 cup","Tandoori masala 1 tbsp","Onion + bell peppers","Tortillas 4","Mint chutney 2 tbsp","Salt, lemon"))
        .steps(String.join("\n","Marinate paneer 15 mins.","Sear paneer + veggies 3–4 mins.","Warm tortillas.","Spread chutney, add filling.","Roll tight and serve."))
        .imageUrl("https://images.unsplash.com/photo-1604909052711-5295f0f294b3?q=80&w=1200&auto=format&fit=crop")
        .authorName("Chef Megha")
        .build());

      repo.save(Recipe.builder()
        .title("Masala Poha")
        .description("Light, fluffy flattened rice with onions, peanuts, and spices.")
        .ingredients(String.join("\n","Poha 2 cups","Onion 1","Green chili 1","Peanuts 1/4 cup","Turmeric, mustard, curry leaves","Lemon, coriander"))
        .steps(String.join("\n","Rinse poha and rest.","Temper spices, sauté onions + chili, add peanuts.","Add poha + salt; steam 2 mins.","Finish with lemon + coriander."))
        .imageUrl("https://images.unsplash.com/photo-1591085686350-b45120b7c512?q=80&w=1200&auto=format&fit=crop")
        .authorName("Swapnil")
        .build());
    };
  }
}
