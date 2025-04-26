package com.transportadora.logistica.mapper;

import com.transportadora.logistica.dto.EntregaRequestDTO;
import com.transportadora.logistica.dto.EntregaResponseDTO;
import com.transportadora.logistica.entity.Entrega;

public class EntregaMapper {

    public static Entrega toEntity(EntregaRequestDTO dto) {
        return Entrega.builder()
                .nomeDestinatario(dto.getNomeDestinatario())
                .endereco(dto.getEndereco())
                .status(Entrega.StatusEntrega.PENDENTE)
                .build();
    }

    public static EntregaResponseDTO toDTO(Entrega entrega) {
        return EntregaResponseDTO.builder()
                .codigoRastreamento(entrega.getCodigoRastreamento())
                .nomeDestinatario(entrega.getNomeDestinatario())
                .endereco(entrega.getEndereco())
                .dataCriacao(entrega.getDataCriacao())
                .dataEntregaEstimada(entrega.getDataEntregaEstimada())
                .status(entrega.getStatus().name())
                .build();
    }
}
