package com.jr.ordemservico.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jr.ordemservico.api.model.OrdemServicoImputModel;
import com.jr.ordemservico.api.model.OrdemServicoModel;
import com.jr.ordemservico.domain.entity.OrdemServico;
import com.jr.ordemservico.domain.repository.OrdemServicoRepository;
import com.jr.ordemservico.domain.service.CadastroOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private CadastroOrdemServicoService ordemServicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<OrdemServicoModel> listar(OrdemServicoModel ordemservico){
		
		return toCollectionModel(ordemServicoRepository.findAll());
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> buscar(@PathVariable Long ordemServicoId) {
		Optional<OrdemServico> os = ordemServicoRepository.findById(ordemServicoId);
		
		if (os.isPresent()) {
			OrdemServicoModel osModel = toModel(os.get());
			return ResponseEntity.ok(osModel);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoModel salvar(@Valid @RequestBody OrdemServicoImputModel ordemServicoImputModel) {
		OrdemServico ordemServico = toEntity(ordemServicoImputModel);
		return toModel(ordemServicoService.salvar(ordemServico));
	}
	
	@PutMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> atualizar(@Valid @RequestBody OrdemServicoImputModel ordemServicoImputModel, @PathVariable Long ordemServicoId){
		
		if (!ordemServicoRepository.existsById(ordemServicoId)) {
			return ResponseEntity.notFound().build();
		}
		
		OrdemServico ordemServico = toEntity(ordemServicoImputModel);
		ordemServico.setId(ordemServicoId);
		ordemServico = ordemServicoService.salvar(ordemServico);
		
		return ResponseEntity.ok(toModel(ordemServico));
	}
	
	@DeleteMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoModel> remover(@PathVariable Long ordemServicoId){
		if (!ordemServicoRepository.existsById(ordemServicoId)) {
			return ResponseEntity.notFound().build();
		}
		
		ordemServicoService.excluir(ordemServicoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId){
		ordemServicoService.finalizaOs(ordemServicoId); 
	}
	
	private OrdemServicoModel toModel(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoModel.class); 
	}

	private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
		return ordensServico.stream().map(ordemServico -> toModel(ordemServico)).collect(Collectors.toList());
	}
	
	private OrdemServico toEntity(OrdemServicoImputModel ordemServicoImput) {
		return modelMapper.map(ordemServicoImput, OrdemServico.class); 
	}
	
}
