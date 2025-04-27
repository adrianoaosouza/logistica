package com.transportadora.logistica.service;


import com.transportadora.logistica.dto.EntregaRequestDTO;
import com.transportadora.logistica.dto.EntregaResponseDTO;
import com.transportadora.logistica.entity.Entrega;
import com.transportadora.logistica.usecase.port.in.EntregaServicePort;
import com.transportadora.logistica.usecase.port.out.EntregaRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EntregaService implements EntregaServicePort {

    private final EntregaRepositoryPort repository;

    public EntregaService(EntregaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public EntregaResponseDTO criarEntrega(EntregaRequestDTO dto) {
        Entrega entrega = Entrega.builder()
                .nomeDestinatario(dto.getNomeDestinatario())
                .endereco(dto.getEndereco())
                .codigoRastreamento(UUID.randomUUID())
                .dataCriacao(LocalDateTime.now())
                .dataEntregaEstimada(LocalDateTime.now().plusDays(5))
                .status(Entrega.StatusEntrega.PENDENTE)
                .build();

        Entrega salva = repository.salvar(entrega);
        return EntregaResponseDTO.fromEntity(salva);
    }

    @Override
    public EntregaResponseDTO buscarPorCodigo(UUID codigo) {
        Entrega entrega = repository.buscarPorCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
        return EntregaResponseDTO.fromEntity(entrega);
    }

    @Override
    public List<EntregaResponseDTO> listarEntregas() {
        return repository.listarTodos()
                .stream()
                .map(EntregaResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelarEntrega(UUID codigo) {
        Entrega entrega = repository.buscarPorCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));

        entrega.setStatus(Entrega.StatusEntrega.CANCELADA);
        repository.salvar(entrega);
    }
}
