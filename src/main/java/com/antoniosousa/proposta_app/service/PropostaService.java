package com.antoniosousa.proposta_app.service;

import com.antoniosousa.proposta_app.dto.PropostaRequestDto;
import com.antoniosousa.proposta_app.dto.PropostaResponseDto;
import com.antoniosousa.proposta_app.entity.Proposta;
import com.antoniosousa.proposta_app.mapper.PropostaMapper;
import com.antoniosousa.proposta_app.repositories.PropostaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;

    public PropostaService(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @Transactional
    public PropostaResponseDto create(PropostaRequestDto propostaRequestDto) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(propostaRequestDto);
        propostaRepository.save(proposta);

        return PropostaMapper.INSTANCE.convertEntitytoDto(proposta);
    }

    @Transactional(readOnly = true)
    public List<PropostaResponseDto> getPropostas() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());
    }
}
