package br.com.kronos.backend.adaptadores.repositorio.curso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.curso.CursoFuncionario;
import br.com.kronos.backend.aplicacao.curso.CursoFuncionarioRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoFuncionario;
import br.com.kronos.backend.aplicacao.curso.api.CursoFuncionarioDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CursoFuncionarioRepositorioImpl extends SqlQueryRepositorio implements CursoFuncionarioRepositorio {
	
	public CursoFuncionarioRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(CursoFuncionario cursoFuncionario) {
        addFields("data_inicio", "data_final", "id_funcionario", "id_curso", 
                  "id_tipo_funcao"); 
		
		addValues(cursoFuncionario.getDataInicio(), cursoFuncionario.getDataFinal(), cursoFuncionario.getIdFuncionario(),cursoFuncionario.getIdCurso(), 
                  cursoFuncionario.getIdTipoFuncao());
		
		return (long) insertAuto("curso_funcionario");
    }

	@Override
	public Long alterar(CursoFuncionario cursoFuncionario) {
		addFields("data_inicio", "data_final", "id_funcionario", "id_curso", 
                  "id_tipo_funcao"); 
		
		addValues(cursoFuncionario.getDataInicio(), cursoFuncionario.getDataFinal(), cursoFuncionario.getIdFuncionario(),cursoFuncionario.getIdCurso(), 
                  cursoFuncionario.getIdTipoFuncao(), cursoFuncionario.getId());
		
		return (long) update("curso_funcionario", cursoFuncionario.getId());
	}
	
	@Override
	public CursoFuncionarioDTO buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select c.id, c.data_inicio, c.data_final, c.id_funcionario, c.id_curso, ");
            query.append("c.id_tipo_funcao, p.nome as nomeFuncionario  from curso_funcionario c ");
            query.append("join funcionario f on (f.id = c.id_funcionario) ");
            query.append("join pessoa p on (p.id = f.id_pessoa) where 1=1 ");
            query = andEqual(query, "c.id", id);
            CursoFuncionarioDTO cursoFuncionario = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new CursoFuncionarioDTOMapper());
			return cursoFuncionario;
		} catch (EmptyResultDataAccessException e) {
			log.info("curso_funcionario não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<CursoFuncionarioDTO> listar(FiltroCursoFuncionario filtroCursoFuncionario) {
        StringBuilder query = createQuery("select c.id, c.data_inicio, c.data_final, c.id_funcionario, c.id_curso, ");
        query.append("c.id_tipo_funcao, p.nome as nomeFuncionario  from curso_funcionario c ");
        query.append("join funcionario f on (f.id = c.id_funcionario) ");
        query.append("join pessoa p on (p.id = f.id_pessoa) where 1=1 ");
        query = andEqual(query, "c.id", filtroCursoFuncionario.getId());
        query = andEqual(query, "c.id_funcionario", filtroCursoFuncionario.getIdFuncionario()); 
        query = andEqual(query, "c.id_curso", filtroCursoFuncionario.getIdCurso()); 
        query = andEqual(query, "c.id_tipo_funcao", filtroCursoFuncionario.getIdTipoFuncao());
        query = orderBy(query, Order.ASC, "c.id_tipo_funcao");
		query = limit(query, filtroCursoFuncionario.getQtdTotal(), filtroCursoFuncionario.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CursoFuncionarioDTOMapper());
	}
	
	@Override
	public int contar(FiltroCursoFuncionario filtroCursoFuncionario) {
		StringBuilder query = createQuery("select count(c.id) from curso_funcionario c ");
		query.append("join funcionario f on (f.id = c.id_funcionario) ");
        query.append("join pessoa p on (p.id = f.id_pessoa) where 1=1 ");
		query = andEqual(query, "c.id", filtroCursoFuncionario.getId());
        query = andEqual(query, "c.id_funcionario", filtroCursoFuncionario.getIdFuncionario()); 
        query = andEqual(query, "c.id_curso", filtroCursoFuncionario.getIdCurso()); 
        query = andEqual(query, "c.id_tipo_funcao", filtroCursoFuncionario.getIdTipoFuncao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("curso_funcionario", id);
		return true;
	}
	
	private class CursoFuncionarioDTOMapper implements RowMapper<CursoFuncionarioDTO> {

		@Override
		public CursoFuncionarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CursoFuncionarioDTO.builder()
                    .id(rs.getLong("id"))
                    .dataInicio(rs.getDate("data_inicio").toLocalDate())
                    .dataFinal(rs.getDate("data_final") != null ? rs.getDate("data_final").toLocalDate() : null)
                    .idFuncionario(rs.getLong("id_funcionario"))
                    .idCurso(rs.getLong("id_curso"))
                    .idTipoFuncao(rs.getInt("id_tipo_funcao"))
                    .nomeFuncionario(rs.getString("nomeFuncionario"))
                    .build();
		}
	}

}