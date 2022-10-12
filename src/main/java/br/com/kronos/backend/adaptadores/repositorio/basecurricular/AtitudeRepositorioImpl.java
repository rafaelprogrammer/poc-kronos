package br.com.kronos.backend.adaptadores.repositorio.basecurricular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.Atitude;
import br.com.kronos.backend.aplicacao.basecurricular.AtitudeRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroAtitude;
import br.com.kronos.backend.aplicacao.basecurricular.api.AtitudeDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtitudeRepositorioImpl extends SqlQueryRepositorio implements AtitudeRepositorio {
	
	public AtitudeRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Atitude atitude) {
        addFields("nome", "id_valor" , "id_instituicao", "ativa", "codigo" );
		
		addValues(atitude.getNome(), atitude.getIdValor(), atitude.getIdInstituicao(), atitude.isAtivo(), atitude.getCodigo()); 
		
		return (long) insertAuto("atitude"); 
    }

	@Override
	public Long alterar(Atitude atitude) {
		addFields("nome", "id_valor" , "id_instituicao", "ativa", "codigo" );
		
		addValues(atitude.getNome(), atitude.getIdValor(), atitude.getIdInstituicao(), atitude.isAtivo(), atitude.getCodigo(), atitude.getId());
		
		return (long) update("atitude", atitude.getId());
	}
		
	@Override
	public AtitudeDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select a.id_instituicao, a.id_valor, a.id, a.codigo, v.nome as valor, a.nome, a.ativa ");
	        query.append("from atitude a ");
	        query.append("left join valor v on (a.id_valor = v.id) where 1=1 ");
            query = andEqual(query, "a.id", id);
            AtitudeDTO atitude = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new AtitudeDTOMapper());
			return atitude;
		} catch (EmptyResultDataAccessException e) {
			log.info("atitude não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<AtitudeDTO> listar(FiltroAtitude filtroAtitude) {
		StringBuilder query = createQuery("select a.id_instituicao, a.id_valor, a.id, a.codigo, v.nome as valor, a.nome, a.ativa ");
        query.append("from atitude a ");
        query.append("left join valor v on (a.id_valor = v.id) where 1=1 ");
        query = andEqual(query, "a.id", filtroAtitude.getId());
	   	query = andLike(query, "a.nome", filtroAtitude.getNome());
	   	query = andEqual(query, "a.codigo", filtroAtitude.getCodigo());
		query = andEqual(query, "a.id_instituicao", filtroAtitude.getIdInstituicao());
		query = andEqual(query, "a.id_valor", filtroAtitude.getIdValor());
		query = andEqual(query, "a.ativa", filtroAtitude.getAtivo());  
        query = orderBy(query, Order.ASC, "a.nome");
		query = limit(query, filtroAtitude.getQtdTotal(), filtroAtitude.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AtitudeDTOMapper());
	}
	
	@Override
	public int contar(FiltroAtitude filtroAtitude) {
	    StringBuilder query = createQuery("select count(a.id) ");
	    query.append("from atitude a ");
        query.append("left join valor v on (a.id_valor = v.id) where 1=1 ");
	    query = andEqual(query, "a.id", filtroAtitude.getId());
	   	query = andLike(query, "a.nome", filtroAtitude.getNome());
	   	query = andEqual(query, "a.codigo", filtroAtitude.getCodigo());
		query = andEqual(query, "a.id_instituicao", filtroAtitude.getIdInstituicao());
		query = andEqual(query, "a.id_valor", filtroAtitude.getIdValor());
		query = andEqual(query, "a.ativa", filtroAtitude.getAtivo());  
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("atitude", id);
		return true;
	}
	
	private class AtitudeDTOMapper implements RowMapper<AtitudeDTO> {

		@Override
		public AtitudeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AtitudeDTO.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .idValor(rs.getLong("id_valor"))
                    .valor(rs.getString("valor"))
                    .idInstituicao(rs.getLong("id_instituicao"))
                    .ativo(rs.getBoolean("ativa"))
                    .codigo(rs.getString("codigo"))
					.build();
		}
	}
}

