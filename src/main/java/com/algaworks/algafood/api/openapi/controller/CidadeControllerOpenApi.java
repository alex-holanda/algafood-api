package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	public ResponseEntity<List<CidadeModel>> listar();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	public ResponseEntity<CidadeModel> buscar(
			@ApiParam(value = "ID de uma cidade", example = "1") Long id);

	@ApiOperation("Cadastra uma cidade")
	public ResponseEntity<CidadeModel> adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade") CidadeInput cidadeInput);

	@ApiOperation("Atualiza uma cidade por ID")
	public ResponseEntity<CidadeModel> atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1") Long id,
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados") CidadeInput cidadeInput);

	@ApiOperation("Exclui uma cidade por ID")
	public ResponseEntity<Void> remover(Long id);
	
}
