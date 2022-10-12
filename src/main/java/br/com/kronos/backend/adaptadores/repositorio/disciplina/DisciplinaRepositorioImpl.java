package br.com.kronos.backend.adaptadores.repositorio.disciplina;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.Disciplina;
import br.com.kronos.backend.aplicacao.disciplina.DisciplinaRepositorio;
import br.com.kronos.backend.aplicacao.disciplina.FiltroDisciplina;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaCreditoDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaDTO;
import br.com.kronos.backend.aplicacao.disciplina.api.DisciplinaResumoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisciplinaRepositorioImpl extends SqlQueryRepositorio implements DisciplinaRepositorio {
	
	public DisciplinaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Disciplina disciplina) {
        addFields("codigo", "nome", "sigla", "carga_horaria_total", 
                  "carga_horaria_presencial", "carga_horaria_distancia", "ativa",  "obrigatoria", 
                  "valor", "id_tipo_disciplina", "id_tipo_regime_letivo", "id_periodo", 
                  "data_final_vigencia", "data_inicio_vigencia", "id_componente", "pre_requisitos", "id_disciplina_unifica");
		
		addValues(disciplina.getCodigo(), disciplina.getNome(), disciplina.getSigla(),disciplina.getCargaHorariaTotal(), 
                  disciplina.getCargaHorariaPresencial(), disciplina.getCargaHorariaDistancia(), disciplina.isAtiva(), disciplina.isObrigatoria(),  
                  disciplina.getValor(), disciplina.getIdTipoDisciplina(), disciplina.getIdTipoRegimeLetivo(), disciplina.getIdPeriodo(), 
                  disciplina.getDataFinalVigencia(), disciplina.getDataInicioVigencia(), disciplina.getIdComponente(), disciplina.isPreRequisitos(), disciplina.getIdDisciplinaUnifica()); 
		
		return (long) insertAuto("disciplina"); 
    }
	
	@Override
	public Long alterar(Disciplina disciplina) {
		addFields("codigo", "nome", "sigla", "carga_horaria_total", 
                "carga_horaria_presencial", "carga_horaria_distancia", "ativa",  "obrigatoria", 
                "valor", "id_tipo_disciplina", "id_tipo_regime_letivo", "id_periodo", 
                "data_final_vigencia", "data_inicio_vigencia", "id_componente", "pre_requisitos", "id_disciplina_unifica");
		
		addValues(disciplina.getCodigo(), disciplina.getNome(), disciplina.getSigla(),disciplina.getCargaHorariaTotal(), 
                disciplina.getCargaHorariaPresencial(), disciplina.getCargaHorariaDistancia(), disciplina.isAtiva(), disciplina.isObrigatoria(),  
                disciplina.getValor(), disciplina.getIdTipoDisciplina(), disciplina.getIdTipoRegimeLetivo(), disciplina.getIdPeriodo(), 
                disciplina.getDataFinalVigencia(), disciplina.getDataInicioVigencia(), disciplina.getIdComponente(), disciplina.isPreRequisitos(),
                disciplina.getIdDisciplinaUnifica(),
                disciplina.getId());
		
		return (long) update("disciplina", disciplina.getId());
	}
	
	@Override
	public DisciplinaDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select d.id, d.codigo, d.nome, d.sigla, d.carga_horaria_total,  ");
	        query.append("d.carga_horaria_presencial, d.carga_horaria_distancia, d.ativa,  d.obrigatoria,");
	        query.append("d.valor, d.id_tipo_disciplina, d.id_tipo_regime_letivo, d.id_periodo, ");
	        query.append("d.data_final_vigencia, d.data_inicio_vigencia, d.id_componente, d.pre_requisitos, u.id as id_disciplina_unificacao, u.sigla as sigla_disciplina_unificacao ");
	        query.append("from disciplina d ");
	        query.append("left join tipo_disciplina t on (d.id_tipo_disciplina = t.id) ");
	        query.append("left join tipo_regime_letivo r on (d.id_tipo_regime_letivo = r.id) ");
	        query.append("left join disciplina u on (d.id_disciplina_unifica = u.id) where 1=1 ");
            query = andEqual(query, "d.id", id);
            DisciplinaDTO disciplina = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new DisciplinaDTOMapper());
			return disciplina;
		} catch (EmptyResultDataAccessException e) {
			log.info("disciplina não existe - " + id);
			return null;
		}
		
	}
	
	@Override
	public List<DisciplinaResumoDTO> listarParaModulosDosProfessores(Long idTurma, Long idPessoaUsuarioLogado) {
		StringBuilder query = createQuery("select distinct d.id, d.sigla ");
		query.append("from horario h ");
		query.append("left join disciplina d on (h.id_disciplina = d.id) ");
		query.append("left join funcionario f on (h.id_funcionario = f.id) where 1=1 ");
		query = andEqual(query, "h.id_turma", idTurma);
		query = andEqual(query, "f.id_pessoa", idPessoaUsuarioLogado);
		query = orderBy(query, Order.ASC, "d.sigla");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaResumoDTOParaDiarioMapper());
	}
	
	
	
	@Override
	public List<DisciplinaResumoDTO> listarPorPeriodoExecucao(long idPeriodoExecucao) {
		StringBuilder query = createQuery("select d.id, d.codigo, d.nome, d.sigla, p.nome as nomePeriodo, p.sigla as siglaPeriodo ");
		query.append("from disciplina d ");
		query.append("left join periodo p on (d.id_periodo = p.id) ");
		query.append("left join periodo_execucao pe on (p.id = pe.id_periodo) where 1=1 ");
		query.append("and d.id_disciplina_unifica is null ");
		query = andEqual(query, "pe.id", idPeriodoExecucao);
		query = orderBy(query, Order.ASC, "d.codigo");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaResumoDTOSemPeriodoMapper());
	}
	
	@Override
	public List<DisciplinaResumoDTO> listarParaUsoEmPreRequisito(long idCurso, int numeroPeriodo, long idDisciplina) {
		StringBuilder query = createQuery("select d.id, d.codigo, d.nome, d.sigla, p.nome as nomePeriodo, p.sigla as siglaPeriodo ");
		query.append("from disciplina d ");
		query.append("left join periodo p on (d.id_periodo = p.id) where 1=1 ");
		query = andEqual(query, "p.id_curso", idCurso);
		query = and(query, "p.numero", numeroPeriodo, "<");
		query.append(" and not exists ");
		query.append("(select pr.id_disciplina_pre_requisito from disciplina_pre_requisito pr where 1=1 ");
		query = and(query, "pr.id_disciplina", idDisciplina, "<>");
		query.append(" and pr.id_disciplina_pre_requisito = d.id) ");
		query = orderBy(query, Order.ASC, "d.sigla");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaResumoDTOMapper());
	}
	
	@Override
	public List<DisciplinaResumoDTO> listarParaCombo(FiltroDisciplina filtro) {
		StringBuilder query = createQuery("select d.id, d.codigo, d.nome, d.sigla, d.id_componente ");
		query.append("from disciplina d where 1=1 ");
		query = andEqual(query, "d.id_periodo", filtro.getIdPeriodo());
		query = orderBy(query, Order.ASC, "d.sigla");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaResumoSemPeriodoDTOMapper());
	}
	
	@Override
	public List<DisciplinaResumoDTO> listarParaComboUnificacao(FiltroDisciplina filtro) {
		StringBuilder query = createQuery("select id, nome, id_periodo, id_disciplina_unifica ");
		query.append("from disciplina d where 1=1 ");
		query = andEqual(query, "d.id_periodo", filtro.getIdPeriodo());
		if (filtro.getId() != null) {
			query = and(query, "d.id", filtro.getId(), "<>");
		}
		query = orderBy(query, Order.ASC, "d.sigla");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaResumoDTOParaComboDisciplinaUnificaMapper());
	}
	
	@Override
	public List<DisciplinaDTO> listar(FiltroDisciplina filtroDisciplina) {
		StringBuilder query = createQuery("select d.id, d.codigo, d.nome, d.sigla, d.carga_horaria_total,  ");
        query.append("d.carga_horaria_presencial, d.carga_horaria_distancia, d.ativa,  d.obrigatoria,");
        query.append("d.valor, d.id_tipo_disciplina, d.id_tipo_regime_letivo, d.id_periodo, ");
        query.append("d.data_final_vigencia, d.data_inicio_vigencia, d.id_componente, d.pre_requisitos, u.id as id_disciplina_unificacao, u.sigla as sigla_disciplina_unificacao ");
        query.append("from disciplina d ");
        query.append("left join tipo_disciplina t on (d.id_tipo_disciplina = t.id) ");
        query.append("left join tipo_regime_letivo r on (d.id_tipo_regime_letivo = r.id) ");
        query.append("left join disciplina u on (d.id_disciplina_unifica = u.id) where 1=1 ");
		query = andEqual(query, "d.id", filtroDisciplina.getId());
		query = andEqual(query, "d.codigo", filtroDisciplina.getCodigo());
        query = andLike(query, "d.nome", filtroDisciplina.getNome());
        query = andEqual(query, "d.sigla", filtroDisciplina.getSigla()); 
        query = andEqual(query, "d.id_periodo", filtroDisciplina.getIdPeriodo());
        query = orderBy(query, Order.ASC, "d.codigo");
		query = limit(query, filtroDisciplina.getQtdTotal(), filtroDisciplina.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaDTOMapper());
	}
	
	@Override
	public int contar(FiltroDisciplina filtroDisciplina) {
		StringBuilder query = createQuery("select count(d.id) from disciplina d ");
        query.append("left join tipo_disciplina t on (d.id_tipo_disciplina = t.id) ");
        query.append("left join tipo_regime_letivo r on (d.id_tipo_regime_letivo = r.id) where 1=1 ");
		query = andEqual(query, "d.id", filtroDisciplina.getId());
		query = andEqual(query, "d.codigo", filtroDisciplina.getCodigo());
        query = andLike(query, "d.nome", filtroDisciplina.getNome());
        query = andEqual(query, "d.sigla", filtroDisciplina.getSigla()); 
        query = andEqual(query, "d.id_periodo", filtroDisciplina.getIdPeriodo());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("disciplina", id);
		return true;
	}
	
	private class DisciplinaDTOMapper implements RowMapper<DisciplinaDTO> {

		@Override
		public DisciplinaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaDTO.builder()
					.id(rs.getLong("id"))
					.codigo(rs.getInt("codigo"))
                    .nome(rs.getString("nome"))
                    .sigla(rs.getString("sigla"))
                    .cargaHorariaTotal(rs.getInt("carga_horaria_total"))
                    .cargaHorariaPresencial(rs.getInt("carga_horaria_presencial"))
                    .cargaHorariaDistancia(rs.getInt("carga_horaria_distancia"))
                    .ativa(rs.getBoolean("ativa"))
                    .obrigatoria(rs.getBoolean("obrigatoria"))
                    .valor(rs.getDouble("valor"))
                    .idTipoDisciplina(rs.getInt("id_tipo_disciplina"))
                    .idTipoRegimeLetivo(rs.getInt("id_tipo_regime_letivo"))
                    .idPeriodo(rs.getLong("id_periodo"))
                    .idDisciplinaUnificacao(rs.getObject("id_disciplina_unificacao") != null ? rs.getLong("id_disciplina_unificacao") : null)
                    .siglaDisciplinaUnificacao(rs.getString("sigla_disciplina_unificacao") !=null ? rs.getString("sigla_disciplina_unificacao") : null)
                    .idComponente(rs.getLong("id_componente"))
                    .dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia") != null ? rs.getDate("data_final_vigencia").toLocalDate() : null)
                    .preRequisitos(rs.getBoolean("pre_requisitos"))
					.build();
		}

	}
	
	private class DisciplinaResumoSemPeriodoDTOMapper implements RowMapper<DisciplinaResumoDTO> {

		@Override
		public DisciplinaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaResumoDTO.builder()
					.id(rs.getLong("id"))
					.codigo(rs.getInt("codigo"))
                    .nome(rs.getString("nome"))
                    .sigla(rs.getString("sigla"))
                    .idComponente(rs.getLong("id_componente"))
					.build();
		}

	}
	
	private class DisciplinaResumoDTOParaComboDisciplinaUnificaMapper implements RowMapper<DisciplinaResumoDTO> {

		@Override
		public DisciplinaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaResumoDTO.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .idPeriodo(rs.getLong("id_periodo"))
                    .idDisciplinaUnificacao(rs.getLong("id_disciplina_unifica"))
					.build();
		}

	}
	
	private class DisciplinaResumoDTOMapper implements RowMapper<DisciplinaResumoDTO> {

		@Override
		public DisciplinaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaResumoDTO.builder()
					.id(rs.getLong("id"))
					.codigo(rs.getInt("codigo"))
                    .nome(rs.getString("nome") + " - " + rs.getString("nomePeriodo") )
                    .sigla(rs.getString("sigla") + " - " + rs.getString("siglaPeriodo"))
					.build();
		}

	}
	
	private class DisciplinaResumoDTOParaDiarioMapper implements RowMapper<DisciplinaResumoDTO> {

		@Override
		public DisciplinaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaResumoDTO.builder()
					.id(rs.getLong("id"))
                    .sigla(rs.getString("sigla"))
					.build();
		}

	}
	
	private class DisciplinaResumoDTOSemPeriodoMapper implements RowMapper<DisciplinaResumoDTO> {

		@Override
		public DisciplinaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaResumoDTO.builder()
					.id(rs.getLong("id"))
					.codigo(rs.getInt("codigo"))
                    .nome(rs.getString("nome"))
                    .sigla(rs.getString("sigla"))
					.build();
		}

	}
	
	@Override
	public void registrarPreRequisito(long idDisciplina, long idDisciplinaPreRequisito) {
		addFields("id_disciplina", "id_disciplina_pre_requisito");
		
		addValues(idDisciplina, idDisciplinaPreRequisito); 
		
		insert("disciplina_pre_requisito");
	}

	@Override
	public boolean excluirPreRequisitos(Long idDisciplina) {
		addFields("id_disciplina");
		
		addValues(idDisciplina);
		
		delete("disciplina_pre_requisito");
		return true;
	}

	@Override
	public List<Long> buscarPreRequisitosRegistrados(long idDisciplina) {
		try {
            StringBuilder query = createQuery("select p.id_disciplina_pre_requisito from disciplina_pre_requisito p where 1=1 ");
            query = andEqual(query, "p.id_disciplina", idDisciplina);
            return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Long.class);
		} catch (EmptyResultDataAccessException e) {
			log.info("não existe pré-requisito da disciplina - " + idDisciplina);
			return null;
		}
	}

	@Override
	public List<DisciplinaCreditoDTO> listarParaGeracaoDeCreditos(FiltroDisciplina filtroDisciplina) {
		StringBuilder query = createQuery("select d.id, d.nome, d.obrigatoria, d.carga_horaria_presencial, d.carga_horaria_distancia, ");
        query.append("d.carga_horaria_total, d.valor, p.sigla as sigla_periodo ");
        query.append("from disciplina d ");
        query.append("left join periodo p on (d.id_periodo = p.id) ");
        query.append("left join periodo_execucao pe on (pe.id_periodo = p.id) ");
        query.append("where d.ativa = true and d.id_disciplina_unifica is null ");
		query = andEqual(query, "pe.id", filtroDisciplina.getIdPeriodoExecucao());
		if (filtroDisciplina.getIdContrato() != null) {
			query.append(" and not exists ");
			query.append("(select c.id_disciplina from credito c where 1=1 ");
			query = andEqual(query, "c.id_contrato", filtroDisciplina.getIdContrato());
			query.append(" and d.id = c.id_disciplina) ");
		}
        query = orderBy(query, Order.ASC, "d.codigo");
        
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaCreditoDTOMapper());
	}
	
	@Override
	public List<DisciplinaCreditoDTO> listarParaGeracaoDeCreditosPendentes(FiltroDisciplina filtroDisciplina) {
		StringBuilder query = createQuery("select d.id, d.nome, d.obrigatoria, d.carga_horaria_presencial, d.carga_horaria_distancia, ");
        query.append("d.carga_horaria_total, d.valor, p.sigla as sigla_periodo ");
        query.append("from disciplina d ");
        query.append("left join periodo p on (d.id_periodo = p.id) ");
        query.append("left join periodo_execucao pe on (pe.id_periodo = p.id) ");
        query.append("where d.ativa = true and d.id_disciplina_unifica is null ");
		query = andEqual(query, "pe.id", filtroDisciplina.getIdPeriodoExecucao());
		if (filtroDisciplina.getIdContrato() != null) {
			query.append(" and not exists ");
			query.append("(select c.id_disciplina from credito c ");
			query.append("left join contrato ct on (c.id_contrato = ct.id) where 1=1 ");
			query = andEqual(query, "ct.id_matricula", filtroDisciplina.getIdMatricula());
			query.append(" and c.pendencia = false and d.id = c.id_disciplina) ");
	        query = orderBy(query, Order.ASC, "d.codigo");
		}
        
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new DisciplinaCreditoDTOMapper());
	}
	
	private class DisciplinaCreditoDTOMapper implements RowMapper<DisciplinaCreditoDTO> {

		@Override
		public DisciplinaCreditoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return DisciplinaCreditoDTO.builder()
					.id(rs.getLong("id"))
					.obrigatoria(rs.getBoolean("obrigatoria"))
                    .nome(rs.getString("nome"))
                    .cargaHorariaDistancia(rs.getInt("carga_horaria_distancia"))
                    .cargaHorariaPresencial(rs.getInt("carga_horaria_presencial"))
                    .cargaHorariaTotal(rs.getInt("carga_horaria_total"))
                    .valor(rs.getDouble("valor"))
                    .siglaPeriodo(rs.getString("sigla_periodo"))
					.build();
		}

	}

}
