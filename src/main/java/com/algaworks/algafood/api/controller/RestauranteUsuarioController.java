package com.algaworks.algafood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.service.CadastroRestauranteUsuarioService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController {
	
	@Autowired
	private CadastroRestauranteUsuarioService restauranteUsuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@GetMapping
	public ResponseEntity<CollectionModel<UsuarioModel>> listar(@PathVariable Long restauranteId) {
		
		return ResponseEntity.ok(usuarioModelAssembler.toCollectionModel(restauranteUsuarioService.responsaveis(restauranteId))
				.removeLinks()
				.add(linkTo(methodOn(RestauranteUsuarioController.class).listar(restauranteId)).withSelfRel()));
	}
	
	@PutMapping("/{usuarioId}")
	public ResponseEntity<Void> adicionar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		
		restauranteUsuarioService.adicionarResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> remover(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		
		restauranteUsuarioService.removerResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
}
