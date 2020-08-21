package com.algaworks.algafood.api.v1.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.algalinks.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.domain.service.CadastroRestauranteUsuarioService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioController {
	
	@Autowired
	private CadastroRestauranteUsuarioService restauranteUsuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public ResponseEntity<CollectionModel<UsuarioModel>> listar(@PathVariable Long restauranteId) {
		CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler.toCollectionModel(restauranteUsuarioService.responsaveis(restauranteId))
				.removeLinks()
				.add(linkTo(methodOn(RestauranteUsuarioController.class).listar(restauranteId)).withSelfRel())
				.add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, null, "associar"));
		
		usuariosModel.getContent().stream().forEach(usuarioModel -> {
			usuarioModel.add(algaLinks.linkToRestauranteResponsavelDessassociacao(restauranteId, usuarioModel.getId(), "desassociar"));
		});
		
		return ResponseEntity.ok(usuariosModel);
	}
	
	@PutMapping("/{usuarioId}")
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		
		restauranteUsuarioService.adicionarResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		
		restauranteUsuarioService.removerResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}
}
