package com.mila.servidor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mila.servidor.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}