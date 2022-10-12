package br.com.kronos.backend.adaptadores.repositorio.basecurricular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.Direito;
import br.com.kronos.backend.aplicacao.basecurricular.DireitoRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroDireito;
import br.com.kronos.backend.aplicacao.basecurricular.api.DireitoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DireitoRepositorioImpl extends SqlQueryRepositorio implements DireitoRepositorio {
	
	public DireitoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Direito direito) {
		 addFields("descricao", "id_nivel" , "codigo", "ativo", 
       		       "bncc", "id_instituicao"  );
		
		 addValues(direito.getDescricao(), direito.getIdNivel(), direito.getCodigo(), direito.isAtivo(),
				direito.getBncc(), direito.getIdInstituicao()); 
		
		
		return (long) insertAuto("direito"); 
    }
	
	@Override
	public Long alterar(Direito direito) {
		 addFields("descricao", "id_nivel" , "codigo", "ativo", 
    		       "bncc", "id_instituicao"  );
		
		 addValues(direito.getDescricao(), direito.getIdNivel(), direito.getCodigo(), direito.isAtivo(),
				direito.getBncc(), direito.getIdInstituicao(), direito.getId());
		
		return (long) update("direito", direito.getId());
	}
	
	@Override
	public Long criarCampoExperienciaDireito(Long idDireito, Long idCampoExperiencia) {
		 addFields("id_direito", "id_campo_experiencia");
		
		 addValues(idDireito, idCampoExperiencia); 
		
		
		return (long) insert("campo_experiencia_direito"); 
    }
	
	@Override
	public DireitoDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select d.id, d.descricao, d.id_nivel, d.codigo, d.ativo, ");
	        query.append("d.bncc, d.id_instituicao, n.nome as nome_nivel ");
	        query.append("from direito d ");
	        query.append("join nivel n on (d.id_nivel = n.id) where 1=1 ");
            query = andEqual(query, "d.id", id);
            DireitoDTO direito = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new DireitoDTOMapper());
			return direito;
		} catch (EmptyResultDataAccessException e) {
			log.info("direito não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<Long> buscarIdsCamposExperienciasDireito(Long idDireito) {
		StringBuilder query = createQuery("select c.id_campo_experiencia ");
        query.append("from campo_experiencia_direito c where 1=1 ");
        query = andEqual(query, "c.id_direito", idDireito);
        return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Long.class);
	}
	
	@Override
	public List<DireitoDTO> listarParaDisciplinaDireito(FiltroDireito filtroDireito) {
		  StringBuilder query = createQuery("select d.id, d.descricao, d.id_nivel, d.codigo, d.ativo, ");
          query.append("d.bncc, d.id_instituicao, n.nome as nome_nivel ");
          query.append("from direito d ");
          query.append("join nivel n on (d.id_nivel = n.id) where d.ativo is true ");
          query.append("and (d.bncc = true ");
          query = or(query, "d.id_instituicao", filtroDireito.getIdInstituicao(), "=");
          query.append(" )");
          query = andEqual(query, "d.id_nivel", filtroDireito.getIdNivel());
          query.append(" and not exists(");
          query.append(" select dd.id_direito from disciplina_direito dd ");
          query.append(" where dd.data_final_vigencia is null ");
          query = andEqual(query, "dd.id_disciplina", filtroDireito.getIdDisciplina());
          query.append(" and dd.id_direito = d.id) ");
          query = orderBy(query, Order.ASC, "d.codigo");
          query = limit(query, filtroDireito.getQtdTotal(), filtroDireito.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DireitoDTOMapper());
	}
	
	@Override
	public int contarParaDisciplinaDireito(FiltroDireito filtroDireito) {
		StringBuilder query = createQuery("select count(d.id) ");
        query.append("from direito d ");
        query.append("join nivel n on (d.id_nivel = n.id) where d.ativo is true ");
        query.append("and (d.bncc = true ");
        query = or(query, "d.id_instituicao", filtroDireito.getIdInstituicao(), "=");
        query.append(" )");
        query = andEqual(query, "d.id_nivel", filtroDireito.getIdNivel());
        query.append(" and not exists(");
        query.append(" select dd.id_direito from disciplina_direito dd ");
        query.append(" where dd.data_final_vigencia is null ");
        query = andEqual(query, "dd.id_disciplina", filtroDireito.getIdDisciplina());
        query.append(" and dd.id_direito = d.id) "); 
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<DireitoDTO> listar(FiltroDireito filtroDireito) {
		  StringBuilder query = createQuery("select d.id, d.descricao, d.id_nivel, d.codigo, d.ativo, ");
          query.append("d.bncc, d.id_instituicao, n.nome as nome_nivel ");
          query.append("from direito d ");
          query.append("join nivel n on (d.id_nivel = n.id) where 1=1 ");
          query.append("and (d.bncc = true ");
          query = or(query, "d.id_instituicao", filtroDireito.getIdInstituicao(), "=");
          query.append(" )");
          query = andEqual(query, "d.id", filtroDireito.getId());
          query = andEqual(query, "d.id_nivel", filtroDireito.getIdNivel());
	   	  query = andEqual(query, "d.codigo", filtroDireito.getCodigo());
	   	  query = andEqual(query, "d.ativo", filtroDireito.getAtivo());  
	   	  query = andEqual(query, "d.bncc", filtroDireito.getBncc());      
          query = orderBy(query, Order.ASC, "d.id_nivel");
		  query = limit(query, filtroDireito.getQtdTotal(), filtroDireito.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DireitoDTOMapper());
	}
	
	@Override
	public int contar(FiltroDireito filtroDireito) {
		  StringBuilder query = createQuery("select count(d.id) ");
		  query.append("from direito d ");
          query.append("join nivel n on (d.id_nivel = n.id) where 1=1 ");
          query.append("and (d.bncc = true ");
          query = or(query, "d.id_instituicao", filtroDireito.getIdInstituicao(), "=");
          query.append(" )");
		  query = andEqual(query, "d.id", filtroDireito.getId());
          query = andEqual(query, "d.id_nivel", filtroDireito.getIdNivel());
	   	  query = andEqual(query, "d.codigo", filtroDireito.getCodigo());
	   	  query = andEqual(query, "d.ativo", filtroDireito.getAtivo());   
	   	  query = andEqual(query, "d.bncc", filtroDireito.getBncc());    
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("direito", id);
		return true;
	}
	
	@Override
	public boolean excluirCamposExperienciasDireito(long idDireito) {
		addFields("id_direito");
		addValues(idDireito);
		delete("campo_experiencia_direito");
		return false;
	}
	
	private class DireitoDTOMapper implements RowMapper<DireitoDTO> {

		@Override
		public DireitoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DireitoDTO.builder()
					.id(rs.getLong("id"))
                    .descricao(rs.getString("descricao"))
                    .idNivel(rs.getLong("id_nivel"))
                    .nivel(rs.getString("nome_nivel"))
                    .codigo(rs.getString("codigo"))
                    .ativo(rs.getBoolean("ativo"))
                    .bncc(rs.getBoolean("bncc"))
                    .idInstituicao(rs.getLong("id_instituicao"))
					.build();
		}
	}
}