package com.medMais.domain.plano;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medMais.domain.pessoa.paciente.PacienteService;
import com.medMais.domain.plano.dto.DataDetalhesAssinatura;
import com.medMais.domain.plano.enums.StatusPagamento;

@Service
public class AssinaturaService {
	
    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @Autowired
    private PlanoRepository planoRepository;
    
    @Autowired
    private PacienteService paciente;

    public DataDetalhesAssinatura trocarPlano(String pacienteName, String novoPlano) {
    	
        Assinatura assinatura = assinaturaRepository.findByUsuarioId(paciente.buscaPacienteLogin(pacienteName).getId())
                									.orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        Plano plano = planoRepository.findByNome(novoPlano)
                					 .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        assinatura.setPlano(plano);
        assinatura.setStatusPagamento(StatusPagamento.PENDENTE);
        assinaturaRepository.save(assinatura);
        
        //fazer um retorno do paypal ou outra forma de pagamento
        
//        String returnUrl = "https://minhaapi.com/paypal/confirmacao"; // URL de retorno após pagamento
//        String cancelUrl = "https://minhaapi.com/paypal/cancelado";  // URL caso o usuário cancele o pagamento
//        

        return new DataDetalhesAssinatura(assinatura, null);

    }

	public ResponseEntity<String> confirmarPagamento(StatusPagamento pago,String orderId) {
		
            Assinatura assinatura = assinaturaRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

            assinatura.setStatusPagamento(StatusPagamento.PAGO);
            assinaturaRepository.save(assinatura);

            return ResponseEntity.ok("Pagamento confirmado!");     
        
	}

}
