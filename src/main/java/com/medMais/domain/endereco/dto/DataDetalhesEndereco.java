package com.medMais.domain.endereco.dto;

import com.medMais.domain.endereco.Endereco;

public record DataDetalhesEndereco(String endereco,
		   						   String cidade,
		   						   String estado,
		   						   String cep,
		   						   String pais,
		   						   String referencia) {

	public DataDetalhesEndereco(Endereco endereco) {
		this(endereco.getEndereco(),
			 endereco.getCidade(),
			 endereco.getEstado(),
			 endereco.getCep(),
			 endereco.getPais()
			 ,endereco.getReferencia());
	}

}
