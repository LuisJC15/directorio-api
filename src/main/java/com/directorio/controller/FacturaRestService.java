package com.directorio.controller;

import com.directorio.model.Factura;
import com.directorio.service.VentasService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
@RequiredArgsConstructor
@Slf4j
public class FacturaRestService {

    private final VentasService ventasService;

    // Crear factura
    @PostMapping("/{identificacionPersona}")
    public ResponseEntity<Factura> crearFactura(
            @Valid @RequestBody Factura factura,
            @PathVariable String identificacionPersona) {

        log.info("POST /facturas/{}", identificacionPersona);

        Factura nueva = ventasService.storeFactura(factura, identificacionPersona);

        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    // Obtener facturas de una persona
    @GetMapping("/{identificacionPersona}")
    public ResponseEntity<List<Factura>> obtenerFacturas(@PathVariable String identificacionPersona) {

        log.info("GET /facturas/{}", identificacionPersona);

        List<Factura> facturas = ventasService.findFacturasByPersona(identificacionPersona);

        return ResponseEntity.ok(facturas);
    }
}
