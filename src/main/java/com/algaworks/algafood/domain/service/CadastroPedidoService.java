package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.fielter.PedidoFilter;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;

@Service
public class CadastroPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Autowired
	private CadastroFormaPagamentoService formaPagamentoService;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private CadastroProdutoService produtoService;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	public Page<Pedido> listar(PedidoFilter filtro, Pageable pageable) {
		return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
	}
	
	public Pedido buscar(String codigoPedido) {
		return buscarOuFalhar(codigoPedido);
	}
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		
		validarPedido(pedido);
		validarItens(pedido);
		
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();
		
		return pedidoRepository.save(pedido);
	}
	
	private void validarPedido(Pedido pedido) {
		Usuario cliente = usuarioService.buscar(1L);
		
		Cidade cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = formaPagamentoService.buscar(pedido.getFormaPagamento().getId());
		
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		
		if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(
					String.format("Forma de pagamento '%s' não é aceita por esse restaurante", formaPagamento.getDescricao()));
		}
	}
	
	private void validarItens(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = produtoService.buscar(pedido.getRestaurante().getId(), item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}
	
	private Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido)
					.orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}
}
