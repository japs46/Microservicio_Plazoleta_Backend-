package com.pragma.backend.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pragma.backend.infrastructure.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
        	.requestMatchers("/api/restaurantes/buscarTodos").hasRole("CLIENTE")
        	.requestMatchers("/api/pedidos/guardar").hasRole("CLIENTE")
        	.requestMatchers("/api/platos/porRestaurante/{idRestaurante}").hasRole("CLIENTE")
        	.requestMatchers("/api/restaurantes/**").hasRole("ADMIN")
            .requestMatchers("/api/platos/**").hasRole("PROPIETARIO")
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
    }

}
