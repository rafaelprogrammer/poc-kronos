package br.com.kronos.backend.adaptadores.repositorio.matricula;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.matricula.FiltroMatriculaDocumento;
import br.com.kronos.backend.aplicacao.matricula.MatriculaDocumento;
import br.com.kronos.backend.aplicacao.matricula.MatriculaDocumentoRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatriculaDocumentoRepositorioImpl extends SqlQueryRepositorio implements MatriculaDocumentoRepositorio {
	
	public MatriculaDocumentoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(MatriculaDocumento matriculaDocumento) {
        addFields("data", "id_matricula", "id_documento");
		
		addValues(matriculaDocumento.getData(), matriculaDocumento.getIdMatricula(), matriculaDocumento.getIdDocumento()); 
		
		return (long) insertAuto("matriculaDocumento"); 
    }
	
	@Override
	public Long alterar(MatriculaDocumento matriculaDocumento) {
		addFields("data", "id_matricula", "id_documento");
		
		addValues(matriculaDocumento.getData(), matriculaDocumento.getIdMatricula(), matriculaDocumento.getIdDocumento(), matriculaDocumento.getId());
		
		return (long) update("matriculaDocumento", matriculaDocumento.getId());
	}
	
	@Override
	public MatriculaDocumento buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select m.data, m.id_matricula, m.id_documento ");
            query.append("from matricula_documento m where 1=1 ");
            query = andEqual(query, "m.id", id);
			MatriculaDocumento matriculaDocumento = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new MatriculaDocumentoMapper());
			return matriculaDocumento;
		} catch (EmptyResultDataAccessException e) {
			log.info("matriculaDocumento não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<MatriculaDocumento> listar(FiltroMatriculaDocumento filtroMatriculaDocumento) {
		StringBuilder query = createQuery("select m.data, m.id_matricula, m.id_documento ");
        query.append("from matricula_documento m where 1=1 ");
        query = andEqual(query, "m.id", filtroMatriculaDocumento.getId());
		query = andEqual(query, "m.id_matricula", filtroMatriculaDocumento.getIdMatricula());
        query = andEqual(query, "m.id_documento", filtroMatriculaDocumento.getIdDocumento());
        query = orderBy(query, Order.ASC, "m.idMatricula");       
		query = limit(query, filtroMatriculaDocumento.getQtdTotal(), filtroMatriculaDocumento.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new MatriculaDocumentoMapper());
	}
	
	@Override
	public int contar(FiltroMatriculaDocumento filtroMatriculaDocumento) {
		StringBuilder query = createQuery("select count(m.idDocumento) from matricula_documento m where 1=1 ");
		query = andEqual(query, "m.id", filtroMatriculaDocumento.getId());
		query = andEqual(query, "m.id_matricula", filtroMatriculaDocumento.getIdMatricula());
        query = andEqual(query, "m.id_documento", filtroMatriculaDocumento.getIdDocumento());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("matriculaDocumento", id);
		return true;
	}
	
	private class MatriculaDocumentoMapper implements RowMapper<MatriculaDocumento> {

		@Override
		public MatriculaDocumento mapRow(ResultSet rs, int rowNum) throws SQLException {
			return MatriculaDocumento.builder()
					.id(rs.getLong("id"))
					.data(rs.getDate("data").toLocalDate())
					.idMatricula(rs.getLong("id_matricula"))
					.idDocumento(rs.getLong("id_documento"))
					.build();
		}

	}
}

