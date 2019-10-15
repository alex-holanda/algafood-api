package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cidade> listar() {
		TypedQuery<Cidade> query = manager.createQuery("from  Cidade", Cidade.class);
		
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public Cidade salvar(Cidade restaurante) {
		return manager.merge(restaurante);
	}
	
	@Override
	public Cidade buscar(Long id) {
		return manager.find(Cidade.class, id);
	}
	
	@Transactional
	@Override
	public void remover(Cidade restaurante) {
		restaurante = buscar(restaurante.getId());
		manager.remove(restaurante);
	}

}
