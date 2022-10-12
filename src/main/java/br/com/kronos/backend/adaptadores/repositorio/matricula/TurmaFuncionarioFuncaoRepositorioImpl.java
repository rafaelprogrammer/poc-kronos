package br.com.kronos.backend.adaptadores.repositorio.matricula;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurmaFuncionarioFuncao;
import br.com.kronos.backend.aplicacao.matricula.TurmaFuncionarioFuncao;
import br.com.kronos.backend.aplicacao.matricula.TurmaFuncionarioFuncaoRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TurmaFuncionarioFuncaoRepositorioImpl extends SqlQueryRepositorio implements TurmaFuncionarioFuncaoRepositorio {
	
	public TurmaFuncionarioFuncaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(TurmaFuncionarioFuncao turmaFuncionarioFuncao) {
        addFields("data_inicio_vigencia", "data_final_vigencia",  "id_funcionario", "id_turma", "id_tipo_funcao");
		
		addValues(turmaFuncionarioFuncao.getDataInicioVigencia(), turmaFuncionarioFuncao.getDataFinalVigencia(), turmaFuncionarioFuncao.getIdFuncionario(), turmaFuncionarioFuncao.getIdTurma(),
				turmaFuncionarioFuncao.getIdTipoFuncao()); 
		
		return (long) insertAuto("turmaFuncionarioFuncao"); 
    }
	
	@Override
	public Long alterar(TurmaFuncionarioFuncao turmaFuncionarioFuncao) {
         addFields("data_inicio_vigencia", "data_final_vigencia",  "id_funcionario", "id_turma", "id_tipo_funcao");
		
		addValues(turmaFuncionarioFuncao.getDataInicioVigencia(), turmaFuncionarioFuncao.getDataFinalVigencia(), turmaFuncionarioFuncao.getIdFuncionario(), turmaFuncionarioFuncao.getIdTurma(),
				turmaFuncionarioFuncao.getIdTipoFuncao(), turmaFuncionarioFuncao.getId());
		
		return (long) update("turmaFuncionarioFuncao", turmaFuncionarioFuncao.getId());
	}
	
	@Override
	public TurmaFuncionarioFuncao buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select t.data_inicio_vigencia, t.data_final_vigencia, t.id_funcionario, t.id_turma, t.id_tipo_funcao ");
            query.append("from turma_funcionario_funcao t where 1=1 ");
            query = andEqual(query, "t.id", id);
			TurmaFuncionarioFuncao turmaFuncionarioFuncao = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new TurmaFuncionarioFuncaoMapper());
			return turmaFuncionarioFuncao;
		} catch (EmptyResultDataAccessException e) {
			log.info("turmaFuncionarioFuncao não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<TurmaFuncionarioFuncao> listar(FiltroTurmaFuncionarioFuncao filtroTurmaFuncionarioFuncao) {
		StringBuilder query = createQuery("select t.data_inicio_vigencia, t.data_final_vigencia, t.id_funcionario, t.id_turma, t.id_turma ");
        query.append("from turma_funcionario_funcao t where 1=1 ");
        query = andEqual(query, "t.id", filtroTurmaFuncionarioFuncao.getId());
		query = andEqual(query, "t.id_funcionario", filtroTurmaFuncionarioFuncao.getIdFuncionario());
        query = andEqual(query, "t.id_turma", filtroTurmaFuncionarioFuncao.getIdTurma());
        query = andEqual(query, "t.id_tipo_funcao", filtroTurmaFuncionarioFuncao.getIdTipoFuncao());
        query = orderBy(query, Order.DESC, "t.data_inicio_vigencia");       
		query = limit(query, filtroTurmaFuncionarioFuncao.getQtdTotal(), filtroTurmaFuncionarioFuncao.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaFuncionarioFuncaoMapper());
	}
	
	@Override
	public int contar(FiltroTurmaFuncionarioFuncao filtroTurmaFuncionarioFuncao) {
		StringBuilder query = createQuery("select count(t.id) from turma_funcionario_funcao t where 1=1 ");
		query = andEqual(query, "t.id", filtroTurmaFuncionarioFuncao.getId());
		query = andEqual(query, "t.id_funcionario", filtroTurmaFuncionarioFuncao.getIdFuncionario());
        query = andEqual(query, "t.id_turma", filtroTurmaFuncionarioFuncao.getIdTurma());
        query = andEqual(query, "t.id_tipo_funcao", filtroTurmaFuncionarioFuncao.getIdTipoFuncao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("turmaFuncionarioFuncao", id);
		return true;
	}
	
	private class TurmaFuncionarioFuncaoMapper implements RowMapper<TurmaFuncionarioFuncao> {

		@Override
		public TurmaFuncionarioFuncao mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TurmaFuncionarioFuncao.builder()
					.id(rs.getLong("id"))
					.dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
					.dataFinalVigencia(rs.getDate("data_final_vigencia").toLocalDate())
					.idFuncionario(rs.getLong("id_funcionario"))
					.idTurma(rs.getLong("id_turma"))
					.idTipoFuncao(rs.getInt("id_tipo_funcao"))
					.build();
		}
		
	}
}

