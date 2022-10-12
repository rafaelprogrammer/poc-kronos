--limpa os dados das tabelas que  serão carregadas
delete from usuario_perfil;
delete from perfil;
delete from usuario;
delete from matricula_documento;
delete from documento;
delete from responsavel;
delete from filiacao;
delete from pessoa_talento;
delete from titulacao;
delete from pessoa_telefone;
delete from pessoa_endereco;
delete from curso_funcionario;
delete from disciplina_funcionario;
delete from calendario_curso;
delete from avaliado;
delete from avaliacao;
delete from credito;
delete from substituto;
delete from horario_hora_atividade;
delete from atividade;
delete from horario;
delete from hora_atividade;
delete from turma;
delete from parcela;
delete from contrato;
delete from sub_fase_execucao;
delete from fase_execucao;
delete from periodo_execucao;
delete from evento;
delete from categoria_evento;
delete from calendario;
delete from funcionario;
delete from matricula;
delete from pessoa;
delete from convenio;
delete from sub_fase;
delete from fase;
delete from disciplina_pre_requisito;
delete from disciplina_competencia;
delete from disciplina_objetivo;
delete from disciplina_habilidade;
delete from disciplina_direito;
delete from disciplina;
delete from periodo;
delete from portaria;
delete from curso;
delete from limite;
delete from escala;
delete from atitude;
delete from valor;
delete from instituicao;
delete from empresa_telefone;
delete from operadora;
delete from empresa_endereco;
delete from empresa_tipo_empresa;
delete from empresa;
delete from arquivo_anexo;


delete from public.tipo_abrangencia;
delete from public.tipo_arquivo;
delete from public.tipo_aval_institucional;  --sem carga
delete from public.tipo_avaliacao; 
delete from public.tipo_funcao;
delete from public.tipo_categoria_funcao;
delete from public.tipo_composicao_valor; 
delete from public.tipo_comprovante; 
delete from public.tipo_conteudo;
delete from public.tipo_credito; 
delete from public.tipo_desconto; 
delete from public.tipo_dia_semana;
delete from public.tipo_disciplina; 
delete from public.tipo_doc_mov; --sem carga
delete from public.tipo_documento; 
delete from public.tipo_documento_origem; --sem carga
delete from public.tipo_empresa; 
delete from public.tipo_endereco; 
delete from public.tipo_filiacao; 
delete from public.tipo_finalidade_conselho;
delete from public.tipo_forma_pagamento;
delete from public.tipo_formato;
delete from public.tipo_funcao_conselho;
delete from public.tipo_genero;
delete from public.tipo_hist_pessoa;
delete from public.tipo_hist_usuario;
delete from public.tipo_material;  --sem carga
delete from public.tipo_movimento;  --sem carga
delete from public.tipo_periodo; 
delete from public.tipo_portaria; 
delete from public.tipo_programa; 
delete from public.tipo_publico_alvo;  --sem carga 
delete from public.tipo_regime_letivo;
delete from public.tipo_registro_nota;
delete from public.tipo_responsavel;
delete from public.tipo_resposta;  --sem carga
delete from public.tipo_resultado;
delete from public.tipo_sala;
delete from public.tipo_situacao_aval_inst;  --sem carga
delete from public.tipo_situacao_calendario;
delete from public.tipo_situacao_contrato;
delete from public.tipo_situacao_doc_mov;  --sem carga
delete from public.tipo_situacao_matricula;
delete from public.tipo_situacao_patrimonial;  --sem carga
delete from public.tipo_talento;
delete from public.tipo_telefone;
delete from public.tipo_titulo;
delete from public.tipo_turno;
delete from public.tipo_uso; 

--Para remover acentos
--create extension unaccent;

-- inicializa as sequences das tabelas que serão carregadas
SELECT setval('public.seq_id_usuario', 1, true);
SELECT setval('public.seq_id_documento', 1, true);
SELECT setval('public.seq_id_responsavel', 1, true);
SELECT setval('public.seq_id_filiacao', 1, true);
SELECT setval('public.seq_id_titulacao', 1, true);
SELECT setval('public.seq_id_pessoa_telefone', 1, true);
SELECT setval('public.seq_id_pessoa_endereco', 1, true);
SELECT setval('public.seq_id_pessoa', 1, true);
SELECT setval('public.seq_id_instituicao', 1, true);
SELECT setval('public.seq_id_empresa_telefone', 1, true);
SELECT setval('public.seq_id_empresa_endereco', 1, true);
SELECT setval('public.seq_id_empresa', 1, true);
SELECT setval('public.seq_id_convenio', 1, true);
SELECT setval('public.seq_id_escala', 1, true);
SELECT setval('public.seq_id_limite', 1, true);

SELECT setval('public.seq_id_disciplina', 1, true);
SELECT setval('public.seq_id_curso', 1, true);
SELECT setval('public.seq_id_curso_funcionario', 1, true);
SELECT setval('public.seq_id_funcionario', 1, true);
SELECT setval('public.seq_id_curso_documento', 1, true);
SELECT setval('public.seq_id_periodo', 1, true);
SELECT setval('public.seq_id_fase', 1, true);
SELECT setval('public.seq_id_sub_fase', 1, true);
SELECT setval('public.seq_id_portaria', 1, true);

SELECT setval('public.seq_id_comprovante', 1, true);
SELECT setval('public.seq_id_matricula', 1, true);
SELECT setval('public.seq_id_matricula_documento', 1, true);
SELECT setval('public.seq_id_credito', 1, true);
SELECT setval('public.seq_id_turma_aluno_funcao', 1, true);
SELECT setval('public.seq_id_turma_sala', 1, true);
SELECT setval('public.seq_id_turma', 1, true);
SELECT setval('public.seq_id_turma_funcionario_funcao', 1, true);

SELECT setval('public.seq_id_parcela', 1, true);
SELECT setval('public.seq_id_contrato', 1, true);

SELECT setval('public.seq_id_calendario', 1, true);
SELECT setval('public.seq_id_periodo_execucao', 1, true);
SELECT setval('public.seq_id_fase_execucao', 1, true);
SELECT setval('public.seq_id_sub_fase_execucao', 1, true);
SELECT setval('public.seq_id_substituto', 1, true);
SELECT setval('public.seq_id_horario', 1, true);
SELECT setval('public.seq_id_hora_atividade', 1, true);
SELECT setval('public.seq_id_categoria_evento', 1, true);
SELECT setval('public.seq_id_evento', 1, true);

SELECT setval('public.seq_id_avaliacao_habilidade', 1, true);
SELECT setval('public.seq_id_grupo_avaliacao', 1, true);
SELECT setval('public.seq_id_resultado_habilidade', 1, true);
SELECT setval('public.seq_id_avaliacao', 1, true);
SELECT setval('public.seq_id_avaliado', 1, true);
SELECT setval('public.seq_id_diario', 1, true);
SELECT setval('public.seq_id_atividade', 1, true);
SELECT setval('public.seq_id_frequencia', 1, true);
SELECT setval('public.seq_id_resultado_sub_fase', 1, true);
SELECT setval('public.seq_id_resultado_fase', 1, true);
SELECT setval('public.seq_id_ocorrencia', 1, true);
SELECT setval('public.seq_id_tipo_ocorrencia', 1, true);
SELECT setval('public.seq_id_desempenho', 1, true);
SELECT setval('public.seq_id_parecer', 1, true);
SELECT setval('public.seq_id_conselho', 1, true);
SELECT setval('public.seq_id_perfil', 1, true);
SELECT setval('public.seq_id_hora_atividade', 1, true);
SELECT setval('public.seq_id_horario', 1, true);

SELECT setval('public.seq_id_disciplina_direito', 1, true);
SELECT setval('public.seq_id_disciplina_objetivo', 1, true);
SELECT setval('public.seq_id_disciplina_competencia', 1, true);	
SELECT setval('public.seq_id_disciplina_habilidade', 1, true);

INSERT INTO public.tipo_abrangencia (id, nome) VALUES (1, 'Sub-fase (Bimestre)');
INSERT INTO public.tipo_abrangencia (id, nome) VALUES (2, 'Fase (Semestre)');
INSERT INTO public.tipo_abrangencia (id, nome) VALUES (3, 'Período (Ano)');
INSERT INTO public.tipo_abrangencia (id, nome) VALUES (4, 'Conselho');

INSERT INTO public.tipo_arquivo (id, nome) VALUES (1, 'Imagem');
INSERT INTO public.tipo_arquivo (id, nome) VALUES (2, 'Texto');
INSERT INTO public.tipo_arquivo (id, nome) VALUES (3, 'Formato portável');

INSERT INTO public.tipo_avaliacao (id, nome) VALUES (1, 'Corrente');
INSERT INTO public.tipo_avaliacao (id, nome) VALUES (2, 'Recuperação');
INSERT INTO public.tipo_avaliacao (id, nome) VALUES (3, 'Exame Final');
INSERT INTO public.tipo_avaliacao (id, nome) VALUES (4, 'Conselho');
INSERT INTO public.tipo_avaliacao (id, nome) VALUES (5, 'Atividade em sala');
INSERT INTO public.tipo_avaliacao (id, nome) VALUES (6, 'Atividade extra-classe');

INSERT INTO public.tipo_categoria_funcao (id, nome) VALUES (1, 'Direção');
INSERT INTO public.tipo_categoria_funcao (id, nome) VALUES (2, 'Coordenação');
INSERT INTO public.tipo_categoria_funcao (id, nome) VALUES (3, 'Professores');
INSERT INTO public.tipo_categoria_funcao (id, nome) VALUES (4, 'Conselheiros');
INSERT INTO public.tipo_categoria_funcao (id, nome) VALUES (5, 'Administração');
INSERT INTO public.tipo_categoria_funcao (id, nome) VALUES (6, 'Alunos');
INSERT INTO public.tipo_categoria_funcao (id, nome) VALUES (7, 'Serviços Gerais');

INSERT INTO public.tipo_composicao_valor(id, nome) VALUES (1, 'Por período');
INSERT INTO public.tipo_composicao_valor(id, nome) VALUES (2, 'Por disciplina');

