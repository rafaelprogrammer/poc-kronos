package br.com.kronos.backend.adaptadores.repositorio.parecer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.parecer.Parecer;
import br.com.kronos.backend.aplicacao.parecer.ParecerRepositorio;
import br.com.kronos.backend.aplicacao.parecer.FiltroParecer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParecerRepositorioImpl extends SqlQueryRepositorio implements ParecerRepositorio {
	
	public ParecerRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Parecer parecer) {
        addFields("id_sub_fase_execucao", "id_matricula", "id_turma", "data",
        	      "id_funcionario", "texto", "rascunho" );
		
		addValues(parecer.getIdSubFaseExecucao(), parecer.getIdMatricula(), parecer.getIdTurma(), parecer.getData(),
				 parecer.getIdFuncionario(), parecer.getTexto(), parecer.isRascunho());
		
		return (long) insertAuto("parecer"); 
    }
		
	@Override
	public Long alterar(Parecer parecer) {
		 addFields("id_sub_fase_execucao", "id_matricula", "id_turma", "data",
       	           "id_funcionario", "texto", "rascunho" );
		
		addValues(parecer.getIdSubFaseExecucao(), parecer.getIdMatricula(), parecer.getIdTurma(), parecer.getData(),
				 parecer.getIdFuncionario(), parecer.getTexto(), parecer.isRascunho(),parecer.getId());
		
		return (long) update("parecer", parecer.getId());
	}
	
	@Override
	public Parecer buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select p.id_sub_fase_execucao, p.id_matricula, p.id_turma, p.data, ");
            query.append("p.id_funcionario, p.texto, p.rascunho ");
            query.append("from parecer p where 1=1 ");
            query = andEqual(query, "p.id", id);
			Parecer parecer = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ParecerMapper());
			return parecer;
		} catch (EmptyResultDataAccessException e) {
			log.info("Parecer não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Parecer> listar(FiltroParecer filtroParecer) {
		StringBuilder query = createQuery("select p.id_sub_fase_execucao, p.id_matricula, p.id_turma, p.data, ");
        query.append("p.id_funcionario, p.texto, p.rascunho ");
        query.append("from parecer p where 1=1 ");
        query = andEqual(query, "p.id", filtroParecer.getId());  
        query = andEqual(query, "p.id_sub_fase_execucao", filtroParecer.getIdSubFaseExecucao()); 
        query = andEqual(query, "p.id_matricula", filtroParecer.getIdMatricula());
        query = andEqual(query, "p.id_turma", filtroParecer.getIdTurma());
        query = orderBy(query, Order.DESC, "p.data");
		query = limit(query, filtroParecer.getQtdTotal(), filtroParecer.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ParecerMapper());
	}
	
	@Override
	public int contar(FiltroParecer filtroParecer) {
		 StringBuilder query = createQuery("select count(p.id) from parecer p where 1=1 ");
		 query = andEqual(query, "p.id", filtroParecer.getId());  
	     query = andEqual(query, "p.id_sub_fase_execucao", filtroParecer.getIdSubFaseExecucao()); 
	     query = andEqual(query, "p.id_matricula", filtroParecer.getIdMatricula());
	     query = andEqual(query, "p.id_turma", filtroParecer.getIdTurma());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("parecer", id);
		return true;
	}
	
	private class ParecerMapper implements RowMapper<Parecer> {

		@Override
		public Parecer mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Parecer.builder()
					.id(rs.getLong("id"))
					.idSubFaseExecucao(rs.getLong("id_sub_fase_execucao"))
                    .idMatricula(rs.getLong("id_matricula"))
                    .idTurma(rs.getLong("id_turma"))
                    .data(rs.getDate("data").toLocalDate())
                    .id(rs.getLong("id_funcionario"))
               //     .texto(rs.getString("texto"))      //Trata-se de umtexto grande formatado
                    .rascunho(rs.getBoolean("rascunho"))
					.build();
		}
	}
}
