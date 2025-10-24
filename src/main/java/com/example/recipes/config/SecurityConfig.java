package com.example.recipes.config;

import com.example.recipes.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomUserDetailsService uds;

  @Bean
  PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .headers(h -> h.frameOptions(f -> f.disable()))
      .authorizeHttpRequests(auth -> auth
        // public pages
        .requestMatchers("/", "/login", "/register", "/css/**", "/h2-console/**").permitAll()

        // ✅ make "create recipe" public (form + submit)
        .requestMatchers(HttpMethod.GET,  "/recipes/new").permitAll()
        .requestMatchers(HttpMethod.POST, "/recipes").permitAll()

        .requestMatchers(HttpMethod.GET,  "/recipes/new").permitAll()
        .requestMatchers(HttpMethod.POST, "/recipes").permitAll()


        // keep admin protected
        .requestMatchers("/admin/**").hasRole("ADMIN")

        // everything else is fine for everyone for now
        .anyRequest().permitAll()
      )
      .userDetailsService(uds)
      .formLogin(f -> f
        .loginPage("/login")
        .defaultSuccessUrl("/", true)
        .permitAll()
      )
      .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/"));
    return http.build();
  }
}
