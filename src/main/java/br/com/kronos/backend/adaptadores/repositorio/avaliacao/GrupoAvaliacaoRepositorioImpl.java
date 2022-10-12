package br.com.kronos.backend.adaptadores.repositorio.avaliacao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroGrupoAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.GrupoAvaliacao;
import br.com.kronos.backend.aplicacao.avaliacao.GrupoAvaliacaoRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.api.GrupoAvaliacaoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrupoAvaliacaoRepositorioImpl extends SqlQueryRepositorio implements GrupoAvaliacaoRepositorio {
	
	public GrupoAvaliacaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(GrupoAvaliacao grupoAvaliacao) {
        addFields("numero", "tema", "id_avaliacao" ); 
		
		addValues(grupoAvaliacao.getNumero(), grupoAvaliacao.getTema(), grupoAvaliacao.getIdAvaliacao());
		
		return (long) insertAuto("grupo_avaliacao");
    }
   
	@Override
	public Long alterar(GrupoAvaliacao grupoAvaliacao) {
		addFields("numero", "tema", "id_avaliacao" ); 
		
		addValues(grupoAvaliacao.getNumero(), grupoAvaliacao.getTema(), grupoAvaliacao.getIdAvaliacao(), grupoAvaliacao.getId());
		
		return (long) update("grupo_avaliacao", grupoAvaliacao.getId());
	}
	
	@Override
	public GrupoAvaliacaoDTO buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select g.id, g.numero, g.tema, g.id_avaliacao, count(a.id_grupo_avaliacao) as qtdAvaliados ");
            query.append("from grupo_avaliacao g ");
            query.append("left join avaliado a on (g.id = a.id_grupo_avaliacao) where 1=1 ");
            query = andEqual(query, "g.id", id);
            query = groupBy(query, "g.id, g.numero, g.tema, g.id_avaliacao ");
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new GrupoAvaliacaoDTOMapper());
		} catch (EmptyResultDataAccessException e) {
			log.info("grupoAvaliacao não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<GrupoAvaliacaoDTO> listar(FiltroGrupoAvaliacao filtroGrupoAvaliacao) {
		 StringBuilder query = createQuery("select g.id, g.numero, g.tema, g.id_avaliacao, count(a.id_grupo_avaliacao) as qtdAvaliados ");
         query.append("from grupo_avaliacao g ");
         query.append("left join avaliado a on (g.id = a.id_grupo_avaliacao) where 1=1 ");
         query = andEqual(query, "g.id", filtroGrupoAvaliacao.getId());
         query = andEqual(query, "g.numero", filtroGrupoAvaliacao.getNumero()); 
         query = andEqual(query, "g.id_avaliacao", filtroGrupoAvaliacao.getIdAvaliacao());
         query = groupBy(query, "g.id, g.numero, g.tema, g.id_avaliacao ");
         query = orderBy(query, Order.ASC, "g.numero");
		 query = limit(query, filtroGrupoAvaliacao.getQtdTotal(), filtroGrupoAvaliacao.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new GrupoAvaliacaoDTOMapper());
	}
	
	@Override
	public int contar(FiltroGrupoAvaliacao filtroGrupoAvaliacao) {
		StringBuilder query = createQuery("select count(g.id) ");
		query.append("from grupo_avaliacao g ");
        query.append("left join avaliado a on (g.id = a.id_grupo_avaliacao) where 1=1 ");
		query = andEqual(query, "p.id", filtroGrupoAvaliacao.getId());
		query = andEqual(query, "g.id", filtroGrupoAvaliacao.getId());
        query = andEqual(query, "g.numero", filtroGrupoAvaliacao.getNumero()); 
        query = andEqual(query, "g.id_avaliacao", filtroGrupoAvaliacao.getIdAvaliacao()); 
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("grupo_avaliacao", id);
		return true;
	}
	
	@Override
	public boolean excluirPorAvaliacao(Long idAvaliacao) {
		addFields("id_avaliacao");
		addValues(idAvaliacao);
		delete("grupo_avaliacao");
		return true;
	}
	
	private class GrupoAvaliacaoDTOMapper implements RowMapper<GrupoAvaliacaoDTO> {

		@Override
		public GrupoAvaliacaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return GrupoAvaliacaoDTO.builder() 
                    .id(rs.getLong("id"))
                    .numero(rs.getInt("numero"))
                    .tema(rs.getString("tema"))
                    .idAvaliacao(rs.getLong("id_avaliacao"))
                    .qtdAvaliados(rs.getInt("qtdAvaliados"))
                    .build();
		}
	}
}

