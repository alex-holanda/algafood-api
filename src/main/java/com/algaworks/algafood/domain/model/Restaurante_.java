package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Restaurante.class)
public abstract class Restaurante_ {

	public static volatile SingularAttribute<Restaurante, BigDecimal> taxaFrete;
	public static volatile SingularAttribute<Restaurante, Cozinha> cozinha;
	public static volatile SingularAttribute<Restaurante, String> nome;
	public static volatile SingularAttribute<Restaurante, Long> id;

	public static final String TAXA_FRETE = "taxaFrete";
	public static final String COZINHA = "cozinha";
	public static final String NOME = "nome";
	public static final String ID = "id";

}

