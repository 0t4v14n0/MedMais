package com.medMais.domain.pessoa.paciente;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import com.medMais.domain.pessoa.Pessoa;
import com.medMais.domain.pessoa.paciente.dto.DataRegistroPaciente;
import com.medMais.domain.pessoa.paciente.enums.TipoPlano;
import com.medMais.domain.pessoa.paciente.enums.TipoSanguineo;
import com.medMais.domain.role.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "paciente")
public class Paciente extends Pessoa{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Enumerated(EnumType.STRING)
    private TipoSanguineo tipoSanguineo;  // O+, A-, B+, etc
    
    private String numeroCarteiraPlano;
    
    private String contatoEmergencia;
    
    private Double peso;  // Em kg

    private Double altura;  // Em metros
    
    @Enumerated(EnumType.STRING)
    private TipoPlano tipoPlano;
	    
    public Paciente(DataRegistroPaciente data, Role role) {
        super(data.dataRegistroPessoa().login(),
          	  role,
          	  data.dataRegistroPessoa().nome(),
          	  data.dataRegistroPessoa().cpf(), 
          	  data.dataRegistroPessoa().email(), 
          	  data.dataRegistroPessoa().telefone(), 
          	  data.dataRegistroPessoa().dataNascimento(),
          	  data.dataRegistroPessoa().dataRegistroEndereco(),
          	  BigDecimal.valueOf(0.0)//saldo inicial
          	  );
        this.tipoSanguineo = data.tipoSanguineo();
        this.numeroCarteiraPlano = super.getId()+"000"+ThreadLocalRandom.current().nextInt(100, 200);// padrao = 01000100
        this.contatoEmergencia = data.contatoEmergencia();
        this.peso = data.peso();
        this.altura = data.altura();
        this.tipoPlano = TipoPlano.BASICO;
    }
    
    //METODO IMC
    public Double getIMC() {
        if (peso != null && altura != null && altura > 0) {
            return peso / (altura * altura);
        }
        return null;
    }

	public TipoSanguineo getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public String getNumeroCarteiraPlano() {
		return numeroCarteiraPlano;
	}

	public void setNumeroCarteiraPlano(String numeroCarteiraPlano) {
		this.numeroCarteiraPlano = numeroCarteiraPlano;
	}

	public String getContatoEmergencia() {
		return contatoEmergencia;
	}

	public void setContatoEmergencia(String contatoEmergencia) {
		this.contatoEmergencia = contatoEmergencia;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public TipoPlano getTipoPlano() {
		return tipoPlano;
	}

	public void setTipoPlano(TipoPlano tipoPlano) {
		this.tipoPlano = tipoPlano;
	}

}