INSERT INTO public.tipo_comprovante(id, nome) VALUES (1, 'Certificado');
INSERT INTO public.tipo_comprovante(id, nome) VALUES (2, 'Diploma');

INSERT INTO public.tipo_conteudo(id, nome) VALUES (1, 'Foto');
INSERT INTO public.tipo_conteudo(id, nome) VALUES (2, 'Logotipo');
INSERT INTO public.tipo_conteudo(id, nome) VALUES (3, 'Documento');

INSERT INTO public.tipo_credito(id, nome) VALUES (1, 'Normal');
INSERT INTO public.tipo_credito(id, nome) VALUES (2, 'Dependência');
INSERT INTO public.tipo_credito(id, nome) VALUES (3, 'Complementar');
INSERT INTO public.tipo_credito(id, nome) VALUES (4, 'Adaptação');

INSERT INTO public.tipo_desconto(id, nome) VALUES (1, 'Convênio');
INSERT INTO public.tipo_desconto(id, nome) VALUES (2, 'Bolsa');
INSERT INTO public.tipo_desconto(id, nome) VALUES (3, 'Antecipação');
INSERT INTO public.tipo_desconto(id, nome) VALUES (4, 'Ex-aluno');


INSERT INTO public.tipo_dia_semana(id, nome, sigla) VALUES (1, 'Segunda-feira', 'Seg');
INSERT INTO public.tipo_dia_semana(id, nome, sigla) VALUES (2, 'Terça-feira', 'Ter');
INSERT INTO public.tipo_dia_semana(id, nome, sigla) VALUES (3, 'Quarta-feira', 'Qua');
INSERT INTO public.tipo_dia_semana(id, nome, sigla) VALUES (4, 'Quinta-feira', 'Qui');
INSERT INTO public.tipo_dia_semana(id, nome, sigla) VALUES (5, 'Sexta-feira', 'Sex');
INSERT INTO public.tipo_dia_semana(id, nome, sigla) VALUES (6, 'Sábado', 'Sab');
INSERT INTO public.tipo_dia_semana(id, nome, sigla) VALUES (7, 'Domingo', 'Dom');

INSERT INTO public.tipo_disciplina(id, nome) VALUES (1, 'Teórica');
INSERT INTO public.tipo_disciplina(id, nome) VALUES (2, 'Prática');

INSERT INTO public.tipo_documento(id, nome) VALUES (1, 'Registro de Nascimento');
INSERT INTO public.tipo_documento(id, nome) VALUES (2, 'Identidade (RG)');
INSERT INTO public.tipo_documento(id, nome) VALUES (3, 'Codigo Pessoa Física (CPF)');
INSERT INTO public.tipo_documento(id, nome) VALUES (4, 'Certificado');
INSERT INTO public.tipo_documento(id, nome) VALUES (5, 'Diploma');
INSERT INTO public.tipo_documento(id, nome) VALUES (6, 'Histórico Escolar');
INSERT INTO public.tipo_documento(id, nome) VALUES (7, 'Título Eleitor');

INSERT INTO public.tipo_empresa(id, nome) VALUES (1, 'Fornecedor');
INSERT INTO public.tipo_empresa(id, nome) VALUES (2, 'Conveniado');
INSERT INTO public.tipo_empresa(id, nome) VALUES (3, 'Instituição de Ensino');

INSERT INTO public.tipo_endereco(id, nome) VALUES (1, 'Residêncial');
INSERT INTO public.tipo_endereco(id, nome) VALUES (2, 'Comercial');

INSERT INTO public.tipo_filiacao(id, nome) VALUES (1, 'Pai');
INSERT INTO public.tipo_filiacao(id, nome) VALUES (2, 'Mae');

INSERT INTO public.tipo_finalidade_conselho(id, nome) VALUES (1, 'Conselho de classe');
INSERT INTO public.tipo_finalidade_conselho(id, nome) VALUES (2, 'Conselho análise ficha individual');

INSERT INTO public.tipo_forma_pagamento(id, nome) VALUES (1, 'Dinheiro');
INSERT INTO public.tipo_forma_pagamento(id, nome) VALUES (2, 'Cheque');
INSERT INTO public.tipo_forma_pagamento(id, nome) VALUES (3, 'Boleto');
INSERT INTO public.tipo_forma_pagamento(id, nome) VALUES (4, 'Cartão de Crédito');
INSERT INTO public.tipo_forma_pagamento(id, nome) VALUES (5, 'Débito em Conta');
INSERT INTO public.tipo_forma_pagamento(id, nome) VALUES (6, 'Depósito em Conta');
INSERT INTO public.tipo_forma_pagamento(id, nome) VALUES (7, 'Transferência Bancária');

INSERT INTO public.tipo_formato(id, nome) VALUES (1, 'Questionário');
INSERT INTO public.tipo_formato(id, nome) VALUES (2, 'Tarefa');
INSERT INTO public.tipo_formato(id, nome) VALUES (3, 'Atividade de pesquisa');
INSERT INTO public.tipo_formato(id, nome) VALUES (4, 'Atividade prática');

INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (1, 'Diretor', 1);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (2, 'Vice-Diretor', 1);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (3, 'Coordenador', 2);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (4, 'Professor', 3);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (5, 'Tesoureiro', 5);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (6, 'Secretário', 5);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (7, 'Auxilar Administrativo', 5);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (8, 'Inspetor', 5);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (9, 'Representante da turma', 6);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (10, 'Guardião', 6);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (11, 'Almoxarife', 5);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (12, 'Chefe da segurança', 5);
INSERT INTO public.tipo_funcao(id, nome, id_tipo_categoria_funcao) VALUES (13, 'Auxilar de segurança', 5);

INSERT INTO public.tipo_funcao_conselho(id, nome) VALUES (1, 'Presidente');
INSERT INTO public.tipo_funcao_conselho(id, nome) VALUES (2, 'Vice-presidente');
INSERT INTO public.tipo_funcao_conselho(id, nome) VALUES (3, 'Membro');

INSERT INTO public.tipo_genero(id, nome, sigla) VALUES (1, 'Masculino', 'M');
INSERT INTO public.tipo_genero(id, nome, sigla) VALUES (2, 'Feminino', 'F');

INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (1, 'Inclusão');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (2, 'Alteração de cadastro');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (3, 'Inclusão de Endereço');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (4, 'Exclusão de Endereço');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (5, 'Inclusão de Responsável');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (6, 'Exclusão de Responsável');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (7, 'Inclusão de Talento');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (8, 'Exclusão de Talento');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (9, 'Inclusão de Filiação');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (10, 'Exclusão de Filiação');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (11, 'Inclusão de Titulação');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (12, 'Exclusão de Titulação');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (13, 'Inclusão de Telefone');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (14, 'Exclusão de Telefone');
INSERT INTO public.tipo_hist_pessoa(id, nome) VALUES (15, 'Inclusão de Documento');

INSERT INTO public.tipo_hist_usuario(id, nome) VALUES (1, 'Inclusão');
INSERT INTO public.tipo_hist_usuario(id, nome) VALUES (2, 'Alteração de perfil');
INSERT INTO public.tipo_hist_usuario(id, nome) VALUES (3, 'Solicitação de senha');
INSERT INTO public.tipo_hist_usuario(id, nome) VALUES (4, 'Definição de senha');
INSERT INTO public.tipo_hist_usuario(id, nome) VALUES (5, 'Bloqueio');
INSERT INTO public.tipo_hist_usuario(id, nome) VALUES (6, 'Inativação');
INSERT INTO public.tipo_hist_usuario(id, nome) VALUES (7, 'Ativação');

INSERT INTO public.tipo_periodo(id, nome) VALUES (1, 'Bimestre');
INSERT INTO public.tipo_periodo(id, nome) VALUES (2, 'Trimestre');
INSERT INTO public.tipo_periodo(id, nome) VALUES (3, 'Semestre');
INSERT INTO public.tipo_periodo(id, nome) VALUES (4, 'Anual');
INSERT INTO public.tipo_periodo(id, nome) VALUES (5, 'Módulo');

INSERT INTO public.tipo_portaria(id, nome) VALUES (1, 'Autorização');
INSERT INTO public.tipo_portaria(id, nome) VALUES (2, 'Renovação');
INSERT INTO public.tipo_portaria(id, nome) VALUES (3, 'Reconhecimento');

INSERT INTO public.tipo_programa(id, nome) VALUES (1, 'Normal');
INSERT INTO public.tipo_programa(id, nome) VALUES (2, 'Portador de necessidaes especiais');

INSERT INTO public.tipo_regime_letivo(id, nome) VALUES (1, 'Presencial');
INSERT INTO public.tipo_regime_letivo(id, nome) VALUES (2, 'Ensino a Distância');
INSERT INTO public.tipo_regime_letivo(id, nome) VALUES (3, 'Misto');

INSERT INTO public.tipo_registro_nota(id, nome) VALUES (1, 'Média global');
INSERT INTO public.tipo_registro_nota(id, nome) VALUES (2, 'Por habilidades');

INSERT INTO public.tipo_responsavel(id, nome) VALUES (1, 'Financeiro');
INSERT INTO public.tipo_responsavel(id, nome) VALUES (2, 'Transporte');
INSERT INTO public.tipo_responsavel(id, nome) VALUES (3, 'Pedagógico');

INSERT INTO public.tipo_resultado (id, nome, sigla) VALUES (1, 'Aprovado', 'AP');
INSERT INTO public.tipo_resultado (id, nome, sigla) VALUES (2, 'Reprovado', 'R');
INSERT INTO public.tipo_resultado (id, nome, sigla) VALUES (3, 'Reprovado por falta', 'RF');

INSERT INTO public.tipo_sala (id, nome) VALUES (1, 'Administração');
INSERT INTO public.tipo_sala (id, nome) VALUES (2, 'Sala de aula');
INSERT INTO public.tipo_sala (id, nome) VALUES (3, 'Laboratório de Informática');
INSERT INTO public.tipo_sala (id, nome) VALUES (4, 'Biblioteca');
INSERT INTO public.tipo_sala (id, nome) VALUES (5, 'Refeitório');

