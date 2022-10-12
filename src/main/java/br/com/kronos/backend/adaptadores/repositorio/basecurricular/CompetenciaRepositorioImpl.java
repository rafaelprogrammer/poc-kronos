package br.com.kronos.backend.adaptadores.repositorio.basecurricular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.Competencia;
import br.com.kronos.backend.aplicacao.basecurricular.CompetenciaRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroCompetencia;
import br.com.kronos.backend.aplicacao.basecurricular.api.CompetenciaDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompetenciaRepositorioImpl extends SqlQueryRepositorio implements CompetenciaRepositorio {
	
	public CompetenciaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Competencia competencia) {
        addFields("descricao", "id_nivel" , "id_componente", "codigo", 
        		  "bncc", "geral", "ativo", "id_instituicao"  );
		
		addValues(competencia.getDescricao(), competencia.getIdNivel(), competencia.getIdComponente(), competencia.getCodigo(),
				  competencia.getBncc(), competencia.getGeral(), competencia.isAtivo(), competencia.getIdInstituicao()); 
		
		return (long) insertAuto("competencia"); 
    }
	
	@Override
	public Long alterar(Competencia competencia) {
		addFields("descricao", "id_nivel" , "id_componente", "codigo", 
      		  "bncc", "geral", "ativo", "id_instituicao"  );
		
		addValues(competencia.getDescricao(), competencia.getIdNivel(), competencia.getIdComponente(), competencia.getCodigo(),
				  competencia.getBncc(), competencia.getGeral(), competencia.isAtivo(), competencia.getIdInstituicao(), competencia.getId());
		
		return (long) update("competencia", competencia.getId());
	}
		
	@Override
	public CompetenciaDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select c.id, c.id_nivel, c.id_componente, c.id_instituicao, c.id, n.nome as nome_nivel, ");
	        query.append("c.bncc, c.geral, c.codigo, cp.nome as componente, c.descricao, c.ativo ");
	        query.append("from competencia c ");
	        query.append("left join nivel n on (c.id_nivel = n.id) ");
	        query.append("left join componente cp on (c.id_componente = cp.id) where 1=1 ");
            query = andEqual(query, "c.id", id);
            CompetenciaDTO competencia = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new CompetenciaDTOMapper());
			return competencia;
		} catch (EmptyResultDataAccessException e) {
			log.info("competencia não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<CompetenciaDTO> listarParaDisciplinaCompetencia(FiltroCompetencia filtroCompetencia) {
		StringBuilder query = createQuery("select c.id, c.id_nivel, c.id_componente, c.id_instituicao, c.id, n.nome as nome_nivel, ");
        query.append("c.bncc, c.geral, c.codigo, cp.nome as componente, c.descricao, c.ativo ");
        query.append("from competencia c ");
        query.append("left join nivel n on (c.id_nivel = n.id) ");
        query.append("left join componente cp on (c.id_componente = cp.id) where c.ativo is true ");
        query.append(" and (c.bncc = true ");
        query = or(query, "c.id_instituicao", filtroCompetencia.getIdInstituicao(), "=");
        query.append(" ) ");
        query = andEqual(query, "c.id_nivel", filtroCompetencia.getIdNivel());
        query.append(" and (c.id_componente is null ");
        query = or(query, "c.id_componente", filtroCompetencia.getIdComponente(), "=");
        query.append(" ) ");
        query.append(" and not exists(");
        query.append(" select d.id_competencia from disciplina_competencia d ");
        query.append(" where d.data_final_vigencia is null ");
        query = andEqual(query, "d.id_disciplina", filtroCompetencia.getIdDisciplina());
        query.append(" and d.id_competencia = c.id) ");
        query = orderBy(query, Order.ASC, "c.codigo");
        query = limit(query, filtroCompetencia.getQtdTotal(), filtroCompetencia.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CompetenciaDTOMapper());
	}
	
	@Override
	public int contarParaDisciplinaCompetencia(FiltroCompetencia filtroCompetencia) {
		StringBuilder query = createQuery("select count(c.id) ");
		query.append("from competencia c ");
        query.append("left join nivel n on (c.id_nivel = n.id) ");
        query.append("left join componente cp on (c.id_componente = cp.id) where c.ativo is true ");
        query.append(" and (c.bncc = true ");
        query = or(query, "c.id_instituicao", filtroCompetencia.getIdInstituicao(), "=");
        query.append(" )");
        query = andEqual(query, "c.id_nivel", filtroCompetencia.getIdNivel());
        query.append(" and (c.id_componente is null ");
        query = or(query, "c.id_componente", filtroCompetencia.getIdComponente(), "=");
        query.append(" )");
        query.append(" and not exists(");
        query.append(" select d.id_competencia from disciplina_competencia d ");
        query.append(" where d.data_final_vigencia is null ");
        query = andEqual(query, "d.id_disciplina", filtroCompetencia.getIdDisciplina());
        query.append(" and d.id_competencia = c.id) ");
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<CompetenciaDTO> listar(FiltroCompetencia filtroCompetencia) {
		StringBuilder query = createQuery("select c.id, c.id_nivel, c.id_componente, c.id_instituicao, c.id, n.nome as nome_nivel, ");
        query.append("c.bncc, c.geral, c.codigo, cp.nome as componente, c.descricao, c.ativo ");
        query.append("from competencia c ");
        query.append("left join nivel n on (c.id_nivel = n.id) ");
        query.append("left join componente cp on (c.id_componente = cp.id) where 1=1 ");
        query.append("and (c.bncc = true ");
        query = or(query, "c.id_instituicao", filtroCompetencia.getIdInstituicao(), "=");
        query.append(" )");
        if (filtroCompetencia.getIds() != null && !filtroCompetencia.getIds().isEmpty()) {
        	query = andIn(query, "c.id", filtroCompetencia.getIds().stream().toArray(Long[]::new));
        }
	   	query = andEqual(query, "c.id_nivel", filtroCompetencia.getIdNivel());
	   	query = andEqual(query, "c.id_componente", filtroCompetencia.getIdComponente());
	    query = andEqual(query, "c.codigo", filtroCompetencia.getCodigo());
	   	query = andEqual(query, "c.ativo", filtroCompetencia.getAtivo());   
	    query = andEqual(query, "c.bncc", filtroCompetencia.getBncc());   
	   	query = andEqual(query, "c.geral", filtroCompetencia.getGeral());   
        query = orderBy(query, Order.ASC, "c.id_nivel");
		query = limit(query, filtroCompetencia.getQtdTotal(), filtroCompetencia.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CompetenciaDTOMapper());
	}
	
	@Override
	public List<CompetenciaDTO> listarPorIds(List<Long> ids) {
		StringBuilder query = createQuery("select c.id, c.id_nivel, c.id_componente, c.id_instituicao, c.id, n.nome as nome_nivel, ");
        query.append("c.bncc, c.geral, c.codigo, cp.nome as componente, c.descricao, c.ativo ");
        query.append("from competencia c ");
        query.append("left join nivel n on (c.id_nivel = n.id) ");
        query.append("left join componente cp on (c.id_componente = cp.id) where 1=1 ");
        query = andIn(query, "c.id", ids.stream().toArray(Long[]::new));
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CompetenciaDTOMapper());
	}
	
	@Override
	public int contar(FiltroCompetencia filtroCompetencia) {
		  StringBuilder query = createQuery("select count(c.id) ");
		  query.append("from competencia c ");
	        query.append("left join nivel n on (c.id_nivel = n.id) ");
	        query.append("left join componente cp on (c.id_componente = cp.id) where 1=1 ");
	        query.append("and (c.bncc = true ");
	        query = or(query, "c.id_instituicao", filtroCompetencia.getIdInstituicao(), "=");
	        query.append(" )");
		  query = andEqual(query, "c.id", filtroCompetencia.getId());          
	   	  query = andEqual(query, "c.id_nivel", filtroCompetencia.getIdNivel());
	   	  query = andEqual(query, "c.id_componente", filtroCompetencia.getIdComponente());
	      query = andEqual(query, "c.codigo", filtroCompetencia.getCodigo());
	   	  query = andEqual(query, "c.ativo", filtroCompetencia.getAtivo());   
	      query = andEqual(query, "c.bncc", filtroCompetencia.getBncc());    
	   	  query = andEqual(query, "c.geral", filtroCompetencia.getGeral());  
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("competencia", id);
		return true;
	}
	
	private class CompetenciaDTOMapper implements RowMapper<CompetenciaDTO> {

		@Override
		public CompetenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CompetenciaDTO.builder()
					.id(rs.getLong("id"))
                    .descricao(rs.getString("descricao"))
                    .idNivel(rs.getLong("id_nivel"))
                    .nivel(rs.getString("nome_nivel"))
                    .idComponente(rs.getLong("id_componente"))
                    .componente(rs.getString("componente"))
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .geral(rs.getBoolean("geral"))
                    .ativo(rs.getBoolean("ativo"))          
                    .idInstituicao(rs.getLong("id_instituicao"))
					.build();
		}
	}
}

