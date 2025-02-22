package com.medMais.domain.historicotransacoes.dto;

import java.time.LocalDateTime;

import com.medMais.domain.historicotransacoes.HistoricoTransacoes;
import com.medMais.domain.historicotransacoes.enums.StatusTransacao;
import com.medMais.domain.pessoa.medico.dto.DataDetalhesMedico;
import com.medMais.domain.pessoa.paciente.dto.DataDetalhesPaciente;

public record DataDetalhesHistoricoTransacoes(Long id,
											  Double valor,
											  LocalDateTime dataTransacao,
											  StatusTransacao status,
											  Long remetente,
											  DataDetalhesPaciente dataDetalhesPaciente,
											  DataDetalhesMedico dataDetalhesMedico) {

	public DataDetalhesHistoricoTransacoes(HistoricoTransacoes data) {
		this(data.getId(),
			 data.getValor(),
			 data.getDataTransacao(),
			 data.getStatus(),
			 data.getRemetente(),
			 new DataDetalhesPaciente(data.getPaciente()),
			 new DataDetalhesMedico(data.getMedico()));
	}
	
}
