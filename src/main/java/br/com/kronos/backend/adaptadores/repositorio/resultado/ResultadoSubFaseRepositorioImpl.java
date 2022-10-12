package br.com.kronos.backend.adaptadores.repositorio.resultado;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroSubFaseExecucao;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.SubFaseExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.api.CursoResumoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;
import br.com.kronos.backend.aplicacao.resultado.FiltroResultadoSubFase;
import br.com.kronos.backend.aplicacao.resultado.ResultadoSubFase;
import br.com.kronos.backend.aplicacao.resultado.ResultadoSubFaseRepositorio;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoAvaliacaoDTO;
import br.com.kronos.backend.aplicacao.resultado.api.ResultadoSubFaseDTO;

public class ResultadoSubFaseRepositorioImpl extends SqlQueryRepositorio implements ResultadoSubFaseRepositorio {

	public ResultadoSubFaseRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(ResultadoSubFase resultadoSubFase) {
		addFields("total_ausencia", "percentual_ausencia",
				"id_sub_fase_execucao", "id_credito", "id_mencao");

		addValues(
				resultadoSubFase.getTotalAusencia(), resultadoSubFase.getPercentualAusencia(),
				resultadoSubFase.getIdSubFaseExecucao(), resultadoSubFase.getIdCredito(),
				resultadoSubFase.getIdMencao());

		return (long) insertAuto("resultado_sub_fase");
	}

	@Override
	public Long alterar(ResultadoSubFase resultadoSubFase) {
		addFields("total_ausencia", "percentual_ausencia",
				"id_sub_fase_execucao", "id_credito", "id_mencao");

		addValues(
				resultadoSubFase.getTotalAusencia(), resultadoSubFase.getPercentualAusencia(),
				resultadoSubFase.getIdSubFaseExecucao(), resultadoSubFase.getIdCredito(),
				resultadoSubFase.getIdMencao(), resultadoSubFase.getId());

		return (long) update("resultado_sub_fase", resultadoSubFase.getId());
	}

