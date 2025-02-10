package com.medMais.domain.pessoa.paciente.dto;

import com.medMais.domain.pessoa.dto.DataDetalhesPessoa;
import com.medMais.domain.pessoa.paciente.Paciente;
import com.medMais.domain.pessoa.paciente.enums.TipoPlano;
import com.medMais.domain.pessoa.paciente.enums.TipoSanguineo;

public record DataDetalhesPaciente(DataDetalhesPessoa dataDetalhesPessoa,
								   TipoSanguineo tipoSanguineo,
								   String numeroCarteiraPlano,
								   String contatoEmergencia,
								   Double altura,
								   Double peso,
								   Double imc,
								   TipoPlano plano) {
	
	public DataDetalhesPaciente(Paciente paciente){
		this(new DataDetalhesPessoa(paciente.getNome(),
								    paciente.getCpf(),
								    paciente.getEmail(),
								    paciente.getTelefone(),
								    paciente.getDataNascimento(),
								    paciente.getEndereco()),
				paciente.getTipoSanguineo(),
				paciente.getNumeroCarteiraPlano(),
				paciente.getContatoEmergencia(),
				paciente.getAltura(),
				paciente.getPeso(),
				paciente.getIMC(),
				paciente.getTipoPlano());
	}
}
