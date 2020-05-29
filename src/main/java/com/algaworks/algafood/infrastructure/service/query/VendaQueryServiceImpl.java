package com.algaworks.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.dto.VendaDiaria;
import com.algaworks.algafood.domain.fielter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Pedido_;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		
		var predicates = criarFiltro(builder, root, filtro);
		
		var functionConvertTzDataCriacao = builder.function("convert_tz", Date.class, 
					root.get(Pedido_.DATA_CRIACAO), builder.literal("+00:00"), builder.literal(timeOffset));
		
		var functionDataCriacao = builder.function("date", Date.class, functionConvertTzDataCriacao);
		
		var selection = builder.construct(VendaDiaria.class, 
				functionDataCriacao, 
				builder.count(root.get(Pedido_.id)),
				builder.sum(root.get(Pedido_.VALOR_TOTAL)) );
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[predicates.size()]));
		query.groupBy(functionDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

	private List<Predicate> criarFiltro(CriteriaBuilder builder, Root<Pedido> root, VendaDiariaFilter filtro) {
		var predicates = new ArrayList<Predicate>();
		
		predicates.add(root.get(Pedido_.STATUS).in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		if (filtro.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get(Pedido_.RESTAURANTE), filtro.getRestauranteId()));
		}
		
		if (filtro.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Pedido_.DATA_CRIACAO), filtro.getDataCriacaoInicio()));
		}
		
		if (filtro.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Pedido_.DATA_CRIACAO), filtro.getDataCriacaoFim()));
		}
		
		return predicates;
	}

}
