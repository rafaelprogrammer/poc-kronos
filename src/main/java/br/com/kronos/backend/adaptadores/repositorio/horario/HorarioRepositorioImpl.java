package br.com.kronos.backend.adaptadores.repositorio.horario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.horario.FiltroHorario;
import br.com.kronos.backend.aplicacao.horario.Horario;
import br.com.kronos.backend.aplicacao.horario.HorarioRepositorio;
import br.com.kronos.backend.aplicacao.horario.api.HorarioDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HorarioRepositorioImpl extends SqlQueryRepositorio implements HorarioRepositorio {
	
	public HorarioRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Horario horario) {
        addFields("data_inicial", "data_final", "regular", "id_funcionario", 
                  "id_disciplina", "id_turma", "id_tipo_dia_semana", "id_tipo_regime_letivo");
		
		addValues(horario.getDataInicial(), horario.getDataFinal(), horario.isRegular(), horario.getIdFuncionario(), 
                  horario.getIdDisciplina(), horario.getIdTurma(),  horario.getIdTipoDiaSemana(),  horario.getIdTipoRegimeLetivo());
		
		return (long) insertAuto("horario"); 
    }
		
	@Override
	public Long alterar(Horario horario) {
		addFields("data_inicial", "data_final", "regular", "id_funcionario", 
                  "id_disciplina", "id_turma", "id_tipo_dia_semana", "id_tipo_regime_letivo");
		
		addValues(horario.getDataInicial(), horario.getDataFinal(), horario.isRegular(), horario.getIdFuncionario(), 
                  horario.getIdDisciplina(), horario.getIdTurma(),  horario.getIdTipoDiaSemana(),  horario.getIdTipoRegimeLetivo(),
                  horario.getId());
		
		return (long) update("horario", horario.getId());
	}
	
	@Override
	public Horario buscarPorId(Long id) {
		try {     
			StringBuilder query = createQuery("select h.id, h.data_inicial, h.data_final, h.regular, h.id_funcionario, ");
	        query.append("h.id_disciplina, h.id_turma, h.id_tipo_dia_semana, h.id_tipo_regime_letivo ");
	        query.append("from horario h where 1=1 ");
            query = andEqual(query, "h.id", id);
            Horario horario = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new HorarioMapper());
			return horario;
		} catch (EmptyResultDataAccessException e) {
			log.info("Horarário não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Horario> listarParaGerarAtividades(Long idTurma, Long idDisciplina, Long idPessoaUsuarioLogado) {
		 StringBuilder query = createQuery("select h.id, h.data_inicial, h.data_final, h.regular, h.id_funcionario, ");
         query.append("h.id_disciplina, h.id_turma, h.id_tipo_dia_semana, h.id_tipo_regime_letivo ");
         query.append("from horario h ");
         query.append("left join funcionario f on (h.id_funcionario = f.id) where 1=1 ");
         query = andEqual(query, "h.id_disciplina", idDisciplina);
         query = andEqual(query, "h.id_turma", idTurma);
         query = andEqual(query, "f.id_pessoa", idPessoaUsuarioLogado);
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new HorarioMapper());
	}
	
	@Override
	public List<Long> listarIdsHorario(FiltroHorario filtroHorario) {
		 StringBuilder query = createQuery("select h.id ");
        query.append("from horario h where 1=1 ");
		query = andEqual(query, "h.regular", filtroHorario.getRegular());   
        query = andEqual(query, "h.id_disciplina", filtroHorario.getIdDisciplina());
        query = andEqual(query, "h.id_turma", filtroHorario.getIdTurma());
        query = andEqual(query, "h.id_tipo_dia_semana", filtroHorario.getIdTipoDiaSemana());
        query = andEqual(query, "h.id_tipo_regime_letivo", filtroHorario.getIdTipoRegimeLetivo());
        query = andEqualDate(query, "h.data_inicial", filtroHorario.getDataInicial().toString());
        query = andEqualDate(query, "h.data_final", filtroHorario.getDataFinal().toString());
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Long.class);
	}
	
	@Override
	public boolean verificarSeExisteHorario(FiltroHorario filtroHorario) {
		
		StringBuilder query = createQuery("select case when count(*) > 0 then true else false end::boolean as existehorario ");
		query.append("from horario h where ");
		
		query.append("(");
		query.append("to_date('");
		query.append(filtroHorario.getDataInicial().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append("between data_inicial and data_final ");
		query.append("or to_date('");
		query.append(filtroHorario.getDataFinal().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append("between data_inicial and data_final ");
		
        query.append("or (");
		query.append("to_date('");
		query.append(filtroHorario.getDataInicial().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append("< h.data_inicial ");
		
		query.append("and ");
		query.append("to_date('");
		query.append(filtroHorario.getDataFinal().toString());
		query.append("', 'yyyy-MM-dd') ");
		query.append("> h.data_final)) ");
		
		query.append("and h.regular = true");
        
		query = andEqual(query, "h.id_funcionario", filtroHorario.getIdFuncionario());
        query = andEqual(query, "h.id_disciplina", filtroHorario.getIdDisciplina());
        query = andEqual(query, "h.id_turma", filtroHorario.getIdTurma());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Boolean.class);
		
	}
	
	@Override
	public List<HorarioDTO> listar(FiltroHorario filtroHorario) {
		 StringBuilder query = createQuery("select h.id, hh.id_hora_atividade, h.data_inicial, h.data_final, ha.hora_inicial, ha.hora_final, h.regular, h.id_funcionario, p.nome_usual as professor, ");
         query.append("h.id_disciplina, d.sigla as disciplina, h.id_turma, h.id_tipo_dia_semana, h.id_tipo_regime_letivo ");
         query.append("from horario h ");
         query.append("left join horario_hora_atividade hh ON (h.id = hh.id_horario) ");
         query.append("left join hora_atividade ha ON (hh.id_hora_atividade = ha.id) ");
         query.append("left join funcionario f ON (h.id_funcionario = f.id) ");
         query.append("left join pessoa p ON (f.id_pessoa = p.id) ");
         query.append("left join disciplina d ON (h.id_disciplina = d.id) where 1=1 ");
         query = andEqual(query, "h.id", filtroHorario.getId());       
		 query = andEqual(query, "h.regular", filtroHorario.getRegular());   
         query = andEqual(query, "h.id_disciplina", filtroHorario.getIdDisciplina());
         query = andEqual(query, "h.id_turma", filtroHorario.getIdTurma());
         query = andEqual(query, "h.id_tipo_dia_semana", filtroHorario.getIdTipoDiaSemana());
         query = andEqual(query, "h.id_tipo_regime_letivo", filtroHorario.getIdTipoRegimeLetivo());
         if (filtroHorario.getDataInicial() != null) {
        	 query = andEqualDate(query, "h.data_inicial", filtroHorario.getDataInicial().toString());
         }
         if (filtroHorario.getDataFinal() != null) {
        	 query = andEqualDate(query, "h.data_final", filtroHorario.getDataFinal().toString());
         }
         query = orderBy(query, Order.ASC, "h.id_tipo_dia_semana", "ha.hora_inicial");
		 query = limit(query, filtroHorario.getQtdTotal(), filtroHorario.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new HorarioDTOMapper());
	}
	
	@Override
	public int contar(FiltroHorario filtroHorario) {
		StringBuilder query = createQuery("select count(h.id) from horario h ");
		query.append("left join horario_hora_atividade hh ON (h.id = hh.id_horario) ");
        query.append("left join hora_atividade ha ON (hh.id_hora_atividade = ha.id) ");
        query.append("left join funcionario f ON (h.id_funcionario = f.id) ");
        query.append("left join pessoa p ON (f.id_pessoa = p.id) ");
        query.append("left join disciplina d ON (h.id_disciplina = d.id) where 1=1 ");
		query = andEqual(query, "h.id", filtroHorario.getId());
    	query = andEqual(query, "h.regular", filtroHorario.getRegular());  
        query = andEqual(query, "h.id_disciplina", filtroHorario.getIdDisciplina());
        query = andEqual(query, "h.id_turma", filtroHorario.getIdTurma());
        query = andEqual(query, "h.id_tipo_dia_semana", filtroHorario.getIdTipoDiaSemana());
        query = andEqual(query, "h.id_tipo_regime_letivo", filtroHorario.getIdTipoRegimeLetivo());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("horario", id);
		return true;
	}
	
	private class HorarioMapper implements RowMapper<Horario> {

		@Override
		public Horario mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Horario.builder()
					.id(rs.getLong("id"))
					.dataInicial(rs.getDate("data_inicial").toLocalDate())
                    .dataFinal(rs.getDate("data_final").toLocalDate())
                    .regular(rs.getBoolean("regular"))
                    .idFuncionario(rs.getLong("id_funcionario"))
                    .idDisciplina(rs.getLong("id_disciplina"))
                    .idTurma(rs.getLong("id_turma"))
                    .idTipoDiaSemana(rs.getInt("id_tipo_dia_semana"))
                    .idTipoRegimeLetivo(rs.getInt("id_tipo_regime_letivo"))
					.build();
		}
	}
	
	private class HorarioDTOMapper implements RowMapper<HorarioDTO> {

		@Override
		public HorarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return HorarioDTO.builder()
					.id(rs.getLong("id"))
					.idHoraAtividade(rs.getLong("id_hora_atividade"))
					.dataInicial(rs.getDate("data_inicial").toLocalDate())
                    .dataFinal(rs.getDate("data_final").toLocalDate())
                    .horaInicial(rs.getTime("hora_inicial").toLocalTime())
                    .horaFinal(rs.getTime("hora_final").toLocalTime())
                    .regular(rs.getBoolean("regular"))
                    .idFuncionario(rs.getLong("id_funcionario"))
                    .professor(rs.getString("professor"))
                    .idDisciplina(rs.getLong("id_disciplina"))
                    .disciplina(rs.getString("disciplina"))
                    .idTurma(rs.getLong("id_turma"))
                    .idTipoDiaSemana(rs.getInt("id_tipo_dia_semana"))
                    .idTipoRegimeLetivo(rs.getInt("id_tipo_regime_letivo"))
					.build();
		}
	}

}
