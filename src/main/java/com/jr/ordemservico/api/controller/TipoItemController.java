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

import com.jr.ordemservico.api.model.TipoItemModel;
import com.jr.ordemservico.domain.entity.TipoItem;
import com.jr.ordemservico.domain.repository.TipoItemRepository;
import com.jr.ordemservico.domain.service.CadastroTipoItemService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/tipoItem")
public class TipoItemController {

	@Autowired
	TipoItemRepository tipoItemRepository;
	
	@Autowired
	CadastroTipoItemService tipoItemService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<TipoItemModel> listar(){
				
		return toCollectionModel(tipoItemRepository.findAll());
	}
	
	@GetMapping("/{tipoItemId}")
	public ResponseEntity<TipoItemModel> buscar(@PathVariable Long tipoItemId) {
		Optional<TipoItem> tipoItem = tipoItemRepository.findById(tipoItemId);
		
		if (tipoItem.isPresent()) {
			TipoItemModel tipoItemModel = toModel(tipoItem.get());
			return ResponseEntity.ok(tipoItemModel);
		}	

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/by")
	public List<TipoItemModel> buscarBy(@RequestParam(value="descricao" , required=false) String descricao){
		
		TipoItem tipoItemFiltros = new TipoItem();
		tipoItemFiltros.setDescricao(descricao);
		
		return toCollectionModel( tipoItemService.buscarBy( tipoItemFiltros ) );
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TipoItemModel adicionar(@Valid @RequestBody TipoItemModel tipoItemModel){
		TipoItem tipoItem = toEntity(tipoItemModel);
		return toModel(tipoItemService.salvar(tipoItem));
	}
	
	@PutMapping("/{tipoItemId}")
	public ResponseEntity<TipoItemModel> atualizar(@Valid @RequestBody TipoItemModel tipoItemModel, @PathVariable Long tipoItemId){
				
		if (!tipoItemRepository.existsById(tipoItemId)) {
			return ResponseEntity.notFound().build();
		}
		
		TipoItem tipoItem = toEntity(tipoItemModel);
		
		tipoItem.setId(tipoItemId);
		
		return ResponseEntity.ok(toModel(tipoItemService.salvar(tipoItem)));
	}
	
	@DeleteMapping("/{tipoItemId}")
	public ResponseEntity<Void> deletar(@Valid @PathVariable Long tipoItemId){
		if (!tipoItemRepository.existsById(tipoItemId)) {
			return ResponseEntity.notFound().build();
		}
		
		tipoItemService.excluir(tipoItemId);
		
		return ResponseEntity.noContent().build();
	}
	
	private TipoItemModel toModel(TipoItem tipoItem) {
		return modelMapper.map(tipoItem, TipoItemModel.class); 
	}
	
	private List<TipoItemModel> toCollectionModel(List<TipoItem> tipoItems) {
		return tipoItems.stream().map(tipoItem -> toModel(tipoItem)).collect(Collectors.toList());
	}
	
	private TipoItem toEntity(TipoItemModel tipoItemModel) {
		return modelMapper.map(tipoItemModel, TipoItem.class);
	}
}