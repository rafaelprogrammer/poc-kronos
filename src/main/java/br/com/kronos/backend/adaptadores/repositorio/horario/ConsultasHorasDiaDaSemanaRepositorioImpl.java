package br.com.kronos.backend.adaptadores.repositorio.horario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.horario.ConsultasHorariosDiaDaSemanaRepositorio;
import br.com.kronos.backend.aplicacao.horario.FiltroHorario;
import br.com.kronos.backend.aplicacao.horario.api.HoraAtividadeDiaDaSemanaDTO;

public class ConsultasHorasDiaDaSemanaRepositorioImpl extends SqlQueryRepositorio implements ConsultasHorariosDiaDaSemanaRepositorio  {

	public ConsultasHorasDiaDaSemanaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * -- Horários da turma já reservado para o professor
	 * -- horário e horas atividades referente aos horários com dia da semana, professor,
	 * -- turma, disciplina, data inicial e data final iguais
	 * @return StringBuilder
	 */
	@Override
	public StringBuilder listarDosDiasDaSemana(FiltroHorario filtro) {
		StringBuilder query = createQuery("SELECT hh.id_horario, ha.id, ha.hora_inicial, ha.hora_final, cast ('(1)' as varchar(3)) as situacao, cast (true as boolean) as editavel, cast (true as boolean) as turma ");
		query.append("FROM hora_atividade ha ");
		query.append("JOIN horario_hora_atividade hh ON (ha.id = hh.id_hora_atividade) ");
		query.append("JOIN horario h ON (hh.id_horario = h.id) where 1=1 ");
		query.append("and h.regular = true ");
		query.append("and h.id_tipo_dia_semana = ");
		query.append(filtro.getIdTipoDiaSemana());
		query.append("and h.id_funcionario = ");
		query.append(filtro.getIdFuncionario());
		query.append("and h.id_turma = ");
		query.append(filtro.getIdTurma());
		query.append("and h.id_disciplina = ");
		query.append(filtro.getIdDisciplina());
		query.append("and ha.id_tipo_matriz_horario = ");
		query.append(filtro.getIdTipoMatrizHorario());
		query.append(" and h.data_inicial = ");
		query.append("to_date('");
		query.append(filtro.getDataInicial().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append("and h.data_final = ");
		query.append("to_date('");
		query.append(filtro.getDataFinal().toString());
		query.append("', 'yyyy-MM-dd') ");
		return query;
	}
	
	/**
	 * -- Horários da turma que o professor está ocupado em outra disciplina
	 * -- horário e horas atividades (nao editaveis) referente aos horários com dia da semana, professor,
	 * -- turma, disciplinas diferentes da selecionada, data inicial e data final iguais
	 * @return StringBuilder
	 */
	@Override
	public StringBuilder listarDosDiasDaSemanaNaoEditavel(FiltroHorario filtro) {
		StringBuilder query = createQuery("SELECT hh.id_horario, ha.id, ha.hora_inicial, ha.hora_final, cast ('(2)' as varchar(5)) as situacao, cast (false as boolean) as editavel, cast (true as boolean) as turma ");
		query.append("FROM hora_atividade ha ");
		query.append("JOIN horario_hora_atividade hh ON (ha.id = hh.id_hora_atividade) ");
		query.append("JOIN horario h ON (hh.id_horario = h.id) where 1=1 ");
		query.append("and h.regular = true ");
		query.append("and h.id_tipo_dia_semana = ");
		query.append(filtro.getIdTipoDiaSemana());
		query.append(" and h.id_funcionario = ");
		query.append(filtro.getIdFuncionario());
		query.append(" and h.id_turma = ");
		query.append(filtro.getIdTurma());
		query.append(" and h.id_disciplina <> ");
		query.append(filtro.getIdDisciplina());
		query.append(" and ha.id_tipo_matriz_horario = ");
		query.append(filtro.getIdTipoMatrizHorario());
		
		addCondicaoPorPeriodo(filtro.getDataInicial(), filtro.getDataInicial(), query);
		
		return query;
	}
	
	/**
	 * -- Horários da turma já ocupados por outro professor
	 * @return StringBuilder
	 */
	@Override
	public StringBuilder listarDosDiasDaSemanaJaOcupadosPorOutroProfessor(FiltroHorario filtro) {
		StringBuilder query = createQuery("SELECT hh.id_horario, ha.id, ha.hora_inicial, ha.hora_final, cast ('(3)' as varchar(3)) as situacao, cast (false as boolean) as editavel, cast (true as boolean) as turma ");
		query.append("FROM hora_atividade ha ");
		query.append("JOIN horario_hora_atividade hh ON (ha.id = hh.id_hora_atividade) ");
		query.append("JOIN horario h ON (hh.id_horario = h.id) where 1=1 ");
		query.append(" and h.regular = true ");
		query.append(" and h.id_tipo_dia_semana = ");
		query.append(filtro.getIdTipoDiaSemana());
		query.append(" and h.id_funcionario <> ");
		query.append(filtro.getIdFuncionario());
		query.append(" and h.id_turma = ");
		query.append(filtro.getIdTurma());
		query.append(" and ha.id_tipo_matriz_horario = ");
		query.append(filtro.getIdTipoMatrizHorario());
		
		addCondicaoPorPeriodo(filtro.getDataInicial(), filtro.getDataFinal(), query);
		
		return query;
	}
	/**
	 * -- horários que estão livres no dia da semana e turma
	 * @return StringBuilder
	 */
	@Override
	public StringBuilder listarDosDiasDaSemanaQueEstaoLivres(FiltroHorario filtro) {
		StringBuilder query = createQuery("SELECT null as id_horario, ha.id, ha.hora_inicial, ha.hora_final, cast ('(4)' as varchar(3)) as situacao, cast (true as boolean) as editavel, cast (true as boolean) as turma ");
		query.append("FROM hora_atividade ha ");
		query.append("where ha.id_tipo_matriz_horario = ");
		query.append(filtro.getIdTipoMatrizHorario());
		
		query.append(" and not exists ");
		//Subselect
		query.append("  (SELECT hh.id_hora_atividade FROM horario_hora_atividade hh ");
		query.append("    JOIN horario h ON (hh.id_horario = h.id) where 1=1 ");
		query.append("      and h.regular = true ");
		query.append("      and h.id_tipo_dia_semana = ");
		query.append(filtro.getIdTipoDiaSemana());
		query.append("      and h.id_turma = ");
		query.append(filtro.getIdTurma());
		addCondicaoPorPeriodo(filtro.getDataInicial(), filtro.getDataFinal(), query);
		query.append("     and hh.id_hora_atividade = ha.id) ");
		//fim Subselect
		query.append("and not exists ");
		//Subselect
		query.append("  (SELECT hh.id_hora_atividade FROM horario_hora_atividade hh ");
		query.append("    JOIN horario h ON (hh.id_horario = h.id) where 1=1 ");
		query.append("      and h.regular = true ");
		query.append("      and h.id_tipo_dia_semana = ");
		query.append(filtro.getIdTipoDiaSemana());
		query.append("      and h.id_funcionario = ");
		query.append(filtro.getIdFuncionario());
		query.append("      and h.id_turma <> ");
		query.append(filtro.getIdTurma());
		query.append("      and h.id_disciplina <> ");
		query.append(filtro.getIdDisciplina());
		
		addCondicaoPorPeriodo(filtro.getDataInicial(), filtro.getDataFinal(), query);
		
		query.append("     and hh.id_hora_atividade = ha.id) ");
		//fim Subselect
		
		return query;
		
	}

	/**
	 * -- horários livres no dia de semana e turma mas ocupados pelo professor em outra turma
	 * @return StringBuilder
	 */
	@Override
	public StringBuilder listarDosDiasDaSemanaOcupadosPeloProfessorEmOutraTurma(FiltroHorario filtro) {
		StringBuilder query = createQuery("SELECT null as id_horario, ha.id, ha.hora_inicial, ha.hora_final, cast ('(5)' as varchar(5)) as situacao, cast (false as boolean) as editavel, cast (false as boolean) as turma ");
		query.append("FROM hora_atividade ha ");
		query.append("where ha.id_tipo_matriz_horario = ");
		query.append(filtro.getIdTipoMatrizHorario());
		query.append(" and not exists ");
		//Subselect
		query.append("  (SELECT hh.id_hora_atividade FROM horario_hora_atividade hh ");
		query.append("    JOIN horario h ON (hh.id_horario = h.id) where 1=1 ");
		query.append("      and h.regular = true ");
		query.append("      and h.id_tipo_dia_semana = ");
		query.append(filtro.getIdTipoDiaSemana());
		query.append("      and h.id_turma = ");
		query.append(filtro.getIdTurma());
		addCondicaoPorPeriodo(filtro.getDataInicial(), filtro.getDataFinal(), query);
		query.append("     and hh.id_hora_atividade = ha.id) ");
		//fim Subselect
		query.append(" and exists ");
		//Subselect
		query.append("  (SELECT hh.id_hora_atividade FROM horario_hora_atividade hh ");
		query.append("    JOIN horario h ON (hh.id_horario = h.id) where 1=1 ");
		query.append("      and h.regular = true ");
		query.append("      and h.id_tipo_dia_semana = ");
		query.append(filtro.getIdTipoDiaSemana());
		query.append("      and h.id_funcionario = ");
		query.append(filtro.getIdFuncionario());
		query.append("      and h.id_turma <> ");
		query.append(filtro.getIdTurma());
		query.append("      and h.id_disciplina = ");
		query.append(filtro.getIdDisciplina());
		addCondicaoPorPeriodo(filtro.getDataInicial(), filtro.getDataFinal(), query);
		query.append("      and hh.id_hora_atividade = ha.id) ");
		
		//fim Subselect
		
		return query;
		
	}
	
	private class HoraAtividadeDiaDaSemanaDTOMapper implements RowMapper<HoraAtividadeDiaDaSemanaDTO> {

		@Override
		public HoraAtividadeDiaDaSemanaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return HoraAtividadeDiaDaSemanaDTO.builder()
					.idHorario(rs.getLong("id_horario") == 0 ? null : rs.getLong("id_horario"))
					.idAtividade(rs.getLong("id"))
                    .horaInicial(rs.getTime("hora_inicial").toLocalTime())
                    .horaFinal(rs.getTime("hora_final").toLocalTime())
                    .editavel(rs.getBoolean("editavel"))
                    .turma(rs.getBoolean("turma"))
                    .situacao(rs.getString("situacao"))
					.build();
		}
	}
	
