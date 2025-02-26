package com.medMais.domain.pessoa.paciente.dto;

import com.medMais.domain.pessoa.dto.DataRegistroPessoa;
import com.medMais.domain.pessoa.paciente.enums.TipoSanguineo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DataRegistroPaciente(@NotNull(message = "O tipo sanguíneo não pode ser nulo")
		   						   TipoSanguineo tipoSanguineo,
							       @NotNull
			 					   @NotBlank
			 					   String contatoEmergencia,
			 					  @NotNull(message = "O peso não pode ser nula")
								   @Positive(message = "O peso deve ser um valor positivo")
								   Double peso,
								   @NotNull(message = "A altura não pode ser nula")
								   @Positive(message = "A altura deve ser um valor positivo")
								   Double altura,
								   DataRegistroPessoa dataRegistroPessoa) {}
