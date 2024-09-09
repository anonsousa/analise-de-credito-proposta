package com.antoniosousa.proposta_app.service;

import com.antoniosousa.proposta_app.dto.PropostaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notificar(PropostaResponseDto proposta) {
        messagingTemplate.convertAndSend("/propostas", proposta);

    }
}
