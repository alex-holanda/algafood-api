package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;

import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;

public class CozinhaInputDisassembler {

	private static ModelMapper mapper = new ModelMapper();
	
	public static Cozinha toDomainObject(CozinhaInput cozinhaInput) {
		return mapper.map(cozinhaInput, Cozinha.class);
	}
	
	public static void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
		mapper.map(cozinhaInput, cozinha);
	}
}
