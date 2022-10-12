package br.com.kronos.backend.adaptadores.repositorio.calendario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.CalendarioCurso;
import br.com.kronos.backend.aplicacao.calendario.CalendarioCursoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroCalendarioCurso;
import br.com.kronos.backend.aplicacao.calendario.api.CalendarioResumidoDTO;

public class CalendarioCursoRepositorioImpl extends SqlQueryRepositorio implements CalendarioCursoRepositorio {
	
	public CalendarioCursoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(CalendarioCurso calendarioCurso) {
		addFields("id_calendario", "id_curso");
		
		addValues(calendarioCurso.getIdCalendario(), calendarioCurso.getIdCurso());
		
		return insert("calendario_curso");
	}
	
	@Override
	public List<Integer> listarAnos(FiltroCalendarioCurso filtroCalendarioCurso) {
		StringBuilder query = createQuery("select distinct ano from calendario c ");
		query.append(" left join calendario_curso cc on (cc.id_calendario = c.id) ");
		query = andEqual(query, "c.id_instituicao", filtroCalendarioCurso.getIdInstituicao());
		query = andEqual(query, "cc.id_curso", filtroCalendarioCurso.getIdCurso());
		
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<CalendarioResumidoDTO> listarParaCombo(FiltroCalendarioCurso filtroCalendarioCurso) {
		StringBuilder query = createQuery("select id, numero, ano, descricao from calendario c ");
		query.append(" left join calendario_curso cc on (cc.id_calendario = c.id) where 1=1 ");
		query = andEqual(query, "c.id_instituicao", filtroCalendarioCurso.getIdInstituicao());
		query = andEqual(query, "cc.id_curso", filtroCalendarioCurso.getIdCurso());
		query = andEqual(query, "c.ano", filtroCalendarioCurso.getAno());
		
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CalendarioResumidoMapper());
	}

	@Override
	public List<CalendarioCurso> listar(FiltroCalendarioCurso filtroCalendarioCurso) {
		StringBuilder query = createQuery("select c.id_calendario, c.id_curso ");
		query.append(" from calendario_curso c where 1=1 ");
		query = andEqual(query, "c.id_calendario", filtroCalendarioCurso.getIdCalendario());
		query = andEqual(query, "c.id_curso", filtroCalendarioCurso.getIdCurso());
		query = limit(query, filtroCalendarioCurso.getQtdTotal(), filtroCalendarioCurso.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CalendarioCursoMapper());
	}
	
	@Override
	public int contar(FiltroCalendarioCurso filtroCalendarioCurso) {
		StringBuilder query = createQuery("select count(c.id_curso) from calendario_curso c where 1=1 ");
		query = andEqual(query, "c.id_calendario", filtroCalendarioCurso.getIdCalendario());
		query = andEqual(query, "c.id_curso", filtroCalendarioCurso.getIdCurso());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(long idCalendario, long idCurso) {
		addFields("id_calendario", "id_curso");
		addValues(idCalendario, idCurso);
		delete("calendario_curso");
		return true;
	}
	
	@Override
	public boolean excluirTodosDoCalendario(long idCalendario) {
		addFields("id_calendario");
		addValues(idCalendario);
		delete("calendario_curso");
		return true;
	}
	
	private class CalendarioCursoMapper implements RowMapper<CalendarioCurso> {

		@Override
		public CalendarioCurso mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CalendarioCurso.builder()
					.idCalendario(rs.getLong("id_calendario"))
					.idCurso(rs.getLong("id_curso"))
					.build();
		}

	}
	
	private class CalendarioResumidoMapper implements RowMapper<CalendarioResumidoDTO> {

		@Override
		public CalendarioResumidoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CalendarioResumidoDTO.builder()
					.id(rs.getLong("id"))
					.numero(rs.getInt("numero"))
					.ano(rs.getInt("ano"))
					.descricao(rs.getString("descricao"))
					.build();
		}

	}

	@Override
	public int contarPorIdCalendarioECurso(FiltroCalendarioCurso filtroCalendarioCurso) {
		StringBuilder query = createQuery("select count(c.id_curso) from calendario_curso c where 1=1 ");
		query = andEqual(query, "c.id_calendario", filtroCalendarioCurso.getIdCalendario());
		query = andEqual(query, "c.id_curso", filtroCalendarioCurso.getIdCurso());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}


}
