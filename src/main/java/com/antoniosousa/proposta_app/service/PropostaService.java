package com.antoniosousa.proposta_app.service;

import com.antoniosousa.proposta_app.dto.PropostaRequestDto;
import com.antoniosousa.proposta_app.dto.PropostaResponseDto;
import com.antoniosousa.proposta_app.entity.Proposta;
import com.antoniosousa.proposta_app.mapper.PropostaMapper;
import com.antoniosousa.proposta_app.repositories.PropostaRepository;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PropostaService {

    private final PropostaRepository propostaRepository;

    private final NotificacaoRabbitService notificacaoRabbitService;

    private final String exchange;

    private static final BigDecimal fixedValue = new BigDecimal(10000);

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

        int priority = proposta.getUsuario().getRenda().compareTo(fixedValue) > 0 ? 10 : 5;
        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setPriority(priority);
            return message;
        };

        notificarRabbitMQ(proposta, messagePostProcessor);

        return PropostaMapper.INSTANCE.convertEntitytoDto(proposta);
    }

    private void notificarRabbitMQ(Proposta proposta, MessagePostProcessor messagePostProcessor) {
        try {
            notificacaoRabbitService.notify(proposta, exchange, messagePostProcessor);
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
