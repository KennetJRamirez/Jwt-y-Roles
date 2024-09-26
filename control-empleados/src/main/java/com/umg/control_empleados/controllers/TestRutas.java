package com.umg.control_empleados.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.umg.control_empleados.repository.empleadosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestRutas {

    @Autowired
    private empleadosRepository empleadosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/modoAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String accesoAdmin() {
        return "Modo Admin";
    }

    @GetMapping("/modoUser")
    @PreAuthorize("hasRole('USER')")
    public String accesoUser() {
        return "Modo Usuario";
    }

    @GetMapping("/modoInvitado")
    @PreAuthorize("hasRole('INVITED')")
    public String accesoInvitado() {
        return "Modo Invitado";
    }

}
