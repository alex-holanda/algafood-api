package com.algaworks.algafood.api.v1.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.algalinks.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteModel.class);
	}

	public RestauranteModel toModel(Restaurante restaurante) {
		var restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		mapper.map(restaurante, restauranteModel);

		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		}

		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restauranteModel.getCozinha().getId()));
		}

		if (algaSecurity.podeConsultarCidades()) {
			if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
				restauranteModel.getEndereco().getCidade()
						.add(algaLinks.linkToCidade(restauranteModel.getEndereco().getCidade().getId()));
			}
		}
		

		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			if (restaurante.ativacaoPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
			}

			if (restaurante.inativacaoPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
			}
		}

		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {

			if (restaurante.aberturaPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
			}

			if (restaurante.fechamentoPermitido()) {
				restauranteModel.add(algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
			}
		}

		if (algaSecurity.podeConsultarFormasPagamento()) {
			restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteModel.getId(), "formas-pagamento"));			
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			restauranteModel.add(algaLinks.linkToResponsaveis(restauranteModel.getId(), "responsaveis"));			
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestauranteProduto(restauranteModel.getId(), "produtos"));			
		}

		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			collectionModel.add(linkTo(RestauranteController.class).withSelfRel());			
		}
		
		return collectionModel;
	}
}
