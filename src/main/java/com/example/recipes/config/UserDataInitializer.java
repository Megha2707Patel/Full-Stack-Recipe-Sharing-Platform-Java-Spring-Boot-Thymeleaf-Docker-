package com.example.recipes.config;

import com.example.recipes.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@EnableConfigurationProperties(AdminProps.class)
@RequiredArgsConstructor
public class UserDataInitializer {

  private final RoleRepository roles;
  private final UserRepository users;
  private final PasswordEncoder encoder;

  @Bean
  CommandLineRunner initAdmin(AdminProps props){
    return args -> {
      Role roleUser  = roles.findByName("ROLE_USER").orElseGet(() -> roles.save(new Role(null, "ROLE_USER")));
      Role roleAdmin = roles.findByName("ROLE_ADMIN").orElseGet(() -> roles.save(new Role(null, "ROLE_ADMIN")));

      String email = props.getEmail() != null ? props.getEmail() : "admin@foodie.app";
      String pwd   = props.getPassword() != null ? props.getPassword() : "ChangeMe123!";

      users.findByEmail(email).orElseGet(() ->
          users.save(User.builder()
              .email(email)
              .password(encoder.encode(pwd))
              .displayName("Admin")
              .roles(Set.of(roleUser, roleAdmin))
              .build())
      );
    };
  }
}
