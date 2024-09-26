package com.umg.control_empleados.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.umg.control_empleados.models.empleados;
import com.umg.control_empleados.repository.empleadosRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private empleadosRepository empleadosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        empleados empleado = empleadosRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));

        Collection<? extends GrantedAuthority> authorities = empleado.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())))
                .collect(Collectors.toSet());

        return new User(empleado.getUsername(), empleado.getContrasena(), true, true, true, true, authorities);
    }

}
