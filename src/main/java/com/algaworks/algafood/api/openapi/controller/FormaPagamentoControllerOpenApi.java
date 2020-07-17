package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento")
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);
	
	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	public ResponseEntity<FormaPagamentoModel> buscar(
			@ApiParam(value = "ID da Forma de pagamento", example = "1")
			Long formaPagamentoId, ServletWebRequest request);
	
	@ApiOperation("Cadastra uma forma de pagamento")
	public ResponseEntity<FormaPagamentoModel> adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento")
			FormaPagamentoInput formaPagamentoInput);
	
	@ApiOperation("Atualiza uma forma de pagamento por ID")
	public ResponseEntity<FormaPagamentoModel> ataulizar(
			@ApiParam(value = "ID de uma forma de pagamento", example = "1")
			Long formaPagamentoId,
			@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados")
			FormaPagamentoInput formaPagamentoInput);
	
	@ApiOperation("Exclui uma forma de pagamento por ID")
	public ResponseEntity<Void> remover(
			@ApiParam(value = "Id de uma forma de pagamento", example = "1")
			Long formaPagamentoId);
}
