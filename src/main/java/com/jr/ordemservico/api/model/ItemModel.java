package com.jr.ordemservico.api.model;

import java.math.BigDecimal;

import com.jr.ordemservico.domain.entity.TipoItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemModel {
	private Long id;
	private String descricao;
	private BigDecimal vlrCusto;
	private BigDecimal vlrVenda;
	private TipoItem tipoItem;
	
}
