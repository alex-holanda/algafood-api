package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;

public class RestauranteModelAssembler {

	private static ModelMapper mapper = new ModelMapper();
	
	public static RestauranteModel toModel(Restaurante restaurante) {
		return mapper.map(restaurante, RestauranteModel.class);
	}
	
	public static List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
	}
}
