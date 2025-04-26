package com.transportadora.logistica.usecase.port.out;

import com.transportadora.logistica.entity.Entrega;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

public interface EntregaRepositoryPort {
    Entrega salvar(Entrega entrega);
    Optional<Entrega> buscarPorCodigo(UUID codigo);
    List<Entrega> listarTodos();

}
