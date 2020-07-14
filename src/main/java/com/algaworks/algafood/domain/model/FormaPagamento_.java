package com.algaworks.algafood.domain.model;

import java.time.OffsetDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FormaPagamento.class)
public abstract class FormaPagamento_ {

	public static volatile SingularAttribute<FormaPagamento, OffsetDateTime> dataAtualizacao;
	public static volatile SingularAttribute<FormaPagamento, Long> id;
	public static volatile SingularAttribute<FormaPagamento, String> descricao;

	public static final String DATA_ATUALIZACAO = "dataAtualizacao";
	public static final String ID = "id";
	public static final String DESCRICAO = "descricao";

}

