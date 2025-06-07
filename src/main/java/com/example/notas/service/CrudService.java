package com.example.notas.service;

import java.util.List;
import java.util.Optional;

// Interfaz base para servicios CRUD (Create, Read, Update, Delete)
public interface CrudService<T, ID> {
    // Obtener todos los registros
    List<T> getAll();
    
    // Buscar un registro por su ID
    Optional<T> getById(ID id);
    
    // Guardar un nuevo registro
    T save(T entity);
    
    // Actualizar un registro existente
    T update(ID id, T entity);
    
    // Eliminar un registro por su ID
    void deleteById(ID id);
}
