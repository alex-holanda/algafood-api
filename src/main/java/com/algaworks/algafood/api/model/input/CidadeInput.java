package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class CidadeInput {

	@NotBlank
	private String nome;
	
	@Valid
	private EstadoIdInput estado;
}
