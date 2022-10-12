package br.com.kronos.backend.adaptadores.repositorio.basecurricular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroValor;
import br.com.kronos.backend.aplicacao.basecurricular.Valor;
import br.com.kronos.backend.aplicacao.basecurricular.ValorRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValorRepositorioImpl extends SqlQueryRepositorio implements ValorRepositorio {
	
	public ValorRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Valor valor) {
        addFields("nome", "id_instituicao", "ativo", "codigo" );
		
		addValues(valor.getNome(), valor.getIdInstituicao(), valor.isAtivo(),  valor.getCodigo() ); 
		
		return (long) insertAuto("valor"); 
    }
	
	@Override
	public Long alterar(Valor valor) {
		 addFields("nome", "id_instituicao", "ativo", "codigo" );
			
			addValues(valor.getNome(), valor.getIdInstituicao(), valor.isAtivo(),  valor.getCodigo(), valor.getId());
		
		return (long) update("valor", valor.getId());
	}
		
	@Override
	public Valor buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select v.id, v.nome, v.id_instituicao, v.ativo, v.codigo ");
            query.append("from valor v where 1=1 ");
            query = andEqual(query, "v.id", id);
			Valor valor = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ValorMapper());
			return valor;
		} catch (EmptyResultDataAccessException e) {
			log.info("valor não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Valor> listar(FiltroValor filtroValor) {
		  StringBuilder query = createQuery("select v.id, v.nome, v.id_instituicao, v.ativo, v.codigo ");
          query.append("from valor v where 1=1 ");
          query = andEqual(query, "v.id",  filtroValor.getId());          
          query = andLike(query, "v.nome", filtroValor.getNome());
	   	  query = andEqual(query, "v.codigo", filtroValor.getCodigo());
	   	  query = andEqual(query, "v.id_instituicao", filtroValor.getIdInstituicao());   
	   	  query = andEqual(query, "v.ativo", filtroValor.getAtivo());
          query = orderBy(query, Order.ASC, "v.codigo");
		  query = limit(query, filtroValor.getQtdTotal(), filtroValor.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ValorMapper());
	}
	
	@Override
	public int contar(FiltroValor filtroValor) {
		  StringBuilder query = createQuery("select count(v.id) from valor v where 1=1 ");
		  query = andEqual(query, "s.id", filtroValor.getId());
		  query = andLike(query, "v.nome", filtroValor.getNome());
	   	  query = andEqual(query, "v.codigo", filtroValor.getCodigo());
	   	  query = andEqual(query, "v.id_instituicao", filtroValor.getIdInstituicao());
	   	query = andEqual(query, "v.ativo", filtroValor.getAtivo());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("valor", id);
		return true;
	}
	
	private class ValorMapper implements RowMapper<Valor> {

		@Override
		public Valor mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Valor.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .idInstituicao(rs.getLong("id_instituicao"))
                    .ativo(rs.getBoolean("ativo"))
                    .codigo(rs.getString("codigo"))
					.build();
		}
	}
}
