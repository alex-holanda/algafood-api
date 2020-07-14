package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade", description = "Representa uma Cidade")
@Getter
@Setter
public class CidadeModel {

	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(example = "Uberlândia")
	private String nome;
	private EstadoModel estado;
}
