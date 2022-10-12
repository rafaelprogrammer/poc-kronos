package br.com.kronos.backend.adaptadores.repositorio.matricula;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurmaSala;
import br.com.kronos.backend.aplicacao.matricula.TurmaSala;
import br.com.kronos.backend.aplicacao.matricula.TurmaSalaRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TurmaSalaRepositorioImpl extends SqlQueryRepositorio implements TurmaSalaRepositorio {
	
	public TurmaSalaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(TurmaSala turmaSala) {
        addFields("principal", "id_sala",  "id_turma");
		
        addValues(turmaSala.isPrincipal(), turmaSala.getIdSala(), turmaSala.getIdTurma()); 
		
		return (long) insertAuto("turmaSala"); 
    }
		
	@Override
	public Long alterar(TurmaSala turmaSala) {
		 addFields("principal", "id_sala",  "id_turma");
			
	        addValues(turmaSala.isPrincipal(), turmaSala.getIdSala(), turmaSala.getIdTurma(), turmaSala.getId());
		
		return (long) update("turmaSala", turmaSala.getId());
	}
	
	@Override
	public TurmaSala buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select t.principal, t.id_sala, t.id_turma ");
            query.append("from turmaSala t where 1=1 ");
            query = andEqual(query, "t.id", id);
			TurmaSala turmaSala = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new TurmaSalaMapper());
			return turmaSala;
		} catch (EmptyResultDataAccessException e) {
			log.info("turmaSala não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<TurmaSala> listar(FiltroTurmaSala filtroTurmaSala) {
		StringBuilder query = createQuery("select t.principal, t.id_sala, t.id_turma ");
        query.append("from turmaSala t where 1=1 ");
        query = andEqual(query, "t.id", filtroTurmaSala.getId());
		query = andEqual(query, "t.principal", filtroTurmaSala.isPrincipal());  
        query = andEqual(query, "t.id_sala", filtroTurmaSala.getIdSala());
        query = andEqual(query, "t.id_turma", filtroTurmaSala.getIdTurma());
        query = orderBy(query, Order.ASC, "t.id_sala");       
		query = limit(query, filtroTurmaSala.getQtdTotal(), filtroTurmaSala.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaSalaMapper());
	}
	
	@Override
	public int contar(FiltroTurmaSala filtroTurmaSala) {
		StringBuilder query = createQuery("select count(t.id) from turmaSala t where 1=1 ");
		query = andEqual(query, "t.id", filtroTurmaSala.getId());
		query = andEqual(query, "t.principal", filtroTurmaSala.isPrincipal());  
        query = andEqual(query, "t.id_sala", filtroTurmaSala.getIdSala());
        query = andEqual(query, "t.id_turma", filtroTurmaSala.getIdTurma());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("turmaSala", id);
		return true;
	}
	
	private class TurmaSalaMapper implements RowMapper<TurmaSala> {

		@Override
		public TurmaSala mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TurmaSala.builder()
					.id(rs.getLong("id"))
					.principal(rs.getBoolean("principal"))
					.idSala(rs.getLong("id_sala"))
					.idTurma(rs.getLong("id_turma"))
					.build();
		}
		
	}
}
