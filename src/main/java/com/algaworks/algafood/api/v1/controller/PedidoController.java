package com.algaworks.algafood.api.v1.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.api.v1.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.fielter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping(path = "/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private CadastroPedidoService pedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourceAssembler;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	@CheckSecurity.Pedidos.PodePesquisar
	@GetMapping
	public ResponseEntity<PagedModel<PedidoResumoModel>> pesquisar(PedidoFilter filtro, Pageable pageable) {
		var pageableTraduzido = traduzirPageable(pageable);
		
		Page<Pedido> pedidosPage = pedidoService.listar(filtro, pageableTraduzido);
		
		var pedidosPagedModel = pagedResourceAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
		
		pedidosPage = new PageWrapper<>(pedidosPage, pageable);
		
		return ResponseEntity.ok(pedidosPagedModel);
	}
	
	@CheckSecurity.Pedidos.PodeBuscar
	@GetMapping("/{codigoPedido}")
	public ResponseEntity<PedidoModel> buscar(@PathVariable String codigoPedido) {
		Pedido pedido = pedidoService.buscar(codigoPedido);
		PedidoModel pedidoModel = pedidoModelAssembler.toModel(pedido);
		
		return ResponseEntity.ok(pedidoModel);
	}
	
	@CheckSecurity.Pedidos.PodeCriar
	@PostMapping
	public ResponseEntity<PedidoModel> adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
		try {
			
			Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
			pedido.setCliente(new Usuario());
			pedido.getCliente().setId(algaSecurity.getUsuarioId());
			
			pedido = pedidoService.emitir(pedido);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(pedido.getCodigo()).toUri();
			
			return ResponseEntity.created(uri).body(pedidoModelAssembler.toModel(pedido));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
				);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
}
