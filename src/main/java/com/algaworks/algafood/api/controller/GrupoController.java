package com.algaworks.algafood.api.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.disassembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private CadastroGrupoService grupoService;
	
	@Autowired
	private GrupoModelAssembler grupoAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoDisasemmbler;
	
	@GetMapping
	public ResponseEntity<CollectionModel<GrupoModel>> listar() {
		var grupoCollectionModel = grupoAssembler.toCollectionModel(grupoService.listar());
		
		return ResponseEntity.ok(grupoCollectionModel);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GrupoModel> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(grupoAssembler.toModel(grupoService.buscar(id)));
	}
	
	@PostMapping
	public ResponseEntity<GrupoModel> adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		
		Grupo grupo = grupoService.salvar(grupoDisasemmbler.toDomainObject(grupoInput));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(grupo.getId()).toUri();
		
		return ResponseEntity.created(uri).body(grupoAssembler.toModel(grupo));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<GrupoModel> atualizar(@RequestBody @Valid GrupoInput grupoInput, @PathVariable Long id) {
		Grupo grupoAtual = grupoService.buscar(id);
		
		grupoDisasemmbler.copyToDomainObject(grupoInput, grupoAtual);
		
		return ResponseEntity.ok(grupoAssembler.toModel(grupoService.atualizar(grupoAtual)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		grupoService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
