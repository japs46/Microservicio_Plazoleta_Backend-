package com.pragma.backend.infrastructure.filters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pragma.backend.domain.models.UsuarioLogin;
import com.pragma.backend.infrastructure.adapters.out.UsuarioFeignClient;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	private Logger LOGGUER= LoggerFactory.getLogger(MyUserDetailsService.class);
	
	private final UsuarioFeignClient usuarioFeignClient;
	
	public MyUserDetailsService(UsuarioFeignClient usuarioFeignClient) {
		this.usuarioFeignClient = usuarioFeignClient;
	}

	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		
		try {
			LOGGUER.info("Iniciando verificación credenciales");
			
			UsuarioLogin usuario = usuarioFeignClient.buscarUsuarioPorCorreo(correo);
			
			System.err.println(usuario.toString());
			System.err.println(usuario.getRol());
			
			List<GrantedAuthority> rol = new ArrayList<>();
			rol.add(new SimpleGrantedAuthority("ROLE_" +usuario.getRol().toString()));
			
			UserDetails userDetails= new User(usuario.getCorreo(), null, rol);
			
			return userDetails;
			
		} catch (Exception e) {
			LOGGUER.info("Ocurrio un error en la verificacion de credenciales: "+e.getMessage());
			return null;
		}
		
	}

}
