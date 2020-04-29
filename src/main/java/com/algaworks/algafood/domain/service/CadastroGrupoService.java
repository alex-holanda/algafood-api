package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	public List<Grupo> listar() {
		return grupoRepository.findAll();
	}
	
	public Grupo buscar(Long id) {
		return buscarOuFalhar(id);
	}
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	public Grupo atualizar(Grupo grupo) {
		return salvar(grupo);
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			grupoRepository.deleteById(id);
			grupoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("O grupo %d não pode ser removido, pois está em uso.", id));
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(id);
		}
	}
	
	private Grupo buscarOuFalhar(Long id) {
		return grupoRepository.findById(id)
					.orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}
}
