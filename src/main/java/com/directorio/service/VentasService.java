package com.directorio.service;

import com.directorio.model.Factura;
import com.directorio.model.Persona;
import com.directorio.repository.FacturaRepository;
import com.directorio.repository.PersonaRepository;
import com.directorio.exception.PersonaNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentasService {

    private final FacturaRepository facturaRepository;
    private final PersonaRepository personaRepository;

    // Guardar factura
    public Factura storeFactura(Factura factura, String identificacionPersona) {
        log.info("Guardando factura para persona {}", identificacionPersona);

        Persona persona = personaRepository.findByIdentificacion(identificacionPersona)
                .orElseThrow(() -> new PersonaNotFoundException(
                        "No se puede guardar factura. Persona no encontrada con identificacion " + identificacionPersona));

        factura.setPersona(persona);

        return facturaRepository.save(factura);
    }

    // Buscar facturas por persona
    public List<Factura> findFacturasByPersona(String identificacionPersona) {

        log.info("Buscando facturas de persona {}", identificacionPersona);

        Persona persona = personaRepository.findByIdentificacion(identificacionPersona)
                .orElseThrow(() -> new PersonaNotFoundException(
                        "No se pueden buscar facturas. Persona no encontrada con identificacion " + identificacionPersona));

        return facturaRepository.findByPersona(persona);
    }
}
