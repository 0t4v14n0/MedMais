package com.medMais.domain.pessoa.medico.dto;

import com.medMais.domain.pessoa.dto.DataDetalhesPessoaPublico;
import com.medMais.domain.pessoa.medico.EspecialidadeMedica;
import com.medMais.domain.pessoa.medico.Medico;

public record DataDetalhesPublicoMedico(DataDetalhesPessoaPublico dataDetalhesPessoa,
		 							    String crm,
		 							    EspecialidadeMedica especialidadeMedica) {

	public DataDetalhesPublicoMedico(Medico medico){
		this(new DataDetalhesPessoaPublico(medico.getId(),
										   medico.getNome()),
										   medico.getCrm(),
										   medico.getEspecialidade());
	}
}
