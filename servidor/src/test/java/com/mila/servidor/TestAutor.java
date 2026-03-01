package com.mila.servidor;

import com.mila.servidor.model.Autor;
import com.mila.servidor.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TestAutor {

    @Autowired
    private AutorRepository autorRepository;

    @Test
    public void testGuardarAutor() {
        // Crear un autor con todos los datos requeridos
        Autor autor = new Autor("Gabriel García Márquez", "Colombiana", 1927);
        Autor guardado = autorRepository.save(autor);
        
        // Verificar que se asignó un ID (significa que se guardó en BD)
        assertThat(guardado.getId()).isNotNull();
    }
}