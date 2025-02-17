package com.medMais.domain.consulta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ObsMedicoDTO(@NotNull
		   				   @NotBlank 
						   String obsPublica
						   //String obsPrivada
						   ) {}
