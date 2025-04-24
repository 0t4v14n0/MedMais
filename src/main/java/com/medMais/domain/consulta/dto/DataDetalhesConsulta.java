package com.medMais.domain.consulta.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.medMais.domain.consulta.Consulta;
import com.medMais.domain.consulta.enums.StatusConsulta;
import com.medMais.domain.pessoa.medico.dto.DataDetalhesPublicoMedico;
import com.medMais.domain.pessoa.paciente.dto.DataDetalhesPublicoPaciente;

public record DataDetalhesConsulta(Long id,
								   DataDetalhesPublicoPaciente dataDetalhesPaciente,
								   DataDetalhesPublicoMedico dataDetalhesMedico,
								   BigDecimal valorConsulta,
								   StatusConsulta statusConsula,
								   LocalDateTime data,
								   String obs) {
	
	public DataDetalhesConsulta(Consulta consulta){
		this(consulta.getId(),
			 new DataDetalhesPublicoPaciente(consulta.getPaciente()),
			 new DataDetalhesPublicoMedico(consulta.getMedico()),
			 consulta.getValorConsulta(),
			 consulta.getStatusConsulta(),
			 consulta.getData(),
			 consulta.getObservacoes());
	}
}