INSERT INTO public.tipo_situacao_calendario (id, nome) VALUES (1, 'Em elaboração');
INSERT INTO public.tipo_situacao_calendario (id, nome) VALUES (2, 'Concluído');
INSERT INTO public.tipo_situacao_calendario (id, nome) VALUES (3, 'Aprovado');

INSERT INTO public.tipo_situacao_contrato (id, nome) VALUES (1, 'Pré-matricula');
INSERT INTO public.tipo_situacao_contrato (id, nome) VALUES (2, 'Validado');
INSERT INTO public.tipo_situacao_contrato (id, nome) VALUES (3, 'Análise financeiro');
INSERT INTO public.tipo_situacao_contrato (id, nome) VALUES (4, 'Aguardando aprovação');
INSERT INTO public.tipo_situacao_contrato (id, nome) VALUES (5, 'Aguardando assinatura');
INSERT INTO public.tipo_situacao_contrato (id, nome) VALUES (6, 'Confirmado');
INSERT INTO public.tipo_situacao_contrato (id, nome) VALUES (7, 'Trancado');
INSERT INTO public.tipo_situacao_contrato (id, nome) VALUES (8, 'Transferido');
INSERT INTO public.tipo_situacao_contrato (id, nome) VALUES (9, 'Cancelado');
INSERT INTO public.tipo_situacao_contrato (id, nome) VALUES (10, 'Desistência');

INSERT INTO public.tipo_situacao_matricula (id, nome) VALUES (1, 'Pré-matricula');
INSERT INTO public.tipo_situacao_matricula (id, nome) VALUES (2, 'Validada');
INSERT INTO public.tipo_situacao_matricula (id, nome) VALUES (3, 'Confirmada');
INSERT INTO public.tipo_situacao_matricula (id, nome) VALUES (4, 'Trancada');
INSERT INTO public.tipo_situacao_matricula (id, nome) VALUES (5, 'Transferida');
INSERT INTO public.tipo_situacao_matricula (id, nome) VALUES (6, 'Cancelada');
INSERT INTO public.tipo_situacao_matricula (id, nome) VALUES (7, 'Desistência');

INSERT INTO public.tipo_talento (id, nome) VALUES (1, 'Professor de Matemática');
INSERT INTO public.tipo_talento (id, nome) VALUES (2, 'Professor de Geografia');
INSERT INTO public.tipo_talento (id, nome) VALUES (3, 'Professor de História');
INSERT INTO public.tipo_talento (id, nome) VALUES (4, 'Professor de Inglês');
INSERT INTO public.tipo_talento (id, nome) VALUES (5, 'Professor de Biologia');
INSERT INTO public.tipo_talento (id, nome) VALUES (6, 'Professor de Fisica');
INSERT INTO public.tipo_talento (id, nome) VALUES (7, 'Professor de Química');
INSERT INTO public.tipo_talento (id, nome) VALUES (8, 'Coordenador Ensino Infantil');
INSERT INTO public.tipo_talento (id, nome) VALUES (9, 'Coordenador Ensino Fundamental I');
INSERT INTO public.tipo_talento (id, nome) VALUES (10, 'Coordenador Ensino Fundamental II');
INSERT INTO public.tipo_talento (id, nome) VALUES (11, 'Coordenador Ensino Médio');

INSERT INTO public.tipo_telefone (id, nome) VALUES (1, 'Celular');
INSERT INTO public.tipo_telefone (id, nome) VALUES (2, 'Fixo');
INSERT INTO public.tipo_telefone (id, nome) VALUES (3, 'Rádio');

INSERT INTO public.tipo_titulo (id, nome) VALUES (1, 'Graduação');
INSERT INTO public.tipo_titulo (id, nome) VALUES (2, 'Pós-graduação Latu-Senso (Especialização)');
INSERT INTO public.tipo_titulo (id, nome) VALUES (3, 'Pós-graduação Stricto-Senso (Mestrado)');
INSERT INTO public.tipo_titulo (id, nome) VALUES (4, 'Doutorado');
INSERT INTO public.tipo_titulo (id, nome) VALUES (5, 'Pós-Doutorado');

INSERT INTO public.tipo_turno (id, nome) VALUES (1, 'Matutino');
INSERT INTO public.tipo_turno (id, nome) VALUES (2, 'Vespertino');
INSERT INTO public.tipo_turno (id, nome) VALUES (3, 'Noturno');
INSERT INTO public.tipo_turno (id, nome) VALUES (4, 'Integral');

INSERT INTO public.tipo_uso (id, nome) VALUES (1, 'Pessoal');
INSERT INTO public.tipo_uso (id, nome) VALUES (2, 'Residencial');
INSERT INTO public.tipo_uso (id, nome) VALUES (3, 'Trabalho');

