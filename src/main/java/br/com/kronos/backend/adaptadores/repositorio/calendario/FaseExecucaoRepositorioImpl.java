package br.com.kronos.backend.adaptadores.repositorio.calendario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.FaseExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.api.DadosCriaFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.FaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.FaseExecucaoResumoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FaseExecucaoRepositorioImpl extends SqlQueryRepositorio implements FaseExecucaoRepositorio {
	
	public FaseExecucaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(FaseExecucao faseExecucao) {
        addFields("data_inicio", "data_fim", "id_periodo_execucao", "id_fase" );
		
		addValues(faseExecucao.getDataInicio(), faseExecucao.getDataFim(), faseExecucao.getIdPeriodoExecucao(),faseExecucao.getIdFase());
		
		return (long) insertAuto("fase_execucao"); 
    }
		
	@Override
	public Long alterar(FaseExecucao faseExecucao) {
		addFields("data_inicio", "data_fim", "id_periodo_execucao", "id_fase" );
		
		addValues(faseExecucao.getDataInicio(), faseExecucao.getDataFim(), faseExecucao.getIdPeriodoExecucao(),faseExecucao.getIdFase(),
				  faseExecucao.getId());
		
		return (long) update("fase_execucao", faseExecucao.getId());
	}
	
	@Override
	public FaseExecucaoDTO buscarPorId(Long id) {
		try {     
			StringBuilder query = createQuery("select fe.id, tp.nome as tipo_periodo, f.sigla as sigla_fase, f.numero as numero_fase, ");
			query.append("fe.data_inicio, fe.data_fim, pe.id as id_periodo_execucao, f.id as id_fase, count(sfe.id) as numero_sub_fase ");
	        query.append("from fase_execucao fe ");
	        query.append("left join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) left join calendario c on (pe.id_calendario = c.id) ");
	        query.append("left join calendario_curso cc on (cc.id_calendario = c.id) ");
	        query.append("left join periodo p on (pe.id_periodo = p.id) ");
	        query.append("left join fase f on (fe.id_fase = f.id) ");
	        query.append("left join tipo_periodo tp on (f.id_tipo_periodo = tp.id) ");
	        query.append("left join sub_fase_execucao sfe on (fe.id = sfe.id_fase_execucao) where 1=1 ");
            query = andEqual(query, "fe.id", id);
            query = groupBy(query, "fe.id, tp.nome, f.sigla, f.numero, fe.data_inicio, fe.data_fim, pe.id, f.id");
            FaseExecucaoDTO faseExecucao = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new FaseExecucaoDTOMapper());
			return faseExecucao;
		} catch (EmptyResultDataAccessException e) {
			log.info("faseExecucao não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public FaseExecucao buscarPorIdFaseEAno(Long idFase, Integer ano) {
		try {     
            StringBuilder query = createQuery("select fe.id, fe.data_inicio, fe.data_fim, fe.id_periodo_execucao, fe.id_fase from fase_execucao fe ");
            query.append("join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) where 1=1 ");
            query = andEqual(query, "fe.id_fase", idFase);
            query = andEqual(query, "pe.ano", ano);
            FaseExecucao faseExecucao = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new FaseExecucaoMapper());
			return faseExecucao;
		} catch (EmptyResultDataAccessException e) {
			log.info("fase de execução não existe para o idFase - " + idFase + "e ano - " + ano);
			return null;
		}
	}
	
	@Override
	public List<FaseExecucaoResumoDTO> listaParaCombo(Long idPeriodoExecucao) {
		StringBuilder query = createQuery("select fe.id, f.sigla as sigla_fase, f.numero as numero_fase ");
		query.append("from fase_execucao fe ");
        query.append("join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) ");
        query.append("join fase f on (fe.id_fase = f.id) ");
        query = andEqual(query, "fe.id_periodo_execucao", idPeriodoExecucao);
        
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new FaseExecucaoResumoDTOMapper());
	}
	
	@Override
	public List<FaseExecucaoDTO> listar(FiltroFaseExecucao filtroFaseExecucao) {
		StringBuilder query = createQuery("select fe.id, tp.nome as tipo_periodo, f.sigla as sigla_fase, f.numero as numero_fase, ");
		query.append("fe.data_inicio, fe.data_fim, pe.id as id_periodo_execucao, f.id as id_fase, count(sfe.id) as numero_sub_fase ");
        query.append("from fase_execucao fe ");
        query.append("left join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) left join calendario c on (pe.id_calendario = c.id) ");
        query.append("left join calendario_curso cc on (cc.id_calendario = c.id) ");
        query.append("left join periodo p on (pe.id_periodo = p.id) ");
        query.append("left join fase f on (fe.id_fase = f.id) ");
        query.append("left join tipo_periodo tp on (f.id_tipo_periodo = tp.id) ");
        query.append("left join sub_fase_execucao sfe on (fe.id = sfe.id_fase_execucao) where 1=1 ");
        query = andEqual(query, "c.id_instituicao", filtroFaseExecucao.getIdInstituicao());
        query = andEqual(query, "cc.id_curso", filtroFaseExecucao.getIdCurso());
        query = andEqual(query, "pe.id", filtroFaseExecucao.getIdPeriodoExecucao());
        query = groupBy(query, "fe.id, tp.nome, f.sigla, f.numero, fe.data_inicio, fe.data_fim, pe.id, f.id");
        query = orderBy(query, Order.ASC, "f.numero");
		query = limit(query, filtroFaseExecucao.getQtdTotal(), filtroFaseExecucao.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new FaseExecucaoDTOMapper());
	}
	
	@Override
	public int contar(FiltroFaseExecucao filtroFaseExecucao) {
		StringBuilder query = createQuery("select distinct f.id ");
		query.append("from fase_execucao fe ");
        query.append("left join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) left join calendario c on (pe.id_calendario = c.id) ");
        query.append("left join calendario_curso cc on (cc.id_calendario = c.id) ");
        query.append("left join periodo p on (pe.id_periodo = p.id) ");
        query.append("left join fase f on (fe.id_fase = f.id) ");
        query.append("left join tipo_periodo tp on (f.id_tipo_periodo = tp.id) ");
        query.append("left join sub_fase_execucao sfe on (fe.id = sfe.id_fase_execucao) where 1=1 ");
        query = andEqual(query, "c.id_instituicao", filtroFaseExecucao.getIdInstituicao());
        query = andEqual(query, "cc.id_curso", filtroFaseExecucao.getIdCurso());
        query = andEqual(query, "pe.id", filtroFaseExecucao.getIdPeriodoExecucao());
        List<Integer> ids = this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Integer.class);
		return ids != null && !ids.isEmpty() ? ids.size() : 0;
	}
	
	private class FaseExecucaoMapper implements RowMapper<FaseExecucao> {

		@Override
		public FaseExecucao mapRow(ResultSet rs, int rowNum) throws SQLException {
			return FaseExecucao.builder()
					.id(rs.getLong("id"))
					.dataInicio(rs.getDate("data_inicio").toLocalDate())
					.dataFim(rs.getDate("data_fim").toLocalDate())
                    .idPeriodoExecucao(rs.getLong("id_periodo_execucao"))
                    .idFase(rs.getLong("id_fase"))
                    .build();
		}

	}
	
	private class FaseExecucaoDTOMapper implements RowMapper<FaseExecucaoDTO> {

		@Override
		public FaseExecucaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return FaseExecucaoDTO.builder()
					.id(rs.getObject("id") != null ? rs.getLong("id") : 0)
					.dataInicio(rs.getDate("data_inicio") != null ? rs.getDate("data_inicio").toLocalDate() : null)
					.dataFim(rs.getDate("data_fim") != null ? rs.getDate("data_fim").toLocalDate() : null)
                    .idPeriodoExecucao(rs.getLong("id_periodo_execucao"))
                    .idFase(rs.getLong("id_fase"))
                    .tipoPeriodo(rs.getString("tipo_periodo"))
                    .siglaFase(rs.getString("sigla_fase"))
                    .numeroFase(rs.getInt("numero_fase"))
                    .numeroSubFases(rs.getInt("numero_sub_fase"))
                    .build();
		}

	}
	
	@Override
	public boolean excluir(Long id) {
		delete("fase_execucao", id);
		return true;
	}
	
	@Override
	public List<DadosCriaFaseExecucaoDTO> listarParaCriar(FiltroFaseExecucao filtroFaseExecucao) {
		StringBuilder query = createQuery("select f.id as id_fase, p.id as id_periodo, f.sigla from fase f ");
        query.append("left join periodo p on (f.id_periodo = p.id) ");
        query.append("left join periodo_execucao pex on (p.id = pex.id_periodo) where 1=1 ");
        query = andEqual(query, "p.id_curso", filtroFaseExecucao.getIdCurso());
        query.append(" and not exists( ");
        query.append(" select fe.id_fase, pd.id from fase_execucao fe ");
        query.append(" join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) ");
        query.append(" join periodo pd on (pe.id_periodo = pd.id) where 1=1 ");
        query = andEqual(query, "pd.id_curso", filtroFaseExecucao.getIdCurso());
        query = andEqual(query, "pe.ano", filtroFaseExecucao.getAno());
        query.append(" and fe.id_fase = f.id ");
        query.append(" and pe.id_periodo = p.id ) ");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DadosCriaFaseExecucaoDTOMapper());
	}
	
	private class DadosCriaFaseExecucaoDTOMapper implements RowMapper<DadosCriaFaseExecucaoDTO> {

		@Override
		public DadosCriaFaseExecucaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DadosCriaFaseExecucaoDTO.builder()
                    .idFase(rs.getLong("id_fase"))
                    .idPeriodo(rs.getLong("id_periodo"))
                    .siglaFase(rs.getString("sigla"))
                    .build();
		}

	}
	
	private class FaseExecucaoResumoDTOMapper implements RowMapper<FaseExecucaoResumoDTO> {

		@Override
		public FaseExecucaoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return FaseExecucaoResumoDTO.builder()
                    .id(rs.getLong("id"))
                    .siglaFase(rs.getString("sigla_fase"))
                    .numeroFase(rs.getInt("numero_fase"))
                    .build();
		}

	}
	
}
