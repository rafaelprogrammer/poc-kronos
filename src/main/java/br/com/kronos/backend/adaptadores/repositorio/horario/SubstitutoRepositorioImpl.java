package br.com.kronos.backend.adaptadores.repositorio.horario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.horario.FiltroSubstituto;
import br.com.kronos.backend.aplicacao.horario.Substituto;
import br.com.kronos.backend.aplicacao.horario.SubstitutoRepositorio;
import br.com.kronos.backend.aplicacao.horario.api.DadosExcluiSubstitutoDTO;
import br.com.kronos.backend.aplicacao.horario.api.SubstitutoDTO;
import br.com.kronos.backend.aplicacao.horario.api.SubstitutoResumoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubstitutoRepositorioImpl extends SqlQueryRepositorio implements SubstitutoRepositorio {
	
	public SubstitutoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Substituto substituto) {
        addFields("data_inicial", "data_final", "id_funcionario", "id_horario" );
		
		addValues(substituto.getDataInicial(), substituto.getDataFinal(), substituto.getIdFuncionario(), substituto.getIdHorario());
		
		return (long) insertAuto("substituto"); 
    }

	@Override
	public Long alterar(Substituto substituto) {
		addFields("data_inicial", "data_final", "id_funcionario", "id_horario" );
		
		addValues(substituto.getDataInicial(), substituto.getDataFinal(), substituto.getIdFuncionario(), substituto.getIdHorario(),
                  substituto.getId());
		
		return (long) update("substituto", substituto.getId());
	}
	
	@Override
	public SubstitutoDTO buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select s.id, s.data_inicial, s.data_final, s.id_funcionario, p.nome, s.id_horario ");
            query.append("from substituto s ");
            query.append("join funcionario f on (f.id = s.id_funcionario) ");
            query.append("join pessoa p on (p.id = f.id_pessoa) where 1=1 ");
            query = andEqual(query, "s.id", id);
            SubstitutoDTO substituto = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new SubstitutoDTOMapper());
			return substituto;
		} catch (EmptyResultDataAccessException e) {
			log.info("Substituto não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<SubstitutoResumoDTO> listarParaProfessoresHorario(Long idDisciplina, Long idFuncionarioDoHorario) {
		 StringBuilder query = createQuery("select distinct f.id, p.nome from funcionario f ");
        query.append("left join disciplina_funcionario df on (f.id = df.id_funcionario) ");
        query.append("left join pessoa p on (f.id_pessoa = p.id) where 1=1 ");
        query = andEqual(query, "df.id_disciplina", idDisciplina);       
        query = and(query, "f.id", idFuncionarioDoHorario, "<>");
        query = orderBy(query, Order.ASC, "p.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new SubstitutoResumoDTOMapper());
	}
	
	@Override
	public List<SubstitutoDTO> listar(FiltroSubstituto filtroSubstituto) {
		 StringBuilder query = createQuery("select s.id, s.data_inicial, s.data_final, s.id_funcionario, p.nome, s.id_horario ");
         query.append("from substituto s ");
         query.append("join funcionario f on (f.id = s.id_funcionario) ");
         query.append("join pessoa p on (p.id = f.id_pessoa) where 1=1 ");
         query = andEqual(query, "s.id", filtroSubstituto.getId());       
         query = andEqual(query, "s.id_funcionario", filtroSubstituto.getIdFuncionario());
         query = andEqual(query, "s.id_horario", filtroSubstituto.getIdHorario());
         query = orderBy(query, Order.DESC, "s.data_inicial");
		 query = limit(query, filtroSubstituto.getQtdTotal(), filtroSubstituto.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new SubstitutoDTOMapper());
	}
	
	@Override
	public long verificarSeExisteSubstituto(FiltroSubstituto filtroSubstituto) {
		StringBuilder query = createQuery("select count(s.id) from substituto s where 1=1 ");
		query = andEqual(query, "s.id_funcionario", filtroSubstituto.getIdFuncionario());
        query = andEqual(query, "s.id_horario", filtroSubstituto.getIdHorario());
        query.append(" and ((s.data_inicial <= to_date('");
        query.append(filtroSubstituto.getDataInicial().toString());
        query.append("', 'yyyy-MM-dd')");
        query.append(" and s.data_final >= to_date('");
        query.append(filtroSubstituto.getDataInicial().toString());
        query.append("', 'yyyy-MM-dd'))");
        query.append(" or (s.data_inicial <= to_date('");
        query.append(filtroSubstituto.getDataFinal().toString());
        query.append("', 'yyyy-MM-dd')");
        query.append(" and s.data_final >= to_date('");
        query.append(filtroSubstituto.getDataFinal().toString());
        query.append("', 'yyyy-MM-dd'))");
        query.append(" or (s.data_inicial > to_date('");
        query.append(filtroSubstituto.getDataInicial().toString());
        query.append("', 'yyyy-MM-dd')");
        query.append(" and s.data_final < to_date('");
        query.append(filtroSubstituto.getDataFinal().toString());
        query.append("', 'yyyy-MM-dd')))");
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Long.class);
	}
	
	@Override
	public int contar(FiltroSubstituto filtroSubstituto) {
		StringBuilder query = createQuery("select count(s.id) from substituto s ");
		query.append("join funcionario f on (f.id = s.id_funcionario) ");
        query.append("join pessoa p on (p.id = f.id_pessoa) where 1=1 ");
		query = andEqual(query, "s.id", filtroSubstituto.getId());
		query = andEqual(query, "s.id_funcionario", filtroSubstituto.getIdFuncionario());
        query = andEqual(query, "s.id_horario", filtroSubstituto.getIdHorario());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(DadosExcluiSubstitutoDTO dados) {
		StringBuilder query = createQuery("delete from substituto s where 1=1 ");
		query = andEqual(query, "s.id_funcionario", dados.getIdFuncionario());
		query = andEqualDate(query, "s.data_inicial", dados.getDataInicialSubstituto().toString());
		query = andEqualDate(query, "s.data_final", dados.getDataFinalSubstituto().toString());
		query.append(" and s.id_horario in ( ");
		query.append("select id from horario where 1=1 ");
		query = andEqual(query, "regular", dados.getRegular());
		query = andEqualDate(query, "data_inicial", dados.getDataInicialHorario().toString());
		query = andEqualDate(query, "data_final", dados.getDataFinalHorario().toString());
		query = andEqual(query, "id_funcionario", dados.getIdFuncionarioHorario());
		query = andEqual(query, "id_disciplina", dados.getIdDisciplina());
		query = andEqual(query, "id_turma", dados.getIdTurma());
		query = andEqual(query, "id_tipo_regime_letivo", dados.getIdTipoRegimeLetivo());
		query.append(") ");
		this.getNamedParameterJdbcTemplate().update(query.toString(), getMapSqlParameterSource());
		return true;
	}
	
	private class SubstitutoDTOMapper implements RowMapper<SubstitutoDTO> {

		@Override
		public SubstitutoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return SubstitutoDTO.builder()
					.id(rs.getLong("id"))
					.dataInicial(rs.getDate("data_inicial").toLocalDate())
                    .dataFinal(rs.getDate("data_final").toLocalDate())
                    .idFuncionario(rs.getLong("id_funcionario"))
                    .nomeFuncionario(rs.getString("nome"))
                    .idHorario(rs.getLong("id_horario"))
					.build();
		}
	}
	
	private class SubstitutoResumoDTOMapper implements RowMapper<SubstitutoResumoDTO> {

		@Override
		public SubstitutoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return SubstitutoResumoDTO.builder()
					.id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
					.build();
		}
	}
}

