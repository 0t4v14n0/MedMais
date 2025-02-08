package com.medMais.domain.historicoDoenca.dto;

import java.time.LocalDate;

import com.medMais.domain.historicoDoenca.HistoricoDoenca;
import com.medMais.domain.historicoDoenca.enums.EstadoDoenca;
import com.medMais.domain.historicoDoenca.enums.Gravidade;

public record DataDetalhesHistoricoDoenca (String nomeDoenca,
		  								   String descricao,
		  								   LocalDate dataDiagnostico,
		  								   LocalDate dataRecuperacao,
		  								   EstadoDoenca estadoAtual,
		  								   String tratamento,
		  								   String medicamentos,
		  								   String observacoesMedicas,
		  								   Gravidade gravidade) {

	public DataDetalhesHistoricoDoenca(HistoricoDoenca historicoDoenca) {
		this(historicoDoenca.getNomeDaDoenca(),
				historicoDoenca.getDescricao(),
				historicoDoenca.getDataDiagnostico(),
				historicoDoenca.getDataRecuperacao(),
				historicoDoenca.getEstadoAtual(),
				historicoDoenca.getTratamento(),
				historicoDoenca.getMedicamentos(),
				historicoDoenca.getObservacoesMedicas(),
				historicoDoenca.getGravidade());
	}

}
