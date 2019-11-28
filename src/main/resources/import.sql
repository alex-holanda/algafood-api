insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, id_estado) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, id_estado) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, id_estado) values (3, 'São Paulo', 2);
insert into cidade (id, nome, id_estado) values (4, 'Campinas', 2);
insert into cidade (id, nome, id_estado) values (5, 'Fortaleza', 3);

insert into restaurante (id, nome, taxa_frete, id_cozinha, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, endereco_complemento, data_cadastro, data_atualizacao) values (1, 'Thai Gourmet', 10, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro', '', utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, id_cozinha, data_cadastro, data_atualizacao) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, id_cozinha, data_cadastro, data_atualizacao) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp);

insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into produto (id, ativo, nome, descricao, preco, restaurante_id) values (1, 1, 'Macarrão', 'Macarrão para sopa', 4.50, 1);