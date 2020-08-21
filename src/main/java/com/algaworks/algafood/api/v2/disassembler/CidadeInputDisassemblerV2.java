package com.algaworks.algafood.api.v2.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassemblerV2 {

	@Autowired
	private ModelMapper mapper;
	
	public Cidade toDomainObject(CidadeInputV2 cidadeInput) {
		return mapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputV2 cidadeInput, Cidade cidade) {
		cidade.setEstado(new Estado());
		
		mapper.map(cidadeInput, cidade);
	}
}
