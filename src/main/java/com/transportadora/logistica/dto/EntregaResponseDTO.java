package com.transportadora.logistica.dto;

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

}
