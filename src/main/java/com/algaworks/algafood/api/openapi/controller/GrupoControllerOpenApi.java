package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	public ResponseEntity<CollectionModel<GrupoModel>> listar();
	
	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrada", response = Problem.class)
	})
	public ResponseEntity<GrupoModel> buscar(
			@ApiParam(value = "ID de uma cidade", example = "1")
			Long id);
	
	@ApiOperation("Cadastra um grupo")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
	public ResponseEntity<GrupoModel> adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
			GrupoInput grupoInput);
	
	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Grupo atualizado"),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
	public ResponseEntity<GrupoModel> atualizar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
			GrupoInput grupoInput, 
			@ApiParam(value = "ID de uma cidade", example = "1")
			Long id);
	
	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Grupo excluído"),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
	public ResponseEntity<Void> remover(
			@ApiParam(value = "ID de uma cidade", example = "1")
			Long id);
}
