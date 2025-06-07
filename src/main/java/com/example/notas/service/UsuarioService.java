package com.example.notas.service;

import com.example.notas.model.Usuario;

// Interfaz específica para operaciones con usuario
// Extiende de CrudService con los tipos concretos (Usuario, Long)
public interface UsuarioService extends CrudService<Usuario, Long> {
}

