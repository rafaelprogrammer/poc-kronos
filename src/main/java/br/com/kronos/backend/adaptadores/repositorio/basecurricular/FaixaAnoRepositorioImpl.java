package br.com.kronos.backend.adaptadores.repositorio.basecurricular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FaixaAno;
import br.com.kronos.backend.aplicacao.basecurricular.FaixaAnoRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroFaixaAno;
import br.com.kronos.backend.aplicacao.basecurricular.api.FaixaAnoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FaixaAnoRepositorioImpl extends SqlQueryRepositorio implements FaixaAnoRepositorio {
	
	public FaixaAnoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	public Integer criar(FaixaAno faixa) {
        addFields("nome", "id_nivel");
		
		addValues(faixa.getNome(), faixa.getIdNivel()); 
		
		return insertAuto("faixa_ano"); 
    }
	
	@Override
	public Integer alterar(FaixaAno faixa) {
		addFields("nome", "id_nivel");
		
		addValues(faixa.getNome(), faixa.getIdNivel(), faixa.getId()); 
		
		return update("faixa_ano", faixa.getId());
	}
	
	@Override
	public FaixaAnoDTO buscarPorId(Integer id) {
		try {
            StringBuilder query = createQuery("select a.id, a.nome, a.id_nivel, n.nome as nome_nivel ");
            query.append("from faixa_ano a ");
            query.append("join nivel n on (a.id_nivel = n.id) where 1=1 ");
            query = andEqual(query, "a.id", id);
            FaixaAnoDTO faixa = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new FaixaAnoDTOMapper());
			return faixa;
		} catch (EmptyResultDataAccessException e) {
			log.info("faixa/ano não existe - " + id);
			return null;
		}
	}

	@Override
	public List<FaixaAnoDTO> listar(FiltroFaixaAno filtro) {
		StringBuilder query = createQuery("select a.id, a.nome, a.id_nivel, n.nome as nome_nivel ");
        query.append("from faixa_ano a ");
        query.append("join nivel n on (a.id_nivel = n.id) where 1=1 ");
		query = andEqual(query, "a.id", filtro.getId());
		query = andLike(query, "a.nome", filtro.getNome());
		query = andEqual(query, "a.id_nivel", filtro.getIdNivel());
		query = orderBy(query, Order.ASC, "a.nome");
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new FaixaAnoDTOMapper());
	}
	
	@Override
	public int contar(FiltroFaixaAno filtro) {
		StringBuilder query = createQuery("select count(a.id) ");
		query.append("from faixa_ano a ");
        query.append("join nivel n on (a.id_nivel = n.id) where 1=1 ");
		query = andEqual(query, "a.id", filtro.getId());
		query = andLike(query, "a.nome", filtro.getNome());
		query = andEqual(query, "a.id_nivel", filtro.getIdNivel());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Integer id) {
		delete("faixa_ano", id);
		return true;
	}
	
	private class FaixaAnoDTOMapper implements RowMapper<FaixaAnoDTO> {

		@Override
		public FaixaAnoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return FaixaAnoDTO.builder()
					.id(rs.getInt("id"))
					.nome(rs.getString("nome"))
					.idNivel(rs.getInt("id_nivel"))
					.nivel(rs.getString("nome_nivel"))
					.build();
		}

	}


}
