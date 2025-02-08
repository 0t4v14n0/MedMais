package com.medMais.domain.pessoa.paciente.dto;

import com.medMais.domain.pessoa.dto.DataRegistroPessoa;

public record DataRegistroPaciente(String contatoEmergencia,
								   Double peso,
								   Double altura,
								   DataRegistroPessoa dataRegistroPessoa) {}
