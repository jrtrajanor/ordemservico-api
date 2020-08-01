package com.jr.ordemservico.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jr.ordemservico.api.model.OsComentarioImputModel;
import com.jr.ordemservico.api.model.OsComentarioModel;
import com.jr.ordemservico.domain.entity.OrdemServico;
import com.jr.ordemservico.domain.entity.OrdemServicoComentario;
import com.jr.ordemservico.domain.exception.EntidadeNaoEncontradaException;
import com.jr.ordemservico.domain.repository.OrdemServicoRepository;
import com.jr.ordemservico.domain.service.CadastroOrdemServicoService;
import com.jr.ordemservico.domain.util.ConstantesMessage;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class OsComentarioController {

	@Autowired
	private CadastroOrdemServicoService ordemServicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository osRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OsComentarioModel adicionar(@Valid @RequestBody OsComentarioImputModel osComentarioImputModel, @PathVariable Long ordemServicoId) {
		
		return toModel(ordemServicoService.adicionarComentario(ordemServicoId, osComentarioImputModel.getDescricao()));
	}
	
	@GetMapping
	public List<OsComentarioModel> listar(OsComentarioImputModel osComentarioImputModel, @PathVariable Long ordemServicoId){
		OrdemServico ordemServico = osRepository.findById(ordemServicoId).orElseThrow(() -> new EntidadeNaoEncontradaException(ConstantesMessage.MSG_OS_NAO_ENCONTRADO));
		
		return toCollectionModel(ordemServico.getOrdemServicoComentario());
	}
	
	private OsComentarioModel toModel(OrdemServicoComentario ordemServicoComentario) {
		return modelMapper.map(ordemServicoComentario, OsComentarioModel.class); 
	}
	
	private List<OsComentarioModel> toCollectionModel(List<OrdemServicoComentario> comentarios){
		return comentarios.stream()
				.map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
	}
}
