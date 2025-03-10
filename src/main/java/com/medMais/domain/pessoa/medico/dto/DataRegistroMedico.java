package com.medMais.domain.pessoa.medico.dto;

import java.math.BigDecimal;

import com.medMais.domain.pessoa.dto.DataRegistroPessoa;
import com.medMais.domain.pessoa.medico.EspecialidadeMedica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegistroMedico(DataRegistroPessoa dataRegistroPessoa,
		 						 @NotNull
			 					 @NotBlank
							     String crm,
							     BigDecimal valorConsulta,
							     @NotNull
							     EspecialidadeMedica especialidadeMedica) {}
