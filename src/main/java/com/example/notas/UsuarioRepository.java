package com.example.notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.notas.model.Usuario;

// Repositorio para manejar operaciones con usuarios
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

