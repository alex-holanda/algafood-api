package com.algaworks.algafood.domain.model;

import java.time.OffsetDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SingularAttribute<Usuario, String> senha;
	public static volatile SingularAttribute<Usuario, String> nome;
	public static volatile SetAttribute<Usuario, Grupo> grupos;
	public static volatile SingularAttribute<Usuario, Long> id;
	public static volatile SingularAttribute<Usuario, OffsetDateTime> dataCadastro;
	public static volatile SingularAttribute<Usuario, String> email;

	public static final String SENHA = "senha";
	public static final String NOME = "nome";
	public static final String GRUPOS = "grupos";
	public static final String ID = "id";
	public static final String DATA_CADASTRO = "dataCadastro";
	public static final String EMAIL = "email";

}

