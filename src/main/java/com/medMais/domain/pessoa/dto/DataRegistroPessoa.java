package com.medMais.domain.pessoa.dto;

import java.time.LocalDate;

import com.medMais.domain.endereco.dto.DataRegistroEndereco;

public record DataRegistroPessoa(String login,
								 String senha,
								 String nome,
								 String cpf,
								 String email,
								 String telefone,
								 LocalDate dataNascimento,
								 DataRegistroEndereco dataRegistroEndereco) {}
