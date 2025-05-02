package com.medMais.domain.pessoa.medico.dto;

import java.math.BigDecimal;

import com.medMais.domain.pessoa.dto.DataDetalhesPessoa;
import com.medMais.domain.pessoa.medico.EspecialidadeMedica;
import com.medMais.domain.pessoa.medico.Medico;

public record DataDetalhesMedico(DataDetalhesPessoa dataDetalhesPessoa,
	     						 String crm,
	     						 BigDecimal  valorConsulta,
	     						 EspecialidadeMedica especialidadeMedica) {
	
	public DataDetalhesMedico(Medico medico){
		this(new DataDetalhesPessoa(medico.getNome(),
									medico.getSaldo(),
									medico.getCpf(),
									medico.getEmail(),
									medico.getTelefone(),
									medico.getDataNascimento(),
									medico.getEndereco()),
									medico.getCrm(),
									medico.getValorConsulta(),
									medico.getEspecialidade());
	}
}
