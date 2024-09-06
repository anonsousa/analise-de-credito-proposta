package com.antoniosousa.proposta_app.controller;

import com.antoniosousa.proposta_app.dto.PropostaRequestDto;
import com.antoniosousa.proposta_app.dto.PropostaResponseDto;
import com.antoniosousa.proposta_app.service.PropostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaResponseDto> create(@RequestBody PropostaRequestDto requestDto) {
        PropostaResponseDto responseDto = propostaService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
