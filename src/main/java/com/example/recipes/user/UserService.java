package com.example.recipes.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository users;
  private final RoleRepository roles;
  private final PasswordEncoder encoder;

  public User register(String email, String rawPassword, String displayName){
    if (users.existsByEmail(email)) {
      throw new IllegalArgumentException("Email already in use");
    }
    Role roleUser = roles.findByName("ROLE_USER")
        .orElseThrow(() -> new IllegalStateException("ROLE_USER missing"));
    User u = User.builder()
        .email(email)
        .password(encoder.encode(rawPassword))
        .displayName(displayName)
        .roles(Set.of(roleUser))
        .build();
    return users.save(u);
  }

  public User byEmail(String email){
    return users.findByEmail(email).orElseThrow();
  }

  public User save(User u){ return users.save(u); }
}
