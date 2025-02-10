package com.medMais.domain.pessoa.paciente.dto;

import com.medMais.domain.pessoa.dto.DataRegistroPessoa;
import com.medMais.domain.pessoa.paciente.enums.TipoSanguineo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegistroPaciente(@NotNull
		   						   @NotBlank 
		   						   TipoSanguineo tipoSanguineo,
							       @NotNull
			 					   @NotBlank
			 					   String contatoEmergencia,
			 					   @NotNull
			  					   @NotBlank
								   Double peso,
			 					   @NotNull
			  					   @NotBlank
								   Double altura,
								   DataRegistroPessoa dataRegistroPessoa) {}
