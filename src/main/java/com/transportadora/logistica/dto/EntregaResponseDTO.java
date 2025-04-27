package com.transportadora.logistica.dto;

import com.transportadora.logistica.entity.Entrega;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class EntregaResponseDTO {
    private UUID codigoRastreamento;
    private String nomeDestinatario;
    private String endereco;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEntregaEstimada;
    private String status;


    public static EntregaResponseDTO fromEntity(Entrega entrega) {
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
