package com.medMais.domain.pessoa.paciente.dto;

import com.medMais.domain.pessoa.dto.DataDetalhesPessoaPublico;
import com.medMais.domain.pessoa.paciente.Paciente;
import com.medMais.domain.pessoa.paciente.enums.TipoPlano;

public record DataDetalhesPublicoPaciente(DataDetalhesPessoaPublico dataDetalhesPessoa,
		   								  String numeroCarteiraPlano,
		   								  TipoPlano plano) {

		public DataDetalhesPublicoPaciente(Paciente paciente){
			this(new DataDetalhesPessoaPublico(paciente.getId(),
											   paciente.getNome()),
			paciente.getNumeroCarteiraPlano(),
			paciente.getTipoPlano());
		}

}
