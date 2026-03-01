package com.mila.servidor.model;
// Importar las anotaciones necesarias para JPA (antes 
// las tenñia con el * pero al guardar, automáticamente se 
// importaron las clases específicas)
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
// Importar las anotaciones necesarias para JPA
@Entity
public class Autor {
    // ID autogenerado para cada autor
    @Id
    // Generar el ID automáticamente al guardar en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String nacionalidad;
    private Integer anyoNacimiento;
    // Relación OneToMany con la entidad Libro, un autor puede tener varios libros
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Libro> libros;

    // Constructor vacío requerido por JPA
    public Autor() {}

    public Autor(String nombre, String nacionalidad, Integer anyoNacimiento) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.anyoNacimiento = anyoNacimiento;
    }

    // Getters y Setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    public String getNacionalidad() { 
        return nacionalidad; 
    }
    public void setNacionalidad(String nacionalidad) { 
        this.nacionalidad = nacionalidad; 
    }

    public Integer getAnyoNacimiento() { 
        return anyoNacimiento; 
    }
    public void setAnyoNacimiento(Integer anyoNacimiento) { 
        this.anyoNacimiento = anyoNacimiento; 
    }

    public List<Libro> getLibros() { 
        return libros; 
    }
    public void setLibros(List<Libro> libros) { 
        this.libros = libros; 
    }
}