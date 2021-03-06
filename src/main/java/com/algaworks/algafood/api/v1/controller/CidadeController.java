package com.algaworks.algafood.api.v1.controller;

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

import com.algaworks.algafood.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeModelAssembler assembler;

	@Autowired
	private CidadeInputDisassembler disassembler;

	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping
	public ResponseEntity<CollectionModel<CidadeModel>> listar() {
		
		return ResponseEntity.ok(assembler.toCollectionModel(cidadeRepository.findAll()));
		
//		cidadesCollectionModel.add(linkTo(CidadeController.class).withSelfRel());
		
	}

	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping("/{id}")
	public ResponseEntity<CidadeModel> buscar(@PathVariable Long id) {

		CidadeModel cidadeModel = assembler.toModel(cadastroCidade.buscarOuFalhar(id)); 

		return ResponseEntity.ok(cidadeModel);
	}

	@CheckSecurity.Cidades.PodeEditar
	@PostMapping
	public ResponseEntity<CidadeModel> adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cadastroCidade.salvar(disassembler.toDomainObject(cidadeInput));
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cidade.getId())
					.toUri();

			return ResponseEntity.created(uri).body(assembler.toModel(cidade));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Cidades.PodeEditar
	@PutMapping("/{id}")
	public ResponseEntity<CidadeModel> atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {

		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);

		disassembler.copyToDomainObject(cidadeInput, cidadeAtual);

		try {
			return ResponseEntity.ok(assembler.toModel(cadastroCidade.salvar(cidadeAtual)));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Cidades.PodeEditar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		cadastroCidade.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
