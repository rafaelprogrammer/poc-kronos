package br.com.kronos.backend.adaptadores.repositorio.avaliacao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.Avaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AvaliacaoRepositorioImpl extends SqlQueryRepositorio implements AvaliacaoRepositorio {
	
	public AvaliacaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	public void criarAvaliacaoAutomatica(Long idAtividadeSelecionada, Long idTurmaSelecionada, Long idDisciplinaSelecionada) {
		StringBuilder query = createQuery("insert into avaliacao (hora_inicio, numero_max_participante, id_turma, id_disciplina, peso, observacao, anulada, motivo_anulacao, ");
        query.append("id_tipo_formato, id_tipo_registro_nota, id_tipo_abrangencia, id_tipo_avaliacao, tempo_duracao, id_atividade) ");
        query.append("values ((select max (hora_inicial) ");
        query.append("from hora_atividade ha ");
        query.append("left join horario_hora_atividade hha on (ha.id = hha.id_hora_atividade) ");
        query.append("left join horario h on (hha.id_horario = h.id) ");
        query.append("left join atividade a on (h.id = a.id_horario) where a.id = ");
        query.append(idAtividadeSelecionada);
        query.append("), ");
        
        
        query.append("(select count(*)  from credito c where c.id_turma = ");
        query.append(idTurmaSelecionada);
        query.append(" and c.id_disciplina = ");
        query.append(idDisciplinaSelecionada);
        query.append("), ");
        
        //query.append("(select count (distinct c.id_contrato) from credito c ");
        //query.append("left join contrato ct on (c.id_contrato = ct.id) where ct.id_tipo_situacao_contrato = ");
        //query.append(EnumTipoSituacaoContrato.CONFIRMADO.id());
        
        //query.append(" and c.id_turma = (select id_turma from horario h left join atividade a on (h.id = a.id_horario) where a.id = ");
        //query.append(idAtividadeSelecionada);
        //query.append(")), ");
        
        query.append("(select id_turma from horario h left join atividade a on (h.id = a.id_horario) where a.id = ");
        query.append(idAtividadeSelecionada);
        query.append("), ");
        
        query.append("(select id_disciplina from horario h left join atividade a on (h.id = a.id_horario) where a.id = ");
        query.append(idAtividadeSelecionada);
        query.append("), 1, null, false, null, 1, 2, 1, 1, 50, ");
        query.append(idAtividadeSelecionada);
        query.append(")");
        
        
        insertCustom(query.toString());
        
	}

	@Override
	public Long criar(Avaliacao avaliacao) {
        addFields("hora_inicio", "grupo", "numero_max_participante", "peso", 
                  "observacao", "anulada", "motivo_anulacao",  "id_tipo_formato", 
                  "id_tipo_registro_nota", "id_tipo_abrangencia", "id_atividade", "id_tipo_avaliacao", 
                  "tempo_duracao", "id_turma", "id_disciplina");
		
		addValues(avaliacao.getHoraInicio(), avaliacao.isGrupo(), avaliacao.getNumeroMaxParticipante(),avaliacao.getPeso(), 
                  avaliacao.getObservacao(), avaliacao.isAnulada(), avaliacao.getMotivoAnulacao(), avaliacao.getIdTipoFormato(),  
                  avaliacao.getIdTipoRegistroNota(), avaliacao.getIdTipoAbrangencia(), avaliacao.getIdAtividade(), avaliacao.getIdTipoAvaliacao(), 
                  avaliacao.getTempoDuracao(), avaliacao.getIdTurma(), avaliacao.getIdDisciplina());
		
		return (long) insertAuto("avaliacao"); 
    }
		
	@Override
	public Long alterar(Avaliacao avaliacao) {
		addFields("hora_inicio", "grupo", "numero_max_participante", "peso", 
                  "observacao", "anulada", "motivo_anulacao",  "id_tipo_formato", 
                  "id_tipo_registro_nota", "id_tipo_abrangencia", "id_atividade", "id_tipo_avaliacao", 
                  "tempo_duracao", "id_turma", "id_disciplina");
		
		addValues(avaliacao.getHoraInicio(), avaliacao.isGrupo(), avaliacao.getNumeroMaxParticipante(),avaliacao.getPeso(), 
                avaliacao.getObservacao(), avaliacao.isAnulada(), avaliacao.getMotivoAnulacao(), avaliacao.getIdTipoFormato(),  
                avaliacao.getIdTipoRegistroNota(), avaliacao.getIdTipoAbrangencia(), avaliacao.getIdAtividade(), avaliacao.getIdTipoAvaliacao(), 
                avaliacao.getTempoDuracao(), avaliacao.getIdTurma(), avaliacao.getIdDisciplina(), avaliacao.getId());
		
		return (long) update("avaliacao", avaliacao.getId());
	}
	
	@Override
	public AvaliacaoDTO buscarPorId(Long id) {
		try {     
			StringBuilder query = createQuery("select av.id, a.id as id_atividade, a.data_prevista, av.hora_inicio, av.tempo_duracao, ta.nome as abrangencia, t.nome as tipo_avaliacao, tf.nome as formato, ");
	        query.append("av.grupo, av.anulada, count(al.id_credito) as qtd_avaliado, av.numero_max_participante, av.peso, ");
	        query.append("av.observacao, av.id_tipo_formato, av.id_tipo_registro_nota, av.id_tipo_abrangencia, av.id_atividade,  av.id_tipo_avaliacao, av.motivo_anulacao, ");
	        query.append("av.tempo_duracao, av.id_turma, av.id_disciplina ");
	        query.append("from avaliacao av ");
	        query.append("left join atividade a on (av.id_atividade = a.id) ");
	        query.append("left join horario h on (a.id_horario = h.id) ");
	        query.append("left join avaliado al on (av.id = al.id_avaliacao) ");
	        query.append("left join tipo_abrangencia ta on (av.id_tipo_abrangencia = ta.id) ");
	        query.append("left join tipo_avaliacao t on (av.id_tipo_avaliacao = t.id) ");
	        query.append("left join tipo_formato tf on (av.id_tipo_formato = tf.id) where 1=1 ");
            query = andEqual(query, "av.id", id);
            query = groupBy(query, "av.id, a.id, a.data_prevista, av.hora_inicio, av.tempo_duracao, ta.nome, t.nome, tf.nome, av.grupo, av.peso, av.anulada, av.numero_max_participante, av.peso, "
            		+ "av.observacao, av.id_tipo_formato, av.id_tipo_registro_nota, av.id_tipo_abrangencia, av.id_atividade,  av.id_tipo_avaliacao, av.motivo_anulacao, av.tempo_duracao, av.id_turma, av.id_disciplina");
            AvaliacaoDTO avaliacao = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new AvaliacaoDTOMapper());
			return avaliacao;
		} catch (EmptyResultDataAccessException e) {
			log.info("avaliacao não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<AvaliacaoDTO> listar(FiltroAvaliacao filtroAvaliacao) {
		StringBuilder query = createQuery("select av.id, a.id as id_atividade, a.data_prevista, av.hora_inicio, av.tempo_duracao, ta.nome as abrangencia, t.nome as tipo_avaliacao, tf.nome as formato, ");
        query.append("av.grupo, av.anulada, count(al.id_credito) as qtd_avaliado, av.numero_max_participante, av.peso, ");
        query.append("av.observacao, av.id_tipo_formato, av.id_tipo_registro_nota, av.id_tipo_abrangencia, av.id_atividade,  av.id_tipo_avaliacao, av.motivo_anulacao, ");
        query.append("av.tempo_duracao, av.id_turma, av.id_disciplina ");
        query.append("from avaliacao av ");
        query.append("left join atividade a on (av.id_atividade = a.id) ");
        query.append("left join horario h on (a.id_horario = h.id) ");
        query.append("left join avaliado al on (av.id = al.id_avaliacao) ");
        query.append("left join tipo_abrangencia ta on (av.id_tipo_abrangencia = ta.id) ");
        query.append("left join tipo_avaliacao t on (av.id_tipo_avaliacao = t.id) ");
        query.append("left join tipo_formato tf on (av.id_tipo_formato = tf.id) where 1=1 ");
        query = andEqual(query, "h.id_turma",  filtroAvaliacao.getIdTurma());
        query = andEqual(query, "h.id_disciplina", filtroAvaliacao.getIdDisciplina());
        query = groupBy(query, "av.id, a.id, a.data_prevista, av.hora_inicio, av.tempo_duracao, ta.nome, t.nome, tf.nome, av.grupo, av.peso, av.anulada, av.numero_max_participante, av.peso, "
        		+ "av.observacao, av.id_tipo_formato, av.id_tipo_registro_nota, av.id_tipo_abrangencia, av.id_atividade,  av.id_tipo_avaliacao, av.motivo_anulacao, av.tempo_duracao, av.id_turma, av.id_disciplina");
        query = orderBy(query, Order.DESC, "a.data_prevista");
		query = limit(query, filtroAvaliacao.getQtdTotal(), filtroAvaliacao.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AvaliacaoDTOMapper());
	}
	
	@Override
	public int contar(FiltroAvaliacao filtroAvaliacao) {
		StringBuilder query = createQuery("select count(distinct av.id) ");
        query.append("from avaliacao av ");
        query.append("left join atividade a on (av.id_atividade = a.id) ");
        query.append("left join horario h on (a.id_horario = h.id) ");
        query.append("left join avaliado al on (av.id = al.id_avaliacao) ");
        query.append("left join tipo_abrangencia ta on (av.id_tipo_abrangencia = ta.id) ");
        query.append("left join tipo_avaliacao t on (av.id_tipo_avaliacao = t.id) ");
        query.append("left join tipo_formato tf on (av.id_tipo_formato = tf.id) where 1=1 ");
        query = andEqual(query, "h.id_turma",  filtroAvaliacao.getIdTurma());
        query = andEqual(query, "h.id_disciplina", filtroAvaliacao.getIdDisciplina()); 
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("avaliacao", id);
		return true;
	}
	
	private class AvaliacaoDTOMapper implements RowMapper<AvaliacaoDTO> {

		@Override
		public AvaliacaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AvaliacaoDTO.builder()
					.id(rs.getLong("id"))
                    .horaInicio(rs.getTime("hora_inicio").toLocalTime())
                    .grupo(rs.getBoolean("grupo"))
					.numeroMaxParticipante(rs.getInt("numero_max_participante"))
                    .peso(rs.getInt("peso"))
                    .observacao(rs.getString("observacao") != null ? rs.getString("observacao") : null)
                    .anulada(rs.getBoolean("anulada"))
                    .motivoAnulacao(rs.getString("motivo_anulacao") != null ? rs.getString("motivo_anulacao") : null)
					.idTipoFormato(rs.getInt("id_tipo_formato"))
					.idAtividade(rs.getLong("id_atividade"))
					.nomeTipoFormato(rs.getString("formato"))
                    .idTipoRegistroNota(rs.getInt("id_tipo_registro_nota"))
                    .idTipoAbrangencia(rs.getInt("id_tipo_abrangencia"))
                    .nomeTipoAbrangencia(rs.getString("abrangencia"))
					.idAtividade(rs.getLong("id_atividade"))
                    .idTipoAvaliacao(rs.getInt("id_tipo_avaliacao"))
                    .nomeTipoAvaliacao(rs.getString("tipo_avaliacao"))
                    .tempoDuracao(rs.getInt("tempo_duracao"))
                    .qtdAvaliados(rs.getInt("qtd_avaliado"))
                    .dataPrevista(rs.getDate("data_prevista").toLocalDate())
                    .idTurma(rs.getLong("id_turma"))
                    .idDisciplina(rs.getLong("id_disciplina"))
					.build();
		}

	}
	
}