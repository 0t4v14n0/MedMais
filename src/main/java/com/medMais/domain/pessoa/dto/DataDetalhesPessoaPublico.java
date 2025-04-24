package com.medMais.domain.pessoa.dto;

import com.medMais.domain.pessoa.Pessoa;

public record DataDetalhesPessoaPublico(String nome,
		 								String telefone,
				 						String email) {
	
	public DataDetalhesPessoaPublico(Pessoa pessoa) {
		this(pessoa.getNome(),pessoa.getTelefone(),pessoa.getEmail());
	}

}
