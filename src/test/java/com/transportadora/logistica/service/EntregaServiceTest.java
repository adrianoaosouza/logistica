package com.transportadora.logistica.service;

import com.transportadora.logistica.dto.EntregaRequestDTO;
import com.transportadora.logistica.dto.EntregaResponseDTO;
import com.transportadora.logistica.entity.Entrega;
import com.transportadora.logistica.mapper.EntregaMapper;
import com.transportadora.logistica.repository.EntregaRepository;
import com.transportadora.logistica.usecase.port.out.EntregaRepositoryPort;
import com.transportadora.logistica.utils.CodigoRastreioGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntregaServiceTest {

    @Mock
    private EntregaRepositoryPort entregaRepositoryPort;

    @Mock
    private CodigoRastreioGenerator rastreioGenerator;

    @InjectMocks
    private EntregaService entregaService;

    @Test
    void deveCriarEntregaComSucesso() {
        EntregaRequestDTO requestDTO = new EntregaRequestDTO();
        requestDTO.setNomeDestinatario("João Silva");
        requestDTO.setEndereco("Rua A, 123");

        Entrega entregaSalva = Entrega.builder()
                .id(1L)
                .nomeDestinatario(requestDTO.getNomeDestinatario())
                .endereco(requestDTO.getEndereco())
                .codigoRastreamento(UUID.randomUUID())
                .dataCriacao(LocalDateTime.now())
                .dataEntregaEstimada(LocalDateTime.now().plusDays(5))
                .status(Entrega.StatusEntrega.PENDENTE)
                .build();

        when(entregaRepositoryPort.salvar(any(Entrega.class))).thenReturn(entregaSalva);

        EntregaResponseDTO response = entregaService.criarEntrega(requestDTO);

        assertNotNull(response);
        assertEquals(entregaSalva.getNomeDestinatario(), response.getNomeDestinatario());
        assertEquals(entregaSalva.getEndereco(), response.getEndereco());
        verify(entregaRepositoryPort).salvar(any(Entrega.class));
    }


    @Test
    void deveBuscarEntregaPorCodigoComSucesso() {
        UUID codigo = UUID.randomUUID();
        Entrega entrega = Entrega.builder()
                .id(1L)
                .codigoRastreamento(codigo)
                .nomeDestinatario("Maria Souza")
                .endereco("Av. Central, 456")
                .dataCriacao(LocalDateTime.now())
                .dataEntregaEstimada(LocalDateTime.now().plusDays(5))
                .status(Entrega.StatusEntrega.PENDENTE)
                .build();

        when(entregaRepositoryPort.buscarPorCodigo(codigo)).thenReturn(Optional.of(entrega));

        EntregaResponseDTO response = entregaService.buscarPorCodigo(codigo);

        assertNotNull(response);
        assertEquals(codigo, response.getCodigoRastreamento());
        verify(entregaRepositoryPort).buscarPorCodigo(codigo);
    }

    @Test
    void deveListarTodasEntregasComSucesso() {
        Entrega entrega1 = Entrega.builder()
                .id(1L)
                .nomeDestinatario("João Silva")
                .endereco("Rua A, 123")
                .codigoRastreamento(UUID.randomUUID())
                .dataCriacao(LocalDateTime.now())
                .dataEntregaEstimada(LocalDateTime.now().plusDays(5))
                .status(Entrega.StatusEntrega.PENDENTE)
                .build();

        Entrega entrega2 = Entrega.builder()
                .id(2L)
                .nomeDestinatario("Maria Souza")
                .endereco("Av. Central, 456")
                .codigoRastreamento(UUID.randomUUID())
                .dataCriacao(LocalDateTime.now())
                .dataEntregaEstimada(LocalDateTime.now().plusDays(5))
                .status(Entrega.StatusEntrega.PENDENTE)
                .build();

        when(entregaRepositoryPort.listarTodos()).thenReturn(List.of(entrega1, entrega2));

        List<EntregaResponseDTO> entregas = entregaService.listarEntregas();

        assertEquals(2, entregas.size());
        verify(entregaRepositoryPort).listarTodos();
    }

    @Test
    void deveCancelarEntregaComSucesso() {
        UUID codigo = UUID.randomUUID();
        Entrega entrega = Entrega.builder()
                .id(1L)
                .codigoRastreamento(codigo)
                .nomeDestinatario("Carlos Mendes")
                .endereco("Rua B, 789")
                .dataCriacao(LocalDateTime.now())
                .dataEntregaEstimada(LocalDateTime.now().plusDays(5))
                .status(Entrega.StatusEntrega.PENDENTE)
                .build();

        when(entregaRepositoryPort.buscarPorCodigo(codigo)).thenReturn(Optional.of(entrega));

        entregaService.cancelarEntrega(codigo);

        assertEquals(Entrega.StatusEntrega.CANCELADA, entrega.getStatus());
        verify(entregaRepositoryPort).salvar(entrega);
    }

}