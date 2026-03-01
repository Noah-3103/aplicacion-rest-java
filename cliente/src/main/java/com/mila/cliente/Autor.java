package com.mila.cliente;

public class Autor {
    private Long id;
    private String nombre;
    private String nacionalidad;
    private Integer anyoNacimiento;

    public Autor() {}

    public Autor(String nombre, String nacionalidad, Integer anyoNacimiento) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.anyoNacimiento = anyoNacimiento;
    }

    // Getters y setters

    //ID
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    //Nombre
    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    //Nacionalidad
    public String getNacionalidad() { 
        return nacionalidad; 
    }
    public void setNacionalidad(String nacionalidad) { 
        this.nacionalidad = nacionalidad; 
    }

    //Año de Nacimiento
    public Integer getAnyoNacimiento() { 
        return anyoNacimiento; 
    }
    public void setAnyoNacimiento(Integer anyoNacimiento) { 
        this.anyoNacimiento = anyoNacimiento; 
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " (" + nacionalidad + ", " + anyoNacimiento + ")";
    }
}