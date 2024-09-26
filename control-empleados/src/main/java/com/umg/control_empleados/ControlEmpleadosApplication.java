package com.umg.control_empleados;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.umg.control_empleados.models.ERole;
import com.umg.control_empleados.models.RoleEntity;
import com.umg.control_empleados.models.empleados;
import com.umg.control_empleados.repository.empleadosRepository;

@SpringBootApplication
public class ControlEmpleadosApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ControlEmpleadosApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	empleadosRepository empleadosRepository;

	@Bean
	CommandLineRunner init() {
		return args -> {
			empleados empleado = empleados.builder()
					.activo(1)
					.email("joab@gmail.com")
					.contrasena(passwordEncoder.encode("1234"))
					.username("joab")
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			empleados empleado2 = empleados.builder()
					.activo(1)
					.email("gus@gmail.com")
					.contrasena(passwordEncoder.encode("1234"))
					.username("gus")
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.USER.name()))
							.build()))
					.build();

			empleados empleado3 = empleados.builder()
					.activo(1)
					.email("ronaldo@gmail.com")
					.contrasena(passwordEncoder.encode("1234"))
					.username("ronaldo")
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.INVITED.name()))
							.build()))
					.build();

			empleadosRepository.save(empleado);
			empleadosRepository.save(empleado2);
			empleadosRepository.save(empleado3);

		};

	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ControlEmpleadosApplication.class);
	}

}
