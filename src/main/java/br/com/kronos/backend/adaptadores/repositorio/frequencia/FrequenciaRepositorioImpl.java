package br.com.kronos.backend.adaptadores.repositorio.frequencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.IntStream;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.atestado.EnumTipoAtestado;
import br.com.kronos.backend.aplicacao.frequencia.EnumTipoFrequencia;
import br.com.kronos.backend.aplicacao.frequencia.FiltroFrequencia;
import br.com.kronos.backend.aplicacao.frequencia.Frequencia;
import br.com.kronos.backend.aplicacao.frequencia.FrequenciaRepositorio;
import br.com.kronos.backend.aplicacao.frequencia.api.FrequenciaDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FrequenciaRepositorioImpl extends SqlQueryRepositorio implements FrequenciaRepositorio {
	
	public FrequenciaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Frequencia frequencia) {
        addFields("id_atividade", "id_credito", "frequencia", "data_reposicao", "nr_presenca", "nr_falta", "nr_falta_justificada");
		addValues(frequencia.getIdAtividade(), frequencia.getIdCredito(), createSqlArray("text", frequencia.getFrequencia()),
				frequencia.getDataReposicao(), frequencia.getNumeroPresenca(), frequencia.getNumeroFalta(), frequencia.getNumeroFaltaJustificada());
		
		return (long) insertAuto("frequencia"); 
    }
		
	@Override
	public Long alterar(Frequencia frequencia) {
		addFields("id_atividade", "id_credito", "frequencia", "data_reposicao", "nr_presenca", "nr_falta", "nr_falta_justificada");
		
		addValues(frequencia.getIdAtividade(), frequencia.getIdCredito(), createSqlArray("text", frequencia.getFrequencia()), frequencia.getDataReposicao(),
				  frequencia.getNumeroPresenca(), frequencia.getNumeroFalta(), frequencia.getNumeroFaltaJustificada(),
                  frequencia.getId());
		
		return (long) update("frequencia", frequencia.getId());
	}
	
	@Override
	public Frequencia buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select f.id, f.id_atividade, f.id_credito, f.frequencia, f.data_reposicao, f.nr_presenca, f.nr_falta, f.nr_falta_justificada ");
            query.append("from frequencia f where 1=1 ");
            query = andEqual(query, "f.id", id);
			Frequencia frequencia = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new FrequenciaMapper());
			return frequencia;
		} catch (EmptyResultDataAccessException e) {
			log.info("Frequência não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<FrequenciaDTO> listar(FiltroFrequencia filtroFrequencia) {
		StringBuilder query = createQuery("select a.data_prevista, f.id, a.id as id_atividade, p.id as id_pessoa, c.id as id_credito, p.nome, f.data_reposicao, count(ha.id_hora_atividade) as nr_atividade, ");
		query.append(" cast((select sum(f.nr_falta) ");
		query.append("from atividade at ");
		query.append("left join frequencia f on (at.id = f.id_atividade) left join horario ho on (at.id_horario = ho.id) where 1=1 ");
		query = andEqual(query, "at.id_sub_fase_execucao", filtroFrequencia.getIdSubFaseExecucao());  
		query = andEqual(query, "ho.id_disciplina", filtroFrequencia.getIdDisciplina()); 
		query.append(" and f.id_credito = c.id) as integer ) as nr_falta, "); 
		
		query.append("CASE ");
		query.append("WHEN (select tf.sigla from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) where at.id_tipo_atestado = ");
		query.append(EnumTipoAtestado.TRANSFERIDO.id());
		query.append(" and at.id_pessoa = p.id ");
		query.append(" and a.data_prevista between at.data_inicio_vigencia and at.data_final_vigencia) is not null THEN 'T' ");
		
		query.append("WHEN (select tf.sigla from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) where at.id_tipo_atestado = ");
		query.append(EnumTipoAtestado.CANCELAMENTO.id());
		query.append(" and at.id_pessoa = p.id ");
		query.append(" and a.data_prevista between at.data_inicio_vigencia and at.data_final_vigencia) is not null THEN 'C' ");
		
		query.append("WHEN (select tf.sigla from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) where at.id_tipo_atestado = ");
		query.append(EnumTipoAtestado.PANDEMIA_COVID_19.id());
		query.append(" and a.data_prevista between at.data_inicio_vigencia and at.data_final_vigencia) is not null THEN 'AD' ");
		
		query.append("WHEN (select tf.sigla from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) where at.id_tipo_atestado = ");
		query.append(EnumTipoAtestado.ATESTADO_MEDICO.id());
		query.append(" and at.id_pessoa = p.id ");
		query.append(" and a.data_prevista between at.data_inicio_vigencia and at.data_final_vigencia) is not null THEN ");
		query.append("(select tf.sigla from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) where at.id_tipo_atestado = ");
		query.append(EnumTipoAtestado.ATESTADO_MEDICO.id());
		query.append(" and at.id_pessoa = p.id ");
		query.append(" and a.data_prevista between at.data_inicio_vigencia and at.data_final_vigencia) ELSE null ");
		query.append(" END AS tp_frequencia_atestado, ");
		
		query.append(" f.frequencia "); 
		query.append("from atividade a ");
		query.append("left join horario h on (a.id_horario = h.id) ");
		query.append("left join turma t on (h.id_turma = t.id) ");
		query.append("left join credito c on (t.id = c.id_turma) ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) ");
		query.append("left join matricula m on (ct.id_matricula = m.id) ");
		query.append("left join pessoa p on (m.id_pessoa = p.id) ");
		query.append("left join frequencia f on (a.id = f.id_atividade and c.id = f.id_credito) ");
		query.append("left join horario_hora_atividade ha on (h.id = ha.id_horario) where 1=1 ");
		query = andEqual(query, "a.id", filtroFrequencia.getIdAtividade());
		query.append(" and h.id_disciplina = c.id_disciplina ");
		//query = andEqual(query, "ct.id_tipo_situacao_contrato", EnumTipoSituacaoContrato.CONFIRMADO.id());
		query = andEqual(query, "ct.ano", filtroFrequencia.getAnoTurma());
		query = groupBy(query, "a.data_prevista, f.id, a.id, p.id, c.id, p.nome, f.data_reposicao, f.frequencia");
        query = orderBy(query, Order.ASC, "p.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new FrequenciaDTOMapper());
	}
	
	@Override
	public int contar(FiltroFrequencia filtroFrequencia) {
		StringBuilder query = createQuery("select distinct f.id ");
		query.append("from atividade a ");
		query.append("left join horario h on (a.id_horario = h.id) ");
		query.append("left join turma t on (h.id_turma = t.id) ");
		query.append("left join credito c on (t.id = c.id_turma) ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) ");
		query.append("left join matricula m on (ct.id_matricula = m.id) ");
		query.append("left join pessoa p on (m.id_pessoa = p.id) ");
		query.append("left join frequencia f on (a.id = f.id_atividade and c.id = f.id_credito) ");
		query.append("left join horario_hora_atividade ha on (h.id = ha.id_horario) where 1=1 ");
		query = andEqual(query, "a.id", filtroFrequencia.getIdAtividade());
		query.append(" and h.id_disciplina = c.id_disciplina ");
		List<Integer> idsFrequencias = this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Integer.class);
		return idsFrequencias != null && !idsFrequencias.isEmpty() ? idsFrequencias.size() : 0;
	}
	
	@Override
	public Long buscarFrequenciaPorIdAtividadeEIdCredito(Long idAtividade, Long idCredito) {
		try {
			StringBuilder query = createQuery("select f.id ");
			query.append("from frequencia f  where 1=1 ");
			query = andEqual(query, "f.id_atividade", idAtividade);
			query = andEqual(query, "f.id_credito", idCredito);
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Long.class);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("frequencia", id);
		return true;
	}
	
	private class FrequenciaDTOMapper implements RowMapper<FrequenciaDTO> {

		@Override
		public FrequenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long numeroAtividade = rs.getLong("nr_atividade");
			String[] frequenciaBanco = rs.getArray("frequencia") != null ? (String[])rs.getArray("frequencia").getArray() : null;
			if (frequenciaBanco == null) {
				String[] frequencia = new String[numeroAtividade.intValue()];
				IntStream.range(0, numeroAtividade.intValue()).forEach(index -> {
					frequencia[index] = EnumTipoFrequencia.I.name();
				});
				frequenciaBanco = frequencia;
			}
			return FrequenciaDTO.builder()
					.id(rs.getLong("id"))
					.dataReposicao(rs.getDate("data_reposicao") != null ? rs.getDate("data_reposicao").toLocalDate() : null)
                    .idAtividade(rs.getLong("id_atividade"))
                    .idCredito(rs.getLong("id_credito"))
                    .frequencia(frequenciaBanco)
                    .numeroAtividade(numeroAtividade)
                    .tipoFrequenciaAtestado(rs.getString("tp_frequencia_atestado"))
                    .numeroFalta(rs.getInt("nr_falta"))
                    .aluno(rs.getString("nome"))
                    .idPessoa(rs.getLong("id_pessoa"))
					.build();
		}
	}
	
	private class FrequenciaMapper implements RowMapper<Frequencia> {

		@Override
		public Frequencia mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Frequencia.builder()
					.id(rs.getLong("id"))
					.dataReposicao(rs.getDate("data_reposicao") !=null ? rs.getDate("data_reposicao").toLocalDate() : null)
                    .idAtividade(rs.getLong("id_atividade"))
                    .idCredito(rs.getLong("id_credito"))
                    .numeroFalta(rs.getInt("nr_falta"))
                    .numeroPresenca(rs.getInt("nr_presenca"))
                    .numeroFaltaJustificada(rs.getInt("nr_falta_justificada"))
                    .frequencia(rs.getArray("frequencia") != null ? (String[])rs.getArray("frequencia").getArray() : null)
					.build();
		}
	}
}
