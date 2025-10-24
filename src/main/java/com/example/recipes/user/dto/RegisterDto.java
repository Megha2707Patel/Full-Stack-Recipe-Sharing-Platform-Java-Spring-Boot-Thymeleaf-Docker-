package com.example.recipes.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDto {
  @NotBlank(message = "Display name is required")
  private String displayName;

  @Email(message = "Enter a valid email")
  @NotBlank(message = "Email is required")
  private String email;

  @NotBlank(message = "Password is required")
  private String password;
}
