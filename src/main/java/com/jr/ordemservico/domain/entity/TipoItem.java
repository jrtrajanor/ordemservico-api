package com.jr.ordemservico.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class TipoItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipoitem_seq")
	private Long id;

	@NotBlank
	@Size(max=255)
	@Length(max=255)
	private String descricao;
	
	@NotNull
	private BigDecimal perComissao;
}
