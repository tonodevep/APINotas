package com.example.notas.controller;

import com.example.notas.model.Nota;
import com.example.notas.model.Usuario;
import com.example.notas.repository.NotaRepository;
import com.example.notas.repository.UsuarioRepository;
import com.example.notas.service.NotaService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notas")
public class NotaController {

    // Repositorios y servicio inyectados mediante constructor
    private final UsuarioRepository usuarioRepository;
    private final NotaService notaService;
    private final NotaRepository notaRepository;

    public NotaController(NotaService notaService, UsuarioRepository usuarioRepository, NotaRepository notaRepository) {
        this.notaService = notaService;
        this.usuarioRepository = usuarioRepository;
        this.notaRepository = notaRepository;
    }

    // Obtener todas las notas
    @GetMapping
    public List<Nota> getNotas() {
        return notaService.getAll();
    }

    // Obtener una nota por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Nota> getNotaById(@PathVariable Long id) {
        return notaService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Obtener notas filtradas por usuario y/o ordenadas por fecha
    @GetMapping("/filtradas")
    public ResponseEntity<List<Nota>> getNotasFiltradas(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        
        // Validar que el parámetro de orden sea válido
        if (!"asc".equalsIgnoreCase(order) && !"desc".equalsIgnoreCase(order)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "El parámetro 'order' debe ser 'asc' o 'desc'");
        }

        // Configurar la dirección de ordenamiento
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "fechaCreacion");
        
        List<Nota> notas;
        
        // Filtrar por usuario si se especifica el ID
        if (usuarioId != null) {
            if (!usuarioRepository.existsById(usuarioId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
            }
            notas = notaRepository.findByUsuarioId(usuarioId, sort);
        } else {
            // Obtener todas las notas si no se especifica usuario
            notas = notaRepository.findAll(sort);
        }
        
        return ResponseEntity.ok(notas);
    }

    // Crear una nueva nota
    @PostMapping
    public ResponseEntity<Nota> createNota(@Valid @RequestBody Nota nota) {
        // Validar que la nota tenga asociado un usuario
        if (nota.getUsuario() == null || nota.getUsuario().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario es requerido");
        }
        
        // Verificar que el usuario exista en la base de datos
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(nota.getUsuario().getId());
        if (usuarioOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        // Asignar el usuario existente a la nota y guardarla
        nota.setUsuario(usuarioOptional.get());
        return new ResponseEntity<>(notaService.save(nota), HttpStatus.CREATED);
    }

    // Actualizar una nota existente
    @PutMapping("/{id}")
    public ResponseEntity<Nota> updateNota(@PathVariable Long id, @Valid @RequestBody Nota nota) {
        return new ResponseEntity<>(notaService.update(id, nota), HttpStatus.OK);
    }

    // Eliminar una nota por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNota(@PathVariable Long id) {
        notaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}