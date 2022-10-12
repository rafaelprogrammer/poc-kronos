package br.com.kronos.backend.adaptadores.repositorio.horario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.horario.HoraAtividade;
import br.com.kronos.backend.aplicacao.horario.HoraAtividadeRepositorio;
import br.com.kronos.backend.aplicacao.horario.FiltroHoraAtividade;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HoraAtividadeRepositorioImpl extends SqlQueryRepositorio implements HoraAtividadeRepositorio {
	
	public HoraAtividadeRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(HoraAtividade horaAtividade) {
        addFields("hora_inicial", "hora_final", "tempo_carga_horaria", "tempo_trabalho_computado", 
                  "id_tipo_turno", "id_instituicao");
		
		addValues(horaAtividade.getHoraInicial(), horaAtividade.getHoraFinal(), horaAtividade.getTempoCargaHoraria(),horaAtividade.getTempoTrabalhoComputado(), 
                  horaAtividade.getIdTipoTurno(), horaAtividade.getIdInstituicao());
		
		return (long) insertAuto("hora_atividade"); 
    }
		
	@Override
	public Long alterar(HoraAtividade horaAtividade) {
		addFields("hora_inicial", "hora_final", "tempo_carga_horaria", "tempo_trabalho_computado", 
                "id_tipo_turno", "id_instituicao");
		
		addValues(horaAtividade.getHoraInicial(), horaAtividade.getHoraFinal(), horaAtividade.getTempoCargaHoraria(),horaAtividade.getTempoTrabalhoComputado(), 
                horaAtividade.getIdTipoTurno(), horaAtividade.getIdInstituicao(), horaAtividade.getId());
		
		return (long) update("hora_atividade", horaAtividade.getId());
	}
	
	@Override
	public HoraAtividade buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select h.hora_inicial, h.hora_final, h.tempo_carga_horaria, h.tempo_trabalho_computado, ");
            query.append("h.id_tipo_turno, h.id_instituicao ");
            query.append("from hora_atividade h where 1=1 ");
            query = andEqual(query, "h.id", id);
			HoraAtividade horaAtividade = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new HoraAtividadeMapper());
			return horaAtividade;
		} catch (EmptyResultDataAccessException e) {
			log.info("Hora atividade não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<HoraAtividade> listar(FiltroHoraAtividade filtroHoraAtividade) {
		StringBuilder query = createQuery("select h.hora_inicial, h.hora_final, h.tempo_carga_horaria, h.tempo_trabalho_computado, ");
        query.append("h.id_tipo_turno, h.id_instituicao ");
        query.append("from hora_atividade h where 1=1 ");
        query = andEqual(query, "h.id", filtroHoraAtividade.getId());
		query = andEqual(query, "h.hora_inicial", filtroHoraAtividade.getHoraInicial()); 
        query = andEqual(query, "h.id_tipo_turno", filtroHoraAtividade.getIdTipoTurno());
        query = andEqual(query, "h.id_instituicao", filtroHoraAtividade.getIdInstituicao());
        query = orderBy(query, Order.ASC, "h.hora_inicial");
		query = limit(query, filtroHoraAtividade.getQtdTotal(), filtroHoraAtividade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new HoraAtividadeMapper());
	}
	
	@Override
	public int contar(FiltroHoraAtividade filtroHoraAtividade) {
		StringBuilder query = createQuery("select count(h.id) from hora_atividade h where 1=1 ");
		query = andEqual(query, "c.id", filtroHoraAtividade.getId());
		query = andEqual(query, "h.id", filtroHoraAtividade.getId());
		query = andEqual(query, "h.hora_inicial", filtroHoraAtividade.getHoraInicial()); 
        query = andEqual(query, "h.id_tipo_turno", filtroHoraAtividade.getIdTipoTurno());
        query = andEqual(query, "h.id_instituicao", filtroHoraAtividade.getIdInstituicao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("hora_atividade", id);
		return true;
	}
	
	private class HoraAtividadeMapper implements RowMapper<HoraAtividade> {

		@Override
		public HoraAtividade mapRow(ResultSet rs, int rowNum) throws SQLException {
			return HoraAtividade.builder()
					.id(rs.getLong("id"))
					.horaInicial(rs.getTime("hora_inicial").toLocalTime())
                    .horaFinal(rs.getTime("hora_final").toLocalTime())
                    .tempoCargaHoraria(rs.getInt("tempo_carga_horaria"))
                    .tempoTrabalhoComputado(rs.getInt("tempo_trabalho_computado"))
                    .idTipoTurno(rs.getInt("id_tipo_turno"))
                    .idInstituicao(rs.getLong("id_instituicao"))
					.build();
		}
	}
}