	@Override
	public List<HoraAtividadeDiaDaSemanaDTO> listarHorasAtividadesDiaDaSemana(FiltroHorario filtro) {
		
		StringBuilder query = listarDosDiasDaSemana(filtro);
		query.append(" union ");
		query.append(
				listarDosDiasDaSemanaNaoEditavel(filtro));
		
		query.append(" union ");
		query.append(
				listarDosDiasDaSemanaJaOcupadosPorOutroProfessor(filtro));
		query.append(" union ");
		query.append(
				listarDosDiasDaSemanaQueEstaoLivres(filtro));
		query.append(" union ");
		query.append(
				listarDosDiasDaSemanaOcupadosPeloProfessorEmOutraTurma(filtro));
		query.append(" order by 3 ");
		
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new HoraAtividadeDiaDaSemanaDTOMapper());
		
	}
	
	private void addCondicaoPorPeriodo(LocalDate dataInicial, LocalDate dataFinal, StringBuilder query) {
		query.append(" and (");
		query.append("to_date('");
		query.append(dataFinal.toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" between data_inicial and data_final ");
		query.append(" or to_date('");
		query.append(dataInicial.toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append("between data_inicial and data_final ");
		
		query.append("or (");
		query.append("to_date('");
		query.append(dataFinal.toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" < h.data_inicial ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(dataInicial.toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append(" > h.data_final)) ");
	}
	
	
}
