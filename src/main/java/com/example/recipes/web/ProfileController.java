package com.example.recipes.web;

import com.example.recipes.user.User;
import com.example.recipes.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class ProfileController {

  private final UserRepository users;

  @GetMapping("/profile")
  public String profile(@AuthenticationPrincipal UserDetails principal, Model model){
    User me = users.findByEmail(principal.getUsername()).orElseThrow();
    model.addAttribute("me", me);
    return "user/profile";
  }

  @PostMapping("/profile")
  public String update(@AuthenticationPrincipal UserDetails principal,
                       @RequestParam String displayName,
                       @RequestParam String bio){
    User me = users.findByEmail(principal.getUsername()).orElseThrow();
    me.setDisplayName(displayName);
    me.setBio(bio);
    users.save(me);
    return "redirect:/user/profile?updated";
  }
}
