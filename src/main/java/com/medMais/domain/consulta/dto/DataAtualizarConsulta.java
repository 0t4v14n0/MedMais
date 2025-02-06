package com.medMais.domain.consulta.dto;

import java.time.LocalDateTime;

public record DataAtualizarConsulta(Long idConsulta,
									LocalDateTime novoHorarioConsulta) {}
