package com.umg.control_empleados.controllers;

import com.umg.control_empleados.controllers.request.createEmployeeDTO;
import com.umg.control_empleados.models.ERole;
import com.umg.control_empleados.models.RoleEntity;
import com.umg.control_empleados.models.empleados;
import com.umg.control_empleados.repository.empleadosRepository;

import jakarta.validation.Valid;

import com.umg.control_empleados.models.validationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class empleadosController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private empleadosRepository empleadosRepository;

    private validationRequest validationRequest;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/empleados")
    public List<empleados> getAllEmpleados() {
        return empleadosRepository.findAll();
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<empleados> getEmpleadosById(@PathVariable(value = "id") Long id) {
        Optional<empleados> empleados = empleadosRepository.findById(id);
        if (empleados.isPresent()) {
            return ResponseEntity.ok(empleados.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/empleados")
    public empleados createEmpleados(@RequestBody empleados empleados) {
        return empleadosRepository.save(empleados);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<empleados> updateEmpleado(@PathVariable(value = "id") Long id,
            @RequestBody empleados empleadoDetalles) {
        Optional<empleados> Optionalempleados = empleadosRepository.findById(id);
        if (Optionalempleados.isPresent()) {
            empleados empleado = Optionalempleados.get();
            empleado.setUsername(empleadoDetalles.getUsername());
            empleado.setContrasena(empleadoDetalles.getContrasena());

            empleadosRepository.save(empleado);
            return ResponseEntity.ok(empleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/empleados/estado/{id}/{estado}")
    public ResponseEntity<empleados> updateEmpleadoEstado(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "estado") int activo) {

        Optional<empleados> Optionalempleados = empleadosRepository.findById(id);
        if (Optionalempleados.isPresent()) {
            empleados empleado = Optionalempleados.get();
            empleado.setActivo(activo);
            empleadosRepository.save(empleado);
            return ResponseEntity.ok(empleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Metodos Nuevos
     * Crear Empleado
     **/
    @PostMapping("/createEmployee")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> createEmployee(@Valid @RequestBody createEmployeeDTO createEmployeeDTO) {

        Set<RoleEntity> roles = createEmployeeDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        empleados empleado = empleados.builder()
                .username(createEmployeeDTO.getUsername())
                .email(createEmployeeDTO.getEmail())
                .contrasena(passwordEncoder.encode(createEmployeeDTO.getContrasena()))
                .activo(1)
                .roles(roles)
                .build();

        empleadosRepository.save(empleado);

        return ResponseEntity.ok(empleado);
    }

    /**
     * Metodo para eliminar un empleado
     **/
    @DeleteMapping("/deleteEmployee")
    @PreAuthorize("hasRole('ADMIN')")

    public String deleteEmployee(@RequestParam String id) {
        empleadosRepository.deleteById(Long.parseLong(id));
        return "Empleado eliminado";
    }
}
