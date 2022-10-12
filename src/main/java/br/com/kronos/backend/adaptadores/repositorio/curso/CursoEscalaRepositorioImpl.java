package br.com.kronos.backend.adaptadores.repositorio.curso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroCursoEscala;
import br.com.kronos.backend.aplicacao.curso.CursoEscala;
import br.com.kronos.backend.aplicacao.curso.CursoEscalaRepositorio;

public class CursoEscalaRepositorioImpl extends SqlQueryRepositorio implements CursoEscalaRepositorio {
	
	public CursoEscalaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public int criar(CursoEscala cursoEscala) {
		addFields("id_curso", "id_escala");
		
		addValues(cursoEscala.getIdCurso(), cursoEscala.getIdEscala());
		
		return insert("curso_escala");
	}

	@Override
	public List<CursoEscala> listar(FiltroCursoEscala filtroCursoEscala) {
		StringBuilder query = createQuery("select c.id_curso, c.id_escala ");
		query.append(" from curso_escala c where 1=1 ");
		query = andEqual(query, "c.id_curso", filtroCursoEscala.getIdCurso());
		query = limit(query, filtroCursoEscala.getQtdTotal(), filtroCursoEscala.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CursoEscalaMapper());
	}
	
	@Override
	public int contar(FiltroCursoEscala filtroCursoEscala) {
		StringBuilder query = createQuery("select count(c.id_escala) from curso_escala c where 1=1 ");
		query = andEqual(query, "c.id_curso", filtroCursoEscala.getIdCurso());
		query = andEqual(query, "c.id_escala", filtroCursoEscala.getIdEscala());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(long idCurso, long idEscala) {
		addFields("id_curso", "id_escala");
		addValues(idCurso, idEscala);
		delete("curso_escala");
		return true;
	}
	
	private class CursoEscalaMapper implements RowMapper<CursoEscala> {

		@Override
		public CursoEscala mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CursoEscala.builder()
					.idCurso(rs.getLong("id_curso"))
					.idEscala(rs.getLong("id_escala"))
					.build();
		}

	}

	@Override
	public int contarPorIdCursoEEscala(FiltroCursoEscala filtroCursoEscala) {
		StringBuilder query = createQuery("select count(c.id_escala) from curso_escala c where 1=1 ");
		query = andEqual(query, "c.id_curso", filtroCursoEscala.getIdCurso());
		query = andEqual(query, "c.id_escala", filtroCursoEscala.getIdEscala());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}


}
