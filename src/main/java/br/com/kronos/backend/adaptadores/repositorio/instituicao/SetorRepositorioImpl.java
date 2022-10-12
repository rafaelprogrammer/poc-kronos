package br.com.kronos.backend.adaptadores.repositorio.instituicao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroSetor;
import br.com.kronos.backend.aplicacao.instituicao.Setor;
import br.com.kronos.backend.aplicacao.instituicao.SetorRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SetorRepositorioImpl extends SqlQueryRepositorio implements SetorRepositorio {
	
	public SetorRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Setor setor) {
        addFields("nome", "ativo" , "id_instituicao" );
		
		addValues(setor.getNome(), setor.isAtivo(), setor.getIdInstitucicao()); 
		
		return (long) insertAuto("setor"); 
    }
	
	@Override
	public Long alterar(Setor setor) {
		addFields("nome", "ativo" , "id_instituicao" );
		
		addValues(setor.getNome(), setor.isAtivo(), setor.getIdInstitucicao(), setor.getId());
		
		return (long) update("setor", setor.getId());
	}
		
	@Override
	public Setor buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select s.nome, s.ativo, s.id_instituicao ");
            query.append("from setor s where 1=1 ");
            query = andEqual(query, "s.id", id);
			Setor setor = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new SetorMapper());
			return setor;
		} catch (EmptyResultDataAccessException e) {
			log.info("setor não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Setor> listar(FiltroSetor filtroSetor) {
		  StringBuilder query = createQuery("select s.nome, s.ativo, s.id_instituicao ");
          query.append("from setor s where 1=1 ");
          query = andEqual(query, "s.id", filtroSetor.getId());
	   	  query = andEqual(query, "s.nome", filtroSetor.getNome());
	   	  query = andEqual(query, "s.id_instituicao", filtroSetor.getIdInstituicao());
          query = orderBy(query, Order.ASC, "s.nome");
		  query = limit(query, filtroSetor.getQtdTotal(), filtroSetor.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new SetorMapper());
	}
	
	@Override
	public int contar(FiltroSetor filtroSetor) {
		  StringBuilder query = createQuery("select count(s.id) from setor s where 1=1 ");
		  query = andEqual(query, "s.id", filtroSetor.getId());
	   	  query = andEqual(query, "s.nome", filtroSetor.getNome());
	   	  query = andEqual(query, "s.id_instituicao", filtroSetor.getIdInstituicao());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("setor", id);
		return true;
	}
	
	private class SetorMapper implements RowMapper<Setor> {

		@Override
		public Setor mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Setor.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .ativo(rs.getBoolean("ativo"))
                    .idInstitucicao(rs.getLong("id_instituicao"))
					.build();
		}
	}
}

