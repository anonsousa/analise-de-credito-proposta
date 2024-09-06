package com.antoniosousa.proposta_app.controller;

import com.antoniosousa.proposta_app.dto.PropostaRequestDto;
import com.antoniosousa.proposta_app.dto.PropostaResponseDto;
import com.antoniosousa.proposta_app.service.PropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaResponseDto> create(@RequestBody PropostaRequestDto requestDto) {
        PropostaResponseDto responseDto = propostaService.create(requestDto);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(responseDto.getId())
                        .toUri())
                        .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<PropostaResponseDto>> getPropostas() {
        return ResponseEntity.ok(propostaService.getPropostas());
    }
}
