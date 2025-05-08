package com.medMais.domain.historicotransacoes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.medMais.domain.historicotransacoes.dto.DataDetalhesHistoricoTransacoes;
import com.medMais.domain.historicotransacoes.enums.StatusTransacao;
import com.medMais.domain.pessoa.medico.Medico;
import com.medMais.domain.pessoa.paciente.Paciente;

@Service
public class HistoricoTransacoesService {
	
	@Autowired
	private HistoricoTransacoesRepository historicoTransacoesRepository;

	public ResponseEntity<Page<DataDetalhesHistoricoTransacoes>> buscaHistoricoStatus(StatusTransacao status,
																	       			  String name,
																	       			  Pageable pageable) {
	    Page<HistoricoTransacoes> historico;

	    if (status.equals(StatusTransacao.TODAS)) {
	        historico = historicoTransacoesRepository.findByAllForMedicoOrPaciente(name, pageable);		
	    } else {
	        historico = historicoTransacoesRepository.findByStatusForMedicoOrPaciente(status, name, pageable);
	    }

	    if (historico == null || historico.isEmpty()) {
	        throw new RuntimeException("Ainda não tem histórico de transações!");
	    }

	    Page<DataDetalhesHistoricoTransacoes> dto = historico.map(DataDetalhesHistoricoTransacoes::new);
	    return ResponseEntity.ok(dto);
	}
	
	//metodo para na orepetir codigo
	public void addHistorico(Medico medico, Paciente paciente, String name, StatusTransacao status) {
	    HistoricoTransacoes h = new HistoricoTransacoes();
	    h.setMedico(medico);
	    h.setPaciente(paciente);

	    Long remetenteId;
	    boolean remetenteEhMedico = medico.getLogin().equals(name);
	    remetenteId = remetenteEhMedico ? medico.getId() : paciente.getId();
	    h.setRemetente(remetenteId);

	    double valorConsulta = medico.getValorConsulta().doubleValue();
	    double valor;

	    switch (status) {
	        case CANCELADO:
	            valor = remetenteEhMedico ? -valorConsulta : valorConsulta;
	            break;

	        case AGENDADO:
	            valor = remetenteEhMedico ? valorConsulta : -valorConsulta;
	            break;

	        default:
	            valor = 0;
	            break;
	    }

	    h.setValor(valor);
	    h.setStatus(status);

	    historicoTransacoesRepository.save(h);
	}

	public List<StatusTransacao> buscaHistoricoDisponivelStatus(String name) {
	    List<StatusTransacao> result = historicoTransacoesRepository.findDistinctStatusTransacao(name);
	    
	    if (result == null) {
	        throw new RuntimeException("Falha ao buscar status de transações: resultado nulo.");
	    }

	    return result;
	}

}
