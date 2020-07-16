package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.fielter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiOperation(value = "Lista os pedidos")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
					name = "campos", paramType = "query", type = "string")
	})
	public ResponseEntity<Page<PedidoResumoModel>> pesquisar(PedidoFilter filtro, Pageable pageable);
	
	@ApiOperation("Busca um pedido por ID")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
					name = "campos", paramType = "query", type = "string")
	})
	@ApiResponses({
		@ApiResponse(code = 404, message = "Pedido não encontrada", response = Problem.class)
	})
	public ResponseEntity<PedidoModel> buscar(
			@ApiParam(value = "Busca um pedido pelo código", example = "02cd99ae-9f7f-4550-bfe0-b63bd7de979d")
			String codigoPedido);
	
	@ApiOperation("Registra um novo pedido")
	public ResponseEntity<PedidoModel> adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo pedido")
			PedidoInput pedidoInput);
}
