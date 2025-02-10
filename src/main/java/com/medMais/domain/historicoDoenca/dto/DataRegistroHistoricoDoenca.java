package com.medMais.domain.historicoDoenca.dto;

import java.time.LocalDate;

import com.medMais.domain.historicoDoenca.enums.EstadoDoenca;
import com.medMais.domain.historicoDoenca.enums.Gravidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataRegistroHistoricoDoenca(@NotNull
		   							      @NotBlank
										  String nomeDoenca,
										  @NotNull
										  @NotBlank
										  String descricao,
										  @NotNull
										  @NotBlank
										  LocalDate dataDiagnostico,
										  @NotNull
										  @NotBlank
										  LocalDate dataRecuperacao,
										  @NotNull
										  @NotBlank
										  EstadoDoenca estadoAtual,
										  @NotNull
										  @NotBlank
										  String tratamento,
										  @NotNull
										  @NotBlank
										  String medicamentos,
										  @NotNull
										  @NotBlank
										  String observacoesMedicas,
										  @NotNull
										  @NotBlank
										  Gravidade gravidade) {}
