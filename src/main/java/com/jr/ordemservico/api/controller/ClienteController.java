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

import com.jr.ordemservico.api.model.ClienteModel;
import com.jr.ordemservico.domain.entity.Cliente;
import com.jr.ordemservico.domain.repository.ClienteRepository;
import com.jr.ordemservico.domain.service.CadastroClienteService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/clientes")
public class ClienteController {
 
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CadastroClienteService clienteService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping 
	public List<Cliente> listar(Cliente cliente){
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if (cliente.isPresent())
			return ResponseEntity.ok(cliente.get());	

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/by")
	public List<ClienteModel> buscarBy(@RequestParam(value="nome" , required=false) String nome,
									   @RequestParam(value="fone" , required=false) String fone,
									   @RequestParam(value="email", required = false) String email){
		
		Cliente clienteFiltros = new Cliente();
		clienteFiltros.setNome(nome);
		clienteFiltros.setFone(fone);
		clienteFiltros.setEmail(email);
		
		return toCollectionModel( clienteService.buscarBy( clienteFiltros ) );
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return clienteService.salvar(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @RequestBody Cliente cliente, @PathVariable Long clienteId) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId);
		cliente = clienteService.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
	}
		
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		clienteService.excluir(clienteId);
		
		return ResponseEntity.noContent().build();
	}
	
	private ClienteModel toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteModel.class); 
	}
	
	private List<ClienteModel> toCollectionModel(List<Cliente> clientes) {
		return clientes.stream().map(cliente -> toModel(cliente)).collect(Collectors.toList());
	}
	
}
