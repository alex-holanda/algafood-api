package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Restaurante_;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		var builder = manager.getCriteriaBuilder();
		var criteria = builder.createQuery(Restaurante.class);
		var root = criteria.from(Restaurante.class);
		
		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get(Restaurante_.NOME), "%" + nome + "%"));
		}
		
		if (taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Restaurante_.TAXA_FRETE), taxaFreteInicial));
		}
		
		if (taxaFreteInicial != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Restaurante_.TAXA_FRETE), taxaFreteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[predicates.size()]));
		
		var query = manager.createQuery(criteria);
		
		return query.getResultList();
	}
}
