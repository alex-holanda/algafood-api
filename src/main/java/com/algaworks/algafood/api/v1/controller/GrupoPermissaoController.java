package com.algaworks.algafood.api.v1.controller;

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
import com.algaworks.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.CadastroGrupoPermissaoService;

@RestController
@RequestMapping(path = "/v1/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController {

	@Autowired
	private PermissaoModelAssembler permissaoModelAssembler;

	@Autowired
	private CadastroGrupoPermissaoService grupoPermissaoService;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public ResponseEntity<CollectionModel<PermissaoModel>> listar(@PathVariable Long grupoId) {

		var permissoesModel = permissaoModelAssembler.toCollectionModel(grupoPermissaoService.listar(grupoId));

		permissoesModel.removeLinks();

		permissoesModel.add(algaLinks.linkToGrupoPermissoes(grupoId));

		if (algaSecurity.podeEditarUsusariosGruposPermissoes()) {
			permissoesModel.add(algaLinks.linkToGrupoPermissoesAssociar(grupoId, "associar"));

			permissoesModel.forEach(permissao -> {
				permissao.add(algaLinks.linkToGrupoPermissoesDesassociar(grupoId, permissao.getId(), "desassociar"));
			});
		}

		return ResponseEntity.ok(permissoesModel);
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PutMapping("/{permissaoId}")
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoPermissaoService.associar(grupoId, permissaoId);

		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("/{permissaoId}")
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoPermissaoService.desassociar(grupoId, permissaoId);

		return ResponseEntity.noContent().build();
	}
}
