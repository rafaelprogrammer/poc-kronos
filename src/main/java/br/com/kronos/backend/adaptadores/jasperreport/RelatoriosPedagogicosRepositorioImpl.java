package br.com.kronos.backend.adaptadores.jasperreport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoDTO;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;
import br.com.kronos.backend.aplicacao.relatorio.RelatoriosPedagogicosRepositorio;

public class RelatoriosPedagogicosRepositorioImpl extends SqlQueryRepositorio
		implements RelatoriosPedagogicosRepositorio {

	public RelatoriosPedagogicosRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public boolean atualizarResultadosBimestrais(FiltroRelatorio filtro) {
//		getJdbcTemplate().update("call AtualizaRelResltadoBimestre (?, ?, ?, ?, ?, ?)", filtro.getIdTurma(),
//				filtro.getAno(), filtro.getAno(), filtro.getIdDisciplina(), filtro.getIdFuncionario(),
//				filtro.getIdSubFaseExecucao());
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(getJdbcTemplate())
				.withProcedureName("AtualizaRelResltadoBimestre");

	
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("v_id_turma", filtro.getIdTurma());
		inParamMap.put("v_ano_turma", filtro.getAno());
		inParamMap.put("v_ano_contrato", filtro.getAno());
		inParamMap.put("v_id_disciplina", filtro.getIdDisciplina());
		inParamMap.put("v_id_funcionario", filtro.getIdFuncionario());
		inParamMap.put("v_id_sub_fase_execucao", filtro.getIdSubFaseExecucao());
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		System.out.println(simpleJdbcCallResult);
		return true;
	}

	@Override
	public Long encerrarDiario(Long idSubFaseExecucao, Long idDisciplina) {
		addFields("id_sub_fase_execucao", "id_disciplina", "data_encerramento");

		addValues(idSubFaseExecucao, idDisciplina, LocalDate.now());

		return (long) insertAuto("encerramento_sub_fase_execucao");
	}

	@Override
	public boolean reabrirDiario(Long idSubFaseExecucao, Long idDisciplina) {
		addFields("id_sub_fase_execucao", "id_disciplina");

		addValues(idSubFaseExecucao, idDisciplina);

		delete("encerramento_sub_fase_execucao");
		return true;
	}

	@Override
	public List<SubFaseExecucaoDTO> listarBimestres(FiltroRelatorio filtro) {
		StringBuilder query = createQuery("select sfe.id, sf.sigla, sfe.data_inicio, sfe.data_fim ");
		query.append("from sub_fase_execucao sfe ");
		query.append("left join fase_execucao fe on (sfe.id_fase_execucao = fe.id) ");
		query.append("left join sub_fase sf on (sfe.id_sub_fase = sf.id) where 1=1 ");
		query = andEqual(query, "fe.id_periodo_execucao", filtro.getIdPeriodoExecucao());
		query = orderBy(query, Order.ASC, "sf.numero");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new SubFaseExecucaoDTOMapper());
	}

	private class SubFaseExecucaoDTOMapper implements RowMapper<SubFaseExecucaoDTO> {

		@Override
		public SubFaseExecucaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return SubFaseExecucaoDTO.builder().id(rs.getLong("id")).siglaSubFase(rs.getString("sigla"))
					.dataInicio(rs.getDate("data_inicio").toLocalDate()).dataFim(rs.getDate("data_fim").toLocalDate())
					.build();
		}

	}

	@Override
	public List<DisciplinaResumoDTO> listarDisciplinasProfessores(FiltroRelatorio filtro) {
		StringBuilder query = createQuery(
				"select d.id, d.nome, f.id as id_funcionario, p.nome as nome_funcionario, d.carga_horaria_total as carga_horaria_prevista, div(sum(ha.tempo_carga_horaria), 60) as carga_horaria_ministrada, ");
		query.append("count(distinct a.id) as nr_dias, ");
		query.append("CASE WHEN e.id is not null THEN true ELSE false END as encerrado ");
		query.append("from atividade a ");
		query.append("left join horario h on (a.id_horario = h.id) ");
		query.append("left join horario_hora_atividade hh on (h.id = hh.id_horario) ");
		query.append("left join hora_atividade ha on (hh.id_hora_atividade = ha.id) ");
		query.append("left join funcionario f on (a.id_funcionario = f.id) ");
		query.append("left join pessoa p on (f.id_pessoa = p.id) ");
		query.append("left join disciplina d on (h.id_disciplina = d.id) ");
		query.append("left join diario di on (a.id = di.id_atividade) ");
		query.append("left join sub_fase_execucao sfe on (a.id_sub_fase_execucao = sfe.id) ");
		query.append(
				"left join encerramento_sub_fase_execucao e on (sfe.id = e.id_sub_fase_execucao and e.id_disciplina = h.id_disciplina ) ");
		query.append("where d.id_disciplina_unifica is null ");
		query = andEqual(query, "h.id_turma", filtro.getIdTurma());
		query = andEqual(query, "a.id_sub_fase_execucao", filtro.getIdSubFaseExecucao());
		query = groupBy(query, "d.id, d.nome, f.id, p.nome, e.id, d.carga_horaria_total ");
		query.append("having count(di.id) > 0 ");
		query = orderBy(query, Order.ASC, "d.nome");
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new DisciplinaResumoDTOMapper());
	}

	private class DisciplinaResumoDTOMapper implements RowMapper<DisciplinaResumoDTO> {

		@Override
		public DisciplinaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaResumoDTO.builder().id(rs.getLong("id")).nome(rs.getString("nome"))
					.idFuncionario(rs.getLong("id_funcionario")).nomeFuncionario(rs.getString("nome_funcionario"))
					.encerrado(rs.getBoolean("encerrado")).numeroDias(rs.getInt("nr_dias"))
					.cargaHorariaPrevista(rs.getInt("carga_horaria_prevista"))
					.cargaHorariaMinistrada(rs.getInt("carga_horaria_ministrada")).build();
		}

	}

	@Override
	public List<DisciplinaResumoDTO> listarDisciplinasProfessoresResultadosBimestrais(FiltroRelatorio filtro) {
		StringBuilder query = createQuery(
				"select d.id, d.nome, f.id as id_funcionario, p.nome as nome_funcionario, d.carga_horaria_total as carga_horaria_prevista, div(sum(ha.tempo_carga_horaria), 60) as carga_horaria_ministrada, ");
		query.append("count(distinct a.id) as nr_dias, rf.data_atualizacao, ");
		query.append("CASE WHEN e.id is not null THEN true ELSE false END as encerrado ");
		query.append("from atividade a ");
		query.append("left join horario h on (a.id_horario = h.id) ");
		query.append("left join horario_hora_atividade hh on (h.id = hh.id_horario) ");
		query.append("left join hora_atividade ha on (hh.id_hora_atividade = ha.id) ");
		query.append("left join funcionario f on (a.id_funcionario = f.id) ");
		query.append("left join pessoa p on (f.id_pessoa = p.id) ");
		query.append("left join disciplina d on (h.id_disciplina = d.id) ");
		query.append("left join diario di on (a.id = di.id_atividade) ");
		query.append("left join sub_fase_execucao sfe on (a.id_sub_fase_execucao = sfe.id) ");
		query.append(
				"left join encerramento_sub_fase_execucao e on (sfe.id = e.id_sub_fase_execucao and e.id_disciplina = h.id_disciplina ) ");
		query.append(
				"left join rel_resultado_bim_flag rf on (h.id_turma = rf.id_turma and	d.id = rf.id_disciplina and sfe.id = rf.id_sub_fase_execucao) ");
		query.append("where d.id_disciplina_unifica is null ");
		query = andEqual(query, "h.id_turma", filtro.getIdTurma());
		query = andEqual(query, "a.id_sub_fase_execucao", filtro.getIdSubFaseExecucao());
		query = groupBy(query, "d.id, d.nome, f.id, p.nome, e.id, d.carga_horaria_total, rf.data_atualizacao ");
		query.append("having count(di.id) > 0 ");
		query = orderBy(query, Order.ASC, "d.nome");
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new DisciplinaResumoDTORelatoriosBimestraisMapper());
	}

	private class DisciplinaResumoDTORelatoriosBimestraisMapper implements RowMapper<DisciplinaResumoDTO> {

		@Override
		public DisciplinaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaResumoDTO.builder().id(rs.getLong("id")).nome(rs.getString("nome"))
					.idFuncionario(rs.getLong("id_funcionario")).nomeFuncionario(rs.getString("nome_funcionario"))
					.encerrado(rs.getBoolean("encerrado")).numeroDias(rs.getInt("nr_dias"))
					.cargaHorariaPrevista(rs.getInt("carga_horaria_prevista"))
					.cargaHorariaMinistrada(rs.getInt("carga_horaria_ministrada"))
					.dataAtualizacao(
							rs.getDate("data_atualizacao") != null ? rs.getDate("data_atualizacao").toLocalDate()
									: null)
					.build();
		}

	}

}
