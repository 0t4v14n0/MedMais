package com.medMais.domain.consulta.dto;

import jakarta.validation.constraints.NotNull;

public record DataRegistroConsulta(@NotNull
		   						   String crm,
		   						   @NotNull
								   Long id) {}
