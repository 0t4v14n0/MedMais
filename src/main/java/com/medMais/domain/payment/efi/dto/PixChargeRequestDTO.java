package com.medMais.domain.payment.efi.dto;

import java.util.List;

public record PixChargeRequestDTO(CalendarioDTO calendario,
								  DevedorDTO devedor,
								  double valor,
								  String chave,
								  List<String> infoAdicionais) {
	
	public PixChargeRequestDTO(CalendarioDTO calendario, DevedorDTO devedor, double valor, String chave, List<String> infoAdicionais) {
	    this.calendario = calendario;
	    this.devedor = devedor;
	    this.valor = valor;
	    this.chave = chave;
	    this.infoAdicionais = infoAdicionais;
	}
}
