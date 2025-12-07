package com.directorio.repository;

import com.directorio.model.Factura;
import com.directorio.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Long> {

    List<Factura> findByPersona(Persona persona);
}