package com.transportadora.logistica.adapter.out;

import com.transportadora.logistica.entity.Entrega;
import com.transportadora.logistica.repository.EntregaRepository;
import com.transportadora.logistica.usecase.port.out.EntregaRepositoryPort;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EntregaRepositoryAdapter implements EntregaRepositoryPort {

    private final EntregaRepository entregaRepository;

    public EntregaRepositoryAdapter(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }

    @Override
    public Entrega salvar(Entrega entrega) {
        return entregaRepository.save(entrega);
    }

    @Override
    public Optional<Entrega> buscarPorCodigo(UUID codigo) {
        return entregaRepository.findByCodigoRastreamento(codigo);
    }

    @Override
    public List<Entrega> listarTodos() {
        return entregaRepository.findAll();
    }
}
