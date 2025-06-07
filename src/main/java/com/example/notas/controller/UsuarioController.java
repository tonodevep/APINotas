package com.example.notas.controller;

import com.example.notas.model.Usuario;
import com.example.notas.repository.UsuarioRepository;
import com.example.notas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    // Inyección de dependencias: servicio y repositorio de usuarios
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    // Obtener todos los usuarios
    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioService.getAll();
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getAutorById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        // Retorna el usuario si existe (200 OK), o 404 Not Found si no existe
        return usuario.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
        // Valida automáticamente el objeto Usuario gracias a @Valid
        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.CREATED);
    }
    
    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        return usuarioRepository.findById(id)
            .map(usuarioExistente -> {
                // Actualiza solo los campos permitidos
                usuarioExistente.setNombre(usuarioDetails.getNombre());
                usuarioExistente.setEmail(usuarioDetails.getEmail());
                return ResponseEntity.ok(usuarioRepository.save(usuarioExistente));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        // Verifica si el usuario existe antes de intentar eliminarlo
        if (usuarioRepository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}