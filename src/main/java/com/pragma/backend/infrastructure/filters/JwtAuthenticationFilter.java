package com.pragma.backend.infrastructure.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pragma.backend.domain.models.UsuarioLogin;
import com.pragma.backend.infrastructure.adapters.out.UsuarioFeignClient;
import com.pragma.backend.infrastructure.providers.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioFeignClient usuarioFeignClient;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UsuarioFeignClient usuarioFeignClient) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioFeignClient = usuarioFeignClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	
        String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            username = jwtTokenProvider.getUsernameFromToken(jwtToken);
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        	UsuarioLogin usuario = usuarioFeignClient.buscarUsuarioPorCorreo(username);
        	List<GrantedAuthority> rol = new ArrayList<>();
			rol.add(new SimpleGrantedAuthority("ROLE_" +usuario.getRol().toString()));
        	
            if (jwtTokenProvider.validateToken(jwtToken, username)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                		usuario.getCorreo(), null, rol);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }

}
