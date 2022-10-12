--Para remover acentos
create extension unaccent;

CREATE TABLE instituicao (
	id bigserial NOT NULL,
	nome varchar(80) NOT NULL,
	ativo bool NOT NULL,
	id_arq_anexo int8 NULL,
	id_empresa int8 NOT NULL,
	mantenedora bool NOT NULL,
	CONSTRAINT pk_instituicao PRIMARY KEY (id)
	--CONSTRAINT fk_instituicao_arquivo_anexo FOREIGN KEY (id_arq_anexo) REFERENCES arquivo_anexo(id),
	--CONSTRAINT fk_instituicao_empresa FOREIGN KEY (id_empresa) REFERENCES empresa(id)
);

INSERT INTO instituicao
(id, nome, ativo, id_arq_anexo, id_empresa, mantenedora)
VALUES(2, 'Instituição Escola Fátima', true, NULL, 2, false);
INSERT INTO public.instituicao
(id, nome, ativo, id_arq_anexo, id_empresa, mantenedora)
VALUES(3, 'Instituição Empresa01', true, NULL, 3, false);

CREATE TABLE pais (
	id bigserial NOT NULL,
	nome varchar(50) NOT NULL,
	ddi varchar(6) NOT NULL,
	gentilico varchar(50) NOT NULL,
	sigla varchar(3) NOT NULL,
	CONSTRAINT pk_pais PRIMARY KEY (id)
);

INSERT INTO pais
(id, nome, ddi, gentilico, sigla)
VALUES(30, 'Brasil', '', '', 'BRA');

CREATE TABLE tipo_genero (
	id int8 NOT NULL,
	nome varchar(10) NOT NULL,
	sigla varchar(1) NOT NULL,
	CONSTRAINT pk_tipo_genero PRIMARY KEY (id),
	CONSTRAINT uk_tipo_genero_nome UNIQUE (nome),
	CONSTRAINT uk_tipo_genero_sigla UNIQUE (sigla)
);

CREATE TABLE public.perfil (
	nome varchar(30) NOT NULL,
	id bigserial NOT NULL,
	CONSTRAINT pk_perfil PRIMARY KEY (id)
);
CREATE INDEX uk_perfil ON public.perfil USING btree (nome);

INSERT INTO public.perfil
(nome, id)
VALUES('Administrador', 1);
INSERT INTO public.perfil
(nome, id)
VALUES('Administrador de usuários', 2);
INSERT INTO public.perfil
(nome, id)
VALUES('Recursos Humanos', 10);
INSERT INTO public.perfil
(nome, id)
VALUES('Auxiliar de Recursos Humanos', 11);


INSERT INTO public.tipo_genero
(id, nome, sigla)
VALUES(1, 'Masculino', 'M');
INSERT INTO public.tipo_genero
(id, nome, sigla)
VALUES(2, 'Feminino', 'S');

CREATE TABLE pessoa (
	id bigserial NOT NULL,
	numero_registro int8 NOT NULL,
	data_nascimento date NOT NULL,
	email varchar(100) NOT NULL,
	nome varchar(100) NOT NULL,
	nome_usual varchar(50) NOT NULL,
	nome_social varchar(100) NOT NULL,
	cpf varchar(11) NOT NULL,
	banco_talento bool NOT NULL,
	grau_comportamento float4 NOT NULL DEFAULT 0,
	id_arq_anexo int8 NULL,
	id_pais int8 NOT NULL,
	id_cidade int8 NULL,
	id_genero int8 NOT NULL,
	id_instituicao int8 NOT NULL,
	CONSTRAINT pk_pessoa PRIMARY KEY (id),
--	CONSTRAINT fk_pessoa_arquivo_anexo FOREIGN KEY (id_arq_anexo) REFERENCES public.arquivo_anexo(id),
--	CONSTRAINT fk_pessoa_cidade FOREIGN KEY (id_cidade) REFERENCES public.cidade(id),
	CONSTRAINT fk_pessoa_instituicao FOREIGN KEY (id_instituicao) REFERENCES public.instituicao(id),
	CONSTRAINT fk_pessoa_pais FOREIGN KEY (id_pais) REFERENCES public.pais(id),
	CONSTRAINT fk_pessoa_tipo_genero FOREIGN KEY (id_genero) REFERENCES public.tipo_genero(id)
);

