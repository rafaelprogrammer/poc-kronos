package br.com.kronos.backend.adaptadores.repositorio.matricula;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurmaAlunoFuncao;
import br.com.kronos.backend.aplicacao.matricula.TurmaAlunoFuncao;
import br.com.kronos.backend.aplicacao.matricula.TurmaAlunoFuncaoRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TurmaAlunoFuncaoRepositorioImpl extends SqlQueryRepositorio implements TurmaAlunoFuncaoRepositorio {
	
	public TurmaAlunoFuncaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(TurmaAlunoFuncao turmaAlunoFuncao) {
        addFields("data_inicio_vigencia", "data_final_vigencia",  "id_matricula", "id_turma", "id_tipo_funcao");
		
		addValues(turmaAlunoFuncao.getDataInicioVigencia(), turmaAlunoFuncao.getDataFinalVigencia(), turmaAlunoFuncao.getIdMatricula(), turmaAlunoFuncao.getIdTurma(),
				turmaAlunoFuncao.getIdTipoFuncao()); 
		
		return (long) insertAuto("turmaAlunoFuncao"); 
    }

	@Override
	public Long alterar(TurmaAlunoFuncao turmaAlunoFuncao) {
         addFields("data_inicio_vigencia", "data_final_vigencia",  "id_matricula", "id_turma", "id_tipo_funcao");
		
		addValues(turmaAlunoFuncao.getDataInicioVigencia(), turmaAlunoFuncao.getDataFinalVigencia(), turmaAlunoFuncao.getIdMatricula(), turmaAlunoFuncao.getIdTurma(),
				turmaAlunoFuncao.getIdTipoFuncao(), turmaAlunoFuncao.getId());
		
		return (long) update("turmaAlunoFuncao", turmaAlunoFuncao.getId());
	}
	
	@Override
	public TurmaAlunoFuncao buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select t.data_inicio_vigencia, t.data_final_vigencia, t.id_matricula, t.id_turma, t.id_tipo_funcao ");
            query.append("from turma_aluno_funcao t where 1=1 ");
            query = andEqual(query, "t.id", id);
			TurmaAlunoFuncao turmaAlunoFuncao = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new TurmaAlunoFuncaoMapper());
			return turmaAlunoFuncao;
		} catch (EmptyResultDataAccessException e) {
			log.info("turmaAlunoFuncao não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<TurmaAlunoFuncao> listar(FiltroTurmaAlunoFuncao filtroTurmaAlunoFuncao) {
		StringBuilder query = createQuery("select t.data_inicio_vigencia, t.data_final_vigencia, t.id_matricula, t.id_turma, t.id_turma ");
        query.append("from turma_aluno_funcao t where 1=1 ");
        query = andEqual(query, "m.id", filtroTurmaAlunoFuncao.getId());
		query = andEqual(query, "t.id_matricula", filtroTurmaAlunoFuncao.getIdMatricula());
        query = andEqual(query, "t.id_turma", filtroTurmaAlunoFuncao.getIdTurma());
        query = andEqual(query, "t.id_tipo_funcao", filtroTurmaAlunoFuncao.getIdTipoFuncao());
        query = orderBy(query, Order.DESC, "t.data_inicio_vigencia");       
		query = limit(query, filtroTurmaAlunoFuncao.getQtdTotal(), filtroTurmaAlunoFuncao.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new TurmaAlunoFuncaoMapper());
	}
	
	@Override
	public int contar(FiltroTurmaAlunoFuncao filtroTurmaAlunoFuncao) {
		StringBuilder query = createQuery("select count(t.id) from turma_aluno_funcao t where 1=1 ");
		query = andEqual(query, "t.id", filtroTurmaAlunoFuncao.getId());
		query = andEqual(query, "t.id_matricula", filtroTurmaAlunoFuncao.getIdMatricula());
        query = andEqual(query, "t.id_turma", filtroTurmaAlunoFuncao.getIdTurma());
        query = andEqual(query, "t.id_tipo_funcao", filtroTurmaAlunoFuncao.getIdTipoFuncao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("turmaAlunoFuncao", id);
		return true;
	}
	
	private class TurmaAlunoFuncaoMapper implements RowMapper<TurmaAlunoFuncao> {

		@Override
		public TurmaAlunoFuncao mapRow(ResultSet rs, int rowNum) throws SQLException {
			return TurmaAlunoFuncao.builder()
					.id(rs.getLong("id"))
					.dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
					.dataFinalVigencia(rs.getDate("data_final_vigencia").toLocalDate())
					.idMatricula(rs.getLong("id_matricula"))
					.idTurma(rs.getLong("id_turma"))
					.idTipoFuncao(rs.getInt("id_tipo_funcao"))
					.build();
		}
		
	}
}

