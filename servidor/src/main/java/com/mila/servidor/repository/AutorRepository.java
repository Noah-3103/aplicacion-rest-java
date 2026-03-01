package com.mila.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mila.servidor.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}