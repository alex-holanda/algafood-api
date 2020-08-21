package com.algaworks.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class CidadeInputV2 {

	@ApiModelProperty(example = "Uberlândia", required = true)
	@NotBlank
	private String nomeCidade;
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long idEstado;
}
