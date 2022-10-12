package br.com.kronos.backend.adaptadores.repositorio.avaliacao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoHabilidade;
import br.com.kronos.backend.aplicacao.avaliacao.AvaliacaoHabilidadeRepositorio;
import br.com.kronos.backend.aplicacao.avaliacao.FiltroAvaliacaoHabilidade;
import br.com.kronos.backend.aplicacao.avaliacao.api.AvaliacaoHabilidadeDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AvaliacaoHabilidadeRepositorioImpl extends SqlQueryRepositorio implements AvaliacaoHabilidadeRepositorio {
	
	public AvaliacaoHabilidadeRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	public void criarAvaliacaoHabilidadeAutomatica(Long idAtividadeSelecionada) {
		StringBuilder query = createQuery("insert into avaliacao_habilidade (id_disciplina_habilidade, id_avaliacao) ");
        query.append("select id_disciplina_habilidade, ");
        query.append("(select max(id) from avaliacao where id_atividade = ");
        query.append(idAtividadeSelecionada);
        query.append(") ");
        query.append("from atividade_disciplina_habilidade where id_atividade = ");
        query.append(idAtividadeSelecionada);
        
        
        insertCustom(query.toString());
        
	}

	@Override
	public Long criar(AvaliacaoHabilidade avaliacaoHabilidade) {
        addFields("id_avaliacao", "id_disciplina_habilidade" ); 
		
		addValues(avaliacaoHabilidade.getIdAvaliacao(), avaliacaoHabilidade.getIdDisciplinaHabilidade());
		
		return (long) insertAuto("avaliacao_habilidade");
    }
   
	@Override
	public Long alterar(AvaliacaoHabilidade avaliacaoHabilidade) {
		addFields("id_avaliacao", "id_disciplina_habilidade" ); 
		
		addValues(avaliacaoHabilidade.getIdAvaliacao(), avaliacaoHabilidade.getIdDisciplinaHabilidade(), avaliacaoHabilidade.getId());
		
		return (long) update("avaliacao_habilidade", avaliacaoHabilidade.getId());
	}
	
	@Override
	public AvaliacaoHabilidadeDTO buscarPorId(Long id) {
		try {     
			StringBuilder query = createQuery("select ah.id, ah.id_avaliacao, ah.id_disciplina_habilidade, h.codigo, h.descricao, h.bncc ");
	        query.append("from avaliacao_habilidade ah ");
	        query.append("left join disciplina_habilidade dh on (ah.id_disciplina_habilidade = dh.id) ");
	        query.append("left join habilidade h on (dh.id_habilidade = h.id) where 1=1 ");
            query = andEqual(query, "ah.id", id);
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new AvaliacaoHabilidadeDTOMapper());
		} catch (EmptyResultDataAccessException e) {
			log.info("avaliacaoHabilidade não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<AvaliacaoHabilidadeDTO> listar(FiltroAvaliacaoHabilidade filtroAvaliacaoHabilidade) {
		StringBuilder query = createQuery("select ah.id, ah.id_avaliacao, ah.id_disciplina_habilidade, h.codigo, h.descricao, h.bncc, count(rh.id) as qtdResultado ");
        query.append("from avaliacao_habilidade ah ");
        query.append("left join disciplina_habilidade dh on (ah.id_disciplina_habilidade = dh.id) ");
        query.append("left join resultado_habilidade rh on (ah.id = rh.id_avaliacao_habilidade) ");
        query.append("left join habilidade h on (dh.id_habilidade = h.id) where 1=1 ");
        query = andEqual(query, "ah.id", filtroAvaliacaoHabilidade.getId());
        query = andEqual(query, "ah.id_avaliacao", filtroAvaliacaoHabilidade.getIdAvaliacao()); 
        query = andEqual(query, "ah.id_habilidade", filtroAvaliacaoHabilidade.getIdHabilidade());
        query = groupBy(query, "ah.id, ah.id_avaliacao, ah.id_disciplina_habilidade, h.codigo, h.descricao, h.bncc");
        query = orderBy(query, Order.ASC, "h.codigo");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AvaliacaoHabilidadeDTOMapper());
	}
	
	@Override
	public int contar(FiltroAvaliacaoHabilidade filtroAvaliacaoHabilidade) {
		StringBuilder query = createQuery("select count(ah.id) ");
		query.append("from avaliacao_habilidade ah ");
        query.append("left join disciplina_habilidade dh on (ah.id_disciplina_habilidade = dh.id) ");
        query.append("left join habilidade h on (dh.id_habilidade = h.id) where 1=1 ");
        query = andEqual(query, "ah.id", filtroAvaliacaoHabilidade.getId());
        query = andEqual(query, "ah.id_avaliacao", filtroAvaliacaoHabilidade.getIdAvaliacao()); 
        query = andEqual(query, "ah.id_habilidade", filtroAvaliacaoHabilidade.getIdHabilidade()); 
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("avaliacao_habilidade", id);
		return true;
	}
	
	@Override
	public boolean excluirPorAvaliacao(Long idAvaliacao) {
		addFields("id_avaliacao");
		addValues(idAvaliacao);
		delete("avaliacao_habilidade");
		return true;
	}
	
	private class AvaliacaoHabilidadeDTOMapper implements RowMapper<AvaliacaoHabilidadeDTO> {

		@Override
		public AvaliacaoHabilidadeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AvaliacaoHabilidadeDTO.builder() 
                    .id(rs.getLong("id"))
                    .idAvaliacao(rs.getLong("id_avaliacao"))
                    .idDisciplinaHabilidade(rs.getLong("id_disciplina_habilidade"))
                    .codigo(rs.getString("codigo"))
                    .descricao(rs.getString("descricao"))
                    .bncc(rs.getBoolean("bncc"))
                    .qtdResultado(rs.getInt("qtdResultado"))
                    .build();
		}
	}
	
	
}