package com.jr.ordemservico.api.model;

import java.time.LocalDate;

import com.jr.ordemservico.domain.entity.TipoCnpjCpfPessoa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModel {
	private Long id;
	private String nome;
	private String email;
	private String fone;
	private LocalDate dtNascimento;
	private TipoCnpjCpfPessoa tpCnpjCpf;
	private String cnpjCpf;
	private Boolean vendedor;
	private String cep;
	private String endereco;
	private String numero;

}
