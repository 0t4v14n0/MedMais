package com.medMais.domain.plano.dto;

import java.util.List;

import com.medMais.domain.pessoa.paciente.enums.TipoPlano;
import com.medMais.domain.plano.enums.StatusPlano;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataAtualizarPlano(@NotNull
								 @NotBlank
								 String nome,
								 TipoPlano tipo,
								 double preco,
								 double desconto,
								 double taxasAdicionais,
								 int duracao,
								 List<String> beneficios,
								 List<String> limitacoes,
								 List<String> metodosPagamento,
								 StatusPlano status) {}
