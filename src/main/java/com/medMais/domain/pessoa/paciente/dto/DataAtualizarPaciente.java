package com.medMais.domain.pessoa.paciente.dto;

import com.medMais.domain.pessoa.paciente.enums.TipoSanguineo;

public record DataAtualizarPaciente(TipoSanguineo tipoSanguineo,
		   							String contatoEmergencia,
		   							Double peso,
		   							Double altura) {}
