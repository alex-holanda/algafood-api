package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {

	@ApiModelProperty(example = "02cd99ae-9f7f-4550-bfe0-b63bd7de979d")
	private String codigo;
	@ApiModelProperty(example = "298.90")
	private BigDecimal subtotal;
	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;
	@ApiModelProperty(example = "308.90")
	private BigDecimal valorTotal;
	@ApiModelProperty(example = "CRIADO")
	private String status;
	@ApiModelProperty(example = "2020-07-14T18:10:43Z")
	private OffsetDateTime dataCriacao;
	
	private RestauranteResumoModel restaurante;
	private UsuarioModel cliente;
}
