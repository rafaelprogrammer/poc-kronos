package br.com.kronos.backend.adaptadores.repositorio.calendario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.CategoriaEvento;
import br.com.kronos.backend.aplicacao.calendario.Evento;
import br.com.kronos.backend.aplicacao.calendario.EventoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroEvento;
import br.com.kronos.backend.aplicacao.calendario.api.EventoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventoRepositorioImpl extends SqlQueryRepositorio implements EventoRepositorio {
	
	public EventoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Evento evento) {
		
        addFields("data_hora_inicio", "data_hora_final", "dia_letivo", "descricao",
        		  "id_categoria_evento", "id_calendario" );
		

		addValues(evento.getDataHoraInicio(), evento.getDataHoraFinal(), evento.isDiaLetivo(), evento.getDescricao(),
				  evento.getIdCategoriaEvento(), evento.getIdCalendario());
		
		return (long) insertAuto("evento"); 
    }
		
	@Override
	public Long alterar(Evento evento) {
		addFields("data_hora_inicio", "data_hora_final", "dia_letivo", "descricao",
				"id_categoria_evento", "id_calendario");
		
		addValues(evento.getDataHoraInicio(), evento.getDataHoraFinal(), evento.isDiaLetivo(), evento.getDescricao(),
				evento.getIdCategoriaEvento(), evento.getIdCalendario(), evento.getId());
		
		return (long) update("evento", evento.getId());
	}
	
	@Override
	public EventoDTO buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select e.id, e.data_hora_inicio, e.data_hora_final, e.dia_letivo, e.descricao, ");
            query.append("e.id_categoria_evento, e.id_calendario, c.cor ");
            query.append("from evento e ");
            query.append("join categoria_evento c on (c.id = e.id_categoria_evento) where 1=1 ");
            query = andEqual(query, "e.id", id);
            EventoDTO evento = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new EventoDTOMapper());
			return evento;
		} catch (EmptyResultDataAccessException e) {
			log.info("evento não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<EventoDTO> listar(FiltroEvento filtroEvento) {

		StringBuilder query = createQuery("select e.id, e.data_hora_inicio, e.data_hora_final, e.dia_letivo, e.descricao, ");
        query.append("e.id_categoria_evento, e.id_calendario, c.cor ");
        query.append("from evento e ");
        query.append("join categoria_evento c on (c.id = e.id_categoria_evento) where 1=1 ");
        query = andEqual(query, "e.id", filtroEvento.getId());
        query = andEqual(query, "e.dia_letivo", filtroEvento.getDiaLetivo());  
        query = andEqual(query, "e.id_categoria_evento", filtroEvento.getIdCategoriaEvento());
        query = andEqual(query, "e.id_calendario", filtroEvento.getIdCalendario());
        query = orderBy(query, Order.ASC, "e.data_hora_inicio");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new EventoDTOMapper());
	}
	
	@Override
	public int contar(FiltroEvento filtroEvento) {
		StringBuilder query = createQuery("select count(e.id) from evento e where 1=1 ");
        query = andEqual(query, "e.id", filtroEvento.getId());
        query = andEqual(query, "e.dia_letivo", filtroEvento.getDiaLetivo());  
        query = andEqual(query, "e.id_categoria_evento", filtroEvento.getIdCategoriaEvento());
        query = andEqual(query, "e.id_calendario", filtroEvento.getIdCalendario());

		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("evento", id);
		return true;
	}
	
	@Override
	public boolean excluir(FiltroEvento filtro) {
		if (filtro.getIdCalendario() > 0) {
			addFields("id_calendario");
			addValues(filtro.getIdCalendario());
		}
		delete("evento");
		return true;
	}
	
	private class EventoDTOMapper implements RowMapper<EventoDTO> {

		@Override
		public EventoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return EventoDTO.builder()
					.id(rs.getLong("id"))
					.dataHoraInicio(rs.getTimestamp("data_hora_inicio").toLocalDateTime())
					.dataHoraFinal(rs.getTimestamp("data_hora_final").toLocalDateTime())
                    .diaLetivo(rs.getBoolean("dia_letivo"))
                    .descricao(rs.getString("descricao"))
                    .idCategoriaEvento(rs.getLong("id_categoria_evento"))
                    .idCalendario(rs.getLong("id_calendario"))
                    .corEvento(rs.getString("cor"))
                    .build();
		}

	}

	@Override
	public List<CategoriaEvento> listarCatetoria() {
		StringBuilder query = createQuery("select c.id, c.nome, c.cor ");
        query.append("from categoria_evento c where 1=1 ");
        query = orderBy(query, Order.ASC, "c.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CategoriaEventoMapper());
	}
	
	@Override
	public List<LocalDateTime> listarDataHoraEventosDiasNaoLetivos(Long idCalendario) {
		StringBuilder query = createQuery("select generate_series ( data_hora_inicio::date, data_hora_final::date, '1 day'::interval) as dia_nao_letivo from evento where dia_letivo = false ");
        query = andEqual(query, "id_calendario", idCalendario);
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new LocalDateTimeMapper());
	}
	
	private class LocalDateTimeMapper implements RowMapper<LocalDateTime> {

		@Override
		public LocalDateTime mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getTimestamp("dia_nao_letivo").toLocalDateTime();
		}

	}
	
	private class CategoriaEventoMapper implements RowMapper<CategoriaEvento> {

		@Override
		public CategoriaEvento mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CategoriaEvento.builder()
					.id(rs.getLong("id"))
					.nome(rs.getString("nome"))
					.cor(rs.getString("cor"))
                    .build();
		}

	}

}