--carga de hora_atividade
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('07:30:00.00', '08:20:00.00', 50, 60, 1, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('08:20:00.00', '09:10:00.00', 50, 60, 1, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('09:10:00.00', '10:00:00.00', 50, 60, 1, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('10:00:00.00', '10:50:00.00', 50, 60, 1, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('10:50:00.00', '11:40:00.00', 50, 60, 1, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('11:40:00.00', '12:30:00.00', 50, 60, 1, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('13:30:00.00', '14:20:00.00', 50, 60, 2, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('14:20:00.00', '15:10:00.00', 50, 60, 2, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('15:10:00.00', '16:00:00.00', 50, 60, 2, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('16:00:00.00', '16:50:00.00', 50, 60, 2, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('16:50:00.00', '17:40:00.00', 50, 60, 2, 2);
insert into hora_atividade
(hora_inicial, hora_final, tempo_carga_horaria, tempo_trabalho_computado, id_tipo_turno, id_instituicao)
values ('17:40:00.00', '18:30:00.00', 50, 60, 2, 2);

--carga de perfis
INSERT INTO public.perfil (nome) VALUES ('Administrador');
INSERT INTO public.perfil (nome) VALUES ('Administrador de usuários');
INSERT INTO public.perfil (nome) VALUES ('Mantenedor');
INSERT INTO public.perfil (nome) VALUES ('Diretor');
INSERT INTO public.perfil (nome) VALUES ('Vice Diretor');
INSERT INTO public.perfil (nome) VALUES ('Tesoureiro');
INSERT INTO public.perfil (nome) VALUES ('Auxiliar de Tesoureiro');
INSERT INTO public.perfil (nome) VALUES ('Secretário');
INSERT INTO public.perfil (nome) VALUES ('Auxiliar de Secretário');
INSERT INTO public.perfil (nome) VALUES ('Recursos Humanos');
INSERT INTO public.perfil (nome) VALUES ('Auxiliar de Recursos Humanos');
INSERT INTO public.perfil (nome) VALUES ('Coordenador de curso');
INSERT INTO public.perfil (nome) VALUES ('Coordenador pedagógico');
INSERT INTO public.perfil (nome) VALUES ('Professor');
INSERT INTO public.perfil (nome) VALUES ('Responsável Financeiro');
INSERT INTO public.perfil (nome) VALUES ('Responsável Pedagógico');
INSERT INTO public.perfil (nome) VALUES ('Aluno');
INSERT INTO public.perfil (nome) VALUES ('Bibliotecário');
INSERT INTO public.perfil (nome) VALUES ('Auxiliar de Bibliotecário');

--carga de empresas
insert into empresa (cnpj, inscricao_estadual, email_contato, nome_contato, nome_fantasia, razao_social, site) values ('90151072000122', '0733205400281', 'secretaria@educacaofatima.com.br', 'Inês Alves Lourenço', 'Escola Fátima', 'Escola Franciscana Nossa Senhora de Fátima', 'www.educacaofatima.com.br');
insert into empresa (cnpj, inscricao_estadual, email_contato, nome_contato, nome_fantasia, razao_social, site) values ('26791940000130', '1014632650', 'empresa01@empresa.com.br', 'Paulo Empresa01 da Silva', 'Empresa01', 'Empresa01 S/A', 'www.empresa01.com.br');
insert into empresa (cnpj, inscricao_estadual, email_contato, nome_contato, nome_fantasia, razao_social, site) values ('88972395000135', '2014632650', 'empresa02@empresa.com.br', 'Paulo Empresa02 da Silva', 'Empresa02', 'Empresa02 S/A', 'www.empresa02.com.br');
insert into empresa (cnpj, inscricao_estadual, email_contato, nome_contato, nome_fantasia, razao_social, site) values ('15757195000196', '3014632650', 'empresa03@empresa.com.br', 'Paulo Empresa03 da Silva', 'Empresa03', 'Empresa03 S/A', 'www.empresa03.com.br');
insert into empresa (cnpj, inscricao_estadual, email_contato, nome_contato, nome_fantasia, razao_social, site) values ('06620691000102', '4014632650', 'empresa04@empresa.com.br', 'Paulo Empresa04 da Silva', 'Empresa04', 'Empresa04 S/A', 'www.empresa04.com.br');
insert into empresa (cnpj, inscricao_estadual, email_contato, nome_contato, nome_fantasia, razao_social, site) values ('98315341000150', '5014632650', 'empresa05@empresa.com.br', 'Paulo Empresa05 da Silva', 'Empresa05', 'Empresa05 S/A', 'www.empresa05.com.br');
--carga tipo empresas
insert into empresa_tipo_empresa (id_tipo_empresa, id_empresa) select 1,id from empresa;
insert into empresa_tipo_empresa (id_tipo_empresa, id_empresa) select 2,id from empresa;
insert into empresa_tipo_empresa (id_tipo_empresa, id_empresa) select 3,id from empresa;
--carga endereços empresas
insert into empresa_endereco (id_empresa, cep, logradouro, complemento, bairro, localidade, uf, unidade, ibge, gia, id_tipo_endereco, numero) select id, cast(70600000 + id + 10 as varchar(8)), 'Quadra '||cast(id as varchar(4))||' Lote '||cast(id as varchar(4)),'Casa', 'Bairro '||cast(id as varchar(4)), 'Localidade '||cast(id as varchar(4)), 'DF', 'Unidade '||cast(id as varchar(4)), cast(100+id as varchar(50)), cast(200+id as varchar(50)), 2, cast(300+id as varchar(50)) from empresa;
insert into empresa_endereco (id_empresa, cep, logradouro, complemento, bairro, localidade, uf, unidade, ibge, gia, id_tipo_endereco, numero) select id, cast(70600000 + id + 100 as varchar(8)), 'Quadra '||cast(id as varchar(4))||' Lote '||cast(id as varchar(4)),'Casa', 'Bairro '||cast(id as varchar(4)), 'Localidade '||cast(id as varchar(4)), 'DF', 'Unidade '||cast(id as varchar(4)), cast(100+id as varchar(50)), cast(200+id as varchar(50)), 2, cast(300+id+30 as varchar(50)) from empresa;
--carga operadora de telefonia
insert into operadora (id, nome, codigo) values (1, 'TIM', 41);
insert into operadora (id, nome, codigo) values (2, 'VIVO', 15);
--carga de telefones de empresas 
insert into empresa_telefone (id_empresa, id_cidade, id_operadora, id_tipo_uso, id_tipo_telefone, numero, principal, what_app) select id, 5564, 1, 3, 1, 981081840 + id, false, true from empresa;
insert into empresa_telefone (id_empresa, id_cidade, id_operadora, id_tipo_uso, id_tipo_telefone, numero, principal, what_app) select id, 5564, 2, 3, 2, 39655890 + id, true, false from empresa;
--carga de instituiçoes
insert into instituicao (id_empresa, nome, ativo, mantenedora, id_composicao_valor, perc_juros_atraso, perc_multa_atraso, perc_bom_pagador) select id, 'Instituição '||nome_fantasia, true, false, 1, 5, 5, 10 from empresa;
--carga de convenios
insert into  convenio (id_instituicao, id_empresa, id_tipo_desconto, data_inicio_vigencia, data_final_vigencia, percentual_desconto) values 
((select min(id) from instituicao), (select min(id) from empresa), 4, current_date, current_date + INTERVAL '1 years', 10 );
insert into  convenio (id_instituicao, id_empresa, id_tipo_desconto, data_inicio_vigencia, data_final_vigencia, percentual_desconto) values 
((select min(id) from instituicao), (select min(id) from empresa), 2, current_date, current_date + INTERVAL '1 years', 20 );
insert into  convenio (id_instituicao, id_empresa, id_tipo_desconto, data_inicio_vigencia, data_final_vigencia, percentual_desconto) values 
((select min(id) from instituicao), (select min(id) from empresa), 2, current_date, current_date + INTERVAL '1 years', 30 );
insert into  convenio (id_instituicao, id_empresa, id_tipo_desconto, data_inicio_vigencia, data_final_vigencia, percentual_desconto) values 
((select min(id) from instituicao), (select min(id) from empresa), 2, current_date, current_date + INTERVAL '1 years', 50 );
insert into  convenio (id_instituicao, id_empresa, id_tipo_desconto, data_inicio_vigencia, data_final_vigencia, percentual_desconto) values 
((select min(id) from instituicao), (select min(id)+1 from empresa), 1, current_date, current_date + INTERVAL '1 years', 15 );
insert into  convenio (id_instituicao, id_empresa, id_tipo_desconto, data_inicio_vigencia, data_final_vigencia, percentual_desconto) values 
((select min(id) from instituicao), (select min(id)+2 from empresa), 1, current_date, current_date + INTERVAL '1 years', 15 );
insert into  convenio (id_instituicao, id_empresa, id_tipo_desconto, data_inicio_vigencia, data_final_vigencia, percentual_desconto) values 
((select min(id) from instituicao), (select min(id)+3 from empresa), 1, current_date, current_date + INTERVAL '1 years', 15 );
insert into  convenio (id_instituicao, id_empresa, id_tipo_desconto, data_inicio_vigencia, data_final_vigencia, percentual_desconto) values 
((select min(id) from instituicao), (select min(id)+4 from empresa), 3, current_date, current_date + INTERVAL '1 years', 20 );
insert into  convenio (id_instituicao, id_empresa, id_tipo_desconto, data_inicio_vigencia, data_final_vigencia, percentual_desconto) values 
((select min(id) from instituicao), (select min(id)+5 from empresa), 3, current_date, current_date + INTERVAL '1 years', 20 );
--carga de escalas
insert into escala (nome, id_instituicao, data_inicial_vigencia, id_tipo_avaliacao, id_tipo_abrangencia) values 
('Corrente [Bimestre]', (select min(id) from instituicao), current_date, 1, 1);
insert into escala (nome, id_instituicao, data_inicial_vigencia, id_tipo_avaliacao, id_tipo_abrangencia) values 
('Corrente [Semestre]', (select min(id) from instituicao), current_date, 1, 2);
insert into escala (nome, id_instituicao, data_inicial_vigencia, id_tipo_avaliacao, id_tipo_abrangencia) values 
('Recuperação [Bimestre]', (select min(id) from instituicao), current_date, 2, 1);
insert into escala (nome, id_instituicao, data_inicial_vigencia, id_tipo_avaliacao, id_tipo_abrangencia) values 
('Recuperação [Semestre]', (select min(id) from instituicao), current_date, 2, 2);
insert into escala (nome, id_instituicao, data_inicial_vigencia, id_tipo_avaliacao, id_tipo_abrangencia) values 
('Exame [Ano]', (select min(id) from instituicao), current_date, 3, 3); 
insert into escala (nome, id_instituicao, data_inicial_vigencia, id_tipo_avaliacao, id_tipo_abrangencia) values 
('Conselho [Ano]', (select min(id) from instituicao), current_date, 4, 3);
--carga de faixas de escalas
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id) from escala), (select min(id) from mencao), 0, 0 );
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+1 from escala), (select min(id) from mencao), 0, 0 );
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+2 from escala), (select min(id) from mencao), 0, 0 );
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+3 from escala), (select min(id) from mencao), 0, 0 );
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+4 from escala), (select min(id) from mencao), 0, 0 );
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+5 from escala), (select min(id) from mencao), 0, 0 );
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id) from escala), (select min(id)+1 from mencao), 0.1, 4.99 );
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+1 from escala), (select min(id)+1 from mencao), 0.1, 4.99);
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+2 from escala), (select min(id)+1 from mencao), 0.1, 4.99);
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+3 from escala), (select min(id)+1 from mencao), 0.1, 4.99);
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+4 from escala), (select min(id)+1 from mencao), 0.1, 4.99);
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+5 from escala), (select min(id)+1 from mencao), 0.1, 4.99);
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id) from escala), (select min(id)+2 from mencao), 5, 10 );
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+1 from escala), (select min(id)+2 from mencao), 5, 10);
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+2 from escala), (select min(id)+3 from mencao), 5, 10);
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+3 from escala), (select min(id)+3 from mencao), 5, 10);
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+4 from escala), (select min(id)+3 from mencao), 5, 10);
insert into limite (id_escala, id_tipo_mencao, minimo, maximo) values ((select min(id)+5 from escala), (select min(id)+3 from mencao), 5, 10);
--carga de pessoas
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '54 years', 'fdboeti@gmail.com', 'Francisco Daniel Batista de Oliveira', 'Francisco Daniel', 'Francisco Daniel Batista de Oliveira', '06849869817', true, 5564, 1, (select min(id) from instituicao));
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '35 years', 'rafa.jesuscristo@gmail.com', 'Rafael Alves Machado', 'Rafael', 'Rafael Alves Machado', '98513346187', true, 5564, 1, (select min(id) from instituicao));
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '20 years', 'paulo@kronos.com', 'Paulo Silva', 'Paulo', 'Paulo Silva', '24018696035', true, 5564, 1, (select min(id) from instituicao));
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '18 years', 'maria@kronos.com', 'Maria Silva', 'maria', 'maria Silva', '09194601003', true, 5564, 2, (select min(id) from instituicao));
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '15 years', 'joana@kronos.com', 'Joana Silva', 'Joana', 'Joana Silva', '23967892018', true, 5564, 2, (select min(id) from instituicao));
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '17 years', 'catarina@kronos.com', 'Catarina Silva', 'Catarina', 'Catarina Silva', '90602226015', true, 5564, 2, (select min(id) from instituicao));
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '21 years', 'carlos@kronos.com', 'Carlos Silva', 'Carlos', 'Carlos Silva', '05707901078', true, 5564, 1, (select max(id) from instituicao));
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '16 years', 'mariana@kronos.com', 'Mariana Silva', 'Mariana', 'Mariana Silva', '05707901078', true, 5564, 2, (select max(id) from instituicao));
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '22 years', 'sandra@kronos.com', 'Sandra Silva', 'Sandra', 'Sandra Silva', '96109602000', true, 5564, 2, (select max(id) from instituicao));
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '75 years', 'paidetodos@kronos.com', 'Pai de Todos', 'Pai', 'Pai de Todos', '93008134041', false, 5564, 1, (select max(id) from instituicao));
insert into pessoa (data_nascimento, email, nome, nome_usual, nome_social, cpf, banco_talento, id_cidade, id_genero, id_instituicao)
values (CAST('2019-09-03' AS DATE) - INTERVAL '74 years', 'maedetodos@kronos.com', 'Mãe de Todos', 'Mãe', 'Mãe de Todos', '85086765064', false, 5564, 2, (select max(id) from instituicao));
--carga endereços pessoas
insert into pessoa_endereco (id_pessoa, cep, logradouro, complemento, bairro, localidade, uf, unidade, ibge, gia, id_tipo_endereco, numero) select id, cast(70600000 + id + 10 as varchar(8)), 'Quadra '||cast(id as varchar(4))||' Lote '||cast(id as varchar(4)),'Casa', 'Bairro '||cast(id as varchar(4)), 'Localidade '||cast(id as varchar(4)), 'DF', 'Unidade '||cast(id as varchar(4)), cast(100+id as varchar(50)), cast(200+id as varchar(50)), 1, cast(300+id as varchar(50)) from pessoa;
insert into pessoa_endereco (id_pessoa, cep, logradouro, complemento, bairro, localidade, uf, unidade, ibge, gia, id_tipo_endereco, numero) select id, cast(70600000 + id + 100 as varchar(8)), 'Quadra '||cast(id as varchar(4))||' Lote '||cast(id as varchar(4)),'Loja', 'Bairro '||cast(id as varchar(4)), 'Localidade '||cast(id as varchar(4)), 'DF', 'Unidade '||cast(id as varchar(4)), cast(100+id as varchar(50)), cast(200+id as varchar(50)), 2, cast(300+id+30 as varchar(50)) from pessoa;
--carga de telefones de pessoas 
insert into pessoa_telefone (id_pessoa, id_cidade, id_operadora, id_tipo_uso, id_tipo_telefone, numero, principal, what_app) select id, 5564, 1, 3, 1, 981081840 + id, true, true from pessoa;
insert into pessoa_telefone (id_pessoa, id_cidade, id_operadora, id_tipo_uso, id_tipo_telefone, numero, principal, what_app) select id, 5564, 2, 3, 2, 39655890 + id, false, false from pessoa;
--carga de titulação de pessoas 
insert into titulacao (id_tipo_titulo, data, nome_titulo, id_pessoa, id_empresa) select id, current_date, 'Especialista em Trabalhar Bem', (select min(id) from pessoa), (select min(id)+1 from empresa) from tipo_titulo; 
insert into titulacao (id_tipo_titulo, data, nome_titulo, id_pessoa, id_empresa) select id, current_date, 'Especialista em Trabalhar Bem', (select min(id)+1 from pessoa), (select min(id)+2 from empresa) from tipo_titulo; 
insert into titulacao (id_tipo_titulo, data, nome_titulo, id_pessoa, id_empresa) select id, current_date, 'Especialista em Trabalhar Bem', (select min(id)+2 from pessoa), (select min(id)+3 from empresa) from tipo_titulo; 
--carga de talentos de pessoas 
insert into pessoa_talento (id_tipo_talento, id_pessoa) select id, (select min(id) from pessoa) from tipo_talento; 
insert into pessoa_talento (id_tipo_talento, id_pessoa) select id, (select min(id)+1 from pessoa) from tipo_talento; 
insert into pessoa_talento (id_tipo_talento, id_pessoa) select id, (select min(id)+2 from pessoa) from tipo_talento; 
insert into pessoa_talento (id_tipo_talento, id_pessoa) select id, (select min(id)+3 from pessoa) from tipo_talento; 
insert into pessoa_talento (id_tipo_talento, id_pessoa) select id, (select min(id)+4 from pessoa) from tipo_talento; 
--carga de filiação de pessoas 
insert into filiacao (id_pessoa_filho, id_pessoa_pais, data_filiacao, filiacao_atual, id_tipo_filiacao) select p.id, (select max(p3.id) from pessoa p3), p.data_nascimento, true, 2 from pessoa p where id < (select max(p1.id)-2 from pessoa p1);
insert into filiacao (id_pessoa_filho, id_pessoa_pais, data_filiacao, filiacao_atual, id_tipo_filiacao) select p.id, (select max(p3.id)-1 from pessoa p3), p.data_nascimento, true, 1 from pessoa p where id < (select max(p1.id)-2 from pessoa p1);
--carga de responsáveis de pessoas 
insert into responsavel (id_pessoa, id_pessoa_responsavel, data_inicio, prioridade, id_tipo_responsavel) select p.id, (select max(p3.id)-1 from pessoa p3), p.data_nascimento, 1, 1 from pessoa p where id < (select max(p1.id)-2 from pessoa p1);
insert into responsavel (id_pessoa, id_pessoa_responsavel, data_inicio, prioridade, id_tipo_responsavel) select p.id, (select max(p3.id) from pessoa p3), p.data_nascimento, 1, 2 from pessoa p where id < (select max(p1.id)-2 from pessoa p1);
insert into responsavel (id_pessoa, id_pessoa_responsavel, data_inicio, prioridade, id_tipo_responsavel) select p.id, (select max(p3.id) from pessoa p3), p.data_nascimento, 1, 3 from pessoa p where id < (select max(p1.id)-2 from pessoa p1);
--carga de documentos de pessoas 
insert into documento (id_pessoa, id_cidade, id_tipo, data_expedicao, numero, livro, folha, nome_cartorio ) select id, 5564, 1, data_nascimento, cast(id+70000 as varchar(15)), 'Lv 100', 'Fl 30', 'Cartório de Registros Naturais' from pessoa;
insert into documento (id_pessoa, id_cidade, id_tipo, data_expedicao, numero, orgao_expedidor) select id, 5564, 2, current_date, cast(id+50000 as varchar(15)), 'SSP' from pessoa;
insert into documento (id_pessoa, id_cidade, id_tipo, data_expedicao, numero, secao, zona) select id, 5564, 7, current_date, cast(id+80000 as varchar(15)), '314', '32' from pessoa;
--carga de usuários
insert into  usuario (id_pessoa, id_instituicao, data_criacao, ativo,  data_ativacao, bloqueado)  select id, id_instituicao, current_timestamp, true, current_timestamp, false  from pessoa;

