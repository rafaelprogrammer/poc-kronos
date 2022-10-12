package br.com.kronos.backend.adaptadores.repositorio.basecurricular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroComponente;
import br.com.kronos.backend.aplicacao.basecurricular.api.ComponenteDTO;
import br.com.kronos.backend.aplicacao.basecurricular.Componente;
import br.com.kronos.backend.aplicacao.basecurricular.ComponenteRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComponenteRepositorioImpl extends SqlQueryRepositorio implements ComponenteRepositorio {
	
	public ComponenteRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Componente componente) {
        addFields("nome", "id_nivel", "bncc", "ativo", "id_instituicao" );
		
		addValues(componente.getNome(), componente.getIdNivel(), componente.getBncc(), componente.isAtivo(), componente.getIdInstituicao()); 
		
		return (long) insertAuto("componente"); 
    }
	
	@Override
	public Long alterar(Componente componente) {
		addFields("nome", "id_nivel", "bncc", "ativo", "id_instituicao");
		
		addValues(componente.getNome(), componente.getIdNivel(), componente.getBncc(), componente.isAtivo(), componente.getIdInstituicao(), componente.getId());
		
		return (long) update("componente", componente.getId());
	}
		
	@Override
	public ComponenteDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select c.id, c.nome, c.id_nivel, n.nome as nome_nivel, c.bncc, c.ativo ");
	        query.append("from componente c ");
	        query.append("join nivel n on (c.id_nivel = n.id) where 1=1 ");
            query = andEqual(query, "c.id", id);
            ComponenteDTO componente = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ComponenteDTOMapper());
			return componente;
		} catch (EmptyResultDataAccessException e) {
			log.info("componente não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<ComponenteDTO> listar(FiltroComponente filtroComponente) {
	  StringBuilder query = createQuery("select c.id, c.nome, c.id_nivel, n.nome as nome_nivel, c.bncc, c.ativo ");
      query.append("from componente c ");
      query.append("join nivel n on (c.id_nivel = n.id) where 1=1 ");
      query.append("and (c.bncc = true ");
      query = or(query, "c.id_instituicao", filtroComponente.getIdInstituicao(), "=");
      query.append(") ");
      query = andEqual(query, "c.id", filtroComponente.getId());
      query = andLike(query, "c.nome", filtroComponente.getNome());
   	  query = andEqual(query, "c.id_nivel", filtroComponente.getIdNivel());
   	  query = andEqual(query, "c.bncc", filtroComponente.getBncc());
   	  query = andEqual(query, "c.ativo", filtroComponente.getAtivo());
      query = orderBy(query, Order.ASC, "c.nome");
	  query = limit(query, filtroComponente.getQtdTotal(), filtroComponente.getNumeroPagina());
	  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ComponenteDTOMapper());
	}
	
	@Override
	public int contar(FiltroComponente filtroComponente) {
	  StringBuilder query = createQuery("select count(c.id) ");
	  query.append("from componente c ");
      query.append("join nivel n on (c.id_nivel = n.id) where 1=1 ");
      query.append("and (c.bncc = true ");
      query = or(query, "c.id_instituicao", filtroComponente.getIdInstituicao(), "=");
      query.append(") ");
      query = andEqual(query, "c.id", filtroComponente.getId());
      query = andLike(query, "c.nome", filtroComponente.getNome());
   	  query = andEqual(query, "c.id_nivel", filtroComponente.getIdNivel());
   	  query = andEqual(query, "c.bncc", filtroComponente.getBncc());
   	  query = andEqual(query, "c.ativo", filtroComponente.getAtivo());
      return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("componente", id);
		return true;
	}
	
	private class ComponenteDTOMapper implements RowMapper<ComponenteDTO> {

		@Override
		public ComponenteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ComponenteDTO.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .idNivel(rs.getLong("id_nivel"))
                    .nivel(rs.getString("nome_nivel"))
                    .ativo(rs.getBoolean("ativo"))
                    .bncc(rs.getBoolean("bncc"))
					.build();
		}
	}
}


