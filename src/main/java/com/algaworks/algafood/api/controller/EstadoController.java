package com.algaworks.algafood.api.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.algaworks.algafood.api.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.disassembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoModelAssembler assembler;
	
	@Autowired
	private EstadoInputDisassembler disassembler;

	@GetMapping
	public ResponseEntity<CollectionModel<EstadoModel>> listar() {

		return ResponseEntity.ok().body(assembler.toCollectionModel(estadoRepository.findAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstadoModel> buscar(@PathVariable Long id) {

		return ResponseEntity.ok(assembler.toModel(cadastroEstado.buscarOuFalhar(id)));
	}

	@PostMapping
	public ResponseEntity<EstadoModel> adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = cadastroEstado.salvar(disassembler.toDomainObject(estadoInput));

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(estado.getId()).toUri();

		return ResponseEntity.created(uri).body(assembler.toModel(estado));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EstadoModel> atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);

		disassembler.copyToDomainObject(estadoInput, estadoAtual);
//		BeanUtils.copyProperties(estado, estadoAtual, "id");

		return ResponseEntity.ok(assembler.toModel(cadastroEstado.salvar(estadoAtual)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		cadastroEstado.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
