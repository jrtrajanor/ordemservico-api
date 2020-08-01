package com.jr.ordemservico.domain.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
	private Long id;
	
	@NotBlank
	@Size(max=50)
	@Length(max=50)
	private String usuario;
	
	@NotBlank
	@Size(max=255)
	@Length(max=255)
	private String nome;
	
	@NotBlank
	@Email
	@Size(max=30)
	@Length(max=30)
	private String email;
	
	private LocalDate dataNascimento;
	
	@NotBlank
	@Size(max=15)
	@Length(max=15)
	private String fone;
	
	@NotBlank
	@Size(max=255)
	@Length(max=255)
	private String senha;
	
	@ManyToMany
	@JoinTable(name="usuario_roles"
		,joinColumns=@JoinColumn(name="usuario_id")
		,inverseJoinColumns=@JoinColumn(name="role_id")
	)
	
	private Set<Permissao> roles;
	
}
