package br.com.kronos.backend.adaptadores.repositorio.calendario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroSubFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.SubFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.SubFaseExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.api.DadosCriaSubFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubFaseExecucaoRepositorioImpl extends SqlQueryRepositorio implements SubFaseExecucaoRepositorio {
	
	public SubFaseExecucaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(SubFaseExecucao subFaseExecucao) {
        addFields("data_inicio", "data_fim", "id_fase_execucao", "id_sub_fase" );
		
		addValues(subFaseExecucao.getDataInicio(), subFaseExecucao.getDataFim(), subFaseExecucao.getIdFaseExecucao(), subFaseExecucao.getIdSubFase());
		
		return (long) insertAuto("sub_fase_execucao"); 
    }
	
	@Override
	public Long alterar(SubFaseExecucao subFaseExecucao) {
		addFields("data_inicio", "data_fim", "id_fase_execucao", "id_sub_fase" );
		
		addValues(subFaseExecucao.getDataInicio(), subFaseExecucao.getDataFim(), subFaseExecucao.getIdFaseExecucao(),subFaseExecucao.getIdSubFase(),
				  subFaseExecucao.getId());
		
		return (long) update("sub_fase_execucao", subFaseExecucao.getId());
	}
	@Override
	public SubFaseExecucaoDTO buscarPorId(Long id) {
		try {     
			StringBuilder query = createQuery("select sfe.id, tp.nome as tipo_periodo, sf.sigla as sigla_sub_fase, sf.numero as numero_sub_fase, ");
	        query.append("sfe.data_inicio, sfe.data_fim, sfe.id_fase_execucao, sf.id as id_sub_fase ");
	        query.append("from sub_fase_execucao sfe ");
	        query.append("left join fase_execucao fe on (sfe.id_fase_execucao = fe.id) ");
	        query.append("left join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) ");
	        query.append("left join calendario c on (pe.id_calendario = c.id) ");
	        query.append("left join calendario_curso cc on (cc.id_calendario = c.id) ");
	        query.append("left join periodo p on (pe.id_periodo = p.id) ");
	        query.append("left join sub_fase sf on (sfe.id_sub_fase = sf.id) ");
	        query.append("left join tipo_periodo tp on (sf.id_tipo_periodo = tp.id) where 1=1 ");
            query = andEqual(query, "sfe.id", id);
            query = groupBy(query, "sfe.id, tp.nome, sf.sigla, sf.numero, sfe.data_inicio, sfe.data_fim, sfe.id_fase_execucao, sf.id");
            SubFaseExecucaoDTO subFaseExecucao = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new SubFaseExecucaoDTOMapper());
			return subFaseExecucao;
		} catch (EmptyResultDataAccessException e) {
			log.info("Sub-fase de execução não existe - " + id);
			return null;
		}
	}
	
	@Override
	public Long buscarIdParaCriarAtividade(Long idHorario, LocalDate data) {
		try {
			StringBuilder query = createQuery("select sfe.id ");
			query.append("from horario h  ");
			query.append("left join turma t on (h.id_turma = t.id) ");
			query.append("left join periodo_execucao pe on (t.id_periodo_execucao = pe.id) ");
			query.append("left join fase_execucao fe on (pe.id = fe.id_periodo_execucao) ");
			query.append("left join sub_fase_execucao sfe on (fe.id = sfe.id_fase_execucao) where 1=1 ");
			query = andEqual(query, "h.id", idHorario);
		    query.append(" and ");
		    query.append("to_date('");
			query.append(data.toString());
			query.append("', 'yyyy-MM-dd') ");
		    query.append(" between sfe.data_inicio and sfe.data_fim ");
		    
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Long.class);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public List<SubFaseExecucaoDTO> listarParaDiarioEFrequencia(Long idTurma, Long idDisciplina, Long idPessoaUsuarioLogado) {
		StringBuilder query = createQuery("select distinct sfe.id, sf.id as id_sub_fase, sf.numero, sf.sigla as sigla_sub_fase ");
        query.append("from sub_fase_execucao sfe ");
        query.append("left join sub_fase sf on (sfe.id_sub_fase = sf.id) ");
        query.append("left join fase_execucao fe on (sfe.id_fase_execucao = fe.id) ");
        query.append("left join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) ");
        query.append("left join turma t on (pe.id = t.id_periodo_execucao) ");
        query.append("left join horario h on (t.id = h.id_turma) ");
        query.append("left join funcionario f on (h.id_funcionario = f.id) where 1=1 ");
        
        query = andEqual(query, "h.id_turma", idTurma);
        query = andEqual(query, "f.id_pessoa", idPessoaUsuarioLogado);
        query = andEqual(query, "h.id_disciplina", idDisciplina);
        query = orderBy(query, Order.ASC, "sf.numero");
        
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new SubFaseExecucaoDTOParaDiarioMapper());
	}
	
	@Override
	public List<SubFaseExecucaoDTO> listar(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select sfe.id, tp.nome as tipo_periodo, sf.sigla as sigla_sub_fase, sf.numero as numero_sub_fase, ");
        query.append("sfe.data_inicio, sfe.data_fim, sfe.id_fase_execucao, sf.id as id_sub_fase ");
        query.append("from sub_fase_execucao sfe ");
        query.append("left join fase_execucao fe on (sfe.id_fase_execucao = fe.id) ");
        query.append("left join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) ");
        query.append("left join calendario c on (pe.id_calendario = c.id) ");
        query.append("left join calendario_curso cc on (cc.id_calendario = c.id) ");
        query.append("left join periodo p on (pe.id_periodo = p.id) ");
        query.append("left join sub_fase sf on (sfe.id_sub_fase = sf.id) ");
        query.append("left join tipo_periodo tp on (sf.id_tipo_periodo = tp.id) where 1=1 ");
        query = andEqual(query, "c.id_instituicao", filtroSubFaseExecucao.getIdInstituicao());
        query = andEqual(query, "cc.id_curso", filtroSubFaseExecucao.getIdCurso());
        query = andEqual(query, "pe.id", filtroSubFaseExecucao.getIdPeriodoExecucao());
        query = andEqual(query, "fe.id", filtroSubFaseExecucao.getIdFaseExecucao());
        query = orderBy(query, Order.ASC, "sf.numero");
		query = limit(query, filtroSubFaseExecucao.getQtdTotal(), filtroSubFaseExecucao.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new SubFaseExecucaoDTOMapper());
	}
	
	@Override
	public int contar(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select count(sfe.id) ");
		query.append("from sub_fase_execucao sfe ");
        query.append("left join fase_execucao fe on (sfe.id_fase_execucao = fe.id) ");
        query.append("left join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) ");
        query.append("left join calendario c on (pe.id_calendario = c.id) ");
        query.append("left join calendario_curso cc on (cc.id_calendario = c.id) ");
        query.append("left join periodo p on (pe.id_periodo = p.id) ");
        query.append("left join sub_fase sf on (sfe.id_sub_fase = sf.id) ");
        query.append("left join tipo_periodo tp on (sf.id_tipo_periodo = tp.id) where 1=1 ");
        query = andEqual(query, "c.id_instituicao", filtroSubFaseExecucao.getIdInstituicao());
        query = andEqual(query, "cc.id_curso", filtroSubFaseExecucao.getIdCurso());
        query = andEqual(query, "pe.id", filtroSubFaseExecucao.getIdPeriodoExecucao());
        query = andEqual(query, "fe.id", filtroSubFaseExecucao.getIdFaseExecucao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("sub_fase_execucao", id);
		return true;
	}
	
	private class SubFaseExecucaoDTOParaDiarioMapper implements RowMapper<SubFaseExecucaoDTO> {

		@Override
		public SubFaseExecucaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return SubFaseExecucaoDTO.builder()
					.id(rs.getLong("id"))
                    .siglaSubFase(rs.getString("sigla_sub_fase"))
                    .idSubFase(rs.getLong("id_sub_fase"))
                    .build();
		}

	}
	
	private class SubFaseExecucaoDTOMapper implements RowMapper<SubFaseExecucaoDTO> {

		@Override
		public SubFaseExecucaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return SubFaseExecucaoDTO.builder()
					.id(rs.getLong("id"))
					.dataInicio(rs.getDate("data_inicio").toLocalDate())
					.dataFim(rs.getDate("data_fim").toLocalDate())
                    .idFaseExecucao(rs.getLong("id_fase_execucao"))
                    .idSubFase(rs.getLong("id_sub_fase"))
                    .tipoPeriodo(rs.getString("tipo_periodo"))
                    .siglaSubFase(rs.getString("sigla_sub_fase"))
                    .numeroSubFase(rs.getString("numero_sub_fase"))
                    .build();
		}

	}
	
	@Override
	public List<DadosCriaSubFaseExecucaoDTO> listarParaCriar(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select sf.id as id_sub_fase, f.id as id_fase, sf.sigla as sigla_sub_fase from sub_fase sf ");
        query.append("left join fase f on (sf.id_fase = f.id) ");
        query.append("left join periodo p on (f.id_periodo = p.id) where 1=1 ");
        query = andEqual(query, "p.id_curso", filtroSubFaseExecucao.getIdCurso());
        query.append(" and not exists( ");
        query.append(" select sfe.id_sub_fase, f.id from sub_fase_execucao sfe ");
        query.append(" join fase_execucao fe on (sfe.id_fase_execucao = fe.id) ");
        query.append(" join periodo_execucao pe on (fe.id_periodo_execucao = pe.id) ");
        query.append(" join periodo pd on (pe.id_periodo = pd.id) ");
        query.append(" join fase f on (fe.id_fase = f.id) where 1=1 ");
        query = andEqual(query, "pd.id_curso", filtroSubFaseExecucao.getIdCurso());
        query = andEqual(query, "pe.ano", filtroSubFaseExecucao.getAnoPeriodoExecucao());
        query.append(" and sfe.id_sub_fase = sf.id ");
        query.append(" and fe.id_fase = f.id ) ");
        
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DadosCriaSubFaseExecucaoDTOMapper());
	}
	
	private class DadosCriaSubFaseExecucaoDTOMapper implements RowMapper<DadosCriaSubFaseExecucaoDTO> {

		@Override
		public DadosCriaSubFaseExecucaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DadosCriaSubFaseExecucaoDTO.builder()
                    .idFase(rs.getLong("id_fase"))
                    .idSubFase(rs.getLong("id_sub_fase"))
                    .siglaSubFase(rs.getString("sigla_sub_fase"))
                    .build();
		}

	}
	
}





