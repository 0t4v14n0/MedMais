package com.medMais.domain.formasDePagamento.paypal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braintreepayments.http.HttpResponse;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;

@Service
public class PaypalService {

    @Autowired
    private PayPalHttpClient payPalHttpClient;

    public String criarPedido(Double valor, String moeda, String returnUrl, String cancelUrl) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.intent("CAPTURE");

        // Definir valor do pagamento
        AmountWithBreakdown amount = new AmountWithBreakdown()
                .currencyCode(moeda)
                .value(String.format("%.2f", valor));

        // Criar unidade de compra
        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest();
        purchaseUnit.amount(amount);

        List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>();
        purchaseUnits.add(purchaseUnit);
        orderRequest.purchaseUnits(purchaseUnits);

        // Definir URLs de retorno e cancelamento
        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl(returnUrl)
                .cancelUrl(cancelUrl)
                .brandName("Med Mais")
                .landingPage("BILLING")
                .userAction("PAY_NOW");

        orderRequest.applicationContext(applicationContext);

        OrdersCreateRequest request = new OrdersCreateRequest().requestBody(orderRequest);

        try {
            HttpResponse<Order> response = payPalHttpClient.execute(request);
            if (response.statusCode() == 201) {
                // Retornar link de pagamento para o usuário
                return response.result().links().stream()
                        .filter(link -> "approve".equals(link.rel()))
                        .findFirst()
                        .map(LinkDescription::href)
                        .orElse(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean capturarPagamento(String orderId) {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);

        try {
            HttpResponse<Order> response = payPalHttpClient.execute(request);
            if (response.statusCode() == 201) {
                // Confirmar se o pagamento foi aprovado
                return response.result().status().equals("COMPLETED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
