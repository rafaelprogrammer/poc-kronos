package br.com.kronos.backend.adaptadores.repositorio.matricula;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.matricula.FiltroHistorico;
import br.com.kronos.backend.aplicacao.matricula.Historico;
import br.com.kronos.backend.aplicacao.matricula.HistoricoRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HistoricoRepositorioImpl extends SqlQueryRepositorio implements HistoricoRepositorio {
	
	public HistoricoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Historico historico) {
        addFields("id_matricula", "id_empresa", "id_credito", "ano", 
                  "nota", "mencao", "disciplina",  "periodo");
		
		addValues(historico.getIdMatricula(), historico.getIdEmpresa(), historico.getIdCredito(), historico.getAno(), 
                  historico.getNota(), historico.getMencao(), historico.getDisciplina(), historico.getPeriodo()); 
		
		return (long) insertAuto("historico"); 
    }
	
	@Override
	public Long alterar(Historico historico) {
		addFields("id_matricula", "id_empresa", "id_credito", "ano", 
                "nota", "mencao", "disciplina",  "periodo");
		
		addValues(historico.getIdMatricula(), historico.getIdEmpresa(), historico.getIdCredito(), historico.getAno(), 
                historico.getNota(), historico.getMencao(), historico.getDisciplina(), historico.getPeriodo(),
                historico.getId());
		
		return (long) update("historico", historico.getId());
	}
	
	@Override
	public Historico buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select h.id_matricula, h.id_empresa, h.id_credito, h.ano, ");
            query.append("h.nota, h.mencao, h.disciplina,  h.periodo ");
            query.append("from historico h where 1=1 ");
            query = andEqual(query, "h.id", id);
			Historico historico = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new HistoricoMapper());
			return historico;
		} catch (EmptyResultDataAccessException e) {
			log.info("historico não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Historico> listar(FiltroHistorico filtroHistorico) {
		StringBuilder query = createQuery("select h.id_matricula, h.id_empresa, h.id_credito, h.ano, ");
        query.append("h.nota, h.mencao, h.disciplina,  h.periodo ");
        query.append("from historico h where 1=1 "); 
		query = andEqual(query, "h.id", filtroHistorico.getId());
		query = andEqual(query, "h.idMatricula", filtroHistorico.getIdMatricula());
        query = andEqual(query, "h.ano", filtroHistorico.getAno());
        query = orderBy(query, Order.ASC, "h.ano");       
		query = limit(query, filtroHistorico.getQtdTotal(), filtroHistorico.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new HistoricoMapper());
	}
	
	@Override
	public int contar(FiltroHistorico filtroHistorico) {
		StringBuilder query = createQuery("select count(c.id) from historico c where 1=1 ");
		query = andEqual(query, "h.id", filtroHistorico.getId());
		query = andEqual(query, "h.idMatricula", filtroHistorico.getIdMatricula());
        query = andEqual(query, "h.ano", filtroHistorico.getAno());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("historico", id);
		return true;
	}
	
	private class HistoricoMapper implements RowMapper<Historico> {

		@Override
		public Historico mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Historico.builder()
					.id(rs.getLong("id"))
					.idMatricula(rs.getLong("id_matricula"))
					.idEmpresa(rs.getLong("id_empresa"))
					.idCredito(rs.getLong("id_credito"))
					.ano(rs.getInt("ano"))
					.nota(rs.getDouble("nota"))
					.mencao(rs.getString("mencao"))
					.disciplina(rs.getString("disciplina"))
					.disciplina(rs.getString("disciplina"))
					.build();
		}

	}

}

