package com.algaworks.algafood.domain.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Grupo.class)
public abstract class Grupo_ {

	public static volatile SetAttribute<Grupo, Permissao> permissoes;
	public static volatile SingularAttribute<Grupo, String> nome;
	public static volatile SingularAttribute<Grupo, Long> id;

	public static final String PERMISSOES = "permissoes";
	public static final String NOME = "nome";
	public static final String ID = "id";

}

