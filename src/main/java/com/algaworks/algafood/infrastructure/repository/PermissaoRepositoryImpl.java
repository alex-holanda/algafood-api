package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Component
public class PermissaoRepositoryImpl implements PermissaoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Permissao> listar() {
		TypedQuery<Permissao> query = manager.createQuery("from  Permissao", Permissao.class);
		
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public Permissao salvar(Permissao restaurante) {
		return manager.merge(restaurante);
	}
	
	@Override
	public Permissao buscar(Long id) {
		return manager.find(Permissao.class, id);
	}
	
	@Transactional
	@Override
	public void remover(Permissao restaurante) {
		restaurante = buscar(restaurante.getId());
		manager.remove(restaurante);
	}

}
