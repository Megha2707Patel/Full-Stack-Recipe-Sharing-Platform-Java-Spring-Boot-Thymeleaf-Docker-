package com.example.recipes.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository users;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User u = users.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found"));
    Set<GrantedAuthority> auths = u.getRoles().stream()
        .map(Role::getName)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());

    return org.springframework.security.core.userdetails.User
        .withUsername(u.getEmail())
        .password(u.getPassword())
        .authorities(auths)
        .build();
  }
}
