package br.com.kronos.backend.adaptadores.repositorio.atestado;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.atestado.Atestado;
import br.com.kronos.backend.aplicacao.atestado.AtestadoRepositorio;
import br.com.kronos.backend.aplicacao.atestado.FiltroAtestado;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtestadoRepositorioImpl extends SqlQueryRepositorio implements AtestadoRepositorio {
	

	public AtestadoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}	
	@Override
	public boolean verificarAtestadoConflitante(FiltroAtestado filtroAtestado) {
		
		StringBuilder query = createQuery("select count(a.id) ");
		query.append("from atestado a ");
        query.append("where ((to_date('");
        query.append(filtroAtestado.getDataInicioVigencia().toString());
        query.append("', 'yyyy-mm-dd')  between a.data_inicio_vigencia and a.data_final_vigencia ) ");
        
        query.append("or (to_date('");
        query.append(filtroAtestado.getDataFinalVigencia().toString());
        query.append("', 'yyyy-mm-dd')  between a.data_inicio_vigencia and a.data_final_vigencia ) ");
        
        query.append("or (to_date('");
        query.append(filtroAtestado.getDataInicioVigencia().toString());
        query.append("', 'yyyy-mm-dd') < a.data_inicio_vigencia) ");
        
        query.append("and (to_date('");
        query.append(filtroAtestado.getDataFinalVigencia().toString());
        query.append("', 'yyyy-mm-dd') > a.data_final_vigencia ))  ");
       
        query = andEqual(query, "a.id_pessoa", filtroAtestado.getIdPessoa());
        query = and(query, "a.id", filtroAtestado.getId(), "<>");  
		int count = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
		return count > 0;
		
	}

	@Override
	public Long criar(Atestado atestado) {
        addFields("data_entrega", "id_pessoa", "id_instituicao", "id_tipo_falta", 
                  "data_inicio_vigencia", "data_final_vigencia", "id_funcionario",  "id_tipo_atestado", "id_contrato" );
        
		addValues(atestado.getDataEntrega(), atestado.getIdPessoa(), atestado.getIdInstituicao(), atestado.getIdTipoFalta(), 
                  atestado.getDataInicioVigencia(), atestado.getDataFinalVigencia(), atestado.getIdFuncionario(), atestado.getIdTipoAtestado(), atestado.getIdContrato()); 
		
		return (long) insertAuto("atestado"); 
    }

	@Override
	public Long alterar(Atestado atestado) {
		addFields("data_entrega", "id_pessoa", "id_instituicao", "id_tipo_falta", 
                "data_inicio_vigencia", "data_final_vigencia", "id_funcionario", "id_arq_anexo", "id_tipo_atestado", "id_contrato" );
		
		addValues(atestado.getDataEntrega(), atestado.getIdPessoa(), atestado.getIdInstituicao(), atestado.getIdTipoFalta(), 
                atestado.getDataInicioVigencia(), atestado.getDataFinalVigencia(), atestado.getIdFuncionario(), atestado.getIdArqAnexo(),
                atestado.getIdTipoAtestado(), atestado.getIdContrato(), atestado.getId());
		
		return (long) update("atestado", atestado.getId());
	}
	
	@Override
	public int alterarArquivo(Long id, int idArqAnexo) {
		addFields("id_arq_anexo");
		
		addValues(idArqAnexo, id);
		
		return update("atestado", id);
	}
	
	@Override
	public Atestado buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery("select a.id, a.data_entrega, a.data_inicio_vigencia, a.data_final_vigencia, ");
	        query.append("a.id_funcionario, a.id_tipo_falta, a.id_arq_anexo, a.id_pessoa, a.id_instituicao ");
	        query.append("from atestado a ");
	        query.append("left join tipo_falta t on (a.id_tipo_falta = t.id) ");
	        query.append("left join instituicao i on (a.id_instituicao = i.id) ");
	        query.append("left join funcionario f on (a.id_funcionario = f.id) ");
	        query.append("left join pessoa p on (f.id_pessoa = p.id) where 1=1 ");
	        query = andEqual(query, "a.id", id);
            Atestado atestado = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new AtestadoDTOMapper());
			return atestado;
		} catch (EmptyResultDataAccessException e) {
			log.info("Atestado não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Atestado> listar(FiltroAtestado filtro) {
		StringBuilder query = createQuery("select a.id, a.data_entrega, a.data_inicio_vigencia, a.data_final_vigencia, ");
        query.append("a.id_funcionario, a.id_tipo_falta, a.id_arq_anexo, a.id_pessoa, a.id_instituicao ");
        query.append("from atestado a ");
        query.append("left join tipo_falta t on (a.id_tipo_falta = t.id) ");
        query.append("left join instituicao i on (a.id_instituicao = i.id) ");
        query.append("left join funcionario f on (a.id_funcionario = f.id) ");
        query.append("left join pessoa p on (f.id_pessoa = p.id) where a.id_tipo_atestado = 1 ");
        query = andEqual(query, "a.id_pessoa", filtro.getIdPessoa());
        query = orderBy(query, Order.DESC, "a.data_inicio_vigencia");
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AtestadoDTOMapper());
	}

	
	@Override
	public int contar(FiltroAtestado filtro) {
		StringBuilder query = createQuery("select count(a.id) ");
		query.append("from atestado a ");
        query.append("left join tipo_falta t on (a.id_tipo_falta = t.id) ");
        query.append("left join instituicao i on (a.id_instituicao = i.id) ");
        query.append("left join funcionario f on (a.id_funcionario = f.id) ");
        query.append("left join pessoa p on (f.id_pessoa = p.id) where 1=1 ");
        query = andEqual(query, "a.id_pessoa", filtro.getIdPessoa());  
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class AtestadoDTOMapper implements RowMapper<Atestado> {

		@Override
		public Atestado mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Atestado.builder()
					.id(rs.getLong("id"))
					.dataEntrega(rs.getDate("data_entrega").toLocalDate())
					.idFuncionario(rs.getLong("id_funcionario"))
                    .idTipoFalta(rs.getInt("id_tipo_falta"))
                    .idArqAnexo(rs.getInt("id_arq_anexo"))
                    .idPessoa(rs.getLong("id_pessoa"))
                    .idInstituicao(rs.getLong("id_instituicao"))
                    .dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia") != null ? rs.getDate("data_final_vigencia").toLocalDate() : null)
					.build();
		}
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("atestado", id);
		return true;
	}
	
}

