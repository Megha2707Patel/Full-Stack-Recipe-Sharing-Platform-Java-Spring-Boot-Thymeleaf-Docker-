package com.example.recipes.recipe;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Recipe {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String title;

  @Column(length = 2000)
  private String description;

  @Column(length = 5000)
  private String ingredients;   // newline-separated

  @Column(length = 8000)
  private String steps;         // newline-separated

  private String imageUrl;
  private String authorName;

  private Instant createdAt;
  private Instant updatedAt;

  @PrePersist void onCreate(){ createdAt = updatedAt = Instant.now(); }
  @PreUpdate  void onUpdate(){ updatedAt = Instant.now(); }
}
