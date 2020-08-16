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
import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.service.CadastroGrupoPermissaoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;
	
	@Autowired
	private CadastroGrupoPermissaoService grupoPermissaoService;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public ResponseEntity<CollectionModel<PermissaoModel>> listar(@PathVariable Long grupoId) {
		
		var permissoesModel = permissaoModelAssembler.toCollectionModel(grupoPermissaoService.listar(grupoId));
		
		permissoesModel
			.removeLinks()
			.add(algaLinks.linkToGrupoPermissoes(grupoId))
			.add(algaLinks.linkToGrupoPermissoesAssociar(grupoId, "associar"));
		
		permissoesModel.forEach(permissao -> {
			permissao.add(algaLinks.linkToGrupoPermissoesDesassociar(grupoId, permissao.getId(), "desassociar"));
		});
		
		return ResponseEntity.ok(permissoesModel);
	}
	
	@PutMapping("/{permissaoId}")
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoPermissaoService.associar(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{permissaoId}")
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoPermissaoService.desassociar(grupoId, permissaoId);
		
		return ResponseEntity.noContent().build();
	}
}
