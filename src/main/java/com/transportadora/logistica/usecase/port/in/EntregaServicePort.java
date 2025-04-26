package com.transportadora.logistica.usecase.port.in;

import com.transportadora.logistica.dto.EntregaRequestDTO;
import com.transportadora.logistica.dto.EntregaResponseDTO;

import java.util.List;
import java.util.UUID;

public interface EntregaServicePort {
    EntregaResponseDTO criarEntrega(EntregaRequestDTO dto);
    EntregaResponseDTO buscarPorCodigo(UUID codigo);
    List<EntregaResponseDTO> listarEntregas();
    void cancelarEntrega(UUID codigo);
}
