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

import com.jr.ordemservico.api.model.UsuarioModel;
import com.jr.ordemservico.api.model.PermissaoModel;
import com.jr.ordemservico.domain.entity.Permissao;
import com.jr.ordemservico.domain.entity.Usuario;
import com.jr.ordemservico.domain.repository.PermissaoRepository;
import com.jr.ordemservico.domain.repository.UsuarioRepository;
import com.jr.ordemservico.domain.service.CadastroUsuarioService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private CadastroUsuarioService usuarioService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<UsuarioModel> listar(){
		return toCollectionModel(usuarioRepository.findAll());
	}
	
	@GetMapping("/by")
	public List<UsuarioModel> buscarBy(@RequestParam(value="nome" , required=false) String nome,
									   @RequestParam(value="usuario" , required=false) String usuario,
									   @RequestParam(value="email", required = false) String email){
		
		Usuario usuarioFiltros = new Usuario();
		usuarioFiltros.setNome(nome);
		usuarioFiltros.setUsuario(usuario);
		usuarioFiltros.setEmail(email);
		
		return toCollectionModel( usuarioService.buscarBy( usuarioFiltros ) );
	}
	
	@GetMapping("/permissoes")
	public List<PermissaoModel> listarPermissoes(){
		return toCollectionPermissaoModel(permissaoRepository.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<UsuarioModel> buscar(@PathVariable Long usuarioId){
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
		
		if (usuario.isPresent()) {
			return ResponseEntity.ok(toModel(usuario.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario cadastrar(@Valid @RequestBody Usuario usuario){
	
		return usuarioService.salvar(usuario);
	}
	
	@PutMapping("/{usuarioId}")
	public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuario, @PathVariable Long usuarioId){
				
		if (!usuarioRepository.existsById(usuarioId)) {
			return ResponseEntity.notFound().build();
		}
		
		usuario.setId(usuarioId);
		usuarioService.salvar(usuario);
		
		return ResponseEntity.ok(usuario);
	}
	
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> deletar(@Valid @PathVariable Long usuarioId){
		if (!usuarioRepository.existsById(usuarioId)) {
			return ResponseEntity.notFound().build();
		}
		
		usuarioService.excluir(usuarioId);
		
		return ResponseEntity.noContent().build();
	}
		
	private UsuarioModel toModel(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioModel.class); 
	}
	
	private List<UsuarioModel> toCollectionModel(List<Usuario> usuarios) {
		return usuarios.stream().map(usuario -> toModel(usuario)).collect(Collectors.toList());
	}
	
	private PermissaoModel toPermissaoModel(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoModel.class); 
	}
	
	private List<PermissaoModel> toCollectionPermissaoModel(List<Permissao> permissoes) {
		return permissoes.stream().map(permissao -> toPermissaoModel(permissao)).collect(Collectors.toList());
	}
		
}
