package com.transportadora.logistica.service;


import com.transportadora.logistica.dto.EntregaRequestDTO;
import com.transportadora.logistica.dto.EntregaResponseDTO;
import com.transportadora.logistica.entity.Entrega;
import com.transportadora.logistica.mapper.EntregaMapper;
import com.transportadora.logistica.repository.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository repository;

    public EntregaResponseDTO criarEntrega(EntregaRequestDTO dto) {
        Entrega entrega = EntregaMapper.toEntity(dto);
        entrega.setCodigoRastreamento(UUID.randomUUID());
        entrega.setDataCriacao(LocalDateTime.now());
        entrega.setDataEntregaEstimada(LocalDateTime.now().plusDays(5));
        repository.save(entrega);
        return EntregaMapper.toDTO(entrega);
    }

    public EntregaResponseDTO buscarPorCodigo(UUID codigo) {
        Entrega entrega = repository.findByCodigoRastreamento(codigo)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
        return EntregaMapper.toDTO(entrega);
    }

    public List<EntregaResponseDTO> listarEntregas() {
        return repository.findAll().stream()
                .map(EntregaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void cancelarEntrega(UUID codigo) {
        Entrega entrega = repository.findByCodigoRastreamento(codigo)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
        if (entrega.getStatus() != Entrega.StatusEntrega.PENDENTE) {
            throw new RuntimeException("Entrega não pode ser cancelada");
        }
        entrega.setStatus(Entrega.StatusEntrega.CANCELADA);
        repository.save(entrega);
    }
}
