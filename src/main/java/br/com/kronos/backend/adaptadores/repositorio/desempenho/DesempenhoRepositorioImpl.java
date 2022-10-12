package br.com.kronos.backend.adaptadores.repositorio.desempenho;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.desempenho.Desempenho;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAtitude;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAvaliacao;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoRepositorio;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenho;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAtitude;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAvaliacao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DesempenhoRepositorioImpl extends SqlQueryRepositorio implements DesempenhoRepositorio {
	
	public DesempenhoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Desempenho desempenho) {
        addFields("id_sub_fase_execucao", "id_credito", "parecer", "consideracoes", 
                  "data_conselho", "data_avaliacao", "data_consideracoes" );
		
		addValues(desempenho.getIdSubFaseExecucao(), desempenho.getIdCredito(), desempenho.getParecer(), desempenho.getConsideracoes(), 
                  desempenho.getDataParecer(), desempenho.getDataAvaliacao(), desempenho.getDataConsideracoes());
		
		return (long) insertAuto("desempenho"); 
    }
	
	@Override
	public Long alterar(Desempenho desempenho) {
		addFields("id_sub_fase_execucao", "id_credito", "parecer", "consideracoes", 
                "data_conselho", "data_avaliacao", "data_consideracoes" );
		
		addValues(desempenho.getIdSubFaseExecucao(), desempenho.getIdCredito(), desempenho.getParecer(), desempenho.getConsideracoes(), 
                desempenho.getDataParecer(), desempenho.getDataAvaliacao(), desempenho.getDataConsideracoes(), desempenho.getId());
		
		return (long) update("desempenho", desempenho.getId());
	}
	
	@Override
	public Desempenho buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select d.id_sub_fase_execucao, d.id_credito, d.parecer, d.consideracoes, ");
            query.append("d.data_conselho, d.data_avaliacao, d.data_consideracoes ");
            query.append("from desempenho d where 1=1 ");
            query = andEqual(query, "d.id", id);
			Desempenho desempenho = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new DesempenhoMapper());
			return desempenho;
		} catch (EmptyResultDataAccessException e) {
			log.info("desempenho não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Desempenho> listar(FiltroDesempenho filtroDesempenho) {
		StringBuilder query = createQuery("select d.id_sub_fase_execucao, d.id_credito, d.parecer, d.consideracoes, ");
        query.append("d.data_conselho, d.data_avaliacao, d.data_consideracoes ");
        query.append("from desempenho d where 1=1 ");
        query = andEqual(query, "d.id", filtroDesempenho.getId());
		query = andEqual(query, "d.id_sub_fase_execucao", filtroDesempenho.getIdSubFaseExecucao());
        query = andEqual(query, "d.id_credito", filtroDesempenho.getIdCredito());    
        query = orderBy(query, Order.ASC, "d.data_conselho");
		query = limit(query, filtroDesempenho.getQtdTotal(), filtroDesempenho.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DesempenhoMapper());
	}
	
	@Override
	public int contar(FiltroDesempenho filtroDesempenho) {
		StringBuilder query = createQuery("select count(d.id) from desempenho d where 1=1 ");
		query = andEqual(query, "d.id", filtroDesempenho.getId());
		query = andEqual(query, "d.id_sub_fase_execucao", filtroDesempenho.getIdSubFaseExecucao());
	    query = andEqual(query, "d.id_credito", filtroDesempenho.getIdCredito());  
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("desempenho", id);
		return true;
	}
	
	@Override
	public void atribuirAtitudeDesempenho(DesempenhoAtitude desempenhoAtitude){
		addFields("id_desempenho", "id_atitude", "nota", "id_mencao" );
		
		addValues(desempenhoAtitude.getIdDesempenho(), desempenhoAtitude.getIdAtitude(), desempenhoAtitude.getNota(), desempenhoAtitude.getIdMencao() ); 
		
		insert("desempenho_atitude"); 
	}

	@Override
	public void excluirAtitudeDesempenho(DesempenhoAtitude desempenhoAtitude) {
		addFields("id_desempenho", "id_atitude");
		
		addValues(desempenhoAtitude.getIdDesempenho(), desempenhoAtitude.getIdAtitude());
		
		delete("desempenho_atitude");
	}
	
	@Override
	public boolean verificarVinculoDesempenhoAtitude(FiltroDesempenhoAtitude filtroDesempenhoAtitude) {
		StringBuilder query = createQuery("select count(d.id_atitude) from desempenho_atitude d where 1=1 ");
		query = andEqual(query, "c.id_desempenho", filtroDesempenhoAtitude.getIdDesempenho());
		query = andEqual(query, "c.id_atitude", filtroDesempenhoAtitude.getIdAtitude());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class) > 0;
	}
	
	@Override
	public void atribuirAvaliacaoDesempenho(DesempenhoAvaliacao desempenhoAvaliacao){
		addFields("id_desempenho", "id_avaliacao" );
		
		addValues(desempenhoAvaliacao.getIdDesempenho(), desempenhoAvaliacao.getIdAvaliacao() ); 
		
		insert("desempenho_avaliacao"); 
	}

	@Override
	public void excluirAvaliacaoDesempenho(DesempenhoAvaliacao desempenhoAvaliacao) {
		addFields("id_desempenho", "id_avaliacao");
		
		addValues(desempenhoAvaliacao.getIdDesempenho(), desempenhoAvaliacao.getIdAvaliacao());
		
		delete("desempenho_avaliacao");
	}
	
	@Override
	public boolean verificarVinculoDesempenhoAvaliacao(FiltroDesempenhoAvaliacao filtroDesempenhoAvaliacao) {
		StringBuilder query = createQuery("select count(d.id_avaliacao) from desempenho_avaliacao d where 1=1 ");
		query = andEqual(query, "c.id_desempenho", filtroDesempenhoAvaliacao.getIdDesempenho());
		query = andEqual(query, "c.id_avaliacao", filtroDesempenhoAvaliacao.getIdAvaliacao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class) > 0;
	}

	
	private class DesempenhoMapper implements RowMapper<Desempenho> {

		@Override
		public Desempenho mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Desempenho.builder()
					.id(rs.getLong("id"))
                    .idSubFaseExecucao(rs.getLong("id_sub_fase_execucao"))
                    .idCredito(rs.getLong("id_credito"))
			//		.parecer(rs.getString("parecer"))              //tipo StringBuilder
            //      .consideracoes(rs.getString("consideracoes"))  //tipo StringBuilder
                    .dataParecer(rs.getDate("data_conselho") != null ? rs.getDate("data_conselho").toLocalDate() : null)
                    .dataAvaliacao(rs.getDate("data_avaliacao") != null ? rs.getDate("data_avaliacao").toLocalDate() : null)
                    .dataConsideracoes(rs.getDate("data_consideracoes") != null ? rs.getDate("data_consideracoes").toLocalDate() : null)
					.build();
		}
	}

}

