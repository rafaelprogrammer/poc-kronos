package br.com.kronos.backend.adaptadores.repositorio.horario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.horario.FiltroHorarioHoraAtividade;
import br.com.kronos.backend.aplicacao.horario.HorarioHoraAtividade;
import br.com.kronos.backend.aplicacao.horario.HorarioHoraAtividadeRepositorio;

public class HorarioHoraAtividadeRepositorioImpl extends SqlQueryRepositorio implements HorarioHoraAtividadeRepositorio {
	
	public HorarioHoraAtividadeRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(HorarioHoraAtividade horarioHoraAtividade) {
		addFields("id_horario", "id_hora_atividade");
		
		addValues(horarioHoraAtividade.getIdHorario(), horarioHoraAtividade.getIdHoraAtividade());
		
		return insert("horario_hora_atividade");
	}

	@Override
	public List<HorarioHoraAtividade> listar(FiltroHorarioHoraAtividade filtroHorarioHoraAtividade) {
		StringBuilder query = createQuery("select h.id_horario, h.id_hora_atividade ");
		query.append(" from horario_hora_atividade h where 1=1 ");
		query = andEqual(query, "h.id_horario", filtroHorarioHoraAtividade.getIdHorario());
		query = andEqual(query, "h.id_hora_atividade", filtroHorarioHoraAtividade.getIdHoraAtividade());
		query = limit(query, filtroHorarioHoraAtividade.getQtdTotal(), filtroHorarioHoraAtividade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new HorarioHoraAtividadeMapper());
	}
	
	@Override
	public int contar(FiltroHorarioHoraAtividade filtroHorarioHoraAtividade) {
		StringBuilder query = createQuery("select count(h.id_hora_atividade) from horario_hora_atividade h where 1=1 ");
		query = andEqual(query, "h.id_horario", filtroHorarioHoraAtividade.getIdHorario());
		query = andEqual(query, "h.id_hora_atividade", filtroHorarioHoraAtividade.getIdHoraAtividade());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(long idHorario, long idHoraAtividade) {
		addFields("id_horario", "id_hora_atividade");
		addValues(idHorario, idHoraAtividade);
		delete("horario_hora_atividade");
		return true;
	}
	
	private class HorarioHoraAtividadeMapper implements RowMapper<HorarioHoraAtividade> {

		@Override
		public HorarioHoraAtividade mapRow(ResultSet rs, int rowNum) throws SQLException {
			return HorarioHoraAtividade.builder()
					.idHorario(rs.getLong("id_horario"))
					.idHoraAtividade(rs.getLong("id_hora_atividade"))
					.build();
		}

	}

	@Override
	public int contarPorIdHorarioEHorAtividade(FiltroHorarioHoraAtividade filtroHorarioHoraAtividade) {
		StringBuilder query = createQuery("select count(h.id_hora_atividade) from horario_hora_atividade h where 1=1 ");
		query = andEqual(query, "h.id_horario", filtroHorarioHoraAtividade.getIdHorario());
		query = andEqual(query, "h.id_hora_atividade", filtroHorarioHoraAtividade.getIdHoraAtividade());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}


}
