package com.jr.ordemservico.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jr.ordemservico.domain.entity.Cliente;
import com.jr.ordemservico.domain.exception.NegocioException;
import com.jr.ordemservico.domain.repository.ClienteRepository;
import com.jr.ordemservico.domain.util.ConstantesMessage;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente salvar(Cliente cliente) {
		Cliente clienteEmailExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if ((clienteEmailExistente != null) && (clienteEmailExistente.getId().longValue() != cliente.getId().longValue())) {
			throw new NegocioException(ConstantesMessage.MSG_EMAIL_EXISTENTE);
		}
			
		return clienteRepository.save(cliente);
	}
	
	public void excluir(Long clienteId) {
		
		clienteRepository.deleteById(clienteId);
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> buscarBy(Cliente clienteFiltros){
		Example<Cliente> filtro = Example.of(clienteFiltros, ExampleMatcher
																.matching()
																.withIgnoreCase()
																.withStringMatcher(StringMatcher.CONTAINING));
		
		return clienteRepository.findAll(filtro);
	}
}
