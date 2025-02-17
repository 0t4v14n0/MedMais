package com.medMais.domain.consulta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.medMais.domain.consulta.Consulta;
import com.medMais.domain.consulta.enums.StatusConsulta;
import com.medMais.domain.pessoa.medico.dto.DataDetalhesMedico;
import com.medMais.domain.pessoa.paciente.dto.DataDetalhesPaciente;

public record DataDetalhesConsulta(Long id,
								   DataDetalhesPaciente dataDetalhesPaciente,
								   DataDetalhesMedico dataDetalhesMedico,
								   BigDecimal valorConsulta,
								   StatusConsulta statusConsula,
								   LocalDateTime data,
								   String obs) {
	
	public DataDetalhesConsulta(Consulta consulta){
		this(consulta.getId(),
			 new DataDetalhesPaciente(consulta.getPaciente()),
			 new DataDetalhesMedico(consulta.getMedico()),
			 consulta.getValorConsulta(),
			 consulta.getStatusConsulta(),
			 consulta.getData(),
			 consulta.getObservacoes());
	}
}
