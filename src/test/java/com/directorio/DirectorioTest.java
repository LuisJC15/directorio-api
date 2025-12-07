package com.directorio;

import com.directorio.model.Persona;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DirectorioTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void crearPersona_deberiaCrearYDevolverPersona() {

        Persona persona = Persona.builder()
                .nombre("Luis")
                .apellidoPaterno("Jimenez")
                .apellidoMaterno("Cortes")
                .identificacion("TEST123")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Persona> request = new HttpEntity<>(persona, headers);

        ResponseEntity<Persona> response = restTemplate.postForEntity(
                "/personas",
                request,
                Persona.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void buscarPersonaPorIdentificacion_deberiaDevolverPersona() {
        Persona persona = Persona.builder()
                .nombre("Buscar")
                .apellidoPaterno("Persona")
                .identificacion("BUSCAR123")
                .build();

        // Crear primero
        restTemplate.postForEntity("/personas", persona, Persona.class);

        // Buscar
        ResponseEntity<Persona> response = restTemplate.getForEntity(
                "/personas/identificacion/BUSCAR123",
                Persona.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNombre()).isEqualTo("Buscar");
    }

    @Test
    void eliminarPersona_deberiaEliminarCorrectamente() {
        Persona persona = Persona.builder()
                .nombre("Eliminar")
                .apellidoPaterno("Test")
                .identificacion("BORRAR999")
                .build();

        // Crear
        restTemplate.postForEntity("/personas", persona, Persona.class);

        // Eliminar
        restTemplate.delete("/personas/BORRAR999");

        // Intentar buscar → debe fallar
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/personas/identificacion/BORRAR999",
                String.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }


}
