CREATE TABLE foto_produto(
	produto_id BIGINT NOT NULL,
    nome_arquivo VARCHAR(150) NOT NULL,
    descricao VARCHAR(150),
    content_type VARCHAR(40) NOT NULL,
    tamanho INT NOT NULL,
    
    CONSTRAINT pk_foto_produto_id PRIMARY KEY (produto_id),
    CONSTRAINT fk_foto_produto_produto FOREIGN KEY (produto_id) REFERENCES produto (id)
) engine=InnoDB default charset=UTF8MB4;