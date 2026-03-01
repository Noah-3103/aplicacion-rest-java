package com.mila.cliente;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String BASE_URL = "http://localhost:12345";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Gestionar Autores");
            System.out.println("2. Gestionar Libros");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> menuAutores();
                case "2" -> menuLibros();
                case "3" -> {
                    System.out.println("¡Hasta luego!");
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }







    // -------------------- AUTORES --------------------
    private static void menuAutores() {
        while (true) {
            System.out.println("\n--- AUTORES ---");
            System.out.println("1. Listar autores");
            System.out.println("2. Insertar autor");
            System.out.println("3. Modificar autor");
            System.out.println("4. Borrar autor");
            System.out.println("5. Volver al menú principal");
            System.out.print("Opción: ");
            String op = scanner.nextLine();

            switch (op) {
                case "1" -> listarAutores();
                case "2" -> insertarAutor();
                case "3" -> modificarAutor();
                case "4" -> borrarAutor();
                case "5" -> { return; }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarAutores() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/autores"))
                .GET()
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                String json = response.body();
                
                List<Autor> autores = jsonToAutores(json);
                
                if (autores.isEmpty()) {
                    System.out.println("No hay autores.");
                
                } else {
                    for (Autor a : autores) {
                        System.out.println(a);
                    }
                }

            } else {
                System.out.println("Error al listar: " + response.statusCode());
            }
            
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    private static void insertarAutor() {
        
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Nacionalidad: ");
            String nacionalidad = scanner.nextLine();
            
            System.out.print("Año de nacimiento: ");
            int anyo = Integer.parseInt(scanner.nextLine());

            String json = String.format("{\"nombre\":\"%s\",\"nacionalidad\":\"%s\",\"anyoNacimiento\":%d}",
                    nombre, nacionalidad, anyo);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/autores"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 201) {
                System.out.println("Autor insertado correctamente.");
            
            } else {
                System.out.println("Error al insertar: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void modificarAutor() {
        
        try {
            System.out.print("ID del autor a modificar: ");
            Long id = Long.parseLong(scanner.nextLine());
            
            System.out.print("Nuevo nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Nueva nacionalidad: ");
            String nacionalidad = scanner.nextLine();
            
            System.out.print("Nuevo año de nacimiento: ");
            int anyo = Integer.parseInt(scanner.nextLine());

            String json = String.format("{\"id\":%d,\"nombre\":\"%s\",\"nacionalidad\":\"%s\",\"anyoNacimiento\":%d}",
                    id, nombre, nacionalidad, anyo);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/autores/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                System.out.println("Autor modificado.");
            
            } else {
                System.out.println("Error al modificar: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void borrarAutor() {
        
        try {
            System.out.print("ID del autor a borrar: ");
            Long id = Long.parseLong(scanner.nextLine());

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/autores/" + id))
                .DELETE()
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
            if (response.statusCode() == 204) {
                System.out.println("Autor borrado.");
    
            } else {
                System.out.println("Error al borrar: " + response.statusCode());
            }
  
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }









    // -------------------- LIBROS --------------------
    private static void menuLibros() {
        
        while (true) {
            System.out.println("\n--- LIBROS ---");
            System.out.println("1. Listar libros");
            System.out.println("2. Insertar libro");
            System.out.println("3. Modificar libro");
            System.out.println("4. Borrar libro");
            System.out.println("5. Volver al menú principal");
            System.out.print("Opción: ");
            String op = scanner.nextLine();

            switch (op) {
                case "1" -> listarLibros();
                case "2" -> insertarLibro();
                case "3" -> modificarLibro();
                case "4" -> borrarLibro();
                case "5" -> { return; }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarLibros() {
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/libros"))
                .GET()
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String json = response.body();

                List<Libro> libros = jsonToLibros(json);

                if (libros.isEmpty()) {
                    System.out.println("No hay libros.");

                } else {
                    for (Libro l : libros) {
                        System.out.println(l);
                    }
                }

            } else {
                System.out.println("Error al listar: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    private static void insertarLibro() {
        
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            
            System.out.print("Año de publicación: ");
            int anyo = Integer.parseInt(scanner.nextLine());
            
            System.out.print("ID del autor: ");
            Long autorId = Long.parseLong(scanner.nextLine());

            Autor autor = obtenerAutorPorId(autorId);
            
            if (autor == null) {
                System.out.println("Autor no encontrado.");
                return;
            }

            String json = String.format(
                "{\"titulo\":\"%s\",\"isbn\":\"%s\",\"anyoPublicacion\":%d,\"autor\":{\"id\":%d,\"nombre\":\"%s\",\"nacionalidad\":\"%s\",\"anyoNacimiento\":%d}}",
                titulo, isbn, anyo,
                autor.getId(), autor.getNombre(), autor.getNacionalidad(), autor.getAnyoNacimiento()
            );

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/libros"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                System.out.println("Libro insertado correctamente.");

            } else {
                System.out.println("Error al insertar: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void modificarLibro() {
        
        try {
            System.out.print("ID del libro a modificar: ");
            Long id = Long.parseLong(scanner.nextLine());
            
            System.out.print("Nuevo título: ");
            String titulo = scanner.nextLine();
            
            System.out.print("Nuevo ISBN: ");
            String isbn = scanner.nextLine();
            
            System.out.print("Nuevo año de publicación: ");
            int anyo = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Nuevo ID del autor: ");
            Long autorId = Long.parseLong(scanner.nextLine());

            Autor autor = obtenerAutorPorId(autorId);
            
            if (autor == null) {
                System.out.println("Autor no encontrado.");
                return;
            }

            String json = String.format(
                "{\"id\":%d,\"titulo\":\"%s\",\"isbn\":\"%s\",\"anyoPublicacion\":%d,\"autor\":{\"id\":%d,\"nombre\":\"%s\",\"nacionalidad\":\"%s\",\"anyoNacimiento\":%d}}",
                id, titulo, isbn, anyo,
                autor.getId(), autor.getNombre(), autor.getNacionalidad(), autor.getAnyoNacimiento()
            );

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/libros/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Libro modificado.");

            } else {
                System.out.println("Error al modificar: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void borrarLibro() {
        
        try {
            System.out.print("ID del libro a borrar: ");
            Long id = Long.parseLong(scanner.nextLine());

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/libros/" + id))
                .DELETE()
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
            if (response.statusCode() == 204) {
                System.out.println("Libro borrado.");
    
            } else {
                System.out.println("Error al borrar: " + response.statusCode());
            }
    
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }









    // -------------------- FUNCIONES AUXILIARES --------------------
    private static Autor obtenerAutorPorId(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/autores/" + id))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
        if (response.statusCode() == 200) {
            return jsonToAutor(response.body());
        }
        return null;
    }

    private static Autor jsonToAutor(String json) {
        Autor a = new Autor();
        a.setId(extraerLong(json, "id"));
        a.setNombre(extraerString(json, "nombre"));
        a.setNacionalidad(extraerString(json, "nacionalidad"));
        a.setAnyoNacimiento(extraerInt(json, "anyoNacimiento"));
        return a;
    }

    private static List<Autor> jsonToAutores(String json) {
        List<Autor> lista = new ArrayList<>();
        
        if (json.equals("[]")) 
            return lista;
        
        String contenido = json.substring(1, json.length() - 1);
        
        String[] objetos = contenido.split("\\},\\{");
        
        for (String obj : objetos) {
        
            if (!obj.startsWith("{")) 
                obj = "{" + obj;

            if (!obj.endsWith("}")) 
                obj = obj + "}";
            
            lista.add(jsonToAutor(obj));
        }
        return lista;
    }

    private static Libro jsonToLibro(String json) {
        
        Libro l = new Libro();
        l.setId(extraerLong(json, "id"));
        l.setTitulo(extraerString(json, "titulo"));
        l.setIsbn(extraerString(json, "isbn"));
        l.setAnyoPublicacion(extraerInt(json, "anyoPublicacion"));
        int autorIndex = json.indexOf("\"autor\":{");
        
        if (autorIndex != -1) {
            int start = json.indexOf("{", autorIndex + 8);
            int end = json.indexOf("}", start) + 1;
            String autorJson = json.substring(start, end);
            l.setAutor(jsonToAutor(autorJson));
        }
        return l;
    }

    private static List<Libro> jsonToLibros(String json) {
        
        List<Libro> lista = new ArrayList<>();
        
        if (json.equals("[]")) return lista;
        String contenido = json.substring(1, json.length() - 1);
        String[] objetos = contenido.split("\\},\\{");
        
        for (String obj : objetos) {
            
            if (!obj.startsWith("{")) 
                obj = "{" + obj;
            
            if (!obj.endsWith("}")) 
                obj = obj + "}";
            
            lista.add(jsonToLibro(obj));
        }
        return lista;
    }

    private static String extraerString(String json, String clave) {
        String patron = "\"" + clave + "\":\"";
        int inicio = json.indexOf(patron);
        
        if (inicio == -1) 
            return null;

        inicio += patron.length();
        int fin = json.indexOf("\"", inicio);
        
        return json.substring(inicio, fin);
    }

    private static Long extraerLong(String json, String clave) {
        String patron = "\"" + clave + "\":";
        int inicio = json.indexOf(patron);
        
        if (inicio == -1) 
            return null;

        inicio += patron.length();
        int fin = json.indexOf(",", inicio);
        
        if (fin == -1) fin = json.indexOf("}", inicio);
        return Long.parseLong(json.substring(inicio, fin).trim());
    }

    private static Integer extraerInt(String json, String clave) {
        
        String patron = "\"" + clave + "\":";
        int inicio = json.indexOf(patron);
        
        if (inicio == -1) 
            return null;

        inicio += patron.length();
        int fin = json.indexOf(",", inicio);
        
        if (fin == -1) fin = json.indexOf("}", inicio);
        return Integer.parseInt(json.substring(inicio, fin).trim());
    }
}