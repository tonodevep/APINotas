package com.example.notas.service;

import com.example.notas.model.Nota;

// Interfaz específica para operaciones con Notas
// Extiende de CrudService con los tipos concretos (Nota, Long)
public interface NotaService extends CrudService<Nota, Long> {
    
}

