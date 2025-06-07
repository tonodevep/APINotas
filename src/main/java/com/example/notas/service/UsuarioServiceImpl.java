package com.example.notas.service;

import com.example.notas.model.Usuario;
import com.example.notas.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

// Implementación concreta del servicio para Usuario
@Service
public class UsuarioServiceImpl extends AbstractCrudService<Usuario, Long> implements UsuarioService {
    
    // Inyecta el repositorio específico de Usuario
    public UsuarioServiceImpl(UsuarioRepository repository) {
        super(repository);
    }
}

