package br.com.kronos.backend.adaptadores.repositorio.resultado;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.resultado.ResultadoHabilidade;
import br.com.kronos.backend.aplicacao.resultado.ResultadoHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.resultado.FiltroResultadoHabilidade;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultadoHabilidadeRepositorioImpl extends SqlQueryRepositorio implements ResultadoHabilidadeRepositorio {
	
	public ResultadoHabilidadeRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(ResultadoHabilidade resultadoHabilidade) {
        addFields("nota", "descarte", "motivo_descarte", "id_avaliacao_habilidade",
        		  "id_avaliado", "id_mencao" );
		
		addValues(resultadoHabilidade.getNota(), resultadoHabilidade.isDescarte(), resultadoHabilidade.getMotivoDescarte(), resultadoHabilidade.getIdAvaliacaoHabilidade(),
				resultadoHabilidade.getIdAvaliado(), resultadoHabilidade.getIdMencao());
		
		return (long) insertAuto("resultado_habilidade"); 
    }

	@Override
	public Long alterar(ResultadoHabilidade resultadoHabilidade) {
		addFields("nota", "descarte", "motivo_descarte", "id_avaliacao_habilidade",
      		  "id_avaliado", "id_mencao" );
		
		addValues(resultadoHabilidade.getNota(), resultadoHabilidade.isDescarte(), resultadoHabilidade.getMotivoDescarte(), resultadoHabilidade.getIdAvaliacaoHabilidade(),
				resultadoHabilidade.getIdAvaliado(), resultadoHabilidade.getIdMencao(), resultadoHabilidade.getId());
		
		return (long) update("resultado_habilidade", resultadoHabilidade.getId());
	}
	
	@Override
	public ResultadoHabilidade buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select r.id, r.nota, r.descarte, r.motivo_descarte, r.id_avaliacao_habilidade ");
            query.append("r.id_avaliado, r.id_mencao ");
            query.append("from resultado_habilidade r where 1=1 ");
            query = andEqual(query, "r.id", id);
			ResultadoHabilidade resultadoHabilidade = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ResultadoHabilidadeMapper());
			return resultadoHabilidade;
		} catch (EmptyResultDataAccessException e) {
			log.info("Resultado da habilidade não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<ResultadoHabilidade> listar(FiltroResultadoHabilidade filtroResultadoHabilidade) {
		StringBuilder query = createQuery("select r.id, r.nota, r.descarte, r.motivo_descarte, r.id_avaliacao_habilidade ");
        query.append("r.id_avaliado, r.id_mencao ");
        query.append("from resultado_habilidade r where 1=1 ");
        query = andEqual(query, "r.id", filtroResultadoHabilidade.getId());  
        query = andEqual(query, "r.id_avaliado", filtroResultadoHabilidade.getIdAvaliado());
        query = andEqual(query, "r.id_avaliacao_habilidade", filtroResultadoHabilidade.getIdAvaliacaoHabilidade());
        query = orderBy(query, Order.ASC, "r.id_avaliado");
		query = limit(query, filtroResultadoHabilidade.getQtdTotal(), filtroResultadoHabilidade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ResultadoHabilidadeMapper());
	}
	
	@Override
	public int contar(FiltroResultadoHabilidade filtroResultadoHabilidade) {
		 StringBuilder query = createQuery("select count(r.id) from resultado_habilidade r where 1=1 ");
		 query = andEqual(query, "r.id", filtroResultadoHabilidade.getId());  
	     query = andEqual(query, "r.id_avaliado", filtroResultadoHabilidade.getIdAvaliado());
	     query = andEqual(query, "r.id_avaliacao_habilidade", filtroResultadoHabilidade.getIdAvaliacaoHabilidade());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("resultado_habilidade", id);
		return true;
	}
	
	@Override
	public boolean excluirPorAvaliadoEAvaliacaoHabilidade(Long idAvaliado, Long idAvaliacaoHabilidade) {
		addFields( "id_avaliado", "id_avaliacao_habilidade");
		addValues(idAvaliado, idAvaliacaoHabilidade);
		delete("resultado_habilidade");
		return true;
	}
	
	private class ResultadoHabilidadeMapper implements RowMapper<ResultadoHabilidade> {

		@Override
		public ResultadoHabilidade mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ResultadoHabilidade.builder()
					.id(rs.getLong("id"))
					.nota(rs.getDouble("nota"))
                    .descarte(rs.getBoolean("descarte"))
                    .motivoDescarte(rs.getString("motivo_descarte"))
                    .idAvaliacaoHabilidade(rs.getLong("id_avaliacao_habilidade"))
                    .idAvaliado(rs.getLong("id_avaliado"))
                    .idMencao(rs.getLong("id_mencao"))
					.build();
		}
	}
	
}
