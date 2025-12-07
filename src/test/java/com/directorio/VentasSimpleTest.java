package com.directorio;

import com.directorio.model.Factura;
import com.directorio.model.Persona;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VentasSimpleTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void crearYListarFacturas_deberiaFuncionar() {
        // 1. Crear persona
        String identificacion = "TEST-VENTAS";
        
        Persona persona = Persona.builder()
                .nombre("Cliente")
                .apellidoPaterno("Ventas")
                .identificacion(identificacion)
                .build();

        restTemplate.postForEntity("/personas", persona, Persona.class);

        // 2. Crear factura
        Factura factura = Factura.builder()
                .fecha(LocalDate.now())
                .monto(999.99)
                .build();

        HttpEntity<Factura> request = new HttpEntity<>(factura);

        ResponseEntity<Factura> crearResponse = restTemplate.postForEntity(
                "/facturas/" + identificacion,
                request,
                Factura.class
        );

        // 3. Verificar creación
        assertThat(crearResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Factura facturaCreada = crearResponse.getBody();
        assertThat(facturaCreada.getId()).isNotNull();
        assertThat(facturaCreada.getPersona().getIdentificacion()).isEqualTo(identificacion);

        // 4. Listar facturas de la persona
        ResponseEntity<Factura[]> listarResponse = restTemplate.getForEntity(
                "/facturas/" + identificacion,
                Factura[].class
        );

        // 5. Verificar listado
        assertThat(listarResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(listarResponse.getBody()).hasSize(1);
        assertThat(listarResponse.getBody()[0].getMonto()).isEqualTo(999.99);
    }

    @Test
    void crearFacturaSinPersona_deberiaFallar() {
        // Intentar crear factura para persona que no existe
        Factura factura = Factura.builder()
                .fecha(LocalDate.now())
                .monto(100.00)
                .build();

        HttpEntity<Factura> request = new HttpEntity<>(factura);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/facturas/NO_EXISTE_123",
                request,
                String.class
        );

        // Debería fallar (404 o 400)
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}