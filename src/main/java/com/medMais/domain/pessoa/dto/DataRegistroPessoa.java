package com.medMais.domain.pessoa.dto;

import java.time.LocalDate;

import com.medMais.domain.endereco.dto.DataRegistroEndereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegistroPessoa(@NotNull
			 					 @NotBlank
								 String login,
		  						 @NotNull
		  						 @NotBlank
								 String senha,
		  						 @NotNull
		  						 @NotBlank
								 String nome,
		  						 @NotNull
		  						 @NotBlank
								 String cpf,
		  						 @NotNull
		  						 @NotBlank
								 String email,
		  						 @NotNull
		  						 @NotBlank
								 String telefone,
		  						 @NotNull
		  						 @NotBlank
								 LocalDate dataNascimento,
		  						 @NotNull
		  						 @NotBlank
								 DataRegistroEndereco dataRegistroEndereco) {}
