package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.domain.model.Cozinha;

public class CozinhaModelAssembler {

	private static ModelMapper mapper = new ModelMapper();
	
	public static CozinhaModel toModel(Cozinha cozinha) {
		return mapper.map(cozinha, CozinhaModel.class);
	}
	
	public static List<CozinhaModel> toCollectionModel(List<Cozinha> cozinhas) {
		return cozinhas.stream().map(cozinha -> toModel(cozinha)).collect(Collectors.toList());
	}
}
