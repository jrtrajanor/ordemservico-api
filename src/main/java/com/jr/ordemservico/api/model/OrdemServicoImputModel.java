package com.jr.ordemservico.api.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrdemServicoImputModel {
	@Valid
	@NotNull
	private ClienteIdImputModel cliente;
	@NotBlank
	private String descricao;
	@NotNull
	private BigDecimal preco;

	public ClienteIdImputModel getCliente() {
		return cliente;
	}

	public void setClienteId(ClienteIdImputModel cliente) {
		this.cliente = cliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
}

