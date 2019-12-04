CREATE TABLE pedido (
	id BIGINT NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(40) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    taxa_frete DECIMAL(10, 2) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    
    restaurante_id BIGINT NOT NULL,
    usuario_cliente_id BIGINT NOT NULL,
    forma_pagamento_id BIGINT NOT NULL,
    
    endereco_cidade_id BIGINT NOT NULL,
    endereco_cep VARCHAR(9) NOT NULL,
    endereco_logradouro VARCHAR(160) NOT NULL,
    endereco_numero VARCHAR(20) NOT NULL,
    endereco_complemento VARCHAR(160),
    endereco_bairro VARCHAR(100) NOT NULL,
    
    status VARCHAR(10) NOT NULL,
    data_craicao DATETIME NOT NULL,
    data_confirmacao DATETIME NULL,
    data_entrega DATETIME NULL,
    data_cancelamento DATETIME NULL,
    
    primary key (id),
    
    CONSTRAINT fk_pedido_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante (id),
    CONSTRAINT fk_pedido_usuario_cliente FOREIGN KEY (usuario_cliente_id) REFERENCES usuario (id),
    CONSTRAINT fk_pedido_forma_pagamento FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id)
) engine=InnoDB default charset=UTF8MB4;

CREATE TABLE item_pedido (
	id BIGINT NOT NULL AUTO_INCREMENT,
    quantidade SMALLINT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    preco_total DECIMAL(10, 2) NOT NULL,
    observacao VARCHAR(255) NULL,
    
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    
    PRIMARY KEY (id),
    
    UNIQUE KEY uk_item_pedido_produto (pedido_id, produto_id),
    
    CONSTRAINT fk_item_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedido (id),
    CONSTRAINT fk_item_pedido_produto FOREIGN KEY (produto_id) REFERENCES produto (id)
) engine=InnoDB default charset=UTF8MB4;