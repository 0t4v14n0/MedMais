package com.medMais.domain.pessoa.medico.dto;

import java.math.BigDecimal;

import com.medMais.domain.pessoa.dto.DataAtualizarPessoa;
import com.medMais.domain.pessoa.medico.EspecialidadeMedica;

public record DataAtualizarMedico(DataAtualizarPessoa dataRegistroPessoa,
	     						  String crm,
	     						  BigDecimal valorConsulta,
	     					      EspecialidadeMedica especialidadeMedica) {}
