package com.jr.ordemservico.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jr.ordemservico.api.model.ItemModel;
import com.jr.ordemservico.domain.entity.Item;
import com.jr.ordemservico.domain.repository.ItemRepository;
import com.jr.ordemservico.domain.service.CadastroItemService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private CadastroItemService itemService;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping 
	public List<ItemModel> listar(ItemModel item){
		return toCollectionModel(itemRepository.findAllItemByOrderByIdAsc());
	}
	
	@GetMapping("/{itemId}")
	public ResponseEntity<ItemModel> buscar(@PathVariable Long itemId) {
		Optional<Item> item = itemRepository.findById(itemId);
		
		if (item.isPresent()) {
			ItemModel itemModel = toModel(item.get());
			return ResponseEntity.ok(itemModel);
		}	

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/by")
	public List<ItemModel> buscarBy(@RequestParam(value="descricao" , required=false) String descricao){
		
		Item itemFiltros = new Item();
		itemFiltros.setDescricao(descricao);
		
		return toCollectionModel( itemService.buscarBy( itemFiltros ) );
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ItemModel adicionar(@Valid @RequestBody ItemModel itemModel) {
		Item item = toEntity(itemModel);
		return toModel(itemService.salvar(item));
	}
	
	@PutMapping("/{itemId}")
	public ResponseEntity<ItemModel> atualizar(@Valid @RequestBody ItemModel itemModel, @PathVariable Long itemId){
				
		if (!itemRepository.existsById(itemId)) {
			return ResponseEntity.notFound().build();
		}
		
		Item item = toEntity(itemModel);
		
		item.setId(itemId);
		
		return ResponseEntity.ok(toModel(itemService.salvar(item)));
	}
	
	@DeleteMapping("/{itemId}")
	public ResponseEntity<Void> deletar(@Valid @PathVariable Long itemId){
		if (!itemRepository.existsById(itemId)) {
			return ResponseEntity.notFound().build();
		}
		
		itemService.excluir(itemId);
		
		return ResponseEntity.noContent().build();
	}
	
	private ItemModel toModel(Item item) {
		return modelMapper.map(item, ItemModel.class); 
	}
	
	private List<ItemModel> toCollectionModel(List<Item> items) {
		return items.stream().map(item -> toModel(item)).collect(Collectors.toList());
	}
	
	private Item toEntity(ItemModel itemModel) {
		return modelMapper.map(itemModel, Item.class); 
	}

}