	@Override
	public List<ResultadoSubFaseDTO> listarAlunosResultados(FiltroResultadoSubFase filtroResultadoSubFase) {

		StringBuilder query = createQuery("select al.id as id_credito, al.numero_registro, al.nome, al.nr_HabilidadesAvaliadas, al.nr_HabilidadesRecuperadas, al.nr_Faltas, al.mencao, ");
		query.append("al.situacao, al.id_mencao, ");
		query.append("(case ");
		query.append("when al.Nr_hora_atividade = 0 then 0 ");
		query.append("when al.Nr_hora_atividade > 0 then trunc((coalesce(al.nr_Faltas, 0) * 100)/ al.Nr_hora_atividade, 0) ");
		query.append("end) as percentual_faltas ");
		
		query.append("from (select ");
		query.append("c.id, p.numero_registro, p.nome, cast((select count(distinct rh.id_avaliacao_habilidade) ");
		query.append("from resultado_habilidade rh ");
		query.append("left join avaliacao_habilidade ah on (rh.id_avaliacao_habilidade = ah.id) ");
		query.append("left join avaliado av on (rh.id_avaliado = av.id) ");
		query.append("left join avaliacao a on (ah.id_avaliacao = a.id) ");
		query.append("left join atividade at on (a.id_atividade = at.id) where 1=1 ");
		query = andEqual(query, "at.id_sub_fase_execucao", filtroResultadoSubFase.getIdSubFaseExecucao());
		query.append(" and av.id_credito = c.id ");
		query.append(" and a.id_tipo_abrangencia = 1 ");
		query.append(" and a.id_tipo_avaliacao = 1 ");
		query = andEqual(query, "a.id_turma", filtroResultadoSubFase.getIdTurma());
		query = andEqual(query, "a.id_disciplina", filtroResultadoSubFase.getIdDisciplina());
		query.append(" ) ");
		query.append(" as integer) as nr_HabilidadesAvaliadas, ");
		

		query.append("cast((select count(distinct rh.id_avaliacao_habilidade) ");
		query.append("from resultado_habilidade rh ");
		query.append("left join avaliacao_habilidade ah on (rh.id_avaliacao_habilidade = ah.id) ");
		query.append("left join avaliado av on (rh.id_avaliado = av.id) ");
		query.append("left join avaliacao a on (ah.id_avaliacao = a.id) ");
		query.append("left join atividade at on (a.id_atividade = at.id) where 1=1 ");
		query = andEqual(query, "at.id_sub_fase_execucao", filtroResultadoSubFase.getIdSubFaseExecucao());
		query.append(" and av.id_credito = c.id ");
		query.append(" and a.id_tipo_abrangencia = 1 ");
		query.append(" and a.id_tipo_avaliacao = 2 ");
		query = andEqual(query, "a.id_turma", filtroResultadoSubFase.getIdTurma());
		query = andEqual(query, "a.id_disciplina", filtroResultadoSubFase.getIdDisciplina());
		query.append(" ) ");

		query.append(" as integer) as nr_HabilidadesRecuperadas, ");
		
		
		query.append(" cast( coalesce((select sum(f.nr_falta)  ");
		query.append(" from atividade a ");
		query.append(" left join frequencia f on (a.id = f.id_atividade)  ");
		query.append(" left join horario h on (a.id_horario = h.id) where 1 = 1 ");
		query = andEqual(query, "a.id_sub_fase_execucao", filtroResultadoSubFase.getIdSubFaseExecucao());
		query = andEqual(query, "h.id_turma", filtroResultadoSubFase.getIdTurma());
		query = andEqual(query, "h.id_disciplina", filtroResultadoSubFase.getIdDisciplina());
		query.append(" and f.id_credito = c.id), 0) as integer ) as nr_Faltas, ");
		
		
		query.append("(SELECT count(hh6.id_hora_atividade) ");
		query.append("FROM atividade a6 ");
		query.append("left join horario h6 on (a6.id_horario = h6.id) ");
		query.append("left join horario_hora_atividade hh6 on (h6.id = hh6.id_horario) where 1=1 ");
		query = andEqual(query, "h6.id_turma", filtroResultadoSubFase.getIdTurma());
		query = andEqual(query, "h6.id_disciplina", filtroResultadoSubFase.getIdDisciplina());
		query.append(" ) as Nr_hora_atividade, ");
		
		adicionarCaseSituacao(filtroResultadoSubFase, query);
		
		adicionarCaseIdMencao(filtroResultadoSubFase, query);
		
		query.append("(select me.simbolo ");
		query.append("from resultado_sub_fase re ");
		query.append("left join mencao me on (me.id = re.id_mencao) ");
		query.append("where re.id_credito = c.id ");
		query = andEqual(query, "re.id_sub_fase_execucao", filtroResultadoSubFase.getIdSubFaseExecucao());
		query.append(") as mencao ");
		
		query.append("from credito c ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) ");
		query.append("left join matricula m on (ct.id_matricula = m.id) ");
		query.append("left join pessoa p on (m.id_pessoa = p.id) where 1=1 ");
		query = andEqual(query, "c.id_turma", filtroResultadoSubFase.getIdTurma());
		query = andEqual(query, "c.id_disciplina", filtroResultadoSubFase.getIdDisciplina());
		query = andEqual(query, "ct.ano", 2022);
		query.append(" ) al ");
		
		query = orderBy(query, Order.ASC, "al.nome");

		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new ResultadoSubFaseDTOMapper());
	}
	
	private void adicionarCaseIdMencao(FiltroResultadoSubFase filtroResultadoSubFase, StringBuilder query) {
		query.append("CASE ");
		query.append("WHEN (select tf.sigla ");
		query.append("from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) ");
		query.append("where at.id_tipo_atestado = 2 ");
		query.append("and at.id_pessoa = p.id ");
		
		query.append(" and extract (Year from ");
		query.append("to_date('");
		query.append(filtroResultadoSubFase.getDataFim().toString());
		query.append("', 'yyyy-MM-dd')) ");
		query.append("= extract (Year from at.data_inicio_vigencia) ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(filtroResultadoSubFase.getDataFim().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append("> at.data_inicio_vigencia) is not null THEN 6 ");
		
		query.append("WHEN (select tf.sigla ");
		query.append("from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) ");
		query.append("where at.id_tipo_atestado = 4 ");
		query.append("and at.id_pessoa = p.id ");
		
		query.append(" and extract (Year from ");
		query.append("to_date('");
		query.append(filtroResultadoSubFase.getDataFim().toString());
		query.append("', 'yyyy-MM-dd')) ");
		query.append("= extract (Year from at.data_inicio_vigencia) ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(filtroResultadoSubFase.getDataFim().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append("> at.data_inicio_vigencia) is not null THEN 5 ");
		
		query.append(" ELSE Null END AS id_mencao, ");
	}
	
	private void adicionarCaseSituacao(FiltroResultadoSubFase filtroResultadoSubFase, StringBuilder query) {
		query.append("CASE ");
		query.append("WHEN (select tf.sigla ");
		query.append("from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) ");
		query.append("where at.id_tipo_atestado = 2 ");
		query.append("and at.id_pessoa = p.id ");
		
		query.append(" and extract (Year from ");
		query.append("to_date('");
		query.append(filtroResultadoSubFase.getDataFim().toString());
		query.append("', 'yyyy-MM-dd')) ");
		query.append("= extract (Year from at.data_inicio_vigencia) ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(filtroResultadoSubFase.getDataFim().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append("> at.data_inicio_vigencia) is not null THEN 'Transferido' ");
		
		
		query.append("WHEN (select tf.sigla ");
		query.append("from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) ");
		query.append("where at.id_tipo_atestado = 4 ");
		query.append("and at.id_pessoa = p.id ");
		
		query.append(" and extract (Year from ");
		query.append("to_date('");
		query.append(filtroResultadoSubFase.getDataFim().toString());
		query.append("', 'yyyy-MM-dd')) ");
		query.append("= extract (Year from at.data_inicio_vigencia) ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(filtroResultadoSubFase.getDataFim().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append("> at.data_inicio_vigencia) is not null THEN 'Cancelamanto' ");
		
		query.append("WHEN (select tf.sigla ");
		query.append("from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) ");
		query.append("where at.id_tipo_atestado = 1 ");
		query.append("and at.id_pessoa = p.id ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(filtroResultadoSubFase.getDataFim().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append("between at.data_inicio_vigencia and at.data_final_vigencia) is not null THEN 'Atestado médico' ");
		
		query.append(" ELSE 'Ativo' END AS situacao, ");
	}

	@Override
	public int contarAlunosResultados(FiltroResultadoSubFase filtroResultadoSubFase) {
		StringBuilder query = createQuery("select count(c.id) ");
		query.append("from credito c ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) ");
		query.append("left join matricula m on (ct.id_matricula = m.id) ");
		query.append("left join pessoa p on (m.id_pessoa = p.id) where 1=1 ");
		query = andEqual(query, "c.id_turma", filtroResultadoSubFase.getIdTurma());
		query = andEqual(query, "c.id_disciplina", filtroResultadoSubFase.getIdDisciplina());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}
	
	@Override
	public Long recuperarIdSeExistir(Long idCredito, Long idSubFaseExecucao) {
		try {
			StringBuilder query = createQuery("select re.id ");
			query.append("from resultado_sub_fase re where 1=1 ");
			query = andEqual(query, "re.id_credito", idCredito);
			query = andEqual(query, "re.id_sub_fase_execucao", idSubFaseExecucao);
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
					Long.class);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	@Override
	public boolean excluir(Long id) {
		delete("resultado_sub_fase", id);
		return true;
	}
	
	@Override
	public boolean excluirPorIdSubFaseExecucao(Long idSubFaseExecucao) {
		addFields("id_sub_fase_execucao");

		addValues(idSubFaseExecucao);
		
		delete("resultado_sub_fase");
		return true;
	}

	private class ResultadoSubFaseDTOMapper implements RowMapper<ResultadoSubFaseDTO> {

		@Override
		public ResultadoSubFaseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ResultadoSubFaseDTO.builder()
					.idCredito(rs.getLong("id_credito"))
					.nomeAluno(rs.getString("nome"))
					.numeroRegistro(rs.getInt("numero_registro"))
					.numeroHabilidadesAvaliadas(rs.getInt("nr_HabilidadesAvaliadas"))
					.numeroHabilidadesRecuperadas(rs.getInt("nr_HabilidadesRecuperadas"))
					.totalAusencia(rs.getInt("nr_Faltas"))
					.percentualAusencia(rs.getDouble("percentual_faltas"))
					.mencao(rs.getString("mencao"))
					.idMencao(rs.getLong("id_mencao"))
					.situacao(rs.getString("situacao"))
					.build();
		}
	}

	@Override
	public List<CursoResumoDTO> listarComboCurso(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select distinct c.id, c.id_nivel, c.nome from curso c ");
		query.append("left join periodo p on (c.id = p.id_curso) ");
		query.append("left join disciplina d on (p.id = d.id_periodo) ");
		query.append("left join horario h on (d.id = h.id_disciplina) ");
		query.append("left join funcionario f on (h.id_funcionario = f.id) ");
		query.append("left join turma t on (h.id_turma = t.id) where t.encerrada = false ");
		query = andEqual(query, "c.id_instituicao", filtroSubFaseExecucao.getIdInstituicao());
		query = andEqual(query, "f.id_pessoa", filtroSubFaseExecucao.getIdPessoaUsuarioLogado());

		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new CursoResumoDTOMapper());
	}

	private class CursoResumoDTOMapper implements RowMapper<CursoResumoDTO> {

		@Override
		public CursoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CursoResumoDTO.builder().id(rs.getLong("id")).idNivel(rs.getLong("id_nivel"))
					.nome(rs.getString("nome")).build();
		}

	}

	@Override
	public List<PeriodoExecucaoResumoDTO> listarComboPeriodoExecucao(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select distinct pe.id, p.id_faixa_ano, p.nome ");
		query.append("from periodo_execucao pe ");
		query.append("left join turma t on (pe.id = t.id_periodo_execucao) ");
		query.append("left join horario h on (t.id = h.id_turma) ");
		query.append("left join funcionario f on (h.id_funcionario = f.id) ");
		query.append("left join periodo p on (pe.id_periodo = p.id) where t.encerrada = false ");
		query = andEqual(query, "p.id_curso", filtroSubFaseExecucao.getIdCurso());
		query = andEqual(query, "f.id_pessoa", filtroSubFaseExecucao.getIdPessoaUsuarioLogado());

		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new PeriodoExecucaoResumoDTOMapper());
	}

	private class PeriodoExecucaoResumoDTOMapper implements RowMapper<PeriodoExecucaoResumoDTO> {

		@Override
		public PeriodoExecucaoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PeriodoExecucaoResumoDTO.builder().id(rs.getLong("id")).idFaixaAno(rs.getLong("id_faixa_ano"))
					.nome(rs.getString("nome")).build();
		}

	}

	@Override
	public List<TurmaResumoDTO> listarComboTurma(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select distinct t.id, t.ano, t.sigla ");
		query.append("from turma t ");
		query.append("left join horario h on (t.id = h.id_turma) ");
		query.append("left join funcionario f on (h.id_funcionario = f.id) where 1=1 ");
		query = andEqual(query, "t.id_periodo_execucao", filtroSubFaseExecucao.getIdPeriodoExecucao());
		query = andEqual(query, "f.id_pessoa", filtroSubFaseExecucao.getIdPessoaUsuarioLogado());

		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new TurmaResumoDTOMapper());
	}

	private class TurmaResumoDTOMapper implements RowMapper<TurmaResumoDTO> {

		@Override
		public TurmaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TurmaResumoDTO.builder().id(rs.getLong("id")).ano(rs.getInt("ano")).sigla(rs.getString("sigla"))
					.build();
		}

	}

	@Override
	public List<DisciplinaResumoDTO> listarComboDisciplina(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select distinct d.id, d.sigla ");
		query.append("from horario h ");
		query.append("left join disciplina d on (h.id_disciplina = d.id) ");
		query.append("left join funcionario f on (h.id_funcionario = f.id) where 1=1 ");
		query = andEqual(query, "h.id_turma", filtroSubFaseExecucao.getIdTurma());
		query = andEqual(query, "f.id_pessoa", filtroSubFaseExecucao.getIdPessoaUsuarioLogado());

		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new DisciplinaResumoDTOMapper());
	}

	private class DisciplinaResumoDTOMapper implements RowMapper<DisciplinaResumoDTO> {

		@Override
		public DisciplinaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaResumoDTO.builder().id(rs.getLong("id")).sigla(rs.getString("sigla")).build();
		}

	}

	@Override
	public List<SubFaseExecucaoResumoDTO> listarComboSubFaseExecucao(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select sfe.id, sf.nome, sfe.data_inicio, sfe.data_fim ");
		query.append("from sub_fase_execucao sfe ");
		query.append("left join fase_execucao fe on (sfe.id_fase_execucao = fe.id) ");
		query.append("left join sub_fase sf on (sfe.id_sub_fase = sf.id) where 1=1 ");
		query = andEqual(query, "fe.id_periodo_execucao", filtroSubFaseExecucao.getIdPeriodoExecucao());

		query = orderBy(query, Order.ASC, "sf.numero");

		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new SubFaseExecucaoResumoDTOMapper());
	}

	private class SubFaseExecucaoResumoDTOMapper implements RowMapper<SubFaseExecucaoResumoDTO> {

		@Override
		public SubFaseExecucaoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return SubFaseExecucaoResumoDTO.builder()
					.id(rs.getLong("id"))
					.dataInicio(rs.getDate("data_inicio").toLocalDate())
					.dataFim(rs.getDate("data_fim").toLocalDate())
					.numeroSubFase(rs.getString("nome")).build();
		}

	}

	@Override
	public int contarAulasDadas(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select count(id_hora_atividade) ");
		query.append("from atividade a ");
		query.append("left join horario h on (a.id_horario = h.id) ");
		query.append("left join horario_hora_atividade hh on (h.id = hh.id_horario) where 1=1 ");
		query = andEqual(query, "a.id_sub_fase_execucao", filtroSubFaseExecucao.getId());
		query = andEqual(query, "h.id_turma", filtroSubFaseExecucao.getIdTurma());
		query = andEqual(query, "h.id_disciplina", filtroSubFaseExecucao.getIdDisciplina());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}

	@Override
	public int contarNrHabilidadesTrabalhadas(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select count(distinct ad.id_disciplina_habilidade) ");
		query.append("from atividade_disciplina_habilidade ad ");
		query.append("left join atividade a on (ad.id_atividade = a.id) ");
		query.append("left join horario h on (a.id_horario = h.id) where 1=1 ");
		query = andEqual(query, "a.id_sub_fase_execucao", filtroSubFaseExecucao.getId());
		query = andEqual(query, "h.id_turma", filtroSubFaseExecucao.getIdTurma());
		query = andEqual(query, "h.id_disciplina", filtroSubFaseExecucao.getIdDisciplina());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}

	@Override
	public int contarNrHabilidadesAvaliadas(FiltroSubFaseExecucao filtroSubFaseExecucao) {
		StringBuilder query = createQuery("select count(distinct ah.id_disciplina_habilidade) ");
		query.append("from avaliacao_habilidade ah ");
		query.append("left join avaliacao a on (ah.id_avaliacao = a.id) ");
		query.append("left join atividade at on (a.id_atividade = at.id) where 1=1 ");

		query.append(" and at.data_prevista >= ");
		query.append("to_date('");
		query.append(filtroSubFaseExecucao.getDataInicio().toString());
		query.append("', 'yyyy-MM-dd') ");

		query.append(" and at.data_prevista <= ");
		query.append("to_date('");
		query.append(filtroSubFaseExecucao.getDataFim().toString());
		query.append("', 'yyyy-MM-dd') ");

		query = andEqual(query, "a.id_turma", filtroSubFaseExecucao.getIdTurma());
		query = andEqual(query, "a.id_disciplina", filtroSubFaseExecucao.getIdDisciplina());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}
	
	@Override
	public List<ResultadoAvaliacaoDTO> listarResultadosAvaliacoes(FiltroResultadoSubFase filtro) {
		StringBuilder query = createQuery("select at.data_prevista as data, ta.sigla as tipo, h.codigo, m.simbolo as mencao ");
		query.append("from resultado_habilidade rh ");
		query.append("left join avaliacao_habilidade ah on (rh.id_avaliacao_habilidade = ah.id) ");
		query.append("left join avaliado av on (rh.id_avaliado = av.id) ");
		query.append("left join avaliacao a on (ah.id_avaliacao = a.id) ");
		query.append("left join atividade at on (a.id_atividade = at.id) ");
		query.append("left join tipo_avaliacao ta on (a.id_tipo_avaliacao = ta.id) ");
		query.append("left join disciplina_habilidade dh on (ah.id_disciplina_habilidade = dh.id) ");
		
		query.append("left join habilidade h on (dh.id_habilidade = h.id) ");
		query.append("left join mencao m on (rh.id_mencao = m.id) where 1=1 ");
		
		
		query.append(" and at.data_prevista between ");
		query.append("to_date('");
		query.append(filtro.getDataInicio().toString());
		query.append("', 'yyyy-MM-dd') ");

		query.append(" and  ");
		query.append("to_date('");
		query.append(filtro.getDataFim().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query = andEqual(query, "av.id_credito", filtro.getIdCredito());
		query = andEqual(query, "a.id_turma", filtro.getIdTurma());
		query = andEqual(query, "a.id_disciplina", filtro.getIdDisciplina());
		
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new ResultadoAvaliacaoDTOMapper());
	}
	
	private class ResultadoAvaliacaoDTOMapper implements RowMapper<ResultadoAvaliacaoDTO> {

		@Override
		public ResultadoAvaliacaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ResultadoAvaliacaoDTO.builder()
					.dataPrevista(rs.getDate("data").toLocalDate())
					.tipo(rs.getString("tipo"))
					.codigo(rs.getString("codigo"))
					.mencao(rs.getString("mencao"))
					.build();
		}

	}
}
