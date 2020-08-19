package com.algaworks.algafood.api.v1.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.UsuarioAlteracaoInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	private ModelMapper mapper;
	
	public Usuario toDomainObject(UsuarioInput usuarioInput) {
		return mapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioAlteracaoInput usuarioAlteracaoInput, Usuario usuario) {
		mapper.map(usuarioAlteracaoInput, usuario);
	}
}
