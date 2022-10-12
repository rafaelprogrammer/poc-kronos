--Para remover acentos
create extension unaccent;

INSERT INTO public.empresa
(id, cnpj, inscricao_estadual, email_contato, nome_contato, nome_fantasia, razao_social, site)
VALUES(1, '56453112000152', 'teste', 'teste@gmail.com', 'teste1', 'Fatima', 'Fátima', 'www.fatima.com.br');

ALTER TABLE public.escala ALTER COLUMN id_instituicao DROP NOT NULL;

ALTER TABLE public.instituicao ALTER COLUMN id_tipo_escala DROP NOT NULL;

INSERT INTO public.instituicao
(id, nome, ativo, id_arq_anexo, id_empresa, id_tipo_escala, mantenedora)
VALUES(6, 'Fátima', true, NULL, 1, NULL, true);
