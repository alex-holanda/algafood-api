package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as cozinhas")
	public ResponseEntity<PagedModel<CozinhaModel>> listar(Pageable pageable);
	
	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	public ResponseEntity<CozinhaModel> buscar(
			@ApiParam(value = "ID de uma cozinha", example = "1")
			Long id);
	
	@ApiOperation("Cadastra uma cozinha")
	public ResponseEntity<CozinhaModel> adicionar(
			@ApiParam(value = "corpo", example = "Representação de uma nova cozinha")
			CozinhaInput cozinhaInput);
	
	@ApiOperation("Atualiza uma cozinha por ID")
	public ResponseEntity<CozinhaModel> atualizar(
			@ApiParam(value = "Id de uma cozinha", example = "1")
			Long id,
			@ApiParam(value = "Representação de uma cozinha com os novos dados")
			CozinhaInput cozinhaInput);
	
	@ApiOperation("Exclui uma cozinha por ID")
	public ResponseEntity<Void> remover(
			@ApiParam(value = "ID de uma cozinha", example = "1")
			Long id);		
}
