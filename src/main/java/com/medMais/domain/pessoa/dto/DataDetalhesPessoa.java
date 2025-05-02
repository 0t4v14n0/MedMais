package com.medMais.domain.pessoa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.medMais.domain.endereco.Endereco;
import com.medMais.domain.endereco.dto.DataDetalhesEndereco;
import com.medMais.domain.pessoa.Pessoa;

public record DataDetalhesPessoa(String nome,
								 BigDecimal saldo,
		 						 String cpf,
		 						 String email,
		 						 String telefone,
		 						 LocalDate dataNascimento,
		 						 DataDetalhesEndereco dataDetalhesEndereco) {

	public DataDetalhesPessoa(String nome,BigDecimal saldo, String cpf, String email, String telefone,
			LocalDate dataNascimento,Endereco endereco) {
		this(nome,saldo,cpf,email,telefone,dataNascimento, new DataDetalhesEndereco(endereco));
	}

	public DataDetalhesPessoa(Pessoa pessoa) {
		this(pessoa.getNome(),pessoa.getSaldo(),pessoa.getCpf(),pessoa.getEmail(),pessoa.getTelefone(),pessoa.getDataNascimento(), new DataDetalhesEndereco(pessoa.getEndereco()));
	}
}
