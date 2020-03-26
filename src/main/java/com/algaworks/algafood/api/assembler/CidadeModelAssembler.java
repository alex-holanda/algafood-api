package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.domain.model.Cidade;

public class CidadeModelAssembler {

	private static ModelMapper mapper = new ModelMapper();
	
	public static CidadeModel toModel(Cidade cidade) {
		return mapper.map(cidade, CidadeModel.class);
	}
	
	public static List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
		return cidades.stream().map(cidade -> toModel(cidade)).collect(Collectors.toList());
	}
}
