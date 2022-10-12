package br.com.kronos.backend.adaptadores.repositorio.matricula;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.matricula.Credito;
import br.com.kronos.backend.aplicacao.matricula.CreditoRepositorio;
import br.com.kronos.backend.aplicacao.matricula.FiltroCredito;
import br.com.kronos.backend.aplicacao.matricula.api.CreditoContratoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreditoRepositorioImpl extends SqlQueryRepositorio implements CreditoRepositorio {
	
	public CreditoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Credito credito) {
        addFields("valor", "carga_horaria_total", "carga_horaria_presencial", "carga_horaria_distancia", 
                  "nota_final_normal", "nota_final_exame", "nota_resultado_final",  "total_minutos_ausencia", 
                  "percentual_ausencia", "id_contrato", "id_disciplina", "id_turma", 
                  "id_tipo_mencao_final", "id_tipo_credito", "id_tipo_programa", "nota_conselho",
                  "pendencia");
		
		addValues(credito.getValor(), credito.getCargaHorariaTotal(), credito.getCargaHorariaPresencial(),credito.getCargaHorariaDistancia(), 
                  credito.getNotaFinalNormal(), credito.getNotaFinalExame(), credito.getNotaResultadoFinal(), credito.getTotalMinutosAusencia(),  
                  credito.getPercentualAusencia(), credito.getIdContrato(), credito.getIdDisciplina(), credito.getIdTurma(), 
                  credito.getIdTipoMencaoFinal(), credito.getIdTipoCredito(), credito.getIdTipoPrograma(), credito.getNotaConselho(),
                  credito.isPendencia()); 
		
		return (long) insertAuto("credito"); 
    }
	
	@Override
	public Long alterar(Credito credito) {
		addFields("valor", "carga_horaria_total", "carga_horaria_presencial", "carga_horaria_distancia", 
                "nota_final_normal", "nota_final_exame", "nota_resultado_final",  "total_minutos_ausencia", 
                "percentual_ausencia", "id_contrato", "id_disciplina", "id_turma", 
                "id_tipo_mencao_final", "id_tipo_credito", "id_tipo_programa", "nota_conselho",
                "pendencia");
		
		addValues(credito.getValor(), credito.getCargaHorariaTotal(), credito.getCargaHorariaPresencial(),credito.getCargaHorariaDistancia(), 
                credito.getNotaFinalNormal(), credito.getNotaFinalExame(), credito.getNotaResultadoFinal(), credito.getTotalMinutosAusencia(),  
                credito.getPercentualAusencia(), credito.getIdContrato(), credito.getIdDisciplina(), credito.getIdTurma(), 
                credito.getIdTipoMencaoFinal(), credito.getIdTipoCredito(), credito.getIdTipoPrograma(), credito.getNotaConselho(),
                credito.isPendencia(),credito.getId());
		
		return (long) update("credito", credito.getId());
	}
	
	@Override
	public Credito buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select c.id, c.valor, c.carga_horaria_total, c.carga_horaria_presencial, c.carga_horaria_distancia, ");
            query.append("c.nota_final_normal, c.nota_final_exame, c.nota_resultado_final,  c.total_minutos_ausencia,   ");
            query.append("c.percentual_ausencia, c.id_contrato, c.id_disciplina, c.id_turma, "); 
            query.append("c.id_tipo_mencao_final, c.id_tipo_credito, c.id_tipo_programa, c.nota_conselho, c.pendencia, "); 
            query.append("from credito c where 1=1 ");
            query.append("p.sigla as sigla_periodo, d.nome as nome_disciplina, d.obrigatoria as disciplina_obrigatoria t.sigla as sigla_turma, t.ano as ano_turma ");
            query.append("from credito c ");
            query.append("join disciplina d on (c.id_disciplina = c.id) ");
            query.append("join periodo p on (d.id_periodo = p.id) ");
            query.append("join periodo_execucao pe on (p.id = pe.id_periodo) ");
            query.append("join turma t on (c.id_turma = t.id) where 1=1 ");
            query = andEqual(query, "c.id", id);
			Credito credito = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new CreditoMapper());
			return credito;
		} catch (EmptyResultDataAccessException e) {
			log.info("credito não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<Credito> listar(FiltroCredito filtroCredito) {
		StringBuilder query = createQuery("select c.id, c.valor, c.carga_horaria_total, c.carga_horaria_presencial, c.carga_horaria_distancia, ");
        query.append("c.nota_final_normal, c.nota_final_exame, c.nota_resultado_final,  c.total_minutos_ausencia,   ");
        query.append("c.percentual_ausencia, c.id_contrato, c.id_disciplina, c.id_turma, "); 
        query.append("c.id_tipo_mencao_final, c.id_tipo_credito, c.id_tipo_programa, c.nota_conselho, c.pendencia ");
        query.append("from credito c where 1=1 ");
		query = andEqual(query, "c.id", filtroCredito.getId());
		query = andEqual(query, "c.id_contrato", filtroCredito.getIdContrato());
        query = andEqual(query, "c.id_disciplina", filtroCredito.getIdDisciplina());
        query = andEqual(query, "c.id_turma", filtroCredito.getIdTurma()); 
        query = andEqual(query, "c.id_tipo_credito", filtroCredito.getIdTipoCredito());
        query = orderBy(query, Order.ASC, "id_tipo_credito");
		query = limit(query, filtroCredito.getQtdTotal(), filtroCredito.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CreditoMapper());
	}
	
	@Override
	public int contar(FiltroCredito filtroCredito) {
		StringBuilder query = createQuery("select count(c.id) from credito c where 1=1 ");
		query = andEqual(query, "c.id", filtroCredito.getId());
		query = andEqual(query, "c.id_contrato", filtroCredito.getIdContrato());
        query = andEqual(query, "c.id_disciplina", filtroCredito.getIdDisciplina());
        query = andEqual(query, "c.id_turma", filtroCredito.getIdTurma()); 
        query = andEqual(query, "c.id_tipo_credito", filtroCredito.getIdTipoCredito());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public Double somarValores(Long idContrato, Boolean pendencia) {
		StringBuilder query = createQuery("select sum(c.valor) from credito c where 1=1 ");
		query = andEqual(query, "c.id_contrato", idContrato);
		query = andEqual(query, "c.pendencia", pendencia);
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Double.class);
	}
	
	@Override
	public boolean excluirPorContrato(Long idContrato) {
		addFields("id_contrato");
		addValues(idContrato);
		delete("credito");
		return true;
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("credito", id);
		return true;
	}
	
	@Override
	public List<CreditoContratoDTO> listarParaContrato(FiltroCredito filtroCredito) {
		StringBuilder query = createQuery("select c.id, c.id_contrato, p.sigla as sigla_periodo, d.nome as nome_disciplina, c.id_disciplina, ");
        query.append("d.obrigatoria as disciplina_obrigatoria, c.id_turma, t.sigla as sigla_turma, t.ano as ano_turma, ");
        query.append("c.id_tipo_credito, c.id_tipo_programa, c.carga_horaria_total, c.carga_horaria_presencial, c.carga_horaria_distancia, c.valor ");
        query.append("from credito c ");
        query.append("join disciplina d on (c.id_disciplina = d.id) ");
        query.append("join periodo p on (d.id_periodo = p.id) ");
        query.append("join periodo_execucao pe on (p.id = pe.id_periodo) ");
        query.append("join turma t on (c.id_turma = t.id) where 1=1 ");
		query = andEqual(query, "c.id", filtroCredito.getId());
		query = andEqual(query, "c.id_contrato", filtroCredito.getIdContrato());
        query = andEqual(query, "c.id_disciplina", filtroCredito.getIdDisciplina());
        query = andEqual(query, "c.id_turma", filtroCredito.getIdTurma()); 
        query = andEqual(query, "c.id_tipo_credito", filtroCredito.getIdTipoCredito());
        query = groupBy(query, "c.id, c.id_contrato, p.sigla, d.nome, c.id, p.sigla, "
        		+ "d.nome, d.obrigatoria, t.sigla, t.ano ");
        query = orderBy(query, Order.ASC, "c.id_tipo_credito");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CreditoContratoDTOMapper());
	}
	
	
	
	private class CreditoContratoDTOMapper implements RowMapper<CreditoContratoDTO> {

		@Override
		public CreditoContratoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CreditoContratoDTO.builder()
					.id(rs.getLong("id"))
                    .idContrato(rs.getLong("id_contrato"))
                    .siglaPeriodo(rs.getString("sigla_periodo"))
                    .idDisciplina(rs.getLong("id_disciplina"))
                    .nomeDisciplina(rs.getString("nome_disciplina"))
                    .disciplinaObrigatoria(rs.getBoolean("disciplina_obrigatoria"))
                    .idTurma(rs.getLong("id_turma"))
                    .siglaTurma(rs.getString("sigla_turma"))
                    .anoTurma(rs.getString("ano_turma"))
                    .idTipoCredito(rs.getInt("id_tipo_credito"))
                    .idTipoPrograma(rs.getInt("id_tipo_programa"))  
                    .cargaHorariaTotal(rs.getInt("carga_horaria_total"))
                    .cargaHorariaPresencial(rs.getInt("carga_horaria_presencial"))
                    .cargaHorariaDistancia(rs.getInt("carga_horaria_distancia"))
                    .valor(rs.getDouble("valor"))
					.build();

		}

	}
	
	private class CreditoMapper implements RowMapper<Credito> {

		@Override
		public Credito mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Credito.builder()
					.id(rs.getLong("id"))
					.valor(rs.getDouble("valor"))
					.cargaHorariaTotal(rs.getInt("carga_horaria_total"))
                    .cargaHorariaPresencial(rs.getInt("carga_horaria_presencial"))
                    .cargaHorariaDistancia(rs.getInt("carga_horaria_distancia"))
                    .totalMinutosAusencia(rs.getInt("total_minutos_ausencia"))
                    .percentualAusencia(rs.getDouble("percentual_ausencia"))
                    .idContrato(rs.getLong("id_contrato"))
                    .idDisciplina(rs.getLong("id_disciplina"))
                    .idTurma(rs.getLong("id_turma"))
                    .idTipoCredito(rs.getInt("id_tipo_credito"))
                    .idTipoPrograma(rs.getInt("id_tipo_programa"))                  
                    .notaFinalNormal(rs.getObject("nota_final_normal") != null ? rs.getDouble("nota_final_normal") : null)
                    .notaFinalExame(rs.getObject("nota_final_exame") != null ? rs.getDouble("nota_final_exame") : null)
                    .notaConselho(rs.getObject("nota_conselho") != null ? rs.getDouble("nota_conselho") : null)
                    .notaResultadoFinal(rs.getObject("nota_resultado_final") != null ? rs.getDouble("nota_resultado_final") : null)        
                    .idTipoMencaoFinal(rs.getObject("id_tipo_mencao_final") != null ? rs.getInt("id_tipo_mencao_final") : null)
                    .pendencia(rs.getBoolean("pendencia"))
					.build();

		}

	}

}
