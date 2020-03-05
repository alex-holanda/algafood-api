package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Estado_;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CidadeMixin {

	@JsonIgnoreProperties(value = Estado_.NOME, allowGetters = true)
	private Estado estado;
}
