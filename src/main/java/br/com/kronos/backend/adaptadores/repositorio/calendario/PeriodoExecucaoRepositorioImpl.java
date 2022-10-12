package br.com.kronos.backend.adaptadores.repositorio.calendario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import br.com.kronos.backend.adaptadores.repositorio.comum.ResultadoPesquisaEstrutura;
import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.FiltroPeriodoExecucao;
import br.com.kronos.backend.aplicacao.calendario.PeriodoExecucao;
import br.com.kronos.backend.aplicacao.calendario.PeriodoExecucaoRepositorio;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoEstruturaAnoLetivoDTO;
import br.com.kronos.backend.aplicacao.calendario.api.PeriodoExecucaoResumoDTO;
import br.com.kronos.backend.aplicacao.curso.Estrutura;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PeriodoExecucaoRepositorioImpl extends SqlQueryRepositorio implements PeriodoExecucaoRepositorio {
	
	public PeriodoExecucaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(PeriodoExecucao periodoExecucao) {
        addFields("id_calendario", "id_periodo", "ano" );
		
		addValues(periodoExecucao.getIdCalendario(), periodoExecucao.getIdPeriodo(), periodoExecucao.getAno());
		
		return (long) insertAuto("periodo_execucao"); 
    }
		
	@Override
	public Long alterar(PeriodoExecucao periodoExecucao) {
		addFields("id_calendario", "id_periodo", "ano" );
		
		addValues(periodoExecucao.getIdCalendario(), periodoExecucao.getIdPeriodo(), periodoExecucao.getAno(), periodoExecucao.getId());
		
		return (long) update("periodo_execucao", periodoExecucao.getId());
	}
	
	@Override
	public PeriodoExecucao buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select p.id_calendario, p.id_periodo, ano ");
            query.append("from periodo_execucao p where 1=1 ");
            query = andEqual(query, "p.id", id);
			PeriodoExecucao periodoExecucao = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new PeriodoExecucaoMapper());
			return periodoExecucao;
		} catch (EmptyResultDataAccessException e) {
			log.info("período de execução não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<PeriodoExecucaoResumoDTO> listarParaModulosDosProfessores(Long idCurso, Long idPessoaUsuarioLogado) {
		StringBuilder query = createQuery("select distinct pe.id, p.id_faixa_ano, p.nome, pe.id_periodo ");
		query.append("from periodo_execucao pe ");
        query.append("left join turma t on (pe.id = t.id_periodo_execucao) ");
        query.append("left join horario h on (t.id = h.id_turma) ");
        query.append("left join funcionario f on (h.id_funcionario = f.id) ");
        query.append("left join periodo p on (pe.id_periodo = p.id) where t.encerrada = false ");
        query = andEqual(query, "p.id_curso", idCurso);
        query = andEqual(query, "f.id_pessoa", idPessoaUsuarioLogado);
        query = orderBy(query, Order.ASC, "p.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PeriodoExecucaoResumoDTOMapper());
	}

	@Override
	public PeriodoExecucao buscarPorIdPeriodoEAno(Long idPeriodo, Integer ano) {
		try {     
            StringBuilder query = createQuery("select p.id, p.id_calendario, p.id_periodo, ano ");
            query.append("from periodo_execucao p where 1=1 ");
            query = andEqual(query, "p.id_periodo", idPeriodo);
            query = andEqual(query, "p.ano", ano);
			PeriodoExecucao periodoExecucao = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new PeriodoExecucaoMapper());
			return periodoExecucao;
		} catch (EmptyResultDataAccessException e) {
			log.info("período de execução não existe para o idPeriodo - " + idPeriodo + "e ano - " + ano);
			return null;
		}
	}
	
	@Override
	public PeriodoExecucaoEstruturaAnoLetivoDTO buscarPorIdParaEstruturaAnoLetivo(Long id) {
		try {     
            StringBuilder query = createQuery("select pe.id, pe.ano, tp.nome as tipo_periodo, p.sigla as periodo, ");
            query.append("p.numero as numero_periodo, c.numero||'/'||c.ano as calendario, ");
            query.append("c.data_inicio_ano_letivo as data_inicio, c.data_final_ano_letivo as data_fim, c.id as id_calendario, p.id as id_periodo ");
            query.append("from periodo_execucao pe ");
            query.append("left join calendario c on (pe.id_calendario = c.id) ");
            query.append("left join periodo p on (pe.id_periodo = p.id) ");
            query.append("left join tipo_periodo tp on (p.id_tipo_periodo = tp.id) where 1=1 ");
            query = andEqual(query, "p.numero", id);
            PeriodoExecucaoEstruturaAnoLetivoDTO periodoExecucao = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new PeriodoExecucaoEstruturaAnoLetivoDTOMapper());
			return periodoExecucao;
		} catch (EmptyResultDataAccessException e) {
			log.info("período de execução não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<PeriodoExecucaoEstruturaAnoLetivoDTO> listarParaEstruturaAnoLetivo(FiltroPeriodoExecucao filtroPeriodoExecucao) {
		StringBuilder query = createQuery("select pe.id, pe.ano, tp.nome as tipo_periodo, p.sigla as periodo, ");
        query.append("p.numero as numero_periodo, c.numero||'/'||c.ano as calendario, ");
        query.append("c.data_inicio_ano_letivo as data_inicio, c.data_final_ano_letivo as data_fim, c.id as id_calendario, p.id as id_periodo, count(fe.id) as nr_fase ");
        query.append("from periodo_execucao pe ");
        query.append("left join calendario c on (pe.id_calendario = c.id) ");
        query.append("left join periodo p on (pe.id_periodo = p.id) ");
        query.append("left join tipo_periodo tp on (p.id_tipo_periodo = tp.id) ");
        query.append("left join fase_execucao fe on (pe.id = fe.id_periodo_execucao) where 1=1 ");
        query = andEqual(query, "c.id_instituicao", filtroPeriodoExecucao.getIdInstituicao());
        query = andEqual(query, "p.id_curso", filtroPeriodoExecucao.getIdCurso());
        query = andEqual(query, "c.ano", filtroPeriodoExecucao.getAno());
        query = andEqual(query, "c.id", filtroPeriodoExecucao.getIdCalendario());
        query = groupBy(query, "pe.id, pe.ano, tp.nome, p.sigla, p.numero, c.numero, c.ano, c.data_inicio_ano_letivo, c.data_final_ano_letivo, c.id, p.id");
        query = orderBy(query, Order.ASC, "p.numero");
        query = limit(query, filtroPeriodoExecucao.getQtdTotal(), filtroPeriodoExecucao.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PeriodoExecucaoEstruturaAnoLetivoDTOMapper());
	}
	
	@Override
	public int contarParaEstruturaAnoLetivo(FiltroPeriodoExecucao filtroPeriodoExecucao) {
		StringBuilder query = createQuery("select count(pe.id) ");
		query.append("from periodo_execucao pe ");
        query.append("left join calendario c on (pe.id_calendario = c.id) ");
        query.append("left join periodo p on (pe.id_periodo = p.id) where 1=1 ");
//        query.append("left join tipo_periodo tp on (p.id_tipo_periodo = tp.id) ");
//        query.append("left join fase_execucao fe on (pe.id = fe.id_periodo_execucao) where 1=1 ");
        query = andEqual(query, "c.id_instituicao", filtroPeriodoExecucao.getIdInstituicao());
        query = andEqual(query, "p.id_curso", filtroPeriodoExecucao.getIdCurso());
        query = andEqual(query, "c.ano", filtroPeriodoExecucao.getAno());
        query = andEqual(query, "c.id", filtroPeriodoExecucao.getIdCalendario());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<PeriodoExecucao> listar(FiltroPeriodoExecucao filtroPeriodoExecucao) {
		StringBuilder query = createQuery("select p.id_calendario, p.id_periodo, ano ");
        query.append("from periodo_execucao p where 1=1 ");
        query = andEqual(query, "p.id", filtroPeriodoExecucao.getId());
        query = andEqual(query, "p.id_calendario", filtroPeriodoExecucao.getIdCalendario());
        query = andEqual(query, "p.id_periodo", filtroPeriodoExecucao.getIdPeriodo());
        query = andEqual(query, "p.ano", filtroPeriodoExecucao.getAno());
        query = orderBy(query, Order.ASC, "p.id_periodo");
		query = limit(query, filtroPeriodoExecucao.getQtdTotal(), filtroPeriodoExecucao.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PeriodoExecucaoMapper());
	}
	
	@Override
	public int contar(FiltroPeriodoExecucao filtroPeriodoExecucao) {
		StringBuilder query = createQuery("select count(p.id) ");
        query.append("from periodo_execucao p where 1=1 ");
        query = andEqual(query, "p.id", filtroPeriodoExecucao.getId());
        query = andEqual(query, "p.id_calendario", filtroPeriodoExecucao.getIdCalendario());
        query = andEqual(query, "p.id_periodo", filtroPeriodoExecucao.getIdPeriodo());
        query = andEqual(query, "p.ano", filtroPeriodoExecucao.getAno());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("periodo_execucao", id);
		return true;
	}
	
	private class PeriodoExecucaoResumoDTOMapper implements RowMapper<PeriodoExecucaoResumoDTO> {

		@Override
		public PeriodoExecucaoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PeriodoExecucaoResumoDTO.builder()
					.id(rs.getLong("id"))
                    .idFaixaAno(rs.getLong("id_faixa_ano"))
                    .nome(rs.getString("nome"))
                    .idPeriodo(rs.getLong("id_periodo"))
                    .build();
		}

	}
	
	private class PeriodoExecucaoMapper implements RowMapper<PeriodoExecucao> {

		@Override
		public PeriodoExecucao mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PeriodoExecucao.builder()
					.id(rs.getLong("id"))
                    .idCalendario(rs.getLong("id_calendario"))
                    .idPeriodo(rs.getLong("id_periodo"))
                    .ano(rs.getInt("ano"))
                    .build();
		}

	}
	
	private class PeriodoExecucaoEstruturaAnoLetivoDTOMapper implements RowMapper<PeriodoExecucaoEstruturaAnoLetivoDTO> {

		@Override
		public PeriodoExecucaoEstruturaAnoLetivoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PeriodoExecucaoEstruturaAnoLetivoDTO.builder()
					.id(rs.getLong("id"))
                    .ano(rs.getInt("ano"))
                    .tipoPeriodo(rs.getString("tipo_periodo"))
                    .siglaPeriodo(rs.getString("periodo"))
                    .numeroPeriodo(rs.getString("numero_periodo"))
                    .calendario(rs.getString("calendario"))
                    .dataInicio(rs.getDate("data_inicio").toLocalDate())
                    .dataFim(rs.getDate("data_fim").toLocalDate())
                    .idCalendario(rs.getLong("id_calendario"))
                    .idPeriodo(rs.getLong("id_periodo"))
                    .numeroFases(rs.getObject("nr_fase") != null ? rs.getInt("nr_fase") : null)
                    .build();
		}

	}
	
	@Override
	public List<Estrutura> visualizarEstrutura(FiltroPeriodoExecucao filtroPeriodoExecucao) {
		StringBuilder query = createQuery("select p.id as id_periodo, p.sigla as nome_periodo, f.id as id_fase, f.sigla as nome_fase, sf.id as id_sub_fase, sf.sigla as nome_sub_fase ");
        query.append("from periodo_execucao pe ");
        query.append("left join fase_execucao fe on (pe.id = fe.id_periodo_execucao) ");
        query.append("left join sub_fase_execucao sfe on (fe.id = sfe.id_fase_execucao) ");
        query.append("left join periodo p on (pe.id_periodo = p.id) ");
        query.append("left join curso c on (p.id_curso = c.id) ");
        query.append("left join fase f on (fe.id_fase = f.id) ");
        query.append("left join sub_fase sf on (sfe.id_sub_fase = sf.id) where 1=1 ");
        query = andEqual(query, "c.id", filtroPeriodoExecucao.getIdCurso());
        query = andEqual(query, "pe.ano", filtroPeriodoExecucao.getAno());
        query = orderBy(query, Order.ASC, "p.numero, f.numero, sf.numero");
		List<ResultadoPesquisaEstrutura> resultados = this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ResultadoPesquisaEstruturaMapper());
		return criarEstrutura(resultados);
	}
	
	private List<Estrutura> criarEstrutura(List<ResultadoPesquisaEstrutura> resultados) {
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
	
	private Estrutura criarEstrutura(Estrutura estrutura, Long id, String nome, ResultadoPesquisaEstrutura resultado) {
		if(resultado.getIdPeriodo() != null &&  resultado.getNomePeriodo() != null && resultado.getIdPeriodo().equals(id) && resultado.getNomePeriodo().equals(nome)) {
			Estrutura estruturaFase = adicionarFilho(estrutura, resultado.getIdFase(), resultado.getNomeFase());
			if(estruturaFase != null) {
				criarEstrutura(estruturaFase, estruturaFase.getId(), estruturaFase.getNome(), resultado);
			}
		} 
		if(resultado.getIdFase() !=null && resultado.getNomeFase() !=null && resultado.getIdFase().equals(id) && resultado.getNomeFase().equals(nome)) {
			adicionarFilho(estrutura, resultado.getIdSubFase(), resultado.getNomeSubFase());
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
	
	private class ResultadoPesquisaEstruturaMapper implements RowMapper<ResultadoPesquisaEstrutura> {

		@Override
		public ResultadoPesquisaEstrutura mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ResultadoPesquisaEstrutura.builder()
                    .idPeriodo(rs.getLong("id_periodo") > 0 ? rs.getLong("id_periodo") : null)
                    .nomePeriodo(rs.getString("nome_periodo"))
                    .idFase(rs.getLong("id_fase") > 0 ? rs.getLong("id_fase") : null)
                    .nomeFase(rs.getString("nome_fase"))
                    .idSubFase(rs.getLong("id_sub_fase") > 0 ? rs.getLong("id_sub_fase") : null)
                    .nomeSubFase(rs.getString("nome_sub_fase"))
                    .build();
		}
	}

}
