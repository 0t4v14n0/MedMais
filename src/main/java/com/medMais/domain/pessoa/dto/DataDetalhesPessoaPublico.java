package com.medMais.domain.pessoa.dto;

import com.medMais.domain.pessoa.Pessoa;

public record DataDetalhesPessoaPublico(Long id,
										String nome) {
	
	public DataDetalhesPessoaPublico(Pessoa pessoa) {
		this(pessoa.getId(),pessoa.getNome());
	}

}
