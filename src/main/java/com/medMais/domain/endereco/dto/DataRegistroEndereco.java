package com.medMais.domain.endereco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegistroEndereco(@NotNull
								   @NotBlank
								   String endereco,
								   @NotNull
								   @NotBlank
						           String cidade,
						           @NotNull
								   @NotBlank
						           String estado,
						           @NotNull
								   @NotBlank
						           String cep,
						           @NotNull
								   @NotBlank
						           String pais,
						           @NotNull
								   @NotBlank
						           String referencia) {}
