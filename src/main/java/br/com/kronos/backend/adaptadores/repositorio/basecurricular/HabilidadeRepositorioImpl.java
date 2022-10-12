package br.com.kronos.backend.adaptadores.repositorio.basecurricular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.FiltroHabilidade;
import br.com.kronos.backend.aplicacao.basecurricular.Habilidade;
import br.com.kronos.backend.aplicacao.basecurricular.HabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.basecurricular.api.HabilidadeDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HabilidadeRepositorioImpl extends SqlQueryRepositorio implements HabilidadeRepositorio {
	
	public HabilidadeRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Habilidade habilidade) {
        addFields("id_componente", "descricao" , "codigo", "bncc",
        		  "campo_atuacao", "pratica_liguagem" , "objeto_conhecimento", "unidade_tematica",
        		  "ativo", "id_instituicao" , "eixo");
		
		addValues(habilidade.getIdComponente(), habilidade.getDescricao(), habilidade.getCodigo(),  habilidade.getBncc(),
				 habilidade.getCampoAtuacao(),  habilidade.getPraticaLinguagem(),  habilidade.getObjetoConhecimento(),  habilidade.getUnidadeTematica(),
				 habilidade.isAtivo(),  habilidade.getIdInstituicao(),  habilidade.getEixo()); 
		
		return (long) insertAuto("habilidade"); 
    }
	
	@Override
	public Long criarHabilidadeCompetencia(Long idHabilidade, Long idCompetencia) {
		addFields("id_habilidade", "id_competencia");
		
		addValues(idHabilidade, idCompetencia); 
		
		
		return (long) insert("habilidade_competencia"); 
    }
	
	@Override
	public Long criarHabilidadeFaixaAno(Long idHabilidade, Long idFaixaAno) {
		addFields("id_habilidade", "id_faixa_ano");
		
		addValues(idHabilidade, idFaixaAno); 
		
		
		return (long) insert("habilidade_faixa_ano"); 
    }
	
	@Override
	public Long alterar(Habilidade habilidade) {
		addFields("id_componente", "descricao" , "codigo", "bncc",
      		      "campo_atuacao", "pratica_liguagem" , "objeto_conhecimento", "unidade_tematica",
      		      "ativo", "id_instituicao" , "eixo");
		
		addValues(habilidade.getIdComponente(), habilidade.getDescricao(), habilidade.getCodigo(),  habilidade.getBncc(),
				 habilidade.getCampoAtuacao(),  habilidade.getPraticaLinguagem(),  habilidade.getObjetoConhecimento(),  habilidade.getUnidadeTematica(),
				 habilidade.isAtivo(),  habilidade.getIdInstituicao(),  habilidade.getEixo(), habilidade.getId());
		
		return (long) update("habilidade", habilidade.getId());
	}
	
	@Override
	public List<Long> buscarIdsCompetencias(Long idHabilidade) {
		StringBuilder query = createQuery("select hc.id_competencia ");
        query.append("from habilidade_competencia hc where 1=1 ");
        query = andEqual(query, "hc.id_habilidade", idHabilidade);
        return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Long.class);
	}
	
	@Override
	public List<Long> buscarIdsFaixasAnos(Long idHabilidade) {
		StringBuilder query = createQuery("select hf.id_faixa_ano ");
        query.append("from habilidade_faixa_ano hf where 1=1 ");
        query = andEqual(query, "hf.id_habilidade", idHabilidade);
        return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Long.class);
	}

	@Override
	public HabilidadeDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select h.id, h.id_instituicao, h.id_componente, n.id as id_nivel, n.nome as nome_nivel, h.bncc, h.codigo, ");
	        query.append("cp.nome as componente, h.descricao, h.ativo, h.campo_atuacao , h.pratica_liguagem, ");
	        query.append("h.objeto_conhecimento, h.unidade_tematica, h.eixo ");
	        query.append("from habilidade h ");
	        query.append("left join componente cp on (h.id_componente = cp.id) ");
	        query.append("left join nivel n on (cp.id_nivel = n.id) where 1=1 ");
            query = andEqual(query, "h.id", id);
            HabilidadeDTO habilidade = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new HabilidadeDTOMapper());
			return habilidade;
		} catch (EmptyResultDataAccessException e) {
			log.info("habilidade não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<HabilidadeDTO> listarParaDisciplinaHabilidade(FiltroHabilidade filtroHabilidade) {
		StringBuilder query = createQuery("select h.id, h.id_instituicao, h.id_componente, n.id as id_nivel, n.nome as nome_nivel, h.bncc, h.codigo, ");
        query.append("cp.nome as componente, h.descricao, h.ativo, h.campo_atuacao , h.pratica_liguagem, ");
        query.append("h.objeto_conhecimento, h.unidade_tematica, h.eixo ");
        query.append("from habilidade h ");
        query.append("left join componente cp on (h.id_componente = cp.id) ");
        query.append("left join nivel n on (cp.id_nivel = n.id) where h.ativo is true ");
        
        query.append("and (h.bncc = true ");
        query = or(query, "h.id_instituicao", filtroHabilidade.getIdInstituicao(), "=");
        query.append(" )");
        query = andEqual(query, "h.id_componente", filtroHabilidade.getIdComponente());
        query.append(" and not exists(");
        query.append(" select d.id_habilidade from disciplina_habilidade d ");
        query.append(" where d.data_final_vigencia is null ");
        query = andEqual(query, "d.id_disciplina", filtroHabilidade.getIdDisciplina());
        query.append(" and d.id_habilidade = h.id) ");
        query = orderBy(query, Order.ASC, "h.codigo");
        
        query = limit(query, filtroHabilidade.getQtdTotal(), filtroHabilidade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new HabilidadeDTOMapper());
	}
	
	@Override
	public int contarParaDisciplinaHabilidade(FiltroHabilidade filtroHabilidade) {
		StringBuilder query = createQuery("select count(h.id) ");
		query.append("from habilidade h ");
        query.append("left join componente cp on (h.id_componente = cp.id) ");
        query.append("left join nivel n on (cp.id_nivel = n.id) where h.ativo is true ");
        
        query.append("and (h.bncc = true ");
        query = or(query, "h.id_instituicao", filtroHabilidade.getIdInstituicao(), "=");
        query.append(" )");
        query = andEqual(query, "h.id_componente", filtroHabilidade.getIdComponente());
        query.append(" and not exists(");
        query.append(" select d.id_habilidade from disciplina_habilidade d ");
        query.append(" where d.data_final_vigencia is null ");
        query = andEqual(query, "d.id_disciplina", filtroHabilidade.getIdDisciplina());
        query.append(" and d.id_habilidade = h.id) ");
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<HabilidadeDTO> listar(FiltroHabilidade filtroHabilidade) {
		StringBuilder query = createQuery("select h.id, h.id_instituicao, h.id_componente, n.id as id_nivel, n.nome as nome_nivel, h.bncc, h.codigo, ");
        query.append("cp.nome as componente, h.descricao, h.ativo, h.campo_atuacao , h.pratica_liguagem, ");
        query.append("h.objeto_conhecimento, h.unidade_tematica, h.eixo ");
        query.append("from habilidade h ");
        query.append("left join componente cp on (h.id_componente = cp.id) ");
        query.append("left join nivel n on (cp.id_nivel = n.id) where 1=1 ");
        query.append("and (h.bncc = true ");
        query = or(query, "h.id_instituicao", filtroHabilidade.getIdInstituicao(), "=");
        query.append(" )");
        query = andEqual(query, "h.id", filtroHabilidade.getId());
	   	query = andEqual(query, "h.id_componente", filtroHabilidade.getIdComponente());
	   	query = andEqual(query, "h.codigo", filtroHabilidade.getCodigo());
	    query = andEqual(query, "h.ativo", filtroHabilidade.getAtivo());    
	   	query = andEqual(query, "h.bncc", filtroHabilidade.getBncc());
	   	query = andEqual(query, "n.id", filtroHabilidade.getIdNivel());
        query = orderBy(query, Order.ASC, "h.id_componente");
		query = limit(query, filtroHabilidade.getQtdTotal(), filtroHabilidade.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new HabilidadeDTOMapper());
	}
	
	@Override
	public int contar(FiltroHabilidade filtroHabilidade) {
		  StringBuilder query = createQuery("select count(h.id) ");
		  query.append("from habilidade h ");
	      query.append("left join componente cp on (h.id_componente = cp.id) ");
	      query.append("left join nivel n on (cp.id_nivel = n.id) where 1=1 ");
	      query.append("and (h.bncc = true ");
	      query = or(query, "h.id_instituicao", filtroHabilidade.getIdInstituicao(), "=");
	      query.append(" )");
		  query = andEqual(query, "h.id", filtroHabilidade.getId());
	   	  query = andEqual(query, "h.id_componente", filtroHabilidade.getIdComponente());
	   	  query = andEqual(query, "h.codigo", filtroHabilidade.getCodigo());
	      query = andEqual(query, "h.ativo", filtroHabilidade.getAtivo());   
	   	  query = andEqual(query, "h.bncc", filtroHabilidade.getBncc());
	   	query = andEqual(query, "n.id", filtroHabilidade.getIdNivel());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("habilidade", id);
		return true;
	}
	
	@Override
	public boolean excluirHabilidadeCompetencia(long idHabilidade) {
		addFields("id_habilidade");
		addValues(idHabilidade);
		delete("habilidade_competencia");
		return false;
	}
	
	@Override
	public boolean excluirHabilidadeFaixaAno(long idHabilidade) {
		addFields("id_habilidade");
		addValues(idHabilidade);
		delete("habilidade_faixa_ano");
		return false;
	}
	
	private class HabilidadeDTOMapper implements RowMapper<HabilidadeDTO> {

		@Override
		public HabilidadeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return HabilidadeDTO.builder()
					.id(rs.getLong("id"))
					.idComponente(rs.getLong("id_componente"))
					.componente(rs.getString("componente"))
					.idNivel(rs.getInt("id_nivel"))
					.nivel(rs.getString("nome_nivel"))
                    .descricao(rs.getString("descricao"))
                    .codigo(rs.getString("codigo"))
                    .bncc(rs.getBoolean("bncc"))
                    .campoAtuacao(rs.getString("campo_atuacao"))
                    .praticaLinguagem(rs.getString("pratica_liguagem"))
                    .objetoConhecimento(rs.getString("objeto_conhecimento"))
                    .unidadeTematica(rs.getString("unidade_tematica"))
                    .ativo(rs.getBoolean("ativo"))        
                    .idInstituicao(rs.getLong("id_instituicao"))
                    .eixo(rs.getString("eixo"))
                    .build();
		}
	}
}