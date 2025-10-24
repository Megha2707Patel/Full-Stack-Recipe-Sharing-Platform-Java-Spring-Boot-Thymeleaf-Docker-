package com.example.recipes.web;

import com.example.recipes.user.*;
import com.example.recipes.user.dto.RegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

  private final UserRepository users;
  private final RoleRepository roles;
  private final PasswordEncoder encoder;

  @GetMapping("/login")
  public String login() {
    return "auth/login";
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    model.addAttribute("reg", new RegisterDto());
    return "auth/register";
  }

  @PostMapping("/register")
  public String doRegister(@Valid @ModelAttribute("reg") RegisterDto reg, BindingResult br, Model model) {
    if (br.hasErrors()) return "auth/register";

    if (users.findByEmail(reg.getEmail()).isPresent()) {
      br.rejectValue("email", "dup", "Email is already registered");
      return "auth/register";
    }

    User u = new User();
    u.setDisplayName(reg.getDisplayName());
    u.setEmail(reg.getEmail());
    u.setPassword(encoder.encode(reg.getPassword()));
    u.getRoles().add(roles.findByName("ROLE_USER").orElseGet(() -> {
      Role r = new Role();
      r.setName("ROLE_USER");
      return roles.save(r);
    }));
    users.save(u);

    return "redirect:/login?registered";
  }
}
