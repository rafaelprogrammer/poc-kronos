package br.com.kronos.backend.adaptadores.repositorio.curso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.curso.FiltroPeriodo;
import br.com.kronos.backend.aplicacao.curso.Periodo;
import br.com.kronos.backend.aplicacao.curso.PeriodoRepositorio;
import br.com.kronos.backend.aplicacao.curso.api.PeriodoDTO;
import br.com.kronos.backend.aplicacao.curso.api.PeriodoResumoDTO;
import br.com.kronos.backend.aplicacao.matricula.EnumTipoResultado;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PeriodoRepositorioImpl extends SqlQueryRepositorio implements PeriodoRepositorio {
	
	public PeriodoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Periodo periodo) {
        addFields("nome", "numero", "sigla", "id_curso", 
                  "id_tipo_periodo", "id_faixa_ano", "valor"); 
		
		addValues(periodo.getNome(), periodo.getNumero(), periodo.getSigla(),periodo.getIdCurso(), 
                  periodo.getIdTipoPeriodo(), periodo.getIdFaixaAno(), periodo.getValor());
		
		return (long) insertAuto("periodo");
    }
    
	@Override
	public Long alterar(Periodo periodo) {
		addFields("nome", "numero", "sigla", "id_curso", 
                  "id_tipo_periodo", "id_faixa_ano", "valor"); 
		
		addValues(periodo.getNome(), periodo.getNumero(), periodo.getSigla(),periodo.getIdCurso(), 
                  periodo.getIdTipoPeriodo(), periodo.getIdFaixaAno(), periodo.getValor(), periodo.getId());
		
		return (long) update("periodo", periodo.getId());
	}
	
	@Override
	public PeriodoDTO buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select p.id, p.nome, p.numero, p.sigla, p.id_curso, ");
            query.append("p.id_tipo_periodo, p.id_faixa_ano, p.valor, f.nome as nomeFaixaAno from periodo p ");
            query.append("join faixa_ano f on (p.id_faixa_ano = f.id) where 1=1 ");
            query = andEqual(query, "p.id", id);
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new PeriodoDTOMapper());
		} catch (EmptyResultDataAccessException e) {
			log.info("periodo não existe - " + id);
			return null;
		}
	}
	
	@Override
	public PeriodoDTO buscarPorIdPeriodoExecucao(Long idPeriodoExecucao) {
		try {     
            StringBuilder query = createQuery("select p.id, p.nome, p.numero, p.sigla, p.id_curso, ");
            query.append("p.id_tipo_periodo, p.id_faixa_ano, p.valor, f.nome as nomeFaixaAno from periodo p ");
            query.append("join faixa_ano f on (p.id_faixa_ano = f.id) ");
            query.append("join periodo_execucao pe on (p.id = pe.id_periodo) where 1=1 ");
            query = andEqual(query, "pe.id", idPeriodoExecucao);
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new PeriodoDTOMapper());
		} catch (EmptyResultDataAccessException e) {
			log.info("periodo não existe - " + idPeriodoExecucao);
			return null;
		}
	}
	
	@Override
	public List<Long> retornarIdsPeriodoParaGerarPeriodosDeExecucao(FiltroPeriodo filtroPeriodo) {
		StringBuilder query = createQuery("select p.id from periodo p where 1=1 ");
		query = andEqual(query, "p.id_curso", filtroPeriodo.getIdCurso());
		query.append(" and not exists(");
		query.append("          select pe.id_periodo from periodo_execucao pe");
		query.append("          join periodo pd on (pe.id_periodo = pd.id) where 1=1 ");
		query = andEqual(query, "pd.id_curso", filtroPeriodo.getIdCurso());
		query = andEqual(query, "pe.ano", filtroPeriodo.getAnoPeriodoExecucao());
		query.append(" and pe.id_periodo = p.id ) ");
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Long.class);
	}
	
	@Override
	public List<PeriodoResumoDTO> listarParaHorario(FiltroPeriodo filtroPeriodo) {
		StringBuilder query = createQuery("select pe.id, p.sigla, p.numero, p.nome ");
		query.append("from periodo_execucao pe ");
		query.append("left join periodo p on (pe.id_periodo = p.id) where 1=1 ");
		query = andEqual(query, "p.id_curso", filtroPeriodo.getIdCurso());
		query = andEqual(query, "pe.ano", filtroPeriodo.getAnoPeriodoExecucao());
		query = orderBy(query, Order.ASC, "p.sigla");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PeriodoResumoDTOMapper());
	}
	
	@Override
	public List<PeriodoResumoDTO> listarParaComboPeriodoPendenteContrato(FiltroPeriodo filtroPeriodo) {
		StringBuilder query = createQuery("select pe.id, p.sigla, p.numero, pe.ano, p.nome from periodo_execucao pe ");
		query.append("left join periodo p on (pe.id_periodo = p.id) where 1=1 ");
		query = andEqual(query, "p.id_curso", filtroPeriodo.getIdCurso());
		query = andEqual(query, "pe.ano", filtroPeriodo.getAnoPeriodoExecucao());
		query = and(query, "p.numero", filtroPeriodo.getNumero(), "<");
		query.append(" and exists ");
		query.append("(select px.id, pd.sigla, pd.numero from periodo pd ");
		query.append("left join periodo_execucao px on (pd.id = px.id_periodo) ");
		query.append("left join contrato ct on (px.id = ct.id_periodo_execucao) where 1=1 ");
		query = andEqual(query, "ct.id_tipo_resultado", EnumTipoResultado.APROVADO.id());
		query = andEqual(query, "ct.dependencia", true);
		query = andEqual(query, "pd.id_curso", filtroPeriodo.getIdCurso());
		query = andEqual(query, "ct.id_matricula", filtroPeriodo.getIdMatricula());
		query.append(" and p.numero = pd.numero) ");
		query = orderBy(query, Order.ASC, "p.numero");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PeriodoResumoDTOMapper());
	}
	
	@Override
	public List<PeriodoResumoDTO> listarParaComboContrato(FiltroPeriodo filtroPeriodo) {
		StringBuilder query = createQuery("select pe.id, p.sigla, p.numero, pe.ano, p.nome from periodo_execucao pe ");
		query.append("left join periodo p on (pe.id_periodo = p.id) where 1=1 ");
		query = andEqual(query, "p.id_curso", filtroPeriodo.getIdCurso());
		query = andEqual(query, "pe.ano", filtroPeriodo.getAnoPeriodoExecucao());
		query.append(" and p.numero > ");
		query.append("coalesce((select max(pd.numero) from periodo pd ");
		query.append("left join periodo_execucao pe on (pd.id = pe.id_periodo) ");
		query.append("left join contrato ct on (pe.id = ct.id_periodo_execucao) where 1=1 ");
		query = andEqual(query, "ct.id_tipo_resultado", EnumTipoResultado.APROVADO.id());
		query = andEqual(query, "pd.id_curso", filtroPeriodo.getIdCurso());
		query = andEqual(query, "ct.id_matricula", filtroPeriodo.getIdMatricula());
		query.append("), 0)");
		query = orderBy(query, Order.ASC, "p.numero");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PeriodoResumoDTOMapper());
	}
	
	@Override
	public List<PeriodoDTO> listarParaCombo(FiltroPeriodo filtroPeriodo) {
		return this.listar(filtroPeriodo);
	}
	
	@Override
	public List<PeriodoDTO> listar(FiltroPeriodo filtroPeriodo) {
        StringBuilder query = createQuery("select p.id, p.nome, p.numero, p.sigla, p.id_curso, ");
        query.append("p.id_tipo_periodo, p.id_faixa_ano, p.valor, f.nome as nomeFaixaAno from periodo p ");
        query.append("join faixa_ano f on (p.id_faixa_ano = f.id) where 1=1 ");
        query = andEqual(query, "p.id", filtroPeriodo.getId());
        query = andEqual(query, "p.numero", filtroPeriodo.getNumero()); 
        query = andEqual(query, "p.nome", filtroPeriodo.getNome());
        query = andEqual(query, "p.sigla", filtroPeriodo.getSigla());
        query = andEqual(query, "p.id_curso", filtroPeriodo.getIdCurso()); 
        query = orderBy(query, Order.ASC, "p.numero");
		query = limit(query, filtroPeriodo.getQtdTotal(), filtroPeriodo.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PeriodoDTOMapper());
	}
	
	@Override
	public int contar(FiltroPeriodo filtroPeriodo) {
		StringBuilder query = createQuery("select count(p.id) from periodo p where 1=1 ");
		query = andEqual(query, "p.id", filtroPeriodo.getId());
        query = andEqual(query, "p.numero", filtroPeriodo.getNumero()); 
        query = andEqual(query, "p.nome", filtroPeriodo.getNome());
        query = andEqual(query, "p.sigla", filtroPeriodo.getSigla());
        query = andEqual(query, "p.id_curso", filtroPeriodo.getIdCurso()); 
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("periodo", id);
		return true;
	}
	
	private class PeriodoDTOMapper implements RowMapper<PeriodoDTO> {

		@Override
		public PeriodoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PeriodoDTO.builder() 
                    .id(rs.getLong("id"))
                    .numero(rs.getInt("numero"))
                    .nome(rs.getString("nome"))
                    .sigla(rs.getString("sigla"))
                    .idCurso(rs.getLong("id_curso"))
                    .idTipoPeriodo(rs.getInt("id_tipo_periodo"))
                    .idFaixaAno(rs.getLong("id_faixa_ano"))
                    .valor(rs.getDouble("valor"))
                    .nomeFaixaAno(rs.getString("nomeFaixaAno"))
                    .build();
		}
	}
	
	private class PeriodoResumoDTOMapper implements RowMapper<PeriodoResumoDTO> {

		@Override
		public PeriodoResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PeriodoResumoDTO.builder() 
                    .id(rs.getLong("id"))
                    .numero(rs.getInt("numero"))
                    .nome(rs.getString("nome"))
                    .sigla(rs.getString("sigla"))
                    .build();
		}
	}

}