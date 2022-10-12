package br.com.kronos.backend.adaptadores.repositorio.ocorrencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.ocorrencia.EnumVigente;
import br.com.kronos.backend.aplicacao.ocorrencia.FiltroTipoOcorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.TipoOcorrencia;
import br.com.kronos.backend.aplicacao.ocorrencia.TipoOcorrenciaRepositorio;
import br.com.kronos.backend.aplicacao.ocorrencia.api.TipoOcorrenciaDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TipoOcorrenciaRepositorioImpl extends SqlQueryRepositorio implements TipoOcorrenciaRepositorio {
	
	public TipoOcorrenciaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(TipoOcorrencia tipoOcorrencia) {
        addFields("fator", "codigo", "descricao", "valor", 
                  "data_inicio_vigencia", "data_final_vigencia", "id_instituicao" );
		
		addValues(tipoOcorrencia.getFator(), tipoOcorrencia.getCodigo(), tipoOcorrencia.getDescricao(),tipoOcorrencia.getValor(), 
                  tipoOcorrencia.getDataInicioVigencia(), tipoOcorrencia.getDataFinalVigencia(), tipoOcorrencia.getIdInstituicao()); 
		
		return (long) insertAuto("tipo_ocorrencia"); 
    }

	@Override
	public Long alterar(TipoOcorrencia tipoOcorrencia) {
		 addFields("fator", "codigo", "descricao", "valor", 
                   "data_inicio_vigencia", "data_final_vigencia", "id_instituicao" );
		
		addValues(tipoOcorrencia.getFator(), tipoOcorrencia.getCodigo(), tipoOcorrencia.getDescricao(),tipoOcorrencia.getValor(), 
                  tipoOcorrencia.getDataInicioVigencia(), tipoOcorrencia.getDataFinalVigencia(), tipoOcorrencia.getIdInstituicao(), tipoOcorrencia.getId());
		
		return (long) update("tipo_ocorrencia", tipoOcorrencia.getId());
	}
	
	@Override
	public TipoOcorrenciaDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select t.id, t.codigo, t.fator, t.valor, t.descricao, t.data_inicio_vigencia, t.data_final_vigencia, count(o.id) as nr_ocorrencia ");
	        query.append("from tipo_ocorrencia t ");
	        query.append("left join ocorrencia o on (t.id = o.id_tipo_ocorrencia) where 1=1 ");
            query = andEqual(query, "t.id", id);
            query = groupBy(query, "t.id, t.codigo, t.fator, t.valor, t.descricao, t.data_inicio_vigencia, t.data_final_vigencia");
            TipoOcorrenciaDTO tipoOcorrencia = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new TipoOcorrenciaDTOMapper());
			return tipoOcorrencia;
		} catch (EmptyResultDataAccessException e) {
			log.info("Tipo de ocorrência não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<TipoOcorrenciaDTO> listar(FiltroTipoOcorrencia filtroTipoOcorrencia) {
		StringBuilder query = createQuery("select t.id, t.codigo, t.fator, t.valor, t.descricao, t.data_inicio_vigencia, t.data_final_vigencia, count(o.id) as nr_ocorrencia ");
        query.append("from tipo_ocorrencia t ");
        query.append("left join ocorrencia o on (t.id = o.id_tipo_ocorrencia) where 1=1 ");
        filtrarVigencia(filtroTipoOcorrencia, query);
        query = andEqual(query, "t.id_instituicao", filtroTipoOcorrencia.getIdInstituicaoUsuarioLogado());
        query = andEqual(query, "t.id", filtroTipoOcorrencia.getId());
		query = andEqual(query, "t.fator", filtroTipoOcorrencia.getFator());
        query = andEqual(query, "t.codigo", filtroTipoOcorrencia.getCodigo());
        query = andLike(query, "t.descricao", filtroTipoOcorrencia.getDescricao());
        query = groupBy(query, "t.id, t.codigo, t.fator, t.valor, t.descricao, t.data_inicio_vigencia, t.data_final_vigencia");
		query = limit(query, filtroTipoOcorrencia.getQtdTotal(), filtroTipoOcorrencia.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TipoOcorrenciaDTOMapper());
	}

	
	@Override
	public int contar(FiltroTipoOcorrencia filtroTipoOcorrencia) {
		StringBuilder query = createQuery("select count(t.id) ");
		query.append("from tipo_ocorrencia t ");
        query.append("left join ocorrencia o on (t.id = o.id_tipo_ocorrencia) where 1=1 ");
        filtrarVigencia(filtroTipoOcorrencia, query);
        query = andEqual(query, "t.id_instituicao", filtroTipoOcorrencia.getIdInstituicaoUsuarioLogado());
		query = andEqual(query, "t.id", filtroTipoOcorrencia.getId());
		query = andEqual(query, "t.fator", filtroTipoOcorrencia.getFator());
        query = andEqual(query, "t.codigo", filtroTipoOcorrencia.getCodigo());
        query = andLike(query, "t.descricao", filtroTipoOcorrencia.getDescricao());  
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private void filtrarVigencia(FiltroTipoOcorrencia filtroTipoOcorrencia, StringBuilder query) {
		if (!StringUtils.isEmpty(filtroTipoOcorrencia.getVigente())) {
			if (filtroTipoOcorrencia.getVigente().equals(EnumVigente.SIM.name())) {
		        query.append(" and (t.data_final_vigencia is null or t.data_final_vigencia >= ");
				query.append("to_date('");
				query.append(DateUtil.dataAtual().toString());
				query.append("', 'yyyy-MM-dd')) ");
	        }
	        
	        if (filtroTipoOcorrencia.getVigente().equals(EnumVigente.NAO.name())) {
		        query.append(" and t.data_final_vigencia < ");
				query.append("to_date('");
				query.append(DateUtil.dataAtual().toString());
				query.append("', 'yyyy-MM-dd') ");
	        }
		}
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("tipo_ocorrencia", id);
		return true;
	}
	
	private class TipoOcorrenciaDTOMapper implements RowMapper<TipoOcorrenciaDTO> {

		@Override
		public TipoOcorrenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TipoOcorrenciaDTO.builder()
					.id(rs.getLong("id"))
					.fator(rs.getInt("fator"))
					.codigo(rs.getString("codigo"))
                    .descricao(rs.getString("descricao"))
                    .valor(rs.getDouble("valor"))
                    .dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia") != null ? rs.getDate("data_final_vigencia").toLocalDate() : null)
                    .qtdOcorrencia(rs.getInt("nr_ocorrencia"))
					.build();
		}
	}
	
	@Override
	public List<TipoOcorrenciaDTO> listarParaOcorrencia(LocalDate dataOcorrencia) {
		StringBuilder query = createQuery("select id, descricao ");
        query.append("from tipo_ocorrencia t where 1=1 ");
        
        query.append(" and t.data_inicio_vigencia <= ");
		query.append("to_date('");
		query.append(dataOcorrencia.toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append(" and (t.data_final_vigencia is null or t.data_final_vigencia > ");
		query.append("to_date('");
		query.append(dataOcorrencia.toString());
		query.append("', 'yyyy-MM-dd')) ");
        
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TipoOcorrenciaDTOParaOcorrenciaMapper());
	}
	
	private class TipoOcorrenciaDTOParaOcorrenciaMapper implements RowMapper<TipoOcorrenciaDTO> {

		@Override
		public TipoOcorrenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TipoOcorrenciaDTO.builder()
					.id(rs.getLong("id"))
                    .descricao(rs.getString("descricao"))
					.build();
		}
	}
	
}

