package com.directorio.service;

import com.directorio.model.Persona;
import com.directorio.repository.PersonaRepository;
import com.directorio.exception.PersonaNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectorioService {

    private final PersonaRepository personaRepository;

    // Crear o guardar persona

public Persona storePersona(Persona persona) {
    log.info("Guardando persona con identificacion {}", persona.getIdentificacion());
    
    String identificacion = persona.getIdentificacion();
    
    // Buscar si ya existe
    boolean existe = personaRepository.findByIdentificacion(identificacion).isPresent();
    
    if (existe) {
        log.error("ERROR: La identificación ya existe: {}", identificacion);
    }
    
    // Si no existe, guardar normalmente
    return personaRepository.save(persona);
}

    // Buscar persona por ID
    public Persona findPersona(Long id) {
        log.info("Buscando persona con id {}", id);

        return personaRepository.findById(id)
                .orElseThrow(() -> {
                log.error("Persona no encontrada con ID: {}", id);
                return new PersonaNotFoundException("Persona no encontrada con ID " + id);
            });
    }

    // Buscar persona por identificacion
    public Persona findPersonaByIdentificacion(String identificacion) {
        log.info("Buscando persona con identificacion {}", identificacion);

        return personaRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> {
                log.error("Persona no encontrada con identificacion: {}", identificacion);
                return new PersonaNotFoundException("Persona con identificacion " + identificacion + " no existe");
            });}

    // Listar todas las personas
    public List<Persona> findAll() {
        log.info("Listando todas las personas");
        return personaRepository.findAll();
    }

    // Listar personas paginadas
    public Page<Persona> findAllPaginated(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return personaRepository.findAll(pageable);
    }

    // Eliminar persona (IMPORTANTE: elimina también facturas)
    public void deletePersonaByIdentificacion(String identificacion) {
        log.info("Eliminando persona con identificacion {}", identificacion);

        Persona persona = personaRepository.findByIdentificacion(identificacion)
                .orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada con identificacion " + identificacion));

        personaRepository.delete(persona);
    }
}
