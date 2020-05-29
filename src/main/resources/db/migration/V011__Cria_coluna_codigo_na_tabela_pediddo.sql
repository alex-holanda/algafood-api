ALTER TABLE pedido
	ADD codigo CHAR(36) NOT NULL AFTER id;

ALTER TABLE pedido
	ADD CONSTRAINT uk_pedido_codigo UNIQUE (codigo);