package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.algalinks.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public PedidoResumoModelAssembler() {
		super(PedidoController.class, PedidoResumoModel.class);
	}
	
	@Override
	public PedidoResumoModel toModel(Pedido pedido) {
		var pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
		mapper.map(pedido, pedidoResumoModel);
		
		if (algaSecurity.podePesquisarPedidos()) {
			pedidoResumoModel.add(algaLinks.linkToPedidos("pedidos"));			
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			pedidoResumoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedidoResumoModel.getRestaurante().getId()));			
		}
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			pedidoResumoModel.getCliente().add(algaLinks.linkToUsuario(pedidoResumoModel.getCliente().getId()));			
		}
		
		return pedidoResumoModel;
	}
}
