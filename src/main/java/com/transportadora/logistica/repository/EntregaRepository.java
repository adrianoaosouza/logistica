package com.transportadora.logistica.repository;

import com.transportadora.logistica.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    Optional<Entrega> findByCodigoRastreamento(UUID codigoRastreamento);
}
