package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;

public class RestauranteInputDisassembler {

	private static ModelMapper mapper = new ModelMapper();

	public static Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return mapper.map(restauranteInput, Restaurante.class);
	}
}
