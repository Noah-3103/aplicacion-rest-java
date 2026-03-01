package com.mila.servidor.model;
// Entidad Libro con anotaciones JPA para mapear a la base de datos
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// Importar las anotaciones necesarias para JPA
@Entity
public class Libro {
    // ID autogenerado para cada libro
    @Id
    // Generar el ID automáticamente al guardar en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String isbn;
    private Integer anyoPublicacion;
    // Relación ManyToOne con la entidad Autor, un libro tiene un autor
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // Constructor vacío requerido por JPA
    public Libro() {}

    public Libro(String titulo, String isbn, Integer anyoPublicacion, Autor autor) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.anyoPublicacion = anyoPublicacion;
        this.autor = autor;
    }

    // Getters y Setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getTitulo() { 
        return titulo; 
    }
    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
    }

    public String getIsbn() { 
        return isbn; 
    }
    public void setIsbn(String isbn) { 
        
        this.isbn = isbn; }

    public Integer getAnyoPublicacion() { 
        return anyoPublicacion; 
    }
    public void setAnyoPublicacion(Integer anyoPublicacion) { 
        this.anyoPublicacion = anyoPublicacion; 
    }

    public Autor getAutor() { 
        return autor; 
    }
    public void setAutor(Autor autor) { 
        this.autor = autor; 
    }
}