ALTER TABLE turma ADD COLUMN id_tipo_turno bigint; 
COMMENT ON COLUMN turma.id_tipo_turno IS 'Identificador do tipo de turno';
ALTER TABLE turma ADD CONSTRAINT fk_turma_tipo_turno FOREIGN KEY (id_tipo_turno) REFERENCES public.tipo_turno (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE RESTRICT;
COMMENT ON CONSTRAINT fk_turma_tipo_turno ON turma IS 'Chave estrangeira que referencia turma a tipo de turno';	
update turma t set id_tipo_turno = (
select c.id_tipo_turno 
   from curso c
     left join periodo p on (c.id = p.id_curso)
     left join periodo_execucao pe on (p.id = pe.id_periodo)
where pe.id = t.id_periodo_execucao);
ALTER TABLE turma ALTER COLUMN id_tipo_turno SET NOT NULL;
ALTER TABLE config_ponto ADD COLUMN carga_horaria_online integer NOT NULL DEFAULT 0;
COMMENT ON COLUMN config_ponto.carga_horaria_online IS 'Carga horária máxima diária online do funcionário';
ALTER TABLE tipo_turno ADD COLUMN sigla character varying(1);
COMMENT ON COLUMN tipo_turno.sigla IS 'Sigla que identifica o tipo de turno';
UPDATE tipo_turno SET sigla = 'M' WHERE id = 1;	
UPDATE tipo_turno SET sigla = 'V' WHERE id = 2;	
UPDATE tipo_turno SET sigla = 'N' WHERE id = 3;	
UPDATE tipo_turno SET sigla = 'I' WHERE id = 4;
ALTER TABLE tipo_turno ALTER COLUMN sigla SET NOT NULL;
ALTER TABLE tipo_avaliacao ADD COLUMN sigla character varying(2);
COMMENT ON COLUMN tipo_avaliacao.sigla IS 'Sigla que identifica o tipo de avaliacao';
UPDATE tipo_avaliacao SET sigla = 'CR' WHERE id = 1;	
UPDATE tipo_avaliacao SET sigla = 'RC' WHERE id = 2;	
UPDATE tipo_avaliacao SET sigla = 'EF' WHERE id = 3;	
UPDATE tipo_avaliacao SET sigla = 'CS' WHERE id = 4;
UPDATE tipo_avaliacao SET sigla = 'AS' WHERE id = 5;	
UPDATE tipo_avaliacao SET sigla = 'AE' WHERE id = 6;
ALTER TABLE tipo_avaliacao ALTER COLUMN sigla SET NOT NULL;










