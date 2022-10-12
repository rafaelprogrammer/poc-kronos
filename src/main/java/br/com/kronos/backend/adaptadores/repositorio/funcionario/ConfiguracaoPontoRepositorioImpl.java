package br.com.kronos.backend.adaptadores.repositorio.funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.ConfiguracaoPonto;
import br.com.kronos.backend.aplicacao.funcionario.ConfiguracaoPontoRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.EnumTipoPeriodoPonto;
import br.com.kronos.backend.aplicacao.funcionario.FiltroConfiguracaoPonto;
import br.com.kronos.backend.aplicacao.funcionario.api.ConfiguracaoPontoDTO;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfiguracaoPontoRepositorioImpl extends SqlQueryRepositorio implements ConfiguracaoPontoRepositorio {

	public ConfiguracaoPontoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(ConfiguracaoPonto configuracaoPonto) {
		addFields("id_funcionario", "tarefa_online", "hora_inicial_p1", "hora_final_p1",  "hora_inicial_p2", "hora_final_p2",
				"hora_inicial_p3", "hora_final_p3", "data_inicial_vigencia", "data_final_vigencia", "carga_horaria_semanal", "tolerancia");

		addValues(configuracaoPonto.getIdFuncionario(), configuracaoPonto.isTarefaOnline(), configuracaoPonto.getHoraInicialP1(),
				configuracaoPonto.getHoraFinalP1(), configuracaoPonto.getHoraInicialP2(), configuracaoPonto.getHoraFinalP2(),
				configuracaoPonto.getHoraInicialP3(), configuracaoPonto.getHoraFinalP3(), configuracaoPonto.getDataInicialVigencia(),
				configuracaoPonto.getDataFinalVigencia(), configuracaoPonto.getCargaHorariaSemanal(), configuracaoPonto.getTolerancia());

		return (long) insertAuto("config_ponto");
		
	}

	@Override
	public Long alterar(ConfiguracaoPonto configuracaoPonto) {
		addFields("id_funcionario", "tarefa_online", "hora_inicial_p1", "hora_final_p1",  "hora_inicial_p2", "hora_final_p2",
				"hora_inicial_p3", "hora_final_p3", "data_inicial_vigencia", "data_final_vigencia", "carga_horaria_semanal", "tolerancia");

		addValues(configuracaoPonto.getIdFuncionario(), configuracaoPonto.isTarefaOnline(), configuracaoPonto.getHoraInicialP1(),
				configuracaoPonto.getHoraFinalP1(), configuracaoPonto.getHoraInicialP2(), configuracaoPonto.getHoraFinalP2(),
				configuracaoPonto.getHoraInicialP3(), configuracaoPonto.getHoraFinalP3(), configuracaoPonto.getDataInicialVigencia(),
				configuracaoPonto.getDataFinalVigencia(), configuracaoPonto.getCargaHorariaSemanal(), configuracaoPonto.getTolerancia(),
				configuracaoPonto.getId());

		return (long) update("config_ponto", configuracaoPonto.getId());
	}
	

	@Override
	public ConfiguracaoPontoDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery(
					"select c.id, c.id_funcionario, c.data_inicial_vigencia, c.data_final_vigencia, c.carga_horaria_semanal, c.tarefa_online, c.hora_inicial_p1,  ");
			query.append("c.hora_final_p1, c.hora_inicial_p2, c.hora_final_p2, c.hora_inicial_p3, c.hora_final_p3, count(p.id) as qtd_ponto, c.tolerancia ");
			query.append("from config_ponto c ");
			query.append("left join ponto p on (c.id = p.id_config_ponto) where 1=1 ");
			query = andEqual(query, "c.id", id);
			query = groupBy(query, "c.id, c.id_funcionario, c.data_inicial_vigencia, c.data_final_vigencia, c.carga_horaria_semanal, c.tarefa_online, c.hora_inicial_p1, c.hora_final_p1, c.hora_inicial_p2, c.hora_final_p2, c.hora_inicial_p3, c.hora_final_p3");
			ConfiguracaoPontoDTO dto = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ConfiguracaoPontoDTOMapper());
			return dto;
		} catch (EmptyResultDataAccessException e) {
			log.info("configuracao ponto não existe - " + id);
			return null;
		}
	}
	
	@Override
	public Long recuperarIdPorIdFuncionario(Long idFuncionario) {
		try {
			StringBuilder query = createQuery("select id from config_ponto c ");
			query.append("where current_date between c.data_inicial_vigencia and c.data_final_vigencia ");
			query = andEqual(query, "c.id_funcionario", idFuncionario);
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), Long.class);
		} catch (EmptyResultDataAccessException e) {
			log.info("id configuracao ponto não existe para funcionario - " + idFuncionario);
			return null;
		}
	}
	
	@Override
	public List<ConfiguracaoPontoDTO> listar(FiltroConfiguracaoPonto filtro) {
		StringBuilder query = createQuery(
				"select c.id, c.id_funcionario, c.data_inicial_vigencia, c.data_final_vigencia, c.carga_horaria_semanal, c.tarefa_online, c.hora_inicial_p1,  ");
		query.append("c.hora_final_p1, c.hora_inicial_p2, c.hora_final_p2, c.hora_inicial_p3, c.hora_final_p3, count(p.id) as qtd_ponto, c.tolerancia ");
		query.append("from config_ponto c ");
		query.append("left join ponto p on (c.id = p.id_config_ponto) where 1=1 ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		query = groupBy(query, "c.id, c.id_funcionario, c.data_inicial_vigencia, c.data_final_vigencia, c.carga_horaria_semanal, c.tarefa_online, c.hora_inicial_p1, c.hora_final_p1, c.hora_inicial_p2, c.hora_final_p2, c.hora_inicial_p3, c.hora_final_p3");
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new ConfiguracaoPontoDTOMapper());
	}
	
	@Override
	public int contar(FiltroConfiguracaoPonto filtro) {
		StringBuilder query = createQuery("select count(c.id) ");
		query.append("from config_ponto c ");
		query.append("left join ponto p on (c.id = p.id_config_ponto) where 1=1 ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}
	
	@Override
	public boolean verificarSeExistePeloMenosUmaConfiguracao(FiltroConfiguracaoPonto filtro) {
		StringBuilder query = createQuery("select ");
		query.append(" CASE ");
		query.append(" WHEN ((select count(*) from ponto p where p.id_tipo_periodo_ponto = ");
		query.append(EnumTipoPeriodoPonto.PRIMEIRO_PERIODO.id());
		query.append(" and p.id_config_ponto = c.id and p.data = current_date) = 0) ");
		query.append(" and (c.hora_inicial_p1 is not null) ");
		query.append(" and (c.tolerancia = '00:00:00' or justify_hours(cast(current_time as time) - c.hora_inicial_p1) <= tolerancia) THEN true ");
		query.append(" WHEN ((select count(*) from ponto p where p.id_tipo_periodo_ponto = ");
		query.append(EnumTipoPeriodoPonto.SEGUNDO_PERIODO.id());
		query.append(" and p.id_config_ponto = c.id and p.data = current_date) = 0) and (c.hora_inicial_p2 is not null) ");
		query.append(" and (c.hora_inicial_p2 is not null) ");
		query.append(" and (c.tolerancia = '00:00:00' or justify_hours(cast(current_time as time) - c.hora_inicial_p2) <= tolerancia) THEN true ");
		query.append(" WHEN ((select count(*) from ponto p where p.id_tipo_periodo_ponto = ");
		query.append(EnumTipoPeriodoPonto.TERCEIRO_PERIODO.id());
		query.append(" and p.id_config_ponto = c.id and p.data = current_date) = 0) ");
		query.append(" and (c.hora_inicial_p3 is not null) ");
		query.append(" and (c.tolerancia = '00:00:00' or justify_hours(cast(current_time as time) - c.hora_inicial_p3) <= tolerancia) THEN true ");
		query.append(" ELSE false ");
		query.append(" END as habilitado ");
		query.append(" from config_ponto c ");
		query.append(" where current_date between c.data_inicial_vigencia and c.data_final_vigencia ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Boolean.class);
	}
	
	@Override
	public int verificarSeExistePeloMenosUmaConfiguracaoOnline(FiltroConfiguracaoPonto filtro) {
		StringBuilder query = createQuery("select count(id) from config_ponto c ");
		query.append("where current_date between c.data_inicial_vigencia and c.data_final_vigencia and c.tarefa_online = true ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}
	
	@Override
	public int verificarSeEstaSobrepondoOutraConfiguracao(FiltroConfiguracaoPonto filtro) {
		StringBuilder query = createQuery("select count(c.id) ");
		query.append("from config_ponto c where ");
		if (filtro.getId() !=null ) {
			query.append(" ( ");
		}
		query.append(" ((c.data_inicial_vigencia between ");
		query.append("to_date('");
		query.append(filtro.getDataInicialVigencia().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" and ");
		query.append("to_date('");
		query.append(filtro.getDataFinalVigencia().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" or c.data_final_vigencia between ");
		query.append("to_date('");
		query.append(filtro.getDataInicialVigencia().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" and ");
		query.append("to_date('");
		query.append(filtro.getDataFinalVigencia().toString());
		query.append("', 'yyyy-MM-dd')) ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		query.append(") ");
		query.append(" or (");
		query.append("to_date('");
		query.append(filtro.getDataInicialVigencia().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" between ");
		query.append(" c.data_inicial_vigencia and c.data_final_vigencia ");
		query.append(" and ");
		query.append("to_date('");
		query.append(filtro.getDataFinalVigencia().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" between ");
		query.append(" c.data_inicial_vigencia and c.data_final_vigencia ");
		query = andEqual(query, "c.id_funcionario", filtro.getIdFuncionario());
		query.append(") ");
		if (filtro.getId() !=null ) {
			query.append(" ) ");
			query = and(query, "c.id", filtro.getId(), "<>");
		}
		
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}
	
	@Override
	public int verificarSeExistePontosForaDaVigencia(FiltroConfiguracaoPonto filtro) {
		StringBuilder query = createQuery("select count(p.id) ");
		query.append("from ponto p where 1=1 ");
		query.append(" and (to_date('");
		query.append(DateUtil.dataAtual());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" < ");
		query.append("to_date('");
		query.append(filtro.getDataInicialVigencia().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" or ");
		query.append(" to_date('");
		query.append(DateUtil.dataAtual());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" > ");
		query.append("to_date('");
		query.append(filtro.getDataFinalVigencia().toString());
		query.append("', 'yyyy-MM-dd')) ");
		
		query = andEqual(query, "p.id_config_ponto", filtro.getId());
		
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("config_ponto", id);
		return true;
	}

	private class ConfiguracaoPontoDTOMapper implements RowMapper<ConfiguracaoPontoDTO> {

		@Override
		public ConfiguracaoPontoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ConfiguracaoPontoDTO.builder()
					.id(rs.getLong("id"))
					.idFuncionario(rs.getLong("id_funcionario"))
					.dataInicialVigencia(rs.getDate("data_inicial_vigencia").toLocalDate())
					.dataFinalVigencia(rs.getDate("data_final_vigencia").toLocalDate())
					.cargaHorariaSemanal(rs.getInt("carga_horaria_semanal"))
					.tarefaOnline(rs.getBoolean("tarefa_online"))
					.horaInicialP1(rs.getTime("hora_inicial_p1") != null ? rs.getTime("hora_inicial_p1").toLocalTime() : null)
					.horaFinalP1(rs.getTime("hora_final_p1") != null ? rs.getTime("hora_final_p1").toLocalTime() : null)
					.horaInicialP2(rs.getTime("hora_inicial_p2") != null ? rs.getTime("hora_inicial_p2").toLocalTime() : null)
					.horaFinalP2(rs.getTime("hora_final_p2") != null ? rs.getTime("hora_final_p2").toLocalTime() : null)
					.horaInicialP3(rs.getTime("hora_inicial_p3") != null ? rs.getTime("hora_inicial_p3").toLocalTime() : null)
					.horaFinalP3(rs.getTime("hora_final_p3") != null ? rs.getTime("hora_final_p3").toLocalTime() : null)
					.tolerancia(rs.getTime("tolerancia").toLocalTime())
					.qtdPonto(rs.getInt("qtd_ponto")).build();
		}

	}


}
