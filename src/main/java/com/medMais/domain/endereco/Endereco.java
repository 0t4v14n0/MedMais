package com.medMais.domain.endereco;

import com.medMais.domain.endereco.dto.DataRegistroEndereco;
import com.medMais.domain.pessoa.Pessoa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Endereco {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    
	private String endereco;
	private String cidade;
	private String estado;
	private String cep;
	private String pais;
	private String referencia;
	
	public Endereco () {}
		
	public Endereco(DataRegistroEndereco dataRegisterResidencia, Pessoa pessoa) {
	    this.endereco = dataRegisterResidencia.endereco();
	    this.cidade = dataRegisterResidencia.cidade();
	    this.estado = dataRegisterResidencia.estado();
	    this.cep = dataRegisterResidencia.cep();
	    this.pais = dataRegisterResidencia.pais();
	    this.referencia = dataRegisterResidencia.referencia();
	    this.pessoa = pessoa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

}
