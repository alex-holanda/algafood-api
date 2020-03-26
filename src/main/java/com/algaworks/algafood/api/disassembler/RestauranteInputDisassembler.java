package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

public class RestauranteInputDisassembler {

	private static ModelMapper mapper = new ModelMapper();

	public static Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return mapper.map(restauranteInput, Restaurante.class);
	}
	
	public static void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
//		Para evitar org.hibernate.HibernateException 
//		org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 3 to 4
		restaurante.setCozinha(new Cozinha());
		
		mapper.map(restauranteInput, restaurante);
	}
}
