package com.example.notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.notas.model.Nota;
import java.util.List;
import org.springframework.data.domain.Sort;

// Repositorio para manejar operaciones con notas
public interface NotaRepository extends JpaRepository<Nota, Long> {
    
    // Busca notas de un usuario específico con ordenamiento
    List<Nota> findByUsuarioId(Long usuarioId, Sort sort);
}

