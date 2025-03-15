package com.medMais.domain.plano;

import java.time.LocalDate;
import java.util.List;

import com.medMais.domain.pessoa.paciente.enums.TipoPlano;
import com.medMais.domain.plano.enums.StatusPlano;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "planos")
public class Plano {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private TipoPlano tipoPlano;
    
    @Column(nullable = false)
    private double preco;

    @Column(nullable = false)
    private double desconto;

    @Column(nullable = false)
    private double taxasAdicionais;
    
    private int duracao;
    
    @ElementCollection
    private List<String> beneficios;

    @ElementCollection
    private List<String> limitacoes;

    @ElementCollection
    private List<String> metodosPagamento;
    
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    
    @Enumerated(EnumType.STRING)
    private StatusPlano status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPlano getTipoPlano() {
		return tipoPlano;
	}

	public void setTipoPlano(TipoPlano tipoPlano) {
		this.tipoPlano = tipoPlano;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public double getTaxasAdicionais() {
		return taxasAdicionais;
	}

	public void setTaxasAdicionais(double taxasAdicionais) {
		this.taxasAdicionais = taxasAdicionais;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	public List<String> getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(List<String> beneficios) {
		this.beneficios = beneficios;
	}

	public List<String> getLimitacoes() {
		return limitacoes;
	}

	public void setLimitacoes(List<String> limitacoes) {
		this.limitacoes = limitacoes;
	}

	public List<String> getMetodosPagamento() {
		return metodosPagamento;
	}

	public void setMetodosPagamento(List<String> metodosPagamento) {
		this.metodosPagamento = metodosPagamento;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(LocalDate dataTermino) {
		this.dataTermino = dataTermino;
	}

	public StatusPlano getStatus() {
		return status;
	}

	public void setStatus(StatusPlano status) {
		this.status = status;
	}
    
}
