package com.algaworks.algafood.api.v1.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioAlteracaoInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Autowired
	private UsuarioModelAssembler usuarioAssembler;
	
	@Autowired
	private UsuarioInputDisassembler usuarioDisassembler;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping
	public ResponseEntity<CollectionModel<UsuarioModel>> listar() {
		return ResponseEntity.ok(usuarioAssembler.toCollectionModel(usuarioService.listar()));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioModel> buscar(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioAssembler.toModel(usuarioService.buscar(id)));
	}
	
	@PostMapping
	public ResponseEntity<UsuarioModel> adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuario = usuarioService.salvar(usuarioDisassembler.toDomainObject(usuarioInput));
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.created(uri).body(usuarioAssembler.toModel(usuario));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioModel> atualizar(@RequestBody @Valid UsuarioAlteracaoInput usuarioAlteracaoInput, @PathVariable Long id) {
		Usuario usuario = usuarioService.buscar(id);
		
		usuarioDisassembler.copyToDomainObject(usuarioAlteracaoInput, usuario);
		
		return ResponseEntity.ok(usuarioAssembler.toModel(usuarioService.atualizar(usuario)));
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
	@PutMapping("/{id}/senha")
	public ResponseEntity<Void> atualizarSenha(@RequestBody @Valid SenhaInput senhaInput, @PathVariable Long id) {
		
		usuarioService.alterarSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
				
		return ResponseEntity.noContent().build();
	}
}
