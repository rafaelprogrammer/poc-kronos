package br.com.kronos.backend.adaptadores.repositorio.instituicao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroLimite;
import br.com.kronos.backend.aplicacao.instituicao.Limite;
import br.com.kronos.backend.aplicacao.instituicao.LimiteRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LimiteRepositorioImpl extends SqlQueryRepositorio implements LimiteRepositorio {
	
	public LimiteRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Limite limite) {
        addFields("id_escala", "id_tipo_mencao", "maximo", "minimo" );
		
		addValues(limite.getIdEscala(), limite.getIdTipoMencao(), limite.getMaximo(), limite.getMinimo()); 
		
		return (long) insertAuto("limite"); 
    }
	
	@Override
	public Long alterar(Limite limite) {
        addFields("id_escala", "id_tipo_mencao", "maximo", "minimo" );
		
		addValues(limite.getIdEscala(), limite.getIdTipoMencao(), limite.getMaximo(), limite.getMinimo(), limite.getId());
		
		return (long) update("limite", limite.getId());
	}
		
	@Override
	public Limite buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select l.id_escala, l.id_tipo_mencao, l.maximo, l.minimo ");
            query.append("from limite l where 1=1 ");
            query = andEqual(query, "l.id", id);
			Limite limite = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new LimiteMapper());
			return limite;
		} catch (EmptyResultDataAccessException e) {
			log.info("limite não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Limite> listar(FiltroLimite filtroLimite) {
		  StringBuilder query = createQuery("select l.id_escala, l.id_tipo_mencao, l.maximo, l.minimo ");
          query.append("from limite l where 1=1 ");
          query = andEqual(query, "l.id", filtroLimite.getId());
	   	  query = andEqual(query, "l.id_escala", filtroLimite.getIdEscala());
          query = orderBy(query, Order.DESC, "l.minimo");
		  query = limit(query, filtroLimite.getQtdTotal(), filtroLimite.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new LimiteMapper());
	}
	
	@Override
	public int contar(FiltroLimite filtroLimite) {
		StringBuilder query = createQuery("select count(l.id) from limite l where 1=1 ");
		query = andEqual(query, "l.id", filtroLimite.getId());
		query = andEqual(query, "l.id_escala", filtroLimite.getIdEscala());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("limite", id);
		return true;
	}
	
	private class LimiteMapper implements RowMapper<Limite> {

		@Override
		public Limite mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Limite.builder()
					.id(rs.getLong("id"))
                    .idEscala(rs.getLong("id_escala"))
                    .idTipoMencao(rs.getInt("id_tipo_mencao"))
                    .minimo(rs.getDouble("minimo"))
                    .maximo(rs.getDouble("maximo"))
					.build();
		}
	}
}
