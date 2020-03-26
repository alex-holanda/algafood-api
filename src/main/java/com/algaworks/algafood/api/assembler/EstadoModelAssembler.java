package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;

public class EstadoModelAssembler {

	private static ModelMapper mapper = new ModelMapper();
	
	public static EstadoModel toModel(Estado estado) {
		return mapper.map(estado, EstadoModel.class);
	}
	
	public static List<EstadoModel> toCollectionModel(List<Estado> estados) {
		return estados.stream().map(estado -> toModel(estado)).collect(Collectors.toList());
	}
}
