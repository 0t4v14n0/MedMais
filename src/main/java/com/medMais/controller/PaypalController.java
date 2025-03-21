package com.medMais.controller;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.plano.AssinaturaService;
import com.medMais.domain.plano.enums.StatusPagamento;

@RestController
@RequestMapping("pagamento/paypal")
public class PaypalController {
	
	@Autowired
	public AssinaturaService assinaturaService;
    
    @PostMapping("/webhook")
    public ResponseEntity<String> receberNotificacao(@RequestBody Map<String, Object> payload) {
        String eventType = (String) payload.get("event_type");

        if ("PAYMENT.CAPTURE.COMPLETED".equals(eventType)) {
            // Pega os detalhes da transaaco
            AtomicReference<String> orderId = new AtomicReference<>("");

            Optional.ofNullable(payload.get("resource"))
                .filter(Map.class::isInstance)
                .map(Map.class::cast)
                .ifPresent(resource -> {
                    orderId.set((String) resource.get("id"));
                    //System.out.println("Pagamento confirmado para Order ID: " + orderId.get());
                });

            // Atualiza o banco de dados para marcar como pago
            //System.out.println("Pagamento confirmado para Order ID: " + orderId.get());
            assinaturaService.confirmarPagamento(StatusPagamento.PAGO, orderId.get());
        }

        return ResponseEntity.ok("Recebido com sucesso");
    }

}
