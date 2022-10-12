package br.com.kronos.backend.adaptadores.repositorio.resultado;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.resultado.ResultadoFase;
import br.com.kronos.backend.aplicacao.resultado.ResultadoFaseRepositorio;
import br.com.kronos.backend.aplicacao.resultado.FiltroResultadoFase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultadoFaseRepositorioImpl extends SqlQueryRepositorio implements ResultadoFaseRepositorio {
	
	public ResultadoFaseRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(ResultadoFase resultadoFase) {
        addFields("nota_final_normal", "nota_final_recuperacao", "total_ausencia", "percentual_ausencia",
        		  "id_fase_execucao", "id_credito", "id_mencao","nota_final" );
		
		addValues(resultadoFase.getNotaFinalNormal(), resultadoFase.getNotaFinalRecuperacao(), resultadoFase.getTotalAusencia(), resultadoFase.getPercentualAusencia(),
				  resultadoFase.getIdFaseExecucao(), resultadoFase.getIdCredito(), resultadoFase.getIdMencao(), resultadoFase.getNotaFinal());
		
		return (long) insertAuto("resultado_fase"); 
    }
	
	@Override
	public Long alterar(ResultadoFase resultadoFase) {
		addFields("nota_final_normal", "nota_final_recuperacao", "total_ausencia", "percentual_ausencia",
        		  "id_fase_execucao", "id_credito", "id_mencao","id_mencao" );
		
		addValues(resultadoFase.getNotaFinalNormal(), resultadoFase.getNotaFinalRecuperacao(), resultadoFase.getTotalAusencia(), resultadoFase.getPercentualAusencia(),
				  resultadoFase.getIdFaseExecucao(), resultadoFase.getIdCredito(), resultadoFase.getIdMencao(), resultadoFase.getNotaFinal(), resultadoFase.getId());
		
		return (long) update("resultado_fase", resultadoFase.getId());
	}
	
	@Override
	public ResultadoFase buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select r.nota_final_normal, r.nota_final_recuperacao, r.total_ausencia, r.percentual_ausencia, ");
            query.append("r.id_fase_execucao, r.id_credito, r.id_mencao, r.id_mencao ");
            query.append("from resultado_fase r where 1=1 ");
            query = andEqual(query, "r.id", id);
			ResultadoFase resultadoFase = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ResultadoFaseMapper());
			return resultadoFase;
		} catch (EmptyResultDataAccessException e) {
			log.info("Resultado da fase não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<ResultadoFase> listar(FiltroResultadoFase filtroResultadoFase) {
		StringBuilder query = createQuery("select r.nota_final_normal, r.nota_final_recuperacao, r.total_ausencia, r.percentual_ausencia, ");
        query.append("r.id_fase_execucao, r.id_credito, r.id_mencao, r.id_mencao ");
        query.append("from resultado_fase r where 1=1 ");
        query = andEqual(query, "r.id", filtroResultadoFase.getId());  
        query = andEqual(query, "r.id_fase_execucao", filtroResultadoFase.getIdFaseExecucao());
        query = andEqual(query, "r.id_credito", filtroResultadoFase.getIdCredito());
        query = orderBy(query, Order.ASC, "r.id_credito");
		query = limit(query, filtroResultadoFase.getQtdTotal(), filtroResultadoFase.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ResultadoFaseMapper());
	}
	
	@Override
	public int contar(FiltroResultadoFase filtroResultadoFase) {
		 StringBuilder query = createQuery("select count(r.id) from resultado_fase r where 1=1 ");
		 query = andEqual(query, "r.id", filtroResultadoFase.getId());  
	     query = andEqual(query, "r.id_fase_execucao", filtroResultadoFase.getIdFaseExecucao());
	     query = andEqual(query, "r.id_credito", filtroResultadoFase.getIdCredito());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("resultado_fase", id);
		return true;
	}
	
	private class ResultadoFaseMapper implements RowMapper<ResultadoFase> {

		@Override
		public ResultadoFase mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ResultadoFase.builder()
					.id(rs.getLong("id"))
					.notaFinalNormal(rs.getDouble("nota_final_recuperacao"))
                    .notaFinalRecuperacao(rs.getDouble("nota_final_recuperacao"))
                    .totalAusencia(rs.getInt("total_ausencia"))
                    .percentualAusencia(rs.getDouble("percentual_ausencia"))
                    .idFaseExecucao(rs.getLong("id_fase_execucao"))
                    .idCredito(rs.getLong("id_credito"))
                    .idMencao(rs.getLong("id_mencao"))
                    .notaFinal(rs.getDouble("nota_final"))
					.build();
		}
	}
}
