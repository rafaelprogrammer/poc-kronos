INSERT INTO public.evento
("data", 
dia_letivo, 
descricao, 
hora_inicio, hora_final, id_categoria_evento, id_calendario, id_evento_inicial, id_evento_sequente, recorrente, dia_todo)
VALUES('2019-11-22', true, 'Dia da Criança', '2019-11-22 10:00:00', '2019-11-22 12:00:00', 4, 2, null, null, false, false);


INSERT INTO public.evento
("data",
dia_letivo,
descricao,
hora_inicio, hora_final, id_categoria_evento, id_calendario, id_evento_inicial, id_evento_sequente, recorrente, dia_todo)
VALUES('2019-11-22', true, 'Dia dos Pais', '2019-11-22 15:00:00', '2019-11-22 18:00:00', 2, 2, null, null, false, false);

ALTER TABLE evento ADD COLUMN data_final date NOT NULL;


ALTER TABLE public.evento DROP COLUMN data;
ALTER TABLE public.evento DROP COLUMN recorrente;
ALTER TABLE public.evento RENAME COLUMN hora_inicio TO data_hora_inicio;
COMMENT ON COLUMN public.evento.data_hora_inicio IS 'Data e hora inicial da ocorrencia do evento';
ALTER TABLE public.evento RENAME COLUMN hora_final TO data_hora_final;
COMMENT ON COLUMN public.evento.data_hora_final IS 'Data e hora final da ocorrencia do evento';
ALTER TABLE public.evento ALTER COLUMN data_hora_inicio SET NOT NULL;
ALTER TABLE public.evento ALTER COLUMN data_hora_final SET NOT NULL;

ALTER TABLE public.hora_atividade ALTER COLUMN hora_inicial TYPE time USING hora_inicial::time;
ALTER TABLE public.hora_atividade ALTER COLUMN hora_final TYPE time USING hora_final::time;


ALTER TABLE public.frequencia ADD COLUMN nr_falta int4 NOT NULL;
ALTER TABLE public.frequencia ADD COLUMN nr_falta_justificada int4 NOT NULL;

ALTER TABLE frequencia ADD nr_presenca integer NOT null default 0;
COMMENT ON COLUMN "public".frequencia.nr_presenca IS 'Número de presenças (Qtde de valor 1 do array de frequencia)';
ALTER TABLE "public".frequencia ADD nr_falta integer NOT NULL default 0;
COMMENT ON COLUMN "public".frequencia.nr_falta IS 'Número de faltas não justificadas (Qtde de valor 2 do array de frequencia)';
ALTER TABLE "public".frequencia ADD nr_falta_justificada integer NOT null default 0;
COMMENT ON COLUMN "public".frequencia.nr_falta_justificada IS 'Número de faltas justificadas (Qtde de valor 3 do array de frequencia)';

COMMENT ON COLUMN "public".frequencia.nr_presenca IS 'Número de presenças (Qtde de valor P do array de frequencia)';
COMMENT ON COLUMN "public".frequencia.nr_falta IS 'Número de faltas não justificadas (Qtde de valor F do array de frequencia)';
COMMENT ON COLUMN "public".frequencia.nr_falta_justificada IS 'Número de faltas justificadas (Qtde de valor FJ do array de frequencia)';


ALTER TABLE public.avaliacao ALTER COLUMN id SET DEFAULT nextval('seq_id_avaliacao'::regclass);

ALTER TABLE public.avaliacao ALTER COLUMN hora_inicio TYPE time USING hora_inicio::time;
