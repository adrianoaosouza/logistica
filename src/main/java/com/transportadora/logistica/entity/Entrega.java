package com.transportadora.logistica.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeDestinatario;
    private String endereco;

    private UUID codigoRastreamento;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataEntregaEstimada;

    @Enumerated(EnumType.STRING)
    private StatusEntrega status;

    public enum StatusEntrega {
        PENDENTE, ENTREGUE, CANCELADA
    }


}


