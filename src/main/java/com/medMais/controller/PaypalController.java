package com.medMais.controller;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medMais.domain.formasDePagamento.paypal.PaypalService;
import com.medMais.domain.plano.AssinaturaService;

@RestController
@RequestMapping("pagamento/paypal")
public class PaypalController {

    @Autowired
    private PaypalService paypalService;
    
    @Autowired
    private AssinaturaService assinaturaService;

    @PostMapping("/confirmar/{orderId}")
    public ResponseEntity<String> confirmarPagamento(@PathVariable String orderId) {
    	
        boolean pago = paypalService.capturarPagamento(orderId); 
        
        if(pago) {
        	assinaturaService.confirmarPagamento(pago,orderId);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao confirmar pagamento.");
    }
    
    @PostMapping("/webhook")
    public ResponseEntity<String> receberNotificacao(@RequestBody Map<String, Object> payload) {
        String eventType = (String) payload.get("event_type");

        if ("PAYMENT.CAPTURE.COMPLETED".equals(eventType)) {
            // Pega os detalhes da transação
            AtomicReference<String> orderId = new AtomicReference<>("");

            Optional.ofNullable(payload.get("resource"))
                .filter(Map.class::isInstance)
                .map(Map.class::cast)
                .ifPresent(resource -> {
                    orderId.set((String) resource.get("id"));
                    System.out.println("Pagamento confirmado para Order ID: " + orderId.get());
                });

            // Atualiza o banco de dados para marcar como pago
            System.out.println("Pagamento confirmado para Order ID: " + orderId.get());
        }

        return ResponseEntity.ok("Recebido com sucesso");
    }

}
