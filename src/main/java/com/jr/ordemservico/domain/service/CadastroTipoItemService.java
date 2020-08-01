package com.jr.ordemservico.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jr.ordemservico.domain.entity.Item;
import com.jr.ordemservico.domain.entity.TipoItem;
import com.jr.ordemservico.domain.exception.NegocioException;
import com.jr.ordemservico.domain.repository.TipoItemRepository;

@Service
public class CadastroTipoItemService {

	@Autowired
	private TipoItemRepository tipoItemRepository;
	
	@Autowired
	private CadastroItemService itemService;

	public TipoItem salvar(TipoItem tipoItem) {
		
		return tipoItemRepository.save(tipoItem);
	}
	
	public void excluir(Long tipoItemId) {
		Item tipoItemFiltros = new Item();
		tipoItemFiltros.setTipoItem(tipoItemRepository.findById(tipoItemId).get());
		
		if (!itemService.buscarBy(tipoItemFiltros).isEmpty()){
			throw new NegocioException("Não é possível excluir o tipo do item, pois o mesmo está vinculado a um item.");
		}

		tipoItemRepository.deleteById(tipoItemId);
	}	
	
	@Transactional(readOnly = true)
	public List<TipoItem> buscarBy(TipoItem tipoItemFiltros){
		Example<TipoItem> filtro = Example.of(tipoItemFiltros, ExampleMatcher
																.matching()
																.withIgnoreCase()
																.withStringMatcher(StringMatcher.CONTAINING));
		
		return tipoItemRepository.findAll(filtro);
	}
}