package com.jr.ordemservico.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jr.ordemservico.domain.entity.Usuario;
import com.jr.ordemservico.domain.exception.NegocioException;
import com.jr.ordemservico.domain.repository.UsuarioRepository;
import com.jr.ordemservico.domain.util.ConstantesMessage;
import com.jr.ordemservico.domain.util.GeradorSenha;

@Service
public class CadastroUsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public Usuario salvar(Usuario usuario) {
		if (usuarioRepository.findByUsuarioOrEmail(usuario.getUsuario(), usuario.getEmail())
				.isPresent() && usuario.getId() == null) {
			throw new NegocioException(ConstantesMessage.MSG_USUARIO_JA_EXISTE);
		}
		
		String senhaDecript = new GeradorSenha().descriptograBase64Reverse(usuario.getSenha());
		
		usuario.setSenha(new GeradorSenha().criptografa(senhaDecript));
		
		return usuarioRepository.save(usuario);
	}
	
	@Transactional(readOnly = true)
	public List<Usuario> buscarBy(Usuario usuarioFiltros){
		Example<Usuario> filtro = Example.of(usuarioFiltros, ExampleMatcher
																.matching()
																.withIgnoreCase()
																.withStringMatcher(StringMatcher.CONTAINING));
		
		return usuarioRepository.findAll(filtro);
	}
	
	public void excluir(Long usuarioId){
		
		usuarioRepository.deleteById(usuarioId);
	}
		
}
