--Ajustes para a Issue #86
ALTER TABLE avaliacao RENAME individual TO grupo;
COMMENT ON COLUMN public.avaliacao.grupo IS 'Status indicador se a avaliação será realizada em grupo.';
ALTER TABLE avaliado ADD CONSTRAINT uk_avaliado UNIQUE ( id_avaliacao, id_credito );
COMMENT ON CONSTRAINT uk_avaliado ON avaliado IS 'Restrição de unicidade para os campos avaliação e crédito' 

