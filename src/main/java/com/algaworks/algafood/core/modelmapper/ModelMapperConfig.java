package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.v1.model.EnderecoModel;
import com.algaworks.algafood.api.v1.model.input.ItemPedidoInput;
import com.algaworks.algafood.api.v2.model.input.CidadeInputV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		
		mapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class).addMappings(m -> m.skip(ItemPedido::setId));
		mapper.createTypeMap(CidadeInputV2.class, Cidade.class).addMappings(m -> m.skip(Cidade::setId));
		
		var enderecoToEnderecoModelTypeMap = mapper.createTypeMap(Endereco.class, EnderecoModel.class);
		
		enderecoToEnderecoModelTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(), 
					(enderecoModelDest, value) -> enderecoModelDest.getCidade().setEstado(value));
		
		return mapper;
	}
}
