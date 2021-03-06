package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Restaurante.class)
public abstract class Restaurante_ {

	public static volatile SingularAttribute<Restaurante, BigDecimal> taxaFrete;
	public static volatile SingularAttribute<Restaurante, Boolean> ativo;
	public static volatile SingularAttribute<Restaurante, Boolean> aberto;
	public static volatile SingularAttribute<Restaurante, Endereco> endereco;
	public static volatile SetAttribute<Restaurante, FormaPagamento> formasPagamento;
	public static volatile SingularAttribute<Restaurante, OffsetDateTime> dataAtualizacao;
	public static volatile ListAttribute<Restaurante, Produto> produtos;
	public static volatile SingularAttribute<Restaurante, Cozinha> cozinha;
	public static volatile SetAttribute<Restaurante, Usuario> responsaveis;
	public static volatile SingularAttribute<Restaurante, String> nome;
	public static volatile SingularAttribute<Restaurante, Long> id;
	public static volatile SingularAttribute<Restaurante, OffsetDateTime> dataCadastro;

	public static final String TAXA_FRETE = "taxaFrete";
	public static final String ATIVO = "ativo";
	public static final String ABERTO = "aberto";
	public static final String ENDERECO = "endereco";
	public static final String FORMAS_PAGAMENTO = "formasPagamento";
	public static final String DATA_ATUALIZACAO = "dataAtualizacao";
	public static final String PRODUTOS = "produtos";
	public static final String COZINHA = "cozinha";
	public static final String RESPONSAVEIS = "responsaveis";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String DATA_CADASTRO = "dataCadastro";

}

