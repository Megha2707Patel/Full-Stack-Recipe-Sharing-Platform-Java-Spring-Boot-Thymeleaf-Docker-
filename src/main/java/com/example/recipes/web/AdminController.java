package com.example.recipes.web;

import com.example.recipes.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  private final UserRepository users;

  @GetMapping("/panel")
  public String panel(Model model){
    model.addAttribute("users", users.findAll());
    return "admin/panel";
  }
}
