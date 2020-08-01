package com.jr.ordemservico.api.model;

import java.time.LocalDate;
import java.util.Set;

import com.jr.ordemservico.domain.entity.Permissao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {
	private Long id;
	private String usuario;
	private String nome;
	private String email;
	private String fone;
	private LocalDate dataNascimento;
	private Set<Permissao> roles;
		
}
