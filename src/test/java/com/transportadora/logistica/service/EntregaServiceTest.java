package com.transportadora.logistica.service;

import com.transportadora.logistica.dto.EntregaRequestDTO;
import com.transportadora.logistica.dto.EntregaResponseDTO;
import com.transportadora.logistica.entity.Entrega;
import com.transportadora.logistica.mapper.EntregaMapper;
import com.transportadora.logistica.repository.EntregaRepository;
import com.transportadora.logistica.utils.CodigoRastreioGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntregaServiceTest {

    @Mock
    private EntregaRepository entregaRepository;

    @Mock
    private CodigoRastreioGenerator rastreioGenerator;

    @InjectMocks
    private EntregaService entregaService;

    @Test
    void deveCriarEntregaComSucesso() {
        // Arrange
        EntregaRequestDTO requestDTO = new EntregaRequestDTO();
        requestDTO.setNomeDestinatario("João Silva");
        requestDTO.setEndereco("Rua das Laranjeiras, 123");

        // Mock do objeto salvo (simula o que o repository salvaria e devolveria)
        Entrega entregaSalva = Entrega.builder()
                .id(1L)
                .nomeDestinatario("João Silva")
                .endereco("Rua das Laranjeiras, 123")
                .codigoRastreamento(UUID.randomUUID())
                .dataCriacao(LocalDateTime.now())
                .dataEntregaEstimada(LocalDateTime.now().plusDays(5))
                .status(Entrega.StatusEntrega.PENDENTE)
                .build();

        when(entregaRepository.save(any(Entrega.class))).thenReturn(entregaSalva);

        // Act
        EntregaResponseDTO response = entregaService.criarEntrega(requestDTO);

        // Assert
        assertNotNull(response);
        assertEquals("João Silva", response.getNomeDestinatario());
        assertEquals("Rua das Laranjeiras, 123", response.getEndereco());
        assertNotNull(response.getCodigoRastreamento());
        assertEquals("PENDENTE", response.getStatus());

        verify(entregaRepository).save(any(Entrega.class));
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

        when(entregaRepository.findByCodigoRastreamento(codigo)).thenReturn(java.util.Optional.of(entrega));

        EntregaResponseDTO response = entregaService.buscarPorCodigo(codigo);

        assertNotNull(response);
        assertEquals(codigo, response.getCodigoRastreamento());
        verify(entregaRepository).findByCodigoRastreamento(codigo);
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

        when(entregaRepository.findAll()).thenReturn(List.of(entrega1, entrega2));

        List<EntregaResponseDTO> entregas = entregaService.listarEntregas();

        assertEquals(2, entregas.size());
        verify(entregaRepository).findAll();
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

        when(entregaRepository.findByCodigoRastreamento(codigo)).thenReturn(java.util.Optional.of(entrega));

        entregaService.cancelarEntrega(codigo);

        assertEquals(Entrega.StatusEntrega.CANCELADA, entrega.getStatus());
        verify(entregaRepository).save(entrega);
    }

}