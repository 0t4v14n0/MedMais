package com.medMais.domain.consulta.dto;

import java.time.LocalDateTime;

public record DataRegistroConsulta(Long id,
								   LocalDateTime horarioConsulta) {}
