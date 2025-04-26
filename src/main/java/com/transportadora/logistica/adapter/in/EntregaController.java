package com.transportadora.logistica.adapter.in;

import com.transportadora.logistica.dto.EntregaRequestDTO;
import com.transportadora.logistica.dto.EntregaResponseDTO;
import com.transportadora.logistica.service.EntregaService;

import com.transportadora.logistica.usecase.port.in.EntregaServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    private final EntregaServicePort entregaService;

    public EntregaController(EntregaServicePort entregaService) {
        this.entregaService = entregaService;
    }

    @PostMapping
    public ResponseEntity<EntregaResponseDTO> criar(@RequestBody EntregaRequestDTO dto) {
        return ResponseEntity.ok(entregaService.criarEntrega(dto));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<EntregaResponseDTO> buscar(@PathVariable UUID codigo) {
        return ResponseEntity.ok(entregaService.buscarPorCodigo(codigo));
    }

    @GetMapping
    public List<EntregaResponseDTO> listar() {
        return entregaService.listarEntregas();
    }

    @PostMapping("/{codigo}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable UUID codigo) {
        entregaService.cancelarEntrega(codigo);
        return ResponseEntity.ok().build();
    }

}
