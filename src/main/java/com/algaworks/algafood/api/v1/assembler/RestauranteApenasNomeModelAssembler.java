package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.algalinks.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeModelAssembler 
	extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public RestauranteApenasNomeModelAssembler() {
		super(RestauranteController.class, RestauranteApenasNomeModel.class);
	}
	
	@Override
	public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
		var restauranteApenasNomeModel = createModelWithId(restaurante.getId(), restaurante);
		mapper.map(restaurante, restauranteApenasNomeModel);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteApenasNomeModel.add(algaLinks.linkToRestaurantes("restaurantes"));			
		}
		
		return restauranteApenasNomeModel;
	}
	
	@Override
	public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteApenasNomeModel> restauranteApenasNomeModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteApenasNomeModel.add(algaLinks.linkToRestaurantes());
		}
		
		return restauranteApenasNomeModel;
	}
}
