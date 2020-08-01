package com.jr.ordemservico.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioImputModel {
	private String nome;
	private String email;
	private String fone;
	private String senha;
}
