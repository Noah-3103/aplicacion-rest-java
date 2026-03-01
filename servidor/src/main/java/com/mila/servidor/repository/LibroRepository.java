package com.mila.servidor.repository;
// Repositorio JPA para la entidad Libro, proporciona métodos CRUD automáticamente
import org.springframework.data.jpa.repository.JpaRepository;

import com.mila.servidor.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}