INSERT INTO public.pessoa
(id, numero_registro, data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, grau_comportamento, id_arq_anexo, id_pais, id_cidade, id_genero, id_instituicao)
VALUES(55, 2019090001, '1965-03-09', 'fdboeti@gmail.com', 'Francisco Daniel Batista de Oliveira', 'Francisco Daniel', 'Francisco Daniel Batista de Oliveira', '06849869817', true, 0, NULL, 30, 5564, 1, 2);
INSERT INTO public.pessoa
(id, numero_registro, data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, grau_comportamento, id_arq_anexo, id_pais, id_cidade, id_genero, id_instituicao)
VALUES(66, 2019090002, '1984-03-09', 'rafa.jesuscristo@gmail.com', 'Rafael Alves Machado', 'Rafael', 'Rafael Alves Machado', '98513346187', true, 0, NULL, 30, 5564, 1, 2);
INSERT INTO public.pessoa
(id, numero_registro, data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, grau_comportamento, id_arq_anexo, id_pais, id_cidade, id_genero, id_instituicao)
VALUES(77, 2019090003, '1999-03-09', 'paulo@kronos.com', 'Paulo Silva', 'Paulo', 'Paulo Silva', '68679696072', true, 0, NULL, 30, 5564, 1, 2);


-- Drop table

CREATE TABLE public.usuario (
	id bigserial NOT NULL,
	id_pessoa int8 NOT NULL,
	ativo bool NOT NULL,
	data_criacao timestamp NOT NULL,
	hash_senha varchar(255) NULL,
	data_ativacao timestamp NOT NULL,
	id_instituicao int8 NOT NULL,
	bloqueado bool NOT NULL DEFAULT false,
	"token" varchar(200) NULL,
	data_hora_token timestamp NULL,
	CONSTRAINT pk_usuario PRIMARY KEY (id)
);

ALTER TABLE public.usuario ADD CONSTRAINT fk_usuario_instituicao FOREIGN KEY (id_instituicao) REFERENCES instituicao(id);
ALTER TABLE public.usuario ADD CONSTRAINT fk_usuario_pessoa FOREIGN KEY (id_pessoa) REFERENCES pessoa(id);

--CREATE SEQUENCE public.seq_id_usuario
--	INCREMENT BY 1
--	MINVALUE 1
--	MAXVALUE 9223372036854775807
--	START 12;
--
--ALTER SEQUENCE public.seq_id_usuario OWNER TO postgres;
--GRANT ALL ON SEQUENCE public.seq_id_usuario TO postgres;
--
--ALTER TABLE public.usuario ALTER COLUMN id SET DEFAULT NEXTVAL("seq_id_usuario"::regclass);

INSERT INTO public.usuario
(id, id_pessoa, ativo, data_criacao, hash_senha, data_ativacao, id_instituicao, bloqueado, "token", data_hora_token)
VALUES(50, 55, true, '2019-09-05 22:05:43.007', NULL, '2019-09-05 22:05:43.007', 2, false, NULL, NULL);
INSERT INTO public.usuario
(id, id_pessoa, ativo, data_criacao, hash_senha, data_ativacao, id_instituicao, bloqueado, "token", data_hora_token)
VALUES(60, 66, true, '2019-09-05 22:05:43.007', NULL, '2019-09-05 22:05:43.007', 2, false, NULL, NULL);


CREATE TABLE public.usuario_perfil (
	id_usuario int8 NOT NULL,
	id_perfil int8 NOT NULL,
	CONSTRAINT uk_usuario_perfil UNIQUE (id_usuario, id_perfil)
);

ALTER TABLE public.usuario_perfil ADD CONSTRAINT fk_usuario_perfil_perfil FOREIGN KEY (id_perfil) REFERENCES perfil(id);
ALTER TABLE public.usuario_perfil ADD CONSTRAINT fk_usuario_perfil_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id);

INSERT INTO public.usuario_perfil
(id_usuario, id_perfil)
VALUES(50, 1);
INSERT INTO public.usuario_perfil
(id_usuario, id_perfil)
VALUES(60, 1);