UPDATE public.usuario SET hash_senha='$2a$10$c.QWzlFjOMO9PM76JSl0EuIIzh8ASL.DJet0IAvPerLFY2FKAX3X.' WHERE id=(select min(id) from pessoa);  --selva
UPDATE public.usuario SET hash_senha='$2a$10$c.QWzlFjOMO9PM76JSl0EuIIzh8ASL.DJet0IAvPerLFY2FKAX3X.' WHERE id=(select min(id)+1 from pessoa); --selva
UPDATE public.usuario SET hash_senha='$2a$10$FbiFtArrvJiS4MymZPwv7.LHn27KwRuDwPUf7yW2HCAU3jj6XkUYS'WHERE id=(select min(id)+1 from pessoa);  --P@z3B3m


insert into  usuario_perfil (id_usuario,id_perfil) select id, 2 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 3 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 4 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 5 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 6 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 7 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 8 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 9 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 10 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 11 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 12 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 13 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 14 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 15 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 16 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 17 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 18 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 19 from usuario;
insert into  usuario_perfil (id_usuario,id_perfil) select id, 20 from usuario;
--carga de funcionarios
insert into funcionario (id_instituicao, id_pessoa, data_admissao ) select (select min(id) from instituicao), id, current_date  from  pessoa where id_instituicao = (select min(id) from instituicao);
insert into funcionario (id_instituicao, id_pessoa, data_admissao ) select (select max(id) from instituicao), id, current_date  from  pessoa where id_instituicao = (select max(id) from instituicao);
--carga de cursos
insert into curso (id_nivel, id_tipo_regime_letivo, id_tipo_turno, id_instituicao, nome, sigla, titulo_masculino, titulo_feminino, data_inicio_vigencia, tempo_maximo_conclusao, tempo_maximo_descontinuo, carga_horaria, nota_min_aprov_direta, nota_min_aprov_recup, ativo)
values (2, 1, 4, (select min(id) from instituicao), 'Ensino Fundamental I', 'EF I', 'Ensino Fundamental I', 'Ensino Fundamental I', current_date - INTERVAL '12 years', 96, 24, 6400, 7, 5, true);
insert into curso (id_nivel, id_tipo_regime_letivo, id_tipo_turno, id_instituicao, nome, sigla, titulo_masculino, titulo_feminino, data_inicio_vigencia, tempo_maximo_conclusao, tempo_maximo_descontinuo, carga_horaria, nota_min_aprov_direta, nota_min_aprov_recup, ativo)
values (2, 1, 4, (select min(id) from instituicao), 'Ensino Fundamental II', 'EF II', 'Ensino Fundamental II', 'Ensino Fundamental II', current_date - INTERVAL '5 years', 120, 24, 6000, 7, 5, true);
insert into curso (id_nivel, id_tipo_regime_letivo, id_tipo_turno, id_instituicao, nome, sigla, titulo_masculino, titulo_feminino, data_inicio_vigencia, tempo_maximo_conclusao, tempo_maximo_descontinuo, carga_horaria, nota_min_aprov_direta, nota_min_aprov_recup, ativo)
values (3, 1, 4, (select min(id) from instituicao), 'Ensino Médio', 'EM', 'Ensino Médio', 'Ensino Médio', current_date - INTERVAL '3 years', 72, 24, 4800, 7, 5, true);
--carga de portarias
delete from portaria;
insert into portaria (id_curso, id_tipo_portaria, descricao, data_publicacao, documento_publicacao, data_inicio_vigencia) select id, 1, 'Portaria de '||nome, current_date, 'DOU NR '||cast(id as varchar(2)), current_date from curso;
insert into portaria (id_curso, id_tipo_portaria, descricao, data_publicacao, documento_publicacao, data_inicio_vigencia) select id, 3, 'Portaria de '||nome, current_date, 'DOU NR '||cast(id+3 as varchar(2)), current_date from curso;
insert into portaria (id_curso, id_tipo_portaria, descricao, data_publicacao, documento_publicacao, data_inicio_vigencia) select id, 2, 'Portaria de '||nome, current_date, 'DOU NR '||cast(id+6 as varchar(2)), current_date from curso;
--carga das escalas nos cursos
insert into curso_escala (id_escala, id_curso) select id, (select min(id) from curso) from escala;
insert into curso_escala (id_escala, id_curso) select id, (select min(id)+1 from curso) from escala;
insert into curso_escala (id_escala, id_curso) select id, (select min(id)+2 from curso) from escala;
--carga dos documentos nos cursos
insert into curso_documento (id_curso, id_tipo_documento, numero_copia, original, autenticado) select id, 1, 1, false, true from curso;
insert into curso_documento (id_curso, id_tipo_documento, numero_copia, original, autenticado) select id, 2, 1, false, false from curso;
insert into curso_documento (id_curso, id_tipo_documento, numero_copia, original, autenticado) select id, 3, 1, false, false from curso;
--carga dos periodos
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id) from curso), 4, 4, '1º Ano', '1A', 101); 
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id) from curso), 4, 5, '2º Ano', '2A', 102); 
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id) from curso), 4, 6, '3º Ano', '3A', 103);											   
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id) from curso), 4, 7, '4º Ano', '4A', 104);	
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id)+1 from curso), 4, 8, '5º Ano', '5A', 205); 
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id)+1 from curso), 4, 9, '6º Ano', '6A', 206); 
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id)+1 from curso), 4, 10, '7º Ano', '7A', 207);											   
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id)+1 from curso), 4, 11, '8º Ano', '8A', 208);
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id)+1 from curso), 4, 12, '9º Ano', '9A', 209);
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id)+2 from curso), 4, 13, '1º Ano', '1A', 301);											   
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id)+2 from curso), 4, 14, '2º Ano', '2A', 302);
insert into periodo (id_curso, id_tipo_periodo, id_faixa_ano, nome, sigla, numero) values ((select min(id)+2 from curso), 4, 15, '3º Ano', '3A', 303);
--carga das fases (Semestres)
insert into fase (id_periodo, id_tipo_periodo, nome, sigla, numero) select id, 3, '1º Semestre', '1º Sem', 1 from periodo;
insert into fase (id_periodo, id_tipo_periodo, nome, sigla, numero) select id, 3, '2º Semestre', '2º Sem', 2 from periodo;
--carga das sub-fases (bimestres)
insert into sub_fase (id_fase, id_tipo_periodo, nome, sigla, numero) select id, 1, '1º Bimestre', '1º Bim', 1 from fase where numero = 1;
insert into sub_fase (id_fase, id_tipo_periodo, nome, sigla, numero) select id, 1, '2º Bimestre', '2º Bim', 2 from fase where numero = 1;
insert into sub_fase (id_fase, id_tipo_periodo, nome, sigla, numero) select id, 1, '3º Bimestre', '3º Bim', 3 from fase where numero = 2;
insert into sub_fase (id_fase, id_tipo_periodo, nome, sigla, numero) select id, 1, '4º Bimestre', '4º Bim', 4 from fase where numero = 2;
--carga das disciplinas
insert into disciplina (id_componente, id_periodo, id_tipo_regime_letivo, id_tipo_disciplina, codigo, nome, sigla, carga_horaria_total, carga_horaria_presencial, carga_horaria_distancia, ativa, obrigatoria, valor, data_inicio_vigencia)
select id, (select id from periodo where numero = 205), 1, 1, 2050 + id, nome, substring(nome,1,3)||'_'||cast(2050+id as varchar(5)), 200, 200, 0, true, true, 150.00, current_date  from componente where id < 10;
insert into disciplina (id_componente, id_periodo, id_tipo_regime_letivo, id_tipo_disciplina, codigo, nome, sigla, carga_horaria_total, carga_horaria_presencial, carga_horaria_distancia, ativa, obrigatoria, valor, data_inicio_vigencia)
select id, (select id from periodo where numero = 206), 1, 1, 2060 + id, nome, substring(nome,1,3)||'_'||cast(2060+id as varchar(5)), 200, 200, 0, true, true, 150.00, current_date  from componente where id < 10;
insert into disciplina (id_componente, id_periodo, id_tipo_regime_letivo, id_tipo_disciplina, codigo, nome, sigla, carga_horaria_total, carga_horaria_presencial, carga_horaria_distancia, ativa, obrigatoria, valor, data_inicio_vigencia)
select id, (select id from periodo where numero = 207), 1, 1, 2070 + id, nome, substring(nome,1,3)||'_'||cast(2070+id as varchar(5)), 200, 200, 0, true, true, 150.00, current_date  from componente where id < 10;
insert into disciplina (id_componente, id_periodo, id_tipo_regime_letivo, id_tipo_disciplina, codigo, nome, sigla, carga_horaria_total, carga_horaria_presencial, carga_horaria_distancia, ativa, obrigatoria, valor, data_inicio_vigencia)
select id, (select id from periodo where numero = 208), 1, 1, 2080 + id, nome, substring(nome,1,3)||'_'||cast(2080+id as varchar(5)), 200, 200, 0, true, true, 150.00, current_date  from componente where id < 10;
insert into disciplina (id_componente, id_periodo, id_tipo_regime_letivo, id_tipo_disciplina, codigo, nome, sigla, carga_horaria_total, carga_horaria_presencial, carga_horaria_distancia, ativa, obrigatoria, valor, data_inicio_vigencia)
select id, (select id from periodo where numero = 209), 1, 1, 2090 + id, nome, substring(nome,1,3)||'_'||cast(2090+id as varchar(5)), 200, 200, 0, true, true, 150.00, current_date  from componente where id < 10;
insert into disciplina (id_componente, id_periodo, id_tipo_regime_letivo, id_tipo_disciplina, codigo, nome, sigla, carga_horaria_total, carga_horaria_presencial, carga_horaria_distancia, ativa, obrigatoria, valor, data_inicio_vigencia)
select id, (select id from periodo where numero = 301), 1, 1, 3010 + id, nome, substring(nome,1,3)||'_'||cast(3010+id as varchar(5)), 200, 200, 0, true, true, 150.00, current_date  from componente where id > 9;
insert into disciplina (id_componente, id_periodo, id_tipo_regime_letivo, id_tipo_disciplina, codigo, nome, sigla, carga_horaria_total, carga_horaria_presencial, carga_horaria_distancia, ativa, obrigatoria, valor, data_inicio_vigencia)
select id, (select id from periodo where numero = 302), 1, 1, 3020 + id, nome, substring(nome,1,3)||'_'||cast(3020+id as varchar(5)), 200, 200, 0, true, true, 150.00, current_date  from componente where id > 9;
insert into disciplina (id_componente, id_periodo, id_tipo_regime_letivo, id_tipo_disciplina, codigo, nome, sigla, carga_horaria_total, carga_horaria_presencial, carga_horaria_distancia, ativa, obrigatoria, valor, data_inicio_vigencia)
select id, (select id from periodo where numero = 303), 1, 1, 3030 + id, nome, substring(nome,1,3)||'_'||cast(3030+id as varchar(5)), 200, 200, 0, true, true, 150.00, current_date  from componente where id > 9;
--carga das disciplinas pre-requisitos
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2051), (select id  from disciplina where codigo = 2061)); 
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2052), (select id  from disciplina where codigo = 2062));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2053), (select id  from disciplina where codigo = 2063));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2054), (select id  from disciplina where codigo = 2064));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2055), (select id  from disciplina where codigo = 2065)); 
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2056), (select id  from disciplina where codigo = 2066));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2057), (select id  from disciplina where codigo = 2067));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2058), (select id  from disciplina where codigo = 2068));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2059), (select id  from disciplina where codigo = 2069));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2061), (select id  from disciplina where codigo = 2071)); 
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2062), (select id  from disciplina where codigo = 2072));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2063), (select id  from disciplina where codigo = 2073));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2064), (select id  from disciplina where codigo = 2074));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2065), (select id  from disciplina where codigo = 2075)); 
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2066), (select id  from disciplina where codigo = 2076));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2067), (select id  from disciplina where codigo = 2077));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2068), (select id  from disciplina where codigo = 2078));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2069), (select id  from disciplina where codigo = 2079));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2071), (select id  from disciplina where codigo = 2081)); 
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2072), (select id  from disciplina where codigo = 2082));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2073), (select id  from disciplina where codigo = 2083));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2074), (select id  from disciplina where codigo = 2084));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2075), (select id  from disciplina where codigo = 2085)); 
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2076), (select id  from disciplina where codigo = 2086));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2077), (select id  from disciplina where codigo = 2087));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2078), (select id  from disciplina where codigo = 2088));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2079), (select id  from disciplina where codigo = 2089));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2081), (select id  from disciplina where codigo = 2091)); 
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2082), (select id  from disciplina where codigo = 2092));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2083), (select id  from disciplina where codigo = 2093));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2084), (select id  from disciplina where codigo = 2094));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2085), (select id  from disciplina where codigo = 2095)); 
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2086), (select id  from disciplina where codigo = 2096));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2087), (select id  from disciplina where codigo = 2097));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2088), (select id  from disciplina where codigo = 2098));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 2089), (select id  from disciplina where codigo = 2099));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 3020), (select id  from disciplina where codigo = 3030)); 
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 3021), (select id  from disciplina where codigo = 3031));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 3022), (select id  from disciplina where codigo = 3032));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 3022), (select id  from disciplina where codigo = 3033));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 3030), (select id  from disciplina where codigo = 3040)); 
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 3031), (select id  from disciplina where codigo = 3041));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 3032), (select id  from disciplina where codigo = 3042));
insert into  disciplina_pre_requisito (id_disciplina_pre_requisito, id_disciplina) values 
((select id  from disciplina where codigo = 3033), (select id  from disciplina where codigo = 3043));
--carga dos funcionários exercendo funções no curso
insert into curso_funcionario (id_curso, id_funcionario, id_tipo_funcao, data_inicio)
select id, (select min(id) from funcionario), 3, current_date from curso where id_instituicao = (select min(id) from instituicao);
--carga dos funcionários habilitados a lecionar nas disciplinas
insert into disciplina_funcionario (id_disciplina, id_funcionario) select id, (select min(id)+1 from funcionario) from disciplina;
insert into disciplina_funcionario (id_disciplina, id_funcionario) select id, (select min(id)+2 from funcionario) from disciplina;
insert into disciplina_funcionario (id_disciplina, id_funcionario) select id, (select min(id)+3 from funcionario) from disciplina;
insert into disciplina_funcionario (id_disciplina, id_funcionario) select id, (select min(id)+4 from funcionario) from disciplina;
--carga de matrículas
insert into matricula (id_pessoa, id_curso, id_tipo_situacao_matricula, data, ano_inicio_curso, semestre_inicio_curso, data_inicio_curso)
select id, (select min(id)+1 from curso), 1, current_date - INTERVAL '1 years', date_part('year', current_date), 1, cast(date_part('year', current_date)||'-02-04' as date)   from pessoa where id between (select min(id)+2 from pessoa) and (select min(id)+8 from pessoa);

