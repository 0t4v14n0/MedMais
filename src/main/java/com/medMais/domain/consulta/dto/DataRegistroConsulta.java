package com.medMais.domain.consulta.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegistroConsulta(@NotNull
		   						   @NotBlank
		   						   Long id,
		   						   @NotNull
								   @NotBlank
								   LocalDateTime horarioConsulta) {}
