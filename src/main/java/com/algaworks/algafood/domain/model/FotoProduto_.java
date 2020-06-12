package com.algaworks.algafood.domain.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FotoProduto.class)
public abstract class FotoProduto_ {

	public static volatile SingularAttribute<FotoProduto, Long> tamanho;
	public static volatile SingularAttribute<FotoProduto, Produto> produto;
	public static volatile SingularAttribute<FotoProduto, String> nomeArquivo;
	public static volatile SingularAttribute<FotoProduto, Long> id;
	public static volatile SingularAttribute<FotoProduto, String> contentType;
	public static volatile SingularAttribute<FotoProduto, String> descricao;

	public static final String TAMANHO = "tamanho";
	public static final String PRODUTO = "produto";
	public static final String NOME_ARQUIVO = "nomeArquivo";
	public static final String ID = "id";
	public static final String CONTENT_TYPE = "contentType";
	public static final String DESCRICAO = "descricao";

}

