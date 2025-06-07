package com.example.notas.service;

import com.example.notas.model.Nota;
import com.example.notas.repository.NotaRepository;
import org.springframework.stereotype.Service;

// Implementación concreta del servicio para Notas
@Service 
public class NotaServiceImpl extends AbstractCrudService<Nota, Long> implements NotaService {

    // Inyecta el repositorio específico de Notas
    public NotaServiceImpl(NotaRepository repository) {
        super(repository);
    }
}