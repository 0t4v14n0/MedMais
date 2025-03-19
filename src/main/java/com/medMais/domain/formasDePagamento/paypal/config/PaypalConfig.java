package com.medMais.domain.formasDePagamento.paypal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

@Configuration
public class PaypalConfig {
	
	@Value("${paypal.client-id}")
	private String clientId;
	
	@Value("${paypal.client-secret}")
	private String clientSecret;
	
	@Value("${paypal.client-mode}")
	private String mode;
	
    @Bean
    public PayPalHttpClient payPalHttpClient() {
        PayPalEnvironment environment = "sandbox".equalsIgnoreCase(mode)
                ? new PayPalEnvironment.Sandbox(clientId, clientSecret)
                : new PayPalEnvironment.Live(clientId, clientSecret);

        return new PayPalHttpClient(environment);
    }

}
