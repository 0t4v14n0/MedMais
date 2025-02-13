package com.medMais.domain.historicoDoenca.dto;

import java.time.LocalDate;

import com.medMais.domain.historicoDoenca.enums.EstadoDoenca;
import com.medMais.domain.historicoDoenca.enums.Gravidade;

public record DataAtualizarHistoricoDoenca(String nomeDoenca,
		      							   String descricao,
		      							   LocalDate dataDiagnostico,
		      							   LocalDate dataRecuperacao,
		      							   EstadoDoenca estadoAtual,
		      							   String tratamento,
		      							   String medicamentos,
		      							   String observacoesMedicas,
		      							   Gravidade gravidade) {}
