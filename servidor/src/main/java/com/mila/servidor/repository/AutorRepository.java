package com.mila.servidor.repository;
// Repositorio JPA para la entidad Autor, proporciona métodos CRUD automáticamente
import org.springframework.data.jpa.repository.JpaRepository;

import com.mila.servidor.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}