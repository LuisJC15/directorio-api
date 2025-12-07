package com.directorio.controller;

import com.directorio.model.Persona;
import com.directorio.service.DirectorioService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")
@RequiredArgsConstructor
@Slf4j
public class DirectorioRestService {

    private final DirectorioService directorioService;

    // Crear persona
    @PostMapping
    public ResponseEntity<Persona> crearPersona(@Valid @RequestBody Persona persona) {
        log.info("POST /personas");
        Persona nueva = directorioService.storePersona(persona);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // Obtener persona por ID
    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPersonaPorId(@PathVariable Long id) {
        log.info("GET /personas/{}", id);
        Persona persona = directorioService.findPersona(id);
        return ResponseEntity.ok(persona);
    }

    // Obtener persona por identificacion
    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<Persona> obtenerPersonaPorIdentificacion(@PathVariable String identificacion) {
        log.info("GET /personas/identificacion/{}", identificacion);
        Persona persona = directorioService.findPersonaByIdentificacion(identificacion);
        return ResponseEntity.ok(persona);
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<Persona>> obtenerTodas() {
        log.info("GET /personas");
        return ResponseEntity.ok(directorioService.findAll());
    }

    @GetMapping("/paginated")
    public ResponseEntity<?> obtenerPaginado(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
    log.info("GET /personas/paginated?page={}&size={}", page, size);
    return ResponseEntity.ok(directorioService.findAllPaginated(page, size));
    }

    // Eliminar persona por identificacion
    @DeleteMapping("/{identificacion}")
    public ResponseEntity<Void> eliminar(@PathVariable String identificacion) {
        log.info("DELETE /personas/{}", identificacion);
        directorioService.deletePersonaByIdentificacion(identificacion);
        return ResponseEntity.noContent().build(); // 204
    }
}