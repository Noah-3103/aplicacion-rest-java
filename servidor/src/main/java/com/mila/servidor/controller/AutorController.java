package com.mila.servidor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mila.servidor.model.Autor;
import com.mila.servidor.repository.AutorRepository;

// Controlador REST para la entidad Autor
@RestController
// Mapear todas las rutas bajo /autores
@RequestMapping("/autores")
public class AutorController {
    // Inyectar el repositorio de autores para acceder a la base de datos
    @Autowired
    private AutorRepository autorRepository;
    // Método para listar todos los autores
    @GetMapping
    public List<Autor> listar() {
        return autorRepository.findAll();
    }
    // Método para obtener un autor por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Autor> obtener(@PathVariable Long id) {
        return autorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Método para crear un nuevo autor
    @PostMapping
    public ResponseEntity<Autor> crear(@RequestBody Autor autor) {
        Autor guardado = autorRepository.save(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
    // Método para actualizar un autor existente
    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizar(@PathVariable Long id, @RequestBody Autor autor) {
        if (!autorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        autor.setId(id);
        Autor actualizado = autorRepository.save(autor);
        return ResponseEntity.ok(actualizado);
    }
    // Método para eliminar un autor por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!autorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        autorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}