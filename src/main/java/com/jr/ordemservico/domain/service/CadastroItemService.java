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
import com.jr.ordemservico.domain.repository.ItemRepository;
import com.jr.ordemservico.domain.repository.TipoItemRepository;
import com.jr.ordemservico.domain.util.ConstantesMessage;

@Service
public class CadastroItemService {
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private TipoItemRepository tipoItemRepository;
	
	public Item salvar(Item item) {
		TipoItem tipoItem = tipoItemRepository.findById(item.getTipoItem().getId()).orElseThrow( () -> new NegocioException(ConstantesMessage.MSG_TIPO_ITEM_NAO_ENCONTRADO) );
		
		item.setTipoItem(tipoItem);
		
		return itemRepository.save(item);
	}
	
	public void excluir(Long itemId) {
		
		itemRepository.deleteById(itemId);
	}	
	
	@Transactional(readOnly = true)
	public List<Item> buscarBy(Item itemFiltros){
		Example<Item> filtro = Example.of(itemFiltros, ExampleMatcher
																.matching()
																.withIgnoreCase()
																.withStringMatcher(StringMatcher.CONTAINING));
		
		return itemRepository.findAll(filtro);
	}
}
