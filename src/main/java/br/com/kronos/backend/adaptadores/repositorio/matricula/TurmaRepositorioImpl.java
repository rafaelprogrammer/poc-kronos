package br.com.kronos.backend.adaptadores.repositorio.matricula;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.api.DadosCriaTurmaDTO;
import br.com.kronos.backend.aplicacao.curso.Estrutura;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurma;
import br.com.kronos.backend.aplicacao.matricula.Turma;
import br.com.kronos.backend.aplicacao.matricula.TurmaRepositorio;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaDiarioFrequenciaDTO;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaResumoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TurmaRepositorioImpl extends SqlQueryRepositorio implements TurmaRepositorio {
	
	public TurmaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Turma turma) {
        addFields("ano", "sigla",  "ativa", "aberta", 
        		  "encerrada", "id_periodo_execucao", "id_calendario", "id_tipo_turno");
		
        addValues(turma.getAno(), turma.getSigla(), turma.isAtiva(), turma.isAberta(),
				turma.isEncerrada(), turma.getIdPeriodoExecucao(), turma.getIdCalendario(), turma.getIdTipoTurno()); 
		
		return (long) insertAuto("turma"); 
    }
	
	@Override
	public Long alterar(Turma turma) {
         addFields("ano", "sigla",  "ativa", "aberta",
                   "encerrada", "id_periodo_execucao", "id_calendario", "id_tipo_turno");
		
		addValues(turma.getAno(), turma.getSigla(), turma.isAtiva(), turma.isAberta(),
				turma.isEncerrada(), turma.getIdPeriodoExecucao(), turma.getIdCalendario(), turma.getIdTipoTurno(), turma.getId());
		
		return (long) update("turma", turma.getId());
	}
	
	@Override
	public TurmaDTO buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select t.id, t.ano, tt.nome as turno, tt.id as id_tipo_turno, t.sigla, t.ativa, t.aberta, t.encerrada, t.id_periodo_execucao, t.id_calendario,  ");
            query.append("tp.nome as tipo_periodo, ca.numero||'/'||ca.ano||'-'||ca.descricao as calendario  ");
            query.append("from turma t ");
            query.append("join periodo_execucao pe on (t.id_periodo_execucao = pe.id) ");
            query.append("join periodo p on (pe.id_periodo = p.id) ");
            query.append("join tipo_periodo tp on (p.id_tipo_periodo = tp.id) ");
            query.append("join calendario ca on (t.id_calendario = ca.id) ");
            query.append("join tipo_turno tt on (t.id_tipo_turno = tt.id) where 1=1 ");
            query = andEqual(query, "t.id", id);
            TurmaDTO turma = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new TurmaDTOMapper());
			return turma;
		} catch (EmptyResultDataAccessException e) {
			log.info("turma não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<TurmaDiarioFrequenciaDTO> listarParaDiarioFrequencia(Long idInstituicaoUsuarioLogado, Long idPessoaUsuarioLogado) {
		StringBuilder query = createQuery("select distinct ");
		query.append("c.id as id_curso, c.id_nivel, p.id as id_periodo, pe.id as id_periodo_execucao, p.id_faixa_ano, t.id as id_turma, d.id as id_disciplina, p.numero, d.codigo, t.ano, ");
		query.append("t.ano||'-'||c.sigla||'-'||p.nome||'-'||t.sigla||'/'||tt.sigla||'-'||d.sigla as disciplina ");
		query.append("from disciplina d ");
		query.append("left join horario h on (d.id = h.id_disciplina) ");
		query.append("left join turma t on (h.id_turma = t.id) ");
		query.append("left join tipo_turno tt on (t.id_tipo_turno = tt.id) ");
		query.append("left join periodo_execucao pe on (t.id_periodo_execucao = pe.id) ");
		query.append("left join periodo p on (pe.id_periodo = p.id) ");
		query.append("left join curso c on (p.id_curso = c.id) ");
		query.append("left join funcionario f on (h.id_funcionario = f.id) ");
		query.append("where t.encerrada = false ");
		query = andEqual(query, "c.id_instituicao", idInstituicaoUsuarioLogado);
		query = andEqual(query, "f.id_pessoa", idPessoaUsuarioLogado);
		query = orderBy(query, Order.ASC, "p.numero, d.codigo");
		
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaDiarioFrequenciaDTOParaDiarioFrequenciaMapper());
	}
	
	@Override
	public List<TurmaResumoDTO> listarParaModulosDosProfessores(Long idPeriodoExecucao, Long idPessoaUsuarioLogado) {
		StringBuilder query = createQuery("select distinct t.id, t.ano, t.sigla||'/'||tt.sigla as sigla ");
        query.append("from turma t ");
        query.append("left join horario h on (t.id = h.id_turma) ");
        query.append("left join funcionario f on (h.id_funcionario = f.id) ");
        query.append("left join tipo_turno tt on (t.id_tipo_turno = tt.id) where 1=1 ");
        query = andEqual(query, "t.id_periodo_execucao", idPeriodoExecucao);
        query = andEqual(query, "f.id_pessoa", idPessoaUsuarioLogado);
        query = orderBy(query, Order.DESC, "t.ano");       
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaResumoDTOMapper());
	}
	
	@Override
	public List<Integer> listarAnosParaHorario() {
		StringBuilder query = createQuery("select distinct t.ano from turma t where 1=1 ");
		query = andEqual(query, "t.encerrada", false);
        query = orderBy(query, Order.DESC, "t.ano");       
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override	
	public List<Integer> listarAnosFiltroIdCurso(Long idCurso) {
		StringBuilder query = createQuery("select distinct t.ano from turma t ");
		query.append("left join periodo_execucao pe on (t.id_periodo_execucao = pe.id) ");
		query.append("left join periodo p on (pe.id_periodo = p.id) where 1=1 ");
		query = andEqual(query, "p.id_curso", idCurso);
        query = orderBy(query, Order.DESC, "t.ano");       
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<TurmaResumoDTO> listarParaHorario(Long idPeriodoExecucao) {
		StringBuilder query = createQuery("select t.id, t.sigla||'/'||tt.sigla as sigla, t.ano ");
        query.append("from turma t ");
        query.append("left join tipo_turno tt on (t.id_tipo_turno = tt.id) where 1=1 ");
        query = andEqual(query, "t.id_periodo_execucao", idPeriodoExecucao);
        query = orderBy(query, Order.DESC, "t.ano");
        
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaResumoDTOMapper());
	}
	
	@Override
	public List<TurmaResumoDTO> listarPorIdCursoEAno(Long idCurso, Integer ano) {
		StringBuilder query = createQuery("select t.id, t.sigla, t.ano ");
        query.append("from turma t ");
        query.append("left join periodo_execucao pe on (t.id_periodo_execucao = pe.id) ");
        query.append("left join periodo p on (pe.id_periodo = p.id) where 1=1 ");
        query = andEqual(query, "p.id_curso", idCurso);
        query = andEqual(query, "t.ano", ano);
        query = orderBy(query, Order.DESC, "t.ano");       
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaResumoDTOMapper());
	}
	
	@Override
	public List<TurmaResumoDTO> listarParaTurmaContratoCombo(Long idPeriodoExecucao) {
		StringBuilder query = createQuery("select t.id, t.ano,  t.sigla||'/'||tt.sigla as sigla ");
        query.append("from turma t ");
        query.append("left join tipo_turno tt on (t.id_tipo_turno = tt.id) where 1=1 ");
		query = andEqual(query, "t.ativa", true);
		query = andEqual(query, "t.aberta", true);
		query = andEqual(query, "t.encerrada", false);
        query = andEqual(query, "t.id_periodo_execucao", idPeriodoExecucao);
        query = orderBy(query, Order.DESC, "t.ano");       
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaResumoDTOMapper());
	}
	
	@Override
	public List<TurmaDTO> listar(FiltroTurma filtroTurma) {
		StringBuilder query = createQuery("select t.id, t.ano, tt.nome as turno, tt.id as id_tipo_turno, t.sigla, t.ativa, t.aberta, t.encerrada, t.id_periodo_execucao, t.id_calendario,  ");
        query.append("tp.nome as tipo_periodo, ca.numero||'/'||ca.ano||'-'||ca.descricao as calendario ");
        query.append("from turma t ");
        query.append("join periodo_execucao pe on (t.id_periodo_execucao = pe.id) ");
        query.append("join periodo p on (pe.id_periodo = p.id) ");
        query.append("join tipo_periodo tp on (p.id_tipo_periodo = tp.id) ");
        query.append("join calendario ca on (t.id_calendario = ca.id) ");
        query.append("join tipo_turno tt on (t.id_tipo_turno = tt.id) where 1=1 ");
        query = andEqual(query, "t.id", filtroTurma.getId());
		query = andEqual(query, "t.ano", filtroTurma.getAno());
        query = andEqual(query, "t.sigla", filtroTurma.getSigla());
        query = andEqual(query, "t.id_periodo_execucao", filtroTurma.getIdPeriodoExecucao());
        query = andEqual(query, "t.aberta", filtroTurma.getAberta());
        query = andEqual(query, "t.id_calendario", filtroTurma.getIdCalendario());
        query = andEqual(query, "t.id_periodo_execucao", filtroTurma.getIdCalendario());
        query = orderBy(query, Order.DESC, "t.sigla");
		query = limit(query, filtroTurma.getQtdTotal(), filtroTurma.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaDTOMapper());
	}
	
	@Override
	public int contar(FiltroTurma filtroTurma) {
		StringBuilder query = createQuery("select count(t.id)  ");
        query.append("from turma t ");
        query.append("join periodo_execucao pe on (t.id_periodo_execucao = pe.id) ");
        query.append("join periodo p on (pe.id_periodo = p.id) ");
        query.append("join tipo_periodo tp on (p.id_tipo_periodo = tp.id) ");
        query.append("join calendario ca on (t.id_calendario = ca.id) ");
        query.append("join tipo_turno tt on (t.id_tipo_turno = tt.id) where 1=1 ");
        query = andEqual(query, "t.id", filtroTurma.getId());
		query = andEqual(query, "t.ano", filtroTurma.getAno());
        query = andEqual(query, "t.sigla", filtroTurma.getSigla());
        query = andEqual(query, "t.id_periodo_execucao", filtroTurma.getIdPeriodoExecucao());
        query = andEqual(query, "t.aberta", filtroTurma.getAberta());
        query = andEqual(query, "t.id_calendario", filtroTurma.getIdCalendario());
        query = andEqual(query, "t.id_periodo_execucao", filtroTurma.getIdCalendario());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("turma", id);
		return true;
	}
	
	private class TurmaDTOMapper implements RowMapper<TurmaDTO> {

		@Override
		public TurmaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TurmaDTO.builder()
					.id(rs.getLong("id"))
					.ano(rs.getInt("ano"))
					.sigla(rs.getString("sigla"))
					.ativa(rs.getBoolean("ativa"))
					.aberta(rs.getBoolean("aberta"))
					.encerrada(rs.getBoolean("encerrada"))
					.idPeriodoExecucao(rs.getLong("id_periodo_execucao"))
					.idCalendario(rs.getLong("id_calendario"))
					.tipoPeriodo(rs.getString("tipo_periodo"))
					.calendario(rs.getString("calendario"))
					.turno(rs.getString("turno"))
					.idTipoTurno(rs.getInt("id_tipo_turno"))
					.build();
		}
		
	}
	
	private class TurmaResumoDTOMapper implements RowMapper<TurmaResumoDTO> {

		@Override
		public TurmaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TurmaResumoDTO.builder()
					.id(rs.getLong("id"))
					.ano(rs.getInt("ano"))
					.sigla(rs.getString("sigla"))
					.build();
		}
		
	}
	
	@Override
	public List<TurmaResumoDTO> listarParaOcorrenciaCombo(Long idMatriculaSelecionada) {
		StringBuilder query = createQuery("select distinct t.id, t.ano||'/'||t.sigla as sigla ");
        query.append("from turma t ");
        query.append("left join credito c on (t.id = c.id_turma) ");
        query.append("left join contrato ct on (c.id_contrato = ct.id) where 1=1 ");
        
		query = andEqual(query, "ct.id_matricula", idMatriculaSelecionada);
		query.append(" and c.id_tipo_mencao_final is null ");
        query = orderBy(query, Order.DESC, "t.ano||'/'||t.sigla");       
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaResumoDTOParaOcorrenciaMapper());
	}
	
	private class TurmaResumoDTOParaOcorrenciaMapper implements RowMapper<TurmaResumoDTO> {

		@Override
		public TurmaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TurmaResumoDTO.builder()
					.id(rs.getLong("id"))
					.sigla(rs.getString("sigla"))
					.build();
		}
		
	}
	
	private class TurmaDiarioFrequenciaDTOParaDiarioFrequenciaMapper implements RowMapper<TurmaDiarioFrequenciaDTO> {

		@Override
		public TurmaDiarioFrequenciaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TurmaDiarioFrequenciaDTO.builder()
					//id e uma soma de id_turma com id_disciplina para gerar uma chave unica
					.id(rs.getLong("id_turma") + rs.getLong("id_disciplina"))
					.idTurma(rs.getLong("id_turma"))
					.idDisciplina(rs.getLong("id_disciplina"))
                    .nome(rs.getString("disciplina"))
                    .anoTurma(rs.getInt("ano"))
                    .idFaixaAno(rs.getLong("id_faixa_ano"))
                    .idNivel(rs.getLong("id_nivel"))
                    .idPeriodoExecucao(rs.getLong("id_periodo_execucao"))
                    .idPeriodo(rs.getLong("id_periodo"))
                    .idCurso(rs.getLong("id_curso"))
					.build();
		}

	}
	
	@Override
	public List<DadosCriaTurmaDTO> listarParaGeracaoDeTurmas(FiltroTurma filtro) {
		StringBuilder query = createQuery("select p.id as id_periodo, px.id as id_periodo_execucao, 'T01-'||p.sigla as sigla, c.id_tipo_turno as id_tipo_turno ");
        query.append("from periodo_execucao px ");
        query.append("left join periodo p on (px.id_periodo = p.id) ");
        query.append("left join curso cc on (p.id_curso = cc.id) where 1=1 ");
        query = andEqual(query, "cc.id_instituicao", filtro.getIdInstituicao());
        query = andEqual(query, "cc.id", filtro.getIdCurso());
        query = andEqual(query, "px.ano", filtro.getAno());
        query.append(" and not exists(");
        query.append(" select pd.sigla, pd.numero, t.id_periodo_execucao, t.sigla ");
        query.append(" from turma t");
        query.append(" left join periodo_execucao pe on (t.id_periodo_execucao = pe.id)");
        query.append(" left join periodo pd on (pe.id_periodo = pd.id)");
        query.append(" left join curso c on (pd.id_curso = c.id) where 1=1 ");
        query = andEqual(query, "c.id_instituicao", filtro.getIdInstituicao());
        query = andEqual(query, "pd.id_curso", filtro.getIdCurso());
        query = andEqual(query, "pe.ano", filtro.getAno());
        query.append(" and t.id_periodo_execucao = px.id )");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DadosCriaTurmaDTOMapper());
	}
	
	private class DadosCriaTurmaDTOMapper implements RowMapper<DadosCriaTurmaDTO> {

		@Override
		public DadosCriaTurmaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DadosCriaTurmaDTO.builder()
                    .idPeriodo(rs.getLong("id_periodo"))
                    .idPeriodoExecucao(rs.getLong("id_periodo_execucao"))
                    .sigla(rs.getString("sigla"))
                    .idTipoTurno(rs.getInt("id_tipo_turno"))
                    .build();
		}

	}
	
	@Override
	public List<Estrutura> visualizarEstrutura(FiltroTurma filtro) {
		StringBuilder query = createQuery("select p.id as id_periodo, p.sigla as nome_periodo, t.id as id_turma, t.sigla||'/'||tt.sigla as nome_turma ");
        query.append("from periodo_execucao pe ");
        query.append("left join periodo p on (pe.id_periodo = p.id) ");
        query.append("left join curso c on (p.id_curso = c.id) ");
        query.append("left join turma t on (pe.id = t.id_periodo_execucao) ");
        query.append("left join tipo_turno tt on (t.id_tipo_turno = tt.id) where 1=1 ");
        query = andEqual(query, "c.id", filtro.getIdCurso());
        query = andEqual(query, "pe.ano", filtro.getAno());
        query = orderBy(query, Order.ASC, "p.numero, t.sigla");
        
		List<ResultadoPesquisaEstruturaTurma> resultados = this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ResultadoPesquisaEstruturaTurmaMapper());
		return criarEstrutura(resultados);
	}
	
	private List<Estrutura> criarEstrutura(List<ResultadoPesquisaEstruturaTurma> resultados) {
		List<Estrutura> estruturas = new ArrayList<>();
		resultados.forEach(r -> {
			Estrutura estrutura = estruturas.stream().filter(e -> e.getId().equals(r.getIdPeriodo())).findAny()
										.orElse(null);
			if(estrutura == null) {
				estrutura = Estrutura.comIdENome(r.getIdPeriodo(), r.getNomePeriodo());
				estruturas.add(estrutura);
			}
			criarEstrutura(estrutura, estrutura.getId(), estrutura.getNome(), r);
		});
		return estruturas;
	}
	
	private Estrutura criarEstrutura(Estrutura estrutura, Long id, String nome, ResultadoPesquisaEstruturaTurma resultado) {
		if(resultado.getIdPeriodo() != null &&  resultado.getNomePeriodo() != null && resultado.getIdPeriodo().equals(id) && resultado.getNomePeriodo().equals(nome)) {
			Estrutura estruturaFase = adicionarFilho(estrutura, resultado.getIdTurma(), resultado.getNomeTurma());
			if(estruturaFase != null) {
				criarEstrutura(estruturaFase, estruturaFase.getId(), estruturaFase.getNome(), resultado);
			}
		} 
		return estrutura;
	}

	private Estrutura adicionarFilho(Estrutura estrutura, Long idFilho, String nomeFilho) {
		Estrutura estruturaFase = estrutura.getFilhos().stream().filter(e -> e.getId().equals(idFilho)).findAny()
				.orElse(null);
		if(estruturaFase == null && idFilho != null && !StringUtils.isEmpty(nomeFilho)) {
			estruturaFase = Estrutura.comIdENome(idFilho, nomeFilho);
			estrutura.adicionarFilho(estruturaFase);
		}
		return estruturaFase;
	}
	
	private class ResultadoPesquisaEstruturaTurmaMapper implements RowMapper<ResultadoPesquisaEstruturaTurma> {

		@Override
		public ResultadoPesquisaEstruturaTurma mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ResultadoPesquisaEstruturaTurma.builder()
                    .idPeriodo(rs.getLong("id_periodo") > 0 ? rs.getLong("id_periodo") : null)
                    .nomePeriodo(rs.getString("nome_periodo"))
                    .idTurma(rs.getLong("id_turma") > 0 ? rs.getLong("id_turma") : null)
                    .nomeTurma(rs.getString("nome_turma"))
                    .build();
		}
	}
}
