package com.example.notas.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

// Clase abstracta que implementa operaciones CRUD básicas
public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID> {
    protected final JpaRepository<T, ID> repository;

    protected AbstractCrudService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    // Obtener todos los elementos
    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    // Buscar elemento por ID
    @Override
    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    // Guardar nuevo elemento
    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    // Actualizar elemento existente
    @Override
    public T update(ID id, T entity) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entidad no encontrada");
        }
        return repository.save(entity);
    }

    // Eliminar elemento por ID
    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}

