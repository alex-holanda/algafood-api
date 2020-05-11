package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping
	public ResponseEntity<List<GrupoModel>> listar(@PathVariable Long usuarioId) {
		
		return ResponseEntity.ok(grupoModelAssembler.toCollectionModel(usuarioGrupoService.listarGrupos(usuarioId)));
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
