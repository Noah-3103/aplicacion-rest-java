package com.mila.cliente;

public class Libro {
    private Long id;
    private String titulo;
    private String isbn;
    private Integer anyoPublicacion;
    private Autor autor;

    public Libro() {}

    public Libro(String titulo, String isbn, Integer anyoPublicacion, Autor autor) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.anyoPublicacion = anyoPublicacion;
        this.autor = autor;
    }

    // Getters y setters

    //ID
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    //Título
    public String getTitulo() { 
        return titulo; 
    }
    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
    }

    //ISBN
    public String getIsbn() { 
        return isbn; 
    }
    public void setIsbn(String isbn) { 
        this.isbn = isbn; 
    }

    //Año de Publicación
    public Integer getAnyoPublicacion() { 
        return anyoPublicacion; 
    }
    public void setAnyoPublicacion(Integer anyoPublicacion) { 
        this.anyoPublicacion = anyoPublicacion; 
    }

    //Autor
    public Autor getAutor() { 
        return autor; 
    }
    public void setAutor(Autor autor) { 
        this.autor = autor; 
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + titulo + " (ISBN: " + isbn + ", " + anyoPublicacion + ") - Autor: " 
                + (autor != null ? autor.getNombre() : "Desconocido");
    }
}