--carga de calendário
 insert into calendario (ano, numero, data_inicio, data_inicio_atrib_credito, data_final_atrib_credito,
						data_inicio_ano_letivo, data_final_ano_letivo, data_final, descricao,
						id_instituicao, id_tipo_situacao_calendario, id_funcionario_elaboracao )  
 select 2019, 1, '2019-02-01', '2019-09-01', '2019-12-31',
        '2019-02-06', '2019-12-20', '2019-12-30', 'Calendário '||nome, 
		id, 3, (select min(id) from funcionario) from instituicao; 
insert into calendario (ano, numero, data_inicio, data_inicio_atrib_credito, data_final_atrib_credito,
						data_inicio_ano_letivo, data_final_ano_letivo, data_final, descricao,
						id_instituicao, id_tipo_situacao_calendario, id_funcionario_elaboracao )  
 select 2020, 1, '2020-02-01', '2020-09-01', '2020-12-31',
        '2020-02-06', '2020-12-20', '2020-12-30', 'Calendário '||nome, 
		id, 3, (select min(id) from funcionario) from instituicao;		
-- carga calendarios cursos 
insert into calendario_curso (id_calendario, id_curso)
  select c.id, x.id from calendario c
     join instituicao i on (c.id_instituicao = i.id)
     join curso x on (i.id = x.id_instituicao );
--carga de período de execução
insert into periodo_execucao (id_calendario, id_periodo, ano) 
select c.id, p.id, c.ano from calendario c
   join instituicao i on (c.id_instituicao = i.id)
   join curso x on (i.id = x.id_instituicao )
   join periodo p on (x.id = p.id_curso ); 
--carga de fase de execução   
insert into fase_execucao (id_periodo_execucao, id_fase, data_inicio, data_fim )		  
select pe.id, f.id, '2019-02-06', '2019-07-10' from periodo_execucao pe	
  join periodo pd on (pe.id_periodo = pd.id)
  join fase f on (pd.id = f.id_periodo)
  where f.sigla = '1º Sem' and pe.ano = 2019
  order by pe.id;
insert into fase_execucao (id_periodo_execucao, id_fase, data_inicio, data_fim )		  
  select pe.id, f.id, '2019-08-01', '2019-12-20' from periodo_execucao pe	
    join periodo pd on (pe.id_periodo = pd.id)
    join fase f on (pd.id = f.id_periodo)
  where f.sigla = '2º Sem' and pe.ano = 2019
  order by pe.id;
insert into fase_execucao (id_periodo_execucao, id_fase, data_inicio, data_fim )		  
select pe.id, f.id, '2020-02-06', '2020-07-10' from periodo_execucao pe	
  join periodo pd on (pe.id_periodo = pd.id)
  join fase f on (pd.id = f.id_periodo)
  where f.sigla = '1º Sem' and pe.ano = 2020
  order by pe.id;
insert into fase_execucao (id_periodo_execucao, id_fase, data_inicio, data_fim )		  
  select pe.id, f.id, '2020-08-01', '2020-12-20' from periodo_execucao pe	
    join periodo pd on (pe.id_periodo = pd.id)
    join fase f on (pd.id = f.id_periodo)
  where f.sigla = '2º Sem' and pe.ano = 2020
  order by pe.id; 
--carga de sub-fase de execução 
insert into sub_fase_execucao (id_fase_execucao, id_sub_fase, data_inicio, data_fim )
select fe.id, sf.id, '2019-02-06', '2019-04-15' 
  from fase_execucao fe	
      join periodo_execucao pe on (fe.id_periodo_execucao = pe.id)
      join periodo pd on (pe.id_periodo = pd.id)
      join fase f on (fe.id_fase = f.id)
      join sub_fase sf on (f.id = sf.id_fase)
  where sf.sigla = '1º Bim' and pe.ano = 2019
  order by  pd.numero; 
insert into sub_fase_execucao (id_fase_execucao, id_sub_fase, data_inicio, data_fim )
select fe.id, sf.id, '2019-04-16', '2019-07-10' 
  from fase_execucao fe	
      join periodo_execucao pe on (fe.id_periodo_execucao = pe.id)
      join periodo pd on (pe.id_periodo = pd.id)
      join fase f on (fe.id_fase = f.id)
      join sub_fase sf on (f.id = sf.id_fase)
  where sf.sigla = '2º Bim' and pe.ano = 2019
  order by  pd.numero; 
insert into sub_fase_execucao (id_fase_execucao, id_sub_fase, data_inicio, data_fim )
select fe.id, sf.id, '2019-08-01', '2019-10-15' 
  from fase_execucao fe	
      join periodo_execucao pe on (fe.id_periodo_execucao = pe.id)
      join periodo pd on (pe.id_periodo = pd.id)
      join fase f on (fe.id_fase = f.id)
      join sub_fase sf on (f.id = sf.id_fase)
  where sf.sigla = '3º Bim' and pe.ano = 2019
  order by  pd.numero; 
insert into sub_fase_execucao (id_fase_execucao, id_sub_fase, data_inicio, data_fim )
select fe.id, sf.id, '2019-10-06', '2019-12-20' 
  from fase_execucao fe	
      join periodo_execucao pe on (fe.id_periodo_execucao = pe.id)
      join periodo pd on (pe.id_periodo = pd.id)
      join fase f on (fe.id_fase = f.id)
      join sub_fase sf on (f.id = sf.id_fase)
  where sf.sigla = '4º Bim' and pe.ano = 2019
  order by  pd.numero; 
insert into sub_fase_execucao (id_fase_execucao, id_sub_fase, data_inicio, data_fim )
select fe.id, sf.id, '2020-02-06', '2020-04-15' 
  from fase_execucao fe	
      join periodo_execucao pe on (fe.id_periodo_execucao = pe.id)
      join periodo pd on (pe.id_periodo = pd.id)
      join fase f on (fe.id_fase = f.id)
      join sub_fase sf on (f.id = sf.id_fase)
  where sf.sigla = '1º Bim' and pe.ano = 2020
  order by  pd.numero; 
insert into sub_fase_execucao (id_fase_execucao, id_sub_fase, data_inicio, data_fim )
select fe.id, sf.id, '2020-04-16', '2020-07-10' 
  from fase_execucao fe	
      join periodo_execucao pe on (fe.id_periodo_execucao = pe.id)
      join periodo pd on (pe.id_periodo = pd.id)
      join fase f on (fe.id_fase = f.id)
      join sub_fase sf on (f.id = sf.id_fase)
  where sf.sigla = '2º Bim' and pe.ano = 2020
  order by  pd.numero; 
insert into sub_fase_execucao (id_fase_execucao, id_sub_fase, data_inicio, data_fim )
select  fe.id, sf.id, '2020-08-01', '2020-10-15' 
  from fase_execucao fe	
      join periodo_execucao pe on (fe.id_periodo_execucao = pe.id)
      join periodo pd on (pe.id_periodo = pd.id)
      join fase f on (fe.id_fase = f.id)
      join sub_fase sf on (f.id = sf.id_fase)
  where sf.sigla = '3º Bim' and pe.ano = 2020
  order by  pd.numero; 
insert into sub_fase_execucao (id_fase_execucao, id_sub_fase, data_inicio, data_fim )
select fe.id, sf.id, '2020-10-06', '2020-12-20' 
  from fase_execucao fe	
      join periodo_execucao pe on (fe.id_periodo_execucao = pe.id)
      join periodo pd on (pe.id_periodo = pd.id)
      join fase f on (fe.id_fase = f.id)
      join sub_fase sf on (f.id = sf.id_fase)
  where sf.sigla = '4º Bim' and pe.ano = 2020
  order by  pd.numero;   
-- carga de contratos
insert into contrato
     (ano, data, perc_juros_atraso, perc_multa_atraso, perc_bom_pagador, 
      id_tipo_situacao_contrato, id_instituicao, id_periodo_execucao, id_matricula, id_tipo_composicao_valor)
select 2019, current_date  - INTERVAL '1 years', i.perc_juros_atraso, i.perc_multa_atraso, i.perc_bom_pagador,
       5, p.id_instituicao, (select id from periodo_execucao where ano = 2019 and id_periodo = 11), m.id, 1
      from matricula m 
      left join pessoa p on (m.id_pessoa = p.id)
	  left join instituicao i on (p.id_instituicao = i.id);
insert into contrato
     (ano, data, perc_juros_atraso, perc_multa_atraso, perc_bom_pagador, 
      id_tipo_situacao_contrato, id_instituicao, id_periodo_execucao, id_matricula, id_tipo_composicao_valor)
select 2020, current_date, i.perc_juros_atraso, i.perc_multa_atraso, i.perc_bom_pagador, 
       1, p.id_instituicao, (select id from periodo_execucao where ano = 2020 and id_periodo = 12), m.id, 1
      from matricula m 
      left join pessoa p on (m.id_pessoa = p.id)
	  left join instituicao i on (p.id_instituicao = i.id);	    
--carga de turma
insert into turma (id_periodo_execucao, id_calendario, ano, sigla, ativa, aberta, encerrada)
select pe.id, pe.id_calendario, pe.ano, 'T'||p.sigla, true, true, false
  from periodo_execucao pe 
    join periodo p on (pe.id_periodo = p.id);	
--carga de créditos
insert into credito (id_contrato, id_disciplina, id_tipo_credito, id_tipo_programa, id_turma, 
					 valor, carga_horaria_total,  carga_horaria_presencial, carga_horaria_distancia, pendencia )
select c.id, d.id, 1, 1, t.id,
       d.valor, d.carga_horaria_total, d.carga_horaria_presencial, d.carga_horaria_distancia, false
    from contrato c
	  left join periodo_execucao pe on (c.id_periodo_execucao = pe.id)
      left join periodo p on (pe.id_periodo = p.id)
	  left join turma t on (pe.id = t.id_periodo_execucao)
	  left join disciplina d on (p.id = d.id_periodo); 
--carga de horario
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (1, 2, true, '2020-02-06', '2020-02-29', 53, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (2, 2, true, '2020-02-06', '2020-02-29', 53, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (3, 2, true, '2020-02-06', '2020-02-29', 53, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (3, 3, true, '2020-02-06', '2020-02-29', 54, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (4, 3, true, '2020-02-06', '2020-02-29', 54, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (5, 3, true, '2020-02-06', '2020-02-29', 54, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (1, 4, true, '2020-02-06', '2020-02-29', 55, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (2, 4, true, '2020-02-06', '2020-02-29', 55, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (3, 4, true, '2020-02-06', '2020-02-29', 55, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (3, 4, true, '2020-02-06', '2020-02-29', 55, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (4, 4, true, '2020-02-06', '2020-02-29', 55, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (5, 4, true, '2020-02-06', '2020-02-29', 55, 22, 1);
insert into horario (id_tipo_dia_semana, id_funcionario, regular, data_inicial, data_final, id_disciplina, id_turma, id_tipo_regime_letivo)
values (1, 4, true, '2020-02-06', '2020-02-29', 59, 24, 1);
--carga de horario_hora_atividade
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 2 from horario where id between 2 and 4;  --1
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 3 from horario where id between 5 and 7;  --2
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 4 from horario where id between 2 and 4;  --3
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 5 from horario where id between 5 and 7; -- 3
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 6 from horario where id between 2 and 4; -- 4
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 7 from horario where id between 2 and 4; -- 5
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 8 from horario where id between 5 and 7;  --1
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 9 from horario where id between 2 and 4;  --2
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 10 from horario where id between 8 and 10;  --3
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 11 from horario where id between 11 and 13;  --3
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 12 from horario where id between 5 and 7;  --4
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 13 from horario where id between 5 and 7;  --5
insert into horario_hora_atividade (id_hora_atividade, id_horario)  select id, 14 from horario where id between 2 and 4;  --1
--carga de substitutos
insert into substituto (data_inicial, data_final, id_funcionario, id_horario) values ('2020-02-06', '2020-02-10', 3, 2);  
insert into substituto (data_inicial, data_final, id_funcionario, id_horario) values ('2020-02-10', '2020-02-29', 4, 7); 

--carga de valores e atitudes 
delete from atitude;
delete from valor;
ALTER TABLE public.atitude ALTER COLUMN id DROP DEFAULT;
ALTER TABLE public.valor ALTER COLUMN id DROP DEFAULT;

insert into valor (id, codigo, ativo, id_instituicao, nome) values (1, 'V0001', true, (select min(id) from instituicao), 'Paz');
insert into valor (id, codigo, ativo, id_instituicao, nome) values (2, 'V0002', true, (select min(id) from instituicao), 'Amor');
insert into valor (id, codigo, ativo, id_instituicao, nome) values (3, 'V0003', true, (select min(id) from instituicao), 'Espiritualidade');
insert into valor (id, codigo, ativo, id_instituicao, nome) values (4, 'V0004', true, (select min(id) from instituicao), 'Justiça');
insert into valor (id, codigo, ativo, id_instituicao, nome) values (5, 'V0005', true, (select min(id) from instituicao), 'Verdade');
insert into valor (id, codigo, ativo, id_instituicao, nome) values (6, 'V0006', true, (select min(id) from instituicao), 'Bem');

insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (1, 'A0001', true, 1, (select min(id) from instituicao), 'Demonstro respeito pelos meus colegas'); 
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (2, 'A0002', true, 1, (select min(id) from instituicao), 'Demonstro respeito pelos meus  professores');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (3, 'A0003', true, 1, (select min(id) from instituicao), 'Primo pela boa relação em sala de aula');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (4, 'A0004', true, 1, (select min(id) from instituicao), 'Sou tolerante com opiniões e posturas diversas');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (5, 'A0005', true, 1, (select min(id) from instituicao), 'Defendo  a cultura de paz, superando conflitos');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (6, 'A0006', true, 1, (select min(id) from instituicao), 'Resolvo conflitos em prol das relações');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (7, 'A0007', true, 1, (select min(id) from instituicao), 'Sou  resiliente nas situações de tensões');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (8, 'A0008', true, 1, (select min(id) from instituicao), 'Sou  equilibrado na defesa de minhas opiniões');

insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (9, 'A0009', true, 2, (select min(id) from instituicao), 'Tenho empatia,  colocando-me no lugar do outro');  
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (10, 'A0010', true, 2, (select min(id) from instituicao), 'Sou  cordial nas relações interpessoais'); 
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (11, 'A0011', true, 2, (select min(id) from instituicao), 'Respeito  as diferenças');  
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (12, 'A0012', true, 2, (select min(id) from instituicao), 'Demonstro  apreço pelos estudos'); 
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (13, 'A0013', true, 2, (select min(id) from instituicao), 'Apoio  gratuitamente quem necessita'); 
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (14, 'A0014', true, 2, (select min(id) from instituicao), 'Demonstro  satisfação para com a vida'); 
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (15, 'A0015', true, 2, (select min(id) from instituicao), 'Trabalho bem em equipe');  
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (16, 'A0016', true, 2, (select min(id) from instituicao), 'Dialogo e convivo bem  com o outro'); 

insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (17, 'A0017', true, 3, (select min(id) from instituicao), 'Demonstro  reverência para com a vida');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (18, 'A0018', true, 3, (select min(id) from instituicao), 'Trabalho minha  autoconsciência'); 
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (19, 'A0019', true, 3, (select min(id) from instituicao), 'Busco  e confio no Transcendente');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (20, 'A0020', true, 3, (select min(id) from instituicao), 'Trabalho  minha  vida  espiritual dando sentido à vida');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (21, 'A0021', true, 3, (select min(id) from instituicao), 'Desenvolvo  a interioridade');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (22, 'A0022', true, 3, (select min(id) from instituicao), 'Tenho uma consciência cósmica de cuidado com a Vida');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (23, 'A0023', true, 3, (select min(id) from instituicao), 'Cuido  da Vida ,  engajando-me  em ações concretas');

insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (24, 'A0024', true, 4, (select min(id) from instituicao), 'Primo  pela igualdade em sala de aula');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (25, 'A0025', true, 4, (select min(id) from instituicao), 'Sou  justo no trato com os professores');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (26, 'A0026', true, 4, (select min(id) from instituicao), 'Sou  ético ao realizar as atividades propostas(exercícios e provas)');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (27, 'A0027', true, 4, (select min(id) from instituicao), 'Tenho  senso moral , discernindo o certo e errado'); 
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (28, 'A0028', true, 4, (select min(id) from instituicao), 'Primo pela minha autonomia intelectual e emocional');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (29, 'A0029', true, 4, (select min(id) from instituicao), 'Sou  honesto ao avaliar o outro e a mim mesmo');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (30, 'A0030', true, 4, (select min(id) from instituicao), 'Sou  solidário para com quem necessita');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (31, 'A0031', true, 4, (select min(id) from instituicao), 'Obedeço as  regras e combinados pedagógicos');

insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (32, 'A0032', true, 5, (select min(id) from instituicao), 'Sou  responsável com o estudo pessoal');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (33, 'A0033', true, 5, (select min(id) from instituicao), 'Dedico-me com afinco  às atividades de sala de aula');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (34, 'A0034', true, 5, (select min(id) from instituicao), 'Sou  verdadeiro e  assumo meus  erros');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (35, 'A0035', true, 5, (select min(id) from instituicao), 'Demonstro  curiosidade e vontade de aprender');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (36, 'A0036', true, 5, (select min(id) from instituicao), 'Tomo  decisões com coerência e responsabilidade');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (37, 'A0037', true, 5, (select min(id) from instituicao), 'Minha avaliação é sincera no processo de aprendizagem');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (38, 'A0038', true, 5, (select min(id) from instituicao), 'Aceito opiniões diferentes');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (39, 'A0039', true, 5, (select min(id) from instituicao), 'Avalio o outro com respeito');

insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (40, 'A0040', true, 6, (select min(id) from instituicao), 'Zelo pelo  ambiente harmonioso em sala de aula e na escola');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (41, 'A0041', true, 6, (select min(id) from instituicao), 'Cuido  dos materiais de uso comum na escola');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (42, 'A0042', true, 6, (select min(id) from instituicao), 'Demonstro  capacidade de escuta e alteridade');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (43, 'A0043', true, 6, (select min(id) from instituicao), 'Sou  grato pelo que recebo da escola, na família, etc');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (44, 'A0044', true, 6, (select min(id) from instituicao), 'Sou  crítico na análise de situações problemas');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (45, 'A0045', true, 6, (select min(id) from instituicao), 'Demonstro  um olhar positivo diante da vida');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (46, 'A0046', true, 6, (select min(id) from instituicao), 'Demonstro  desapego e capacidade de doação');
insert into atitude  (id, codigo, ativa, id_valor, id_instituicao, nome) values (47, 'A0047', true, 6, (select min(id) from instituicao), 'Sou  sóbrio e não me envolver em situações de risco');

SELECT setval('public.seq_id_atitude', 47, true);
SELECT setval('public.seq_id_valor', 6, true);
ALTER TABLE public.atitude ALTER COLUMN id SET DEFAULT nextval('seq_id_atitude'::regclass);
ALTER TABLE public.valor ALTER COLUMN id SET DEFAULT nextval('seq_id_valor'::regclass);

INSERT INTO public.categoria_evento (nome, cor) VALUES('Reunião dos pais', 'blue');
INSERT INTO public.categoria_evento (nome, cor) VALUES('Reunião dos professores', 'indigo');
INSERT INTO public.categoria_evento (nome, cor) VALUES('Dia da criança', 'purple');
INSERT INTO public.categoria_evento (nome, cor) VALUES('Dia do livro', 'cyan');











