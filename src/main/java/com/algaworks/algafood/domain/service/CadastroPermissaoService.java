package com.algaworks.algafood.domain.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Collection<Permissao> listar() {
		return permissaoRepository.findAll();
	}
	
	public Permissao buscar(Long permissaoId) {
		return buscar(permissaoId);
	}
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
					.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	}
}
