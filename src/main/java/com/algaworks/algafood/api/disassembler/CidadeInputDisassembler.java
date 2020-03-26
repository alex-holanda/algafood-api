package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

public class CidadeInputDisassembler {

	private static ModelMapper mapper = new ModelMapper();
	
	public static Cidade toDomainObject(CidadeInput cidadeInput) {
		return mapper.map(cidadeInput, Cidade.class);
	}
	
	public static void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
//		adicionado para evitar org.hibernate.HibernateException
		cidade.setEstado(new Estado());
		
		mapper.map(cidadeInput, cidade);
	}
}
