package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.algalinks.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteBasicoModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public RestauranteBasicoModelAssembler() {
		super(RestauranteController.class, RestauranteBasicoModel.class);
	}
	
	@Override
	public RestauranteBasicoModel toModel(Restaurante restaurante) {
		var restauranteBasicoModel = createModelWithId(restaurante.getId(), restaurante);
		mapper.map(restaurante, restauranteBasicoModel);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteBasicoModel.add(algaLinks.linkToRestaurantes("restaurantes"));			
		}
		
		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteBasicoModel.getCozinha()
			.add(algaLinks.linkToCozinha(restauranteBasicoModel.getCozinha().getId()));			
		}
		
		return restauranteBasicoModel;
	}
	
	@Override
	public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		
		CollectionModel<RestauranteBasicoModel> collectionModel =  super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			collectionModel.add(algaLinks.linkToRestaurantes());
		}
		
		return collectionModel;
	}
}
