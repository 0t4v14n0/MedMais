package com.medMais.domain.pessoa.paciente.dto;

import com.medMais.domain.pessoa.dto.DataDetalhesPessoa;
import com.medMais.domain.pessoa.paciente.Paciente;

public record DataDetalhesPaciente(DataDetalhesPessoa dataDetalhesPessoa) {
	
	public DataDetalhesPaciente(Paciente paciente){
		this(new DataDetalhesPessoa(paciente.getNome(),
								    paciente.getCpf(),
								    paciente.getEmail(),
								    paciente.getTelefone(),
								    paciente.getDataNascimento(),
								    paciente.getEndereco()));
	}
}
