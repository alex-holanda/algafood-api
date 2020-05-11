CREATE TABLE restaurante_usuario_responsavel(
	restaurante_id	BIGINT NOT NULL,
    usuario_id	BIGINT NOT NULL,
    
    CONSTRAINT fk_restaurante_usuario_restaurante FOREIGN KEY (restaurante_id)	REFERENCES restaurante (id),
    CONSTRAINT fk_restaurante_usuario_usuario 	FOREIGN KEY (restaurante_id) 	REFERENCES usuario (id),
    PRIMARY KEY (restaurante_id, usuario_id)
) engine=InnoDB default charset=UTF8MB4;