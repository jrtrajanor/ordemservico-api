package com.jr.ordemservico.api.model;

import javax.validation.constraints.NotNull;

public class ClienteIdImputModel {

	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
