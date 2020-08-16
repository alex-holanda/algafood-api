package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.algalinks.AlgaLinks;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.domain.service.CadastroUsuarioGrupoService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private CadastroUsuarioGrupoService usuarioGrupoService;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public ResponseEntity<CollectionModel<GrupoModel>> listar(@PathVariable Long usuarioId) {
		
		var gruposModel = grupoModelAssembler.toCollectionModel(usuarioGrupoService.listarGrupos(usuarioId));
		
		gruposModel.removeLinks()
			.add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
		
		gruposModel.forEach(grupoModel -> {
			grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(usuarioId, grupoModel.getId(), "desassociar"));
		});
		
		return ResponseEntity.ok(gruposModel);
	}
	
	@PutMapping("/{grupoId}")
	public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		
		usuarioGrupoService.associar(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{grupoId}")
	public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		
		usuarioGrupoService.desassociar(usuarioId, grupoId);
		
		return ResponseEntity.noContent().build();
	}
}
