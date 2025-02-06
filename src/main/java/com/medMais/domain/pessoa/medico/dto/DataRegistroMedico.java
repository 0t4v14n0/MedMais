package com.medMais.domain.pessoa.medico.dto;

import java.math.BigDecimal;

import com.medMais.domain.pessoa.dto.DataRegistroPessoa;
import com.medMais.domain.pessoa.medico.EspecialidadeMedica;

public record DataRegistroMedico(DataRegistroPessoa dataRegistroPessoa,
							     String crm,
							     BigDecimal valorConsulta,
							     EspecialidadeMedica especialidadeMedica) {}
