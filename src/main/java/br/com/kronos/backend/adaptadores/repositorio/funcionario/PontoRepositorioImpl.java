package br.com.kronos.backend.adaptadores.repositorio.funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.api.dto.comum.MesDTO;
import br.com.kronos.backend.aplicacao.funcionario.EnumTipoPeriodoPonto;
import br.com.kronos.backend.aplicacao.funcionario.FiltroPonto;
import br.com.kronos.backend.aplicacao.funcionario.Ponto;
import br.com.kronos.backend.aplicacao.funcionario.PontoRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.api.AusenteDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.PontoDTO;
import br.com.kronos.backend.aplicacao.funcionario.api.TipoPeriodoPontoDTO;
import br.com.kronos.backend.aplicacao.horario.EnumTipoDiaSemana;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PontoRepositorioImpl extends SqlQueryRepositorio implements PontoRepositorio {

	public PontoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Ponto ponto) {
		addFields("tarefa_online", "hora_inicial_registro", "hora_final_registro",  "hora_inicial_informado", "hora_final_informado",
				"data", "id_tipo_periodo_ponto", "id_config_ponto", "id_funcionario_liberacao", "data_liberacao", "data_homologacao",
				"id_funcionario_homologacao", "descricao");
		
		addValues(ponto.isTarefaOnline(), ponto.getHoraInicialRegistro(), ponto.getHoraFinalRegistro(), ponto.getHoraInicialInformado(),
				ponto.getHoraFinalInformado(), ponto.getData(), ponto.getIdTipoPeriodoPonto(), ponto.getIdConfiguracaoPonto(),
				ponto.getIdFuncionarioLiberacao(), ponto.getDataLiberacao(), ponto.getDataHomologacao(), ponto.getIdFuncionarioHomologacao(),
				ponto.getDescricao());

		return (long) insertAuto("ponto");
		
	}

	@Override
	public Long alterar(Ponto ponto) {
		addFields("tarefa_online", "hora_inicial_registro", "hora_final_registro",  "hora_inicial_informado", "hora_final_informado",
				"data", "id_tipo_periodo_ponto", "id_config_ponto", "id_funcionario_liberacao", "data_liberacao", "data_homologacao",
				"id_funcionario_homologacao", "descricao");
		
		addValues(ponto.isTarefaOnline(), ponto.getHoraInicialRegistro(), ponto.getHoraFinalRegistro(), ponto.getHoraInicialInformado(),
				ponto.getHoraFinalInformado(), ponto.getData(), ponto.getIdTipoPeriodoPonto(), ponto.getIdConfiguracaoPonto(),
				ponto.getIdFuncionarioLiberacao(), ponto.getDataLiberacao(), ponto.getDataHomologacao(), ponto.getIdFuncionarioHomologacao(),
				ponto.getDescricao(), ponto.getId());

		return (long) update("ponto", ponto.getId());
	}
	
	@Override
	public Long alterarDadosLiberacao(PontoDTO ponto) {
		addFields("id_funcionario_liberacao", "data_liberacao");
		
		addValues(ponto.getIdFuncionarioLiberacao(), ponto.getDataLiberacao(), ponto.getId());

		return (long) update("ponto", ponto.getId());
	}
	
	@Override
	public Long alterarDadosHomologacao(PontoDTO ponto) {
		addFields("data_homologacao", "id_funcionario_homologacao");
		
		addValues(ponto.getDataHomologacao(), ponto.getIdFuncionarioHomologacao(), ponto.getId());

		return (long) update("ponto", ponto.getId());
	}
	
	@Override
	public List<Integer> listarAnos(Long idFuncionario) {
		StringBuilder query = createQuery("select distinct extract('year' from data) ");
		query.append("from ponto p ");
		query.append("left join config_ponto c on (p.id_config_ponto = c.id) where 1=1 ");
		query = andEqual(query, "c.id_funcionario",idFuncionario);
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}
	
	@Override
	public List<MesDTO> listarMeses(Long idFuncionario, Integer ano) {
		StringBuilder query = createQuery("select distinct extract(MONTH FROM p.data) as numero_mes, ");
		query.append("CASE EXTRACT(MONTH FROM p.data) ");
		query.append("WHEN 1 THEN 'Janeiro' ");
		query.append("WHEN 2 THEN 'Fevereiro' ");
		query.append("WHEN 3 THEN 'Março' ");
		query.append("WHEN 4 THEN 'Abril' ");
		query.append("WHEN 5 THEN 'Maio' ");
		query.append("WHEN 6 THEN 'Junho' ");
		query.append("WHEN 7 THEN 'Julho' ");
		query.append("WHEN 8 THEN 'Agosto' ");
		query.append("WHEN 9 THEN 'Setembro' ");
		
		query.append("WHEN 10 THEN 'Outubro' ");
		query.append("WHEN 11 THEN 'Novembro' ");
		query.append("WHEN 12 THEN 'Dezembro' ");
		
		query.append("END AS nome_mes ");
		
		query.append("from ponto p ");
		query.append("left join config_ponto c on (p.id_config_ponto = c.id) where 1=1 ");
		query.append("and  extract(YEAR FROM p.data) = ");
		query.append(ano);
		query = andEqual(query, "c.id_funcionario",idFuncionario);
		query.append(" order by 1 ");
		
		
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new MesDTOMapper());
	}
	
	@Override
	public List<AusenteDTO> listarAusentes(FiltroPonto filtro) {
		LocalDate dataInicial = DateUtil.paraLocalDate(filtro.getAno(), filtro.getMes(), 1);
		LocalDate dataFinal = dataInicial.with(TemporalAdjusters.lastDayOfMonth());
		StringBuilder query = createQuery("");
		query = sqlListaAusentes(filtro, query, EnumTipoPeriodoPonto.PRIMEIRO_PERIODO, 1, dataInicial, dataFinal);
		query.append(" union ");
		query = sqlListaAusentes(filtro, query, EnumTipoPeriodoPonto.SEGUNDO_PERIODO, 2, dataInicial, dataFinal);
		query.append(" union ");
		query = sqlListaAusentes(filtro, query, EnumTipoPeriodoPonto.TERCEIRO_PERIODO, 3, dataInicial, dataFinal);
		query.append(" order by 2,1 ");
		
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new AusenteDTOMapper());
	}

	private StringBuilder sqlListaAusentes(FiltroPonto filtro, StringBuilder query, EnumTipoPeriodoPonto enumTipoPeriodoPonto, Integer numeroHoraInicialPonto,
			LocalDate dataInicial, LocalDate dataFinal) {
		
		query.append("select ");
		query.append(enumTipoPeriodoPonto.id());
		query.append(" as id_tipo_periodo_ponto, ");
		query.append("d.dg as data_geracao, cp.hora_inicial_p");
		query.append(numeroHoraInicialPonto);
		query.append(" as hora_inicial_prevista, cp.id_funcionario ");
		query.append("from config_ponto cp ");
		query.append("left join (select * from (select generate_series( (date '");
		query.append(dataInicial.toString());
		query.append("')::date, (date '");
		query.append(dataFinal.toString());
		query.append("')::date, interval '1 day' ) dg ) as dias) d on (1 = 1) ");
		query.append("where cp.hora_inicial_p");
		query.append(numeroHoraInicialPonto);
		query.append(" is not null ");
		query = andEqual(query, "cp.id_funcionario", filtro.getIdFuncionario());
		query.append(" and not exists ( ");
		query.append("select p.id_tipo_periodo_ponto, p.data ");
		query.append("from ponto p ");
		query.append("left join config_ponto x on (p.id_config_ponto = x.id) where 1=1 ");
		query = andEqual(query, "cp.id_funcionario", filtro.getIdFuncionario());
		query.append(" and date_part('month', p.data) = ");
		query.append(filtro.getMes());
		query.append(" and date_part('year', p.data) = ");
		query.append(filtro.getAno());
		query.append(" and p.id_tipo_periodo_ponto = ");
		query.append(enumTipoPeriodoPonto.id());
		query.append(" and p.data = d.dg ) ");
		if (filtro.getIdsDiasSubrimidos() !=null &&  filtro.getIdsDiasSubrimidos().length > 0) {
			query.append(" and extract(DOW from d.dg) not in (");
			for (int id : filtro.getIdsDiasSubrimidos()) {
				query.append(id);
				query.append(",");
			}
			query.replace(query.length()-1, query.length(), " ");
			query.append(") ");
		}
		return query;
	}

	
	@Override
	public List<TipoPeriodoPontoDTO> listarTiposPeriodos(FiltroPonto filtro) {
		StringBuilder query = createQuery("select t.id, t.nome ");
		query.append("from tipo_periodo_ponto t where ");
		query.append("t.id in ( ");
		
		query.append("select 1 from config_ponto c ");
		query.append("where c.hora_inicial_p1 is not null ");
		query.append("and current_date between c.data_inicial_vigencia and c.data_final_vigencia ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		
		query.append(" union ");
		
		query.append("select 2 from config_ponto c ");
		query.append("where c.hora_inicial_p2 is not null ");
		query.append("and current_date between c.data_inicial_vigencia and c.data_final_vigencia ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		
		query.append(" union ");
		
		query.append("select 3 from config_ponto c ");
		query.append("where c.hora_inicial_p3 is not null ");
		query.append("and current_date between c.data_inicial_vigencia and c.data_final_vigencia ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		query.append(") ");
		
		query.append(" and not exists ( ");
		query.append("select id_tipo_periodo_ponto from ponto p left join config_ponto c on (p.id_config_ponto = c.id) ");
		query.append("where p.data = current_date ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		query.append(" and p.id_tipo_periodo_ponto = t.id) ");
		
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new TipoPeriodoPontoDTOMapper());
	}
	
	@Override
	public PontoDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select p.id, p.data, p.id_tipo_periodo_ponto, p.hora_inicial_registro, p.hora_inicial_informado, p.hora_final_informado, p.id_config_ponto ");
			query.append("from ponto p where 1=1 ");
			query = andEqual(query, "p.id", id);
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
					new PontoDTOParaEdicaoMapper());
		} catch (EmptyResultDataAccessException e) {
			log.info("Ponto não existe - " + id);
			return null;
		}
	}
	
	@Override
	public PontoDTO recuperarHorasPrevistas(FiltroPonto filtro) {
		try {
			StringBuilder query = createQuery("select ");
			if (filtro.getIdTipoPeriodoPonto().equals(EnumTipoPeriodoPonto.PRIMEIRO_PERIODO.id())) {
				query.append("c.hora_inicial_p1 as hora_inicial_prevista, c.hora_final_p1 as hora_final_prevista ");
			}
			if (filtro.getIdTipoPeriodoPonto().equals(EnumTipoPeriodoPonto.SEGUNDO_PERIODO.id())) {
				query.append("c.hora_inicial_p2 as hora_inicial_prevista, c.hora_final_p2 as hora_final_prevista ");
			}
			if (filtro.getIdTipoPeriodoPonto().equals(EnumTipoPeriodoPonto.TERCEIRO_PERIODO.id())) {
				query.append("c.hora_inicial_p3 as hora_inicial_prevista, c.hora_final_p3 as hora_final_prevista ");
			}
			query.append("from config_ponto c ");
			query.append("where c.id = (select id from config_ponto cp ");
			query.append("where current_date between cp.data_inicial_vigencia and cp.data_final_vigencia ");
			query = andEqual(query, "cp.id_funcionario", filtro.getIdFuncionario());
			query.append(") ");
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
					new PontoDTOParaHorasPrevistasMapper());
		} catch (EmptyResultDataAccessException e) {
			log.info("Horas Previstas não existem para o funcionario - " + filtro.getIdFuncionario());
			return null;
		}
	}
	
	
	@Override
	public List<PontoDTO> listar(FiltroPonto filtro) {
		StringBuilder query = createQuery(
				"select p.id, p.descricao, p.tarefa_online, p.id_tipo_periodo_ponto, p.data, t.nome as periodo, p.hora_inicial_registro, p.hora_final_registro, ");
		query.append("p.hora_inicial_informado, p.hora_final_informado, p.data_homologacao, p.descricao as tarefa, ");
		
		adicionarCaseHoraInicialPrevista(query);
		
		adicionarCaseHoraFinalPrevista(query);
		
		adicionarCaseToleranciaEntrada(query);
		
		adicionarCaseToleranciaSaida(query);
		
		query.append("from ponto p ");
		query.append("left join config_ponto c on (p.id_config_ponto = c.id) ");
		query.append("left join tipo_periodo_ponto t on (p.id_tipo_periodo_ponto = t.id) where 1=1 ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		
		if(filtro.isPendente()) {
			query.append(" and p.hora_final_informado is null ");
		}
		if (filtro.getAno() != null) {
			query.append(" and extract(YEAR FROM p.data) = ");
			query.append(filtro.getAno());
		}
		if (filtro.getMes() != null) {
			query.append(" and extract(MONTH FROM p.data) = ");
			query.append(filtro.getMes());
		}
		query = orderBy(query, Order.DESC, "p.data, p.id_tipo_periodo_ponto");
		
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new PontoDTOMapper());
	}

	private void adicionarCaseToleranciaSaida(StringBuilder query) {
		query.append("CASE ");
		query.append("WHEN p.hora_final_informado is null or p.data_liberacao is not null THEN true else false ");
		query.append("END ");
		
		//-- as hoje,
		query.append("and ");
		
		query.append("CASE ");
		query.append("WHEN p.data = current_date or p.data_liberacao is not null THEN true else false ");
		query.append("END ");
		
		//-- as hoje,
		query.append("and ");
		
		query.append("CASE ");
		query.append("WHEN c.tolerancia = '00:00:00' THEN true ");
		query.append("WHEN p.data_liberacao is not null and p.hora_final_informado is null THEN true ");
		query.append("WHEN id_tipo_periodo_ponto = 1 and justify_hours(cast(current_time as time) - c.hora_final_p1) > tolerancia THEN false ");
		query.append("WHEN id_tipo_periodo_ponto = 2 and justify_hours(cast(current_time as time) - c.hora_final_p2) > tolerancia THEN false ");
		query.append("WHEN id_tipo_periodo_ponto = 3 and justify_hours(cast(current_time as time) - c.hora_final_p3) > tolerancia THEN false ");
		query.append("WHEN id_tipo_periodo_ponto = 4 THEN false ");
		query.append("ELSE true ");
		query.append("END as tolerancia_saida ");
	}

	private void adicionarCaseToleranciaEntrada(StringBuilder query) {
		query.append("CASE ");
		
		query.append("WHEN p.hora_inicial_informado is null or p.data_liberacao is not null ");
		query.append("THEN true ");
		query.append("ELSE false ");
		query.append("END ");
		
		query.append("and ");
		
		query.append("CASE ");
		query.append("WHEN p.data = current_date or p.data_liberacao is not null  ");
		query.append("THEN true ");
		query.append("ELSE false ");
		query.append("END ");
		
		query.append("and ");
		
		query.append("CASE ");
		query.append("WHEN c.tolerancia = '00:00:00' THEN true ");
		query.append("WHEN p.data_liberacao is not null and p.hora_inicial_informado is null THEN true ");
		query.append("WHEN id_tipo_periodo_ponto = 1 and justify_hours(cast(current_time as time) - c.hora_inicial_p1) > tolerancia THEN false ");
		query.append("WHEN id_tipo_periodo_ponto = 2 and justify_hours(cast(current_time as time) - c.hora_inicial_p2) > tolerancia THEN false ");
		query.append("WHEN id_tipo_periodo_ponto = 3 and justify_hours(cast(current_time as time) - c.hora_inicial_p3) > tolerancia THEN false ");
		query.append("WHEN id_tipo_periodo_ponto = 4 THEN false ");
		query.append("ELSE true ");
		query.append("END as tolerancia_entrada, ");
	}

	private void adicionarCaseHoraFinalPrevista(StringBuilder query) {
		query.append("CASE ");
		
		query.append("WHEN id_tipo_periodo_ponto = 1 THEN c.hora_final_p1  ");
		query.append("WHEN id_tipo_periodo_ponto = 2 THEN c.hora_final_p2 ");
		query.append("WHEN id_tipo_periodo_ponto = 3 THEN c.hora_final_p3 ");
		query.append("ELSE Null ");
		query.append("END as hora_final_prevista, ");
	}

	private void adicionarCaseHoraInicialPrevista(StringBuilder query) {
		query.append("CASE ");
		
		query.append("WHEN id_tipo_periodo_ponto = 1 THEN c.hora_inicial_p1 ");
		query.append("WHEN id_tipo_periodo_ponto = 2 THEN c.hora_inicial_p2 ");
		query.append("WHEN id_tipo_periodo_ponto = 3 THEN c.hora_inicial_p3 ");
		query.append("ELSE Null ");
		query.append("END as hora_inicial_prevista, ");
	}
	
	@Override
	public int contar(FiltroPonto filtro) {
		StringBuilder query = createQuery("select count(p.id) ");
		query.append("from ponto p ");
		query.append("left join config_ponto c on (p.id_config_ponto = c.id) ");
		query.append("left join tipo_periodo_ponto t on (p.id_tipo_periodo_ponto = t.id) where 1=1 ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		if(filtro.isPendente()) {
			query.append(" and p.hora_final_informado is null ");
		}
		if (filtro.getAno() != null) {
			query.append(" and extract(YEAR FROM p.data) = ");
			query.append(filtro.getAno());
		}
		if (filtro.getMes() != null) {
			query.append(" and extract(MONTH FROM p.data) = ");
			query.append(filtro.getMes());
		}
		
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}
	
	private class PontoDTOParaEdicaoMapper implements RowMapper<PontoDTO> {

		@Override
		public PontoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PontoDTO.builder()
					.id(rs.getLong("id"))
					.data(rs.getDate("data").toLocalDate())
					.horaInicialRegistro(rs.getTime("hora_inicial_registro") != null ? rs.getTime("hora_inicial_registro").toLocalTime() : null)
					.horaInicialInformado(rs.getTime("hora_inicial_informado") != null ? rs.getTime("hora_inicial_informado").toLocalTime() : null)
					.horaFinalInformado(rs.getTime("hora_final_informado") != null ? rs.getTime("hora_final_informado").toLocalTime() : null)
					.idTipoPeriodoPonto(rs.getInt("id_tipo_periodo_ponto"))
					.idConfiguracaoPonto(rs.getLong("id_config_ponto"))
					.build();
		}

	}
	
	private class PontoDTOParaHorasPrevistasMapper implements RowMapper<PontoDTO> {

		@Override
		public PontoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PontoDTO.builder()
					.horaInicialPrevista(rs.getTime("hora_inicial_prevista") != null ? rs.getTime("hora_inicial_prevista").toLocalTime() : null)
					.horaFinalPrevista(rs.getTime("hora_final_prevista") != null ? rs.getTime("hora_final_prevista").toLocalTime() : null)
					.build();
		}

	}
	
	private class PontoDTOMapper implements RowMapper<PontoDTO> {

		@Override
		public PontoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PontoDTO.builder()
					.id(rs.getLong("id"))
					.data(rs.getDate("data").toLocalDate())
					.idTipoPeriodoPonto(rs.getInt("id_tipo_periodo_ponto"))
					.horaInicialRegistro(rs.getTime("hora_inicial_registro") != null ? rs.getTime("hora_inicial_registro").toLocalTime() : null)
					.horaFinalRegistro(rs.getTime("hora_final_registro") != null ? rs.getTime("hora_final_registro").toLocalTime() : null)
					.horaInicialInformado(rs.getTime("hora_inicial_informado") != null ? rs.getTime("hora_inicial_informado").toLocalTime() : null)
					.horaFinalInformado(rs.getTime("hora_final_informado") != null ? rs.getTime("hora_final_informado").toLocalTime() : null)
					.dataHomologacao(rs.getDate("data_homologacao") !=  null ? rs.getDate("data_homologacao").toLocalDate() : null)
					.descricao(rs.getString("descricao"))
					.horaInicialPrevista(rs.getTime("hora_inicial_prevista") != null ? rs.getTime("hora_inicial_prevista").toLocalTime() : null)
					.horaFinalPrevista(rs.getTime("hora_final_prevista") != null ? rs.getTime("hora_final_prevista").toLocalTime() : null)
					.horaInicialInformado(rs.getTime("hora_inicial_informado") != null ? rs.getTime("hora_inicial_informado").toLocalTime() : null)
					.horaFinalInformado(rs.getTime("hora_final_informado") != null ? rs.getTime("hora_final_informado").toLocalTime() : null)
					.toleranciaEntrada(rs.getObject("tolerancia_entrada") != null ? rs.getBoolean("tolerancia_entrada") : null)
					.toleranciaSaida(rs.getObject("tolerancia_saida") != null ? rs.getBoolean("tolerancia_saida") : null)
					.tarefaOnline(rs.getBoolean("tarefa_online"))
					.build();
		}

	}
	
	private class TipoPeriodoPontoDTOMapper implements RowMapper<TipoPeriodoPontoDTO> {

		@Override
		public TipoPeriodoPontoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TipoPeriodoPontoDTO.builder()
					.id(rs.getInt("id"))
					.nome(rs.getString("nome"))
					.build();
		}

	}
	
	private class PontoDTOParaFolhaDePontoMapper implements RowMapper<PontoDTO> {

		@Override
		public PontoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PontoDTO.builder()
					.id(rs.getLong("id"))
					.data(rs.getDate("data").toLocalDate())
					.idTipoPeriodoPonto(rs.getInt("id_tipo_periodo_ponto"))
					.horaInicialRegistro(rs.getTime("hora_inicial_registro") != null ? rs.getTime("hora_inicial_registro").toLocalTime() : null)
					.horaFinalRegistro(rs.getTime("hora_final_registro") != null ? rs.getTime("hora_final_registro").toLocalTime() : null)
					.horaInicialInformado(rs.getTime("hora_inicial_informado") != null ? rs.getTime("hora_inicial_informado").toLocalTime() : null)
					.horaFinalInformado(rs.getTime("hora_final_informado") != null ? rs.getTime("hora_final_informado").toLocalTime() : null)
					.dataHomologacao(rs.getDate("data_homologacao") !=  null ? rs.getDate("data_homologacao").toLocalDate() : null)
					.descricao(rs.getString("descricao"))
					.horaInicialPrevista(rs.getTime("hora_inicial_prevista") != null ? rs.getTime("hora_inicial_prevista").toLocalTime() : null)
					.horaFinalPrevista(rs.getTime("hora_final_prevista") != null ? rs.getTime("hora_final_prevista").toLocalTime() : null)
					.horaInicialInformado(rs.getTime("hora_inicial_informado") != null ? rs.getTime("hora_inicial_informado").toLocalTime() : null)
					.horaFinalInformado(rs.getTime("hora_final_informado") != null ? rs.getTime("hora_final_informado").toLocalTime() : null)
					.funcionarioHomologacao(rs.getString("func_homologacao"))
					.funcionarioLiberacao(rs.getString("func_liberacao"))
					.diaSemana(rs.getString("dia_semana"))
					.tarefaOnline(rs.getBoolean("tarefa_online"))
					.dataLiberacao(rs.getDate("data_liberacao") !=  null ? rs.getDate("data_liberacao").toLocalDate() : null)
					.build();
		}

	}
	
	@Override
	public List<PontoDTO> listarParaFolhaDePonto(FiltroPonto filtro) {
		StringBuilder query = createQuery("select p.id, p.data, p.id_tipo_periodo_ponto, p.hora_inicial_registro, p.hora_final_registro, p.hora_inicial_informado, p.hora_final_informado, ");
		query.append("p.data_liberacao, p.data_homologacao, p.descricao, pl.nome as func_liberacao, pl.nome as func_homologacao, p.tarefa_online, ");
		
		query.append("case ");
		query.append("extract( DOW from p.data) ");
		query.append("when 0 then 'Domingo' ");
		query.append("when 1 then 'Segunda' ");
		query.append("when 2 then 'Terça' ");
		query.append("when 3 then 'Quarta' ");
		query.append("when 4 then 'Quinta' ");
		query.append("when 5 then 'Sexta' ");
		query.append("when 6 then 'Sábado' ");
		query.append("end as dia_semana, ");
		
		query.append("case ");
		query.append("when p.id_tipo_periodo_ponto = 1 then c.hora_inicial_p1 ");
		query.append("when p.id_tipo_periodo_ponto = 2 then c.hora_inicial_p2 ");
		query.append("when p.id_tipo_periodo_ponto = 3 then c.hora_inicial_p3 ");
		query.append("else null  ");
		query.append("end as hora_inicial_prevista, ");
		
		query.append("case ");
		query.append("when p.id_tipo_periodo_ponto = 1 then c.hora_final_p1 ");
		query.append("when p.id_tipo_periodo_ponto = 2 then c.hora_final_p2 ");
		query.append("when p.id_tipo_periodo_ponto = 3 then c.hora_final_p3 ");
		query.append("else null  ");
		query.append("end as hora_final_prevista ");
		

		
		query.append("from ponto p ");
		query.append("left join config_ponto c on (p.id_config_ponto = c.id) ");
		query.append("left join tipo_periodo_ponto t on (p.id_tipo_periodo_ponto = t.id) ");
		
		query.append("left join funcionario fl on (p.id_funcionario_liberacao = fl.id) ");
		query.append("left join pessoa pl on (fl.id_pessoa = pl.id) ");
		query.append("left join funcionario fh on (p.id_funcionario_homologacao = fh.id) ");
		query.append("left join pessoa ph on (fh.id_pessoa = ph.id) where 1=1 ");
		
		
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		
		if(filtro.isPendente()) {
			query.append(" and p.hora_final_informado is null ");
		}
		if (filtro.getAno() != null) {
			query.append(" and extract(YEAR FROM p.data) = ");
			query.append(filtro.getAno());
		}
		if (filtro.getMes() != null) {
			query.append(" and extract(MONTH FROM p.data) = ");
			query.append(filtro.getMes());
		}
		query = orderBy(query, Order.DESC, "p.data, p.id_tipo_periodo_ponto");
		
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new PontoDTOParaFolhaDePontoMapper());
	}
	
	@Override
	public int contarParaFolhaDePonto(FiltroPonto filtro) {
		StringBuilder query = createQuery("select count(p.id) ");
		query.append("from ponto p ");
		query.append("left join config_ponto c on (p.id_config_ponto = c.id) ");
		query.append("left join tipo_periodo_ponto t on (p.id_tipo_periodo_ponto = t.id) ");
		query.append("left join funcionario fl on (p.id_funcionario_liberacao = fl.id) ");
		query.append("left join pessoa pl on (fl.id_pessoa = pl.id) ");
		query.append("left join funcionario fh on (p.id_funcionario_homologacao = fh.id) ");
		query.append("left join pessoa ph on (fh.id_pessoa = ph.id) where 1=1 ");
		
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		if(filtro.isPendente()) {
			query.append(" and p.hora_final_informado is null ");
		}
		if (filtro.getAno() != null) {
			query.append(" and extract(YEAR FROM p.data) = ");
			query.append(filtro.getAno());
		}
		if (filtro.getMes() != null) {
			query.append(" and extract(MONTH FROM p.data) = ");
			query.append(filtro.getMes());
		}
		
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}

	private class MesDTOMapper implements RowMapper<MesDTO> {

		@Override
		public MesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return MesDTO.builder()
					.numero(rs.getInt("numero_mes"))
					.nome(rs.getString("nome_mes"))
					.build();
		}

	}
	
	private class AusenteDTOMapper implements RowMapper<AusenteDTO> {

		@Override
		public AusenteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AusenteDTO.builder()
					.idTipoPeriodoPonto(rs.getInt("id_tipo_periodo_ponto"))
					.horaInicialPrevista((rs.getTime("hora_inicial_prevista") != null ? rs.getTime("hora_inicial_prevista").toLocalTime() : null))
					.dataGeracao((rs.getTimestamp("data_geracao") != null ? rs.getTimestamp("data_geracao").toLocalDateTime().toLocalDate() : null))
					.diaDaSemana(EnumTipoDiaSemana.porId(rs.getTimestamp("data_geracao").toLocalDateTime().toLocalDate().getDayOfWeek().getValue()).label())
					.idFuncionario(rs.getLong("id_funcionario"))
					.build();
		}

	}

}
