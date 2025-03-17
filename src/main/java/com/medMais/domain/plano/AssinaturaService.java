package com.medMais.domain.plano;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medMais.domain.pessoa.paciente.PacienteService;
import com.medMais.domain.plano.dto.DataAtualizarPlano;
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

    public DataDetalhesAssinatura trocarPlano(String pacienteName, DataAtualizarPlano novoPlano) {
    	
        Assinatura assinatura = assinaturaRepository.findByUsuarioId(paciente.buscaPacienteLogin(pacienteName).getId())
                									.orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        Plano plano = planoRepository.findByTipoPlano(novoPlano.novoPlano())
                					 .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        assinatura.setPlano(plano);
        assinatura.setStatusPagamento(StatusPagamento.PENDENTE);
        assinaturaRepository.save(assinatura);
        
        //fazer um retorno do paypal ou outra forma de pagamento
        
        return new DataDetalhesAssinatura(assinatura);

    }

}
