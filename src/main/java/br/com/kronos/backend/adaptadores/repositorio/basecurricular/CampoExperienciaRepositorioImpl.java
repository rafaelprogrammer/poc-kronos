package br.com.kronos.backend.adaptadores.repositorio.basecurricular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroCampoExperiencia;
import br.com.kronos.backend.aplicacao.basecurricular.api.CampoExperienciaDTO;
import br.com.kronos.backend.aplicacao.basecurricular.CampoExperiencia;
import br.com.kronos.backend.aplicacao.basecurricular.CampoExperienciaRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CampoExperienciaRepositorioImpl extends SqlQueryRepositorio implements CampoExperienciaRepositorio {
	
	public CampoExperienciaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(CampoExperiencia campoExperiencia) {
        addFields("nome", "id_nivel" );
		
		addValues(campoExperiencia.getNome(), campoExperiencia.getIdNivel()); 
		
		return (long) insertAuto("campo_experiencia"); 
    }
		
	@Override
	public Long alterar(CampoExperiencia campoExperiencia) {
		addFields("nome", "id_nivel" );
		
		addValues(campoExperiencia.getNome(), campoExperiencia.getIdNivel(), campoExperiencia.getId());
		
		return (long) update("campo_experiencia", campoExperiencia.getId());
	}
		
	@Override
	public CampoExperienciaDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select c.id, c.nome, c.id_nivel, n.nome as nome_nivel ");
	        query.append("from campo_experiencia c ");
	        query.append("join nivel n on (c.id_nivel = n.id) where 1=1 ");
            query = andEqual(query, "c.id", id);
            CampoExperienciaDTO campoExperiencia = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new CampoExperienciaDTOMapper());
			return campoExperiencia;
		} catch (EmptyResultDataAccessException e) {
			log.info("campoExperiencia não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<CampoExperienciaDTO> listar(FiltroCampoExperiencia filtroCampoExperiencia) {
		StringBuilder query = createQuery("select c.id, c.nome, c.id_nivel, n.nome as nome_nivel ");
        query.append("from campo_experiencia c ");
        query.append("join nivel n on (c.id_nivel = n.id) where 1=1 ");
        query = andEqual(query, "c.id", filtroCampoExperiencia.getId());
	   	query = andEqual(query, "c.id_nivel", filtroCampoExperiencia.getIdNivel());
        query = orderBy(query, Order.ASC, "c.nome");
		query = limit(query, filtroCampoExperiencia.getQtdTotal(), filtroCampoExperiencia.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CampoExperienciaDTOMapper());
	}
	
	@Override
	public int contar(FiltroCampoExperiencia filtroCampoExperiencia) {
		  StringBuilder query = createQuery("select count(c.id) ");
		  query.append("from campo_experiencia c ");
	      query.append("join nivel n on (c.id_nivel = n.id) where 1=1 ");
		  query = andEqual(query, "c.id", filtroCampoExperiencia.getId());
		  query = andEqual(query, "c.id_nivel", filtroCampoExperiencia.getIdNivel());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("campo_experiencia", id);
		return true;
	}
	
	private class CampoExperienciaDTOMapper implements RowMapper<CampoExperienciaDTO> {

		@Override
		public CampoExperienciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CampoExperienciaDTO.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .idNivel(rs.getLong("id_nivel"))
                    .nivel(rs.getString("nome_nivel"))
					.build();
		}
	}
}