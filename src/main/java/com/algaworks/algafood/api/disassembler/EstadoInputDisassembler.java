package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;

import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;

public class EstadoInputDisassembler {

	private static ModelMapper mapper = new ModelMapper();
	
	public static Estado toDomainObject(EstadoInput estadoInput) {
		return mapper.map(estadoInput, Estado.class);
	}
	
	public static void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
		mapper.map(estadoInput, estado);
	}
}
