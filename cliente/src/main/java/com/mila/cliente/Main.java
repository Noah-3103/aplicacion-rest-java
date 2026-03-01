package com.mila.cliente;
// Clase principal del cliente, que proporciona una interfaz de línea de comandos para interactuar con el servidor REST
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // URL base del servidor REST
    private static final String BASE_URL = "http://localhost:12345";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Bucle principal del menú, se repite hasta que el usuario elige salir
        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Gestionar Autores");
            System.out.println("2. Gestionar Libros");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");
            String opcion = scanner.nextLine();
            
            // Dependiendo de la opción elegida, se llama al método 
            // correspondiente para gestionar autores o libros, 
            // o se sale del programa
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
        // Bucle del menú de autores, se repite hasta que el usuario elige volver al menú principal
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
    // Método para listar todos los autores, hace una petición GET al servidor y muestra los resultados
    private static void listarAutores() {
        // Se construye la petición HTTP GET para obtener la lista de autores
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/autores"))
                .GET()
                .build();
            // Se envía la petición y se obtiene la respuesta del servidor
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Si la respuesta es exitosa (código 200), se procesa el JSON recibido y se muestra la lista de autores
            if (response.statusCode() == 200) {
                String json = response.body();
                
                List<Autor> autores = jsonToAutores(json);
                // Si la lista de autores está vacía, se muestra un mensaje indicando que no hay autores,
                // de lo contrario se imprime cada autor en la consola
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
        // Si ocurre algún error durante la conexión o el procesamiento de la respuesta, se captura la excepción y se muestra un mensaje de error    
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    // Método para insertar un nuevo autor, pide los datos al usuario, construye un JSON y hace una petición POST al servidor
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

            // Se construye la petición HTTP POST para insertar un nuevo autor, se envía al servidor y se procesa la respuesta
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

    // Método para modificar un autor existente, pide el ID del autor a modificar y los nuevos datos, construye un JSON y hace una petición PUT al servidor
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

            // Se construye el JSON con los nuevos datos del autor, incluyendo el ID para identificar qué autor se va a modificar
            String json = String.format("{\"id\":%d,\"nombre\":\"%s\",\"nacionalidad\":\"%s\",\"anyoNacimiento\":%d}",
                    id, nombre, nacionalidad, anyo);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/autores/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Si la respuesta es exitosa (código 200), se muestra un mensaje indicando que el autor fue modificado, 
            // de lo contrario se muestra un mensaje de error con el código de respuesta
            if (response.statusCode() == 200) {
                System.out.println("Autor modificado.");
            
            } else {
                System.out.println("Error al modificar: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método para borrar un autor, pide el ID del autor a borrar y hace una petición DELETE al servidor
    private static void borrarAutor() {
        
        try {
            System.out.print("ID del autor a borrar: ");
            Long id = Long.parseLong(scanner.nextLine());

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/autores/" + id))
                .DELETE()
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
            // Si la respuesta es exitosa (código 204), se muestra un mensaje indicando que el autor fue borrado,
            // de lo contrario se muestra un mensaje de error con el código de respuesta
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

    // Método para listar todos los libros, hace una petición GET al servidor y muestra los resultados
    private static void listarLibros() {
        
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/libros"))
                .GET()
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Si la respuesta es exitosa (código 200), se procesa el JSON recibido y se muestra la lista de libros,
            // de lo contrario se muestra un mensaje de error con el código de respuesta
            if (response.statusCode() == 200) {
                String json = response.body();

                List<Libro> libros = jsonToLibros(json);

                // Si la lista de libros está vacía, se muestra un mensaje indicando que no hay libros,
                // de lo contrario se imprime cada libro en la consola
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

    // Método para insertar un nuevo libro, pide los datos al usuario, construye un JSON y hace una petición POST al servidor
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

            // Se construye el JSON con los datos del nuevo libro, incluyendo el autor como un objeto anidado con su ID y datos para que el servidor pueda asociar el libro con el autor correcto
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

            // Si la respuesta es exitosa (código 201), se muestra un mensaje indicando que el libro fue insertado,
            // de lo contrario se muestra un mensaje de error con el código de respuesta
            if (response.statusCode() == 201) {
                System.out.println("Libro insertado correctamente.");

            } else {
                System.out.println("Error al insertar: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Método para modificar un libro existente, pide el ID del libro a modificar y los nuevos datos, construye un JSON y hace una petición PUT al servidor
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
            
            // Si el autor con el ID proporcionado no existe, se muestra un mensaje de error y se cancela la modificación del libro
            if (autor == null) {
                System.out.println("Autor no encontrado.");
                return;
            }

            // Se construye el JSON con los nuevos datos del libro, incluyendo el ID para identificar qué libro se va a modificar y el autor como un objeto anidado con su ID
            // y datos para que el servidor pueda asociar el libro con el autor correcto
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

    // Método para borrar un libro, pide el ID del libro a borrar y hace una petición DELETE al servidor
    private static void borrarLibro() {
        
        try {
            System.out.print("ID del libro a borrar: ");
            Long id = Long.parseLong(scanner.nextLine());

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/libros/" + id))
                .DELETE()
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
            // Si la respuesta es exitosa (código 204), se muestra un mensaje indicando que el libro fue borrado,
            // de lo contrario se muestra un mensaje de error con el código de respuesta
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
    
    // Método para obtener un autor por su ID, hace una petición GET 
    // al servidor y devuelve un objeto Autor si se encuentra, 
    // o null si no se encuentra
    private static Autor obtenerAutorPorId(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/autores/" + id))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
        // Si la respuesta es exitosa (código 200), se procesa el JSON recibido y se devuelve el objeto Autor.
        if (response.statusCode() == 200) {
            return jsonToAutor(response.body());
        }
        return null;
    }

    // Métodos para convertir JSON a objetos Autor y Libro, y para extraer valores de un JSON dado una clave
    private static Autor jsonToAutor(String json) {
        Autor a = new Autor();
        // Se extraen los valores del JSON utilizando las funciones auxiliares y se asignan a los atributos del objeto Autor
        a.setId(extraerLong(json, "id"));
        a.setNombre(extraerString(json, "nombre"));
        a.setNacionalidad(extraerString(json, "nacionalidad"));
        a.setAnyoNacimiento(extraerInt(json, "anyoNacimiento"));
        return a;
    }

    // Método para convertir un JSON que representa una lista de autores 
    // en una List<Autor>, maneja el caso de una lista vacía y divide el JSON
    // en objetos individuales para convertir cada uno a un Autor
    private static List<Autor> jsonToAutores(String json) {
        List<Autor> lista = new ArrayList<>();
        // Si el JSON es una lista vacía, se devuelve una lista vacía sin intentar procesar el contenido
        if (json.equals("[]")) 
            return lista;
        
        // Se elimina el primer y último carácter del JSON para obtener el contenido dentro de los corchetes,
        // luego se divide el contenido en objetos individuales utilizando "},{" como separador, 
        // y se procesa cada objeto para convertirlo a un Autor
        String contenido = json.substring(1, json.length() - 1);
        
        String[] objetos = contenido.split("\\},\\{");
        // Debido a que al dividir por "},{" se pierden los corchetes de cada objeto, se añaden 
        // manualmente antes de convertir cada uno a un Autor
        for (String obj : objetos) {
        
            if (!obj.startsWith("{")) 
                obj = "{" + obj;

            if (!obj.endsWith("}")) 
                obj = obj + "}";
            
            lista.add(jsonToAutor(obj));
        }
        return lista;
    }

    // Método para convertir un JSON que representa un libro en un objeto Libro, 
    // maneja el caso de un autor anidado dentro del JSON del libro
    private static Libro jsonToLibro(String json) {
        
        // Se extraen los valores del JSON utilizando las funciones auxiliares 
        // y se asignan a los atributos del objeto Libro
        Libro l = new Libro();
        l.setId(extraerLong(json, "id"));
        l.setTitulo(extraerString(json, "titulo"));
        l.setIsbn(extraerString(json, "isbn"));
        l.setAnyoPublicacion(extraerInt(json, "anyoPublicacion"));
        int autorIndex = json.indexOf("\"autor\":{");
        
        // Si el JSON del libro contiene un autor anidado, se extrae el 
        // JSON del autor y se convierte a un objeto Autor para asignarlo al libro
        if (autorIndex != -1) {
            int start = json.indexOf("{", autorIndex + 8);
            int end = json.indexOf("}", start) + 1;
            String autorJson = json.substring(start, end);
            l.setAutor(jsonToAutor(autorJson));
        }
        return l;
    }

    // Método para convertir un JSON que representa una lista de libros en una List<Libro>,
    // maneja el caso de una lista vacía y divide el JSON en objetos individuales para 
    // convertir cada uno a un Libro
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

    // Funciones auxiliares para extraer valores de un JSON dado una clave,
    // buscan el patrón de la clave en el JSON y extraen el valor correspondiente
    private static String extraerString(String json, String clave) {
        String patron = "\"" + clave + "\":\"";
        int inicio = json.indexOf(patron);
        
        // Si no se encuentra la clave en el JSON, se devuelve null
        if (inicio == -1) 
            return null;

        // Si se encuentra la clave, se calcula el índice de inicio del valor 
        // sumando la longitud del patrón, luego se busca el índice del siguiente 
        // carácter de comillas para encontrar el final del valor.
        inicio += patron.length();
        int fin = json.indexOf("\"", inicio);
        
        return json.substring(inicio, fin);
    }

    // Función auxiliar para extraer un valor numérico (Long) de un JSON dado una clave,
    // busca el patrón de la clave en el JSON y extrae el valor correspondiente, maneja 
    // el caso de que el valor sea el último en el JSON sin una coma al final.
    private static Long extraerLong(String json, String clave) {
        String patron = "\"" + clave + "\":";
        int inicio = json.indexOf(patron);
        
        if (inicio == -1) 
            return null;

        inicio += patron.length();
        int fin = json.indexOf(",", inicio);
        
        // Si no se encuentra una coma después del valor, se busca el siguiente carácter 
        // de cierre de llave para encontrar el final del valor
        if (fin == -1) fin = json.indexOf("}", inicio);
        return Long.parseLong(json.substring(inicio, fin).trim());
    }

    // Función auxiliar para extraer un valor numérico (Integer) de un JSON dado una clave.
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