package com.medMais.domain.endereco.dto;

public record DataRegistroEndereco(String endereco,
						           String cidade,
						           String estado,
						           String cep,
						           String pais,
						           String referencia) {}
