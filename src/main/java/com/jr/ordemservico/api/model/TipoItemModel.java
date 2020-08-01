package com.jr.ordemservico.api.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoItemModel {

	private Long id;
	private String descricao;
	private BigDecimal perComissao;
	
}