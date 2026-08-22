package com.Kash.KashDuv.config;

import com.Kash.KashDuv.entity.Usuario;
import com.Kash.KashDuv.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean UserDetailsService userDetailsService(UsuarioRepository usuarios) { return username -> usuarios.findByUsername(username).map(u -> User.withUsername(u.getUsername()).password(u.getPassword()).roles("USER").build()).orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException(username)); }
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { return http.csrf(csrf -> csrf.disable()).cors(Customizer.withDefaults()).authorizeHttpRequests(auth -> auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll().anyRequest().authenticated()).httpBasic(Customizer.withDefaults()).build(); }
    @Bean CommandLineRunner criarUsuarioPadrao(UsuarioRepository usuarios, PasswordEncoder encoder, @Value("${app.security.default-username}") String username, @Value("${app.security.default-password}") String password) { return args -> { if (usuarios.findByUsername(username).isEmpty()) { Usuario usuario = new Usuario(); usuario.setUsername(username); usuario.setPassword(encoder.encode(password)); usuarios.save(usuario); } }; }
}
