package com.transportadora.logistica.controller;

import com.transportadora.logistica.dto.EntregaRequestDTO;
import com.transportadora.logistica.dto.EntregaResponseDTO;
import com.transportadora.logistica.service.EntregaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    private EntregaService service;

    @PostMapping
    public EntregaResponseDTO criarEntrega(@RequestBody EntregaRequestDTO dto) {
        return service.criarEntrega(dto);
    }

    @GetMapping("/{codigo}")
    public EntregaResponseDTO buscarEntrega(@PathVariable UUID codigo) {
        return service.buscarPorCodigo(codigo);
    }

    @GetMapping
    public List<EntregaResponseDTO> listarEntregas() {
        return service.listarEntregas();
    }

    @PutMapping("/{codigo}/cancelar")
    public void cancelarEntrega(@PathVariable UUID codigo) {
        service.cancelarEntrega(codigo);
    }
}
