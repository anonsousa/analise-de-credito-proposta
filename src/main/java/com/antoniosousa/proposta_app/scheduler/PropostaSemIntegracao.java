package com.antoniosousa.proposta_app.scheduler;

import com.antoniosousa.proposta_app.entity.Proposta;
import com.antoniosousa.proposta_app.repositories.PropostaRepository;
import com.antoniosousa.proposta_app.service.NotificacaoRabbitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PropostaSemIntegracao {

    private  final PropostaRepository propostaRepository;

    private final NotificacaoRabbitService notificacaoRabbitService;

    private final String exchange;

    private static final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    public PropostaSemIntegracao(PropostaRepository propostaRepository,
                                 NotificacaoRabbitService notificacaoRabbitService,
                                 @Value("${rabbitmq.propostapendente.exchange}") String exchange) {
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void searchPropostasSemIntegração(){
        logger.info("Executando busca de propostas que nao foram integradas");
        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {

                notificacaoRabbitService.notify(proposta, exchange);
                updateProposta(proposta);

            } catch (RuntimeException e) {

                logger.error(e.getMessage());
            }
        });
    }

    private void updateProposta(Proposta proposta) {
        proposta.setIntegrada(true);
        propostaRepository.save(proposta);
    }
}
