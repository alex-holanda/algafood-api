package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pedido.class)
public abstract class Pedido_ {

	public static volatile SingularAttribute<Pedido, FormaPagamento> formaPagamento;
	public static volatile SingularAttribute<Pedido, BigDecimal> taxaFrete;
	public static volatile SingularAttribute<Pedido, String> codigo;
	public static volatile SingularAttribute<Pedido, OffsetDateTime> dataConfirmacao;
	public static volatile ListAttribute<Pedido, ItemPedido> itens;
	public static volatile SingularAttribute<Pedido, Endereco> enderecoEntrega;
	public static volatile SingularAttribute<Pedido, Restaurante> restaurante;
	public static volatile SingularAttribute<Pedido, OffsetDateTime> dataCancelamento;
	public static volatile SingularAttribute<Pedido, Usuario> cliente;
	public static volatile SingularAttribute<Pedido, BigDecimal> subtotal;
	public static volatile SingularAttribute<Pedido, BigDecimal> valorTotal;
	public static volatile SingularAttribute<Pedido, OffsetDateTime> dataEntrega;
	public static volatile SingularAttribute<Pedido, Long> id;
	public static volatile SingularAttribute<Pedido, OffsetDateTime> dataCriacao;
	public static volatile SingularAttribute<Pedido, StatusPedido> status;

	public static final String FORMA_PAGAMENTO = "formaPagamento";
	public static final String TAXA_FRETE = "taxaFrete";
	public static final String CODIGO = "codigo";
	public static final String DATA_CONFIRMACAO = "dataConfirmacao";
	public static final String ITENS = "itens";
	public static final String ENDERECO_ENTREGA = "enderecoEntrega";
	public static final String RESTAURANTE = "restaurante";
	public static final String DATA_CANCELAMENTO = "dataCancelamento";
	public static final String CLIENTE = "cliente";
	public static final String SUBTOTAL = "subtotal";
	public static final String VALOR_TOTAL = "valorTotal";
	public static final String DATA_ENTREGA = "dataEntrega";
	public static final String ID = "id";
	public static final String DATA_CRIACAO = "dataCriacao";
	public static final String STATUS = "status";

}

