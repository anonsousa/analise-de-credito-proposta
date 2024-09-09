package com.antoniosousa.proposta_app.service;

import com.antoniosousa.proposta_app.dto.PropostaRequestDto;
import com.antoniosousa.proposta_app.dto.PropostaResponseDto;
import com.antoniosousa.proposta_app.entity.Proposta;
import com.antoniosousa.proposta_app.mapper.PropostaMapper;
import com.antoniosousa.proposta_app.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;

    private final NotificacaoRabbitService notificacaoRabbitService;

    private final String exchange;

    public PropostaService(@Value("${rabbitmq.propostapendente.exchange}") String exchange,
                           NotificacaoRabbitService notificacaoRabbitService,
                           PropostaRepository propostaRepository) {
        this.exchange = exchange;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.propostaRepository = propostaRepository;
    }

    @Transactional
    public PropostaResponseDto create(PropostaRequestDto propostaRequestDto) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(propostaRequestDto);
        propostaRepository.save(proposta);

        notificarRabbitMQ(proposta);

        return PropostaMapper.INSTANCE.convertEntitytoDto(proposta);
    }

    private void notificarRabbitMQ(Proposta proposta) {
        try {
            notificacaoRabbitService.notify(proposta, exchange);
        } catch (RuntimeException e) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }

    }

    @Transactional(readOnly = true)
    public List<PropostaResponseDto> getPropostas() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());
    }
}
