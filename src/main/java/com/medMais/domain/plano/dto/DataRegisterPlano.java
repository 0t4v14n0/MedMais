package com.medMais.domain.plano.dto;

import java.time.LocalDate;
import java.util.List;

import com.medMais.domain.pessoa.paciente.enums.TipoPlano;
import com.medMais.domain.plano.enums.StatusPlano;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegisterPlano(@NotNull
		 						@NotBlank
								String nome,
								@NotNull
		 						@NotBlank
								TipoPlano tipo,
								@NotNull
		 						@NotBlank
								double preco,
								@NotNull
		 						@NotBlank
								double desconto,
								@NotNull
		 						@NotBlank
								double taxasAdicionais,
								@NotNull
		 						@NotBlank
								int duracao,
								@NotNull
		 						@NotBlank
								List<String> beneficios,
								@NotNull
		 						@NotBlank
								List<String> limitacoes,
								@NotNull
		 						@NotBlank
								List<String> metodosPagamento,
								@NotNull
		 						@NotBlank
								LocalDate dataInicio,
								@NotNull
		 						@NotBlank
								LocalDate dataTermino,
								@NotNull
		 						@NotBlank
								StatusPlano status) {}
