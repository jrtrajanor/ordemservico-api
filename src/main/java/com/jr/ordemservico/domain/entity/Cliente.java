package com.jr.ordemservico.domain.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.jr.ordemservico.domain.ValidationGroups;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
	
	@NotNull(groups = ValidationGroups.ClienteId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
	private Long id;
	
	@NotBlank
	@Size(max=255)
	@Length(max=255)
	private String nome;
	
	@NotBlank
	@Email
	@Size(max=50)
	@Length(max=50)
	private String email;
	
	@NotBlank
	@Size(max=20)
	@Length(max=20)
	private String fone;
	
	private LocalDate dtNascimento;
	
	@Enumerated(EnumType.STRING)
	private TipoCnpjCpfPessoa tpCnpjCpf;
	
	@Size(max=14)
	@Length(max=14)
	@Column(name="cnpj_cpf")
	private String cnpjCpf;
	
	//@Convert(converter = Conversor.class)
	private Boolean vendedor;
	
	@Size(max=8)
	@Length(max=8)
	private String cep;
	
	@NotBlank
	@Size(max=255)
	@Length(max=255)
	private String endereco;
	
	@Size(max=255)
	@Length(max=255)
	private String numero;
		
	/*
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	*/
	
}
