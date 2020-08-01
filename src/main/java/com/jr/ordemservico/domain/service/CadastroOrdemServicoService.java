package com.jr.ordemservico.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jr.ordemservico.domain.entity.Cliente;
import com.jr.ordemservico.domain.entity.OrdemServico;
import com.jr.ordemservico.domain.entity.OrdemServicoComentario;
import com.jr.ordemservico.domain.entity.StatusOrdemServico;
import com.jr.ordemservico.domain.exception.EntidadeNaoEncontradaException;
import com.jr.ordemservico.domain.exception.NegocioException;
import com.jr.ordemservico.domain.repository.ClienteRepository;
import com.jr.ordemservico.domain.repository.OrdemServicoComentarioRepository;
import com.jr.ordemservico.domain.repository.OrdemServicoRepository;
import com.jr.ordemservico.domain.util.ConstantesMessage;

@Service
public class CadastroOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private OrdemServicoComentarioRepository osComentarioRepository;
	
	public OrdemServico salvar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow( () -> new NegocioException(ConstantesMessage.MSG_CLIENTE_NAO_ENCONTRADO) );
		
		ordemServico.setCliente(cliente);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void excluir(Long osId) {
		ordemServicoRepository.deleteById(osId);
	}
	
	public OrdemServicoComentario adicionarComentario(Long osId, String comentario) {
		OrdemServico os = buscarOsById(osId);
				
		OrdemServicoComentario osComentario = new OrdemServicoComentario();
		
		osComentario.setDescricao(comentario);
		osComentario.setDataEnvio(OffsetDateTime.now());
		osComentario.setOrdemServico(os);
		
		return osComentarioRepository.save(osComentario);
	}

	public void finalizaOs(Long osId) {
		OrdemServico ordemServico = buscarOsById(osId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	private OrdemServico buscarOsById(Long osId) {
		return ordemServicoRepository.findById(osId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(ConstantesMessage.MSG_OS_NAO_ENCONTRADO) );
	}
}
