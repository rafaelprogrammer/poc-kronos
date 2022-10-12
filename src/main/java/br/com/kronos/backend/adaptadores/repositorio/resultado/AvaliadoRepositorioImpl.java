package br.com.kronos.backend.adaptadores.repositorio.resultado;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.contrato.EnumTipoSituacaoContrato;
import br.com.kronos.backend.aplicacao.resultado.Avaliado;
import br.com.kronos.backend.aplicacao.resultado.AvaliadoRepositorio;
import br.com.kronos.backend.aplicacao.resultado.FiltroAvaliado;
import br.com.kronos.backend.aplicacao.resultado.api.AvaliadoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AvaliadoRepositorioImpl extends SqlQueryRepositorio implements AvaliadoRepositorio {
	
	public AvaliadoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Override
	public void criarAvaliadoAutomatico(Long idAtividadeSelecionada) {
		StringBuilder query = createQuery("insert into avaliado (id_credito, id_grupo_avaliacao, nota, descarte, motivo_descarte, id_mencao, id_avaliacao) ");
        query.append("(select c.id, null,null, false, null, null, (select max(id) from avaliacao where id_atividade = ");
        query.append(idAtividadeSelecionada);
        query.append(") ");
        
        query.append("from credito c ");
        query.append("left join contrato ct on (c.id_contrato = ct.id) where ct.id_tipo_situacao_contrato = ");
        query.append(EnumTipoSituacaoContrato.CONFIRMADO.id());
        
        query.append("and c.id_turma = (select id_turma from horario h left join atividade a on (h.id = a.id_horario) where a.id = ");
        query.append(idAtividadeSelecionada);
        query.append(") ");
        
        query.append("and c.id_disciplina = (select id_disciplina from horario h left join atividade a on (h.id = a.id_horario) where a.id = ");
        query.append(idAtividadeSelecionada);
        query.append(")) ");
        
        
        insertCustom(query.toString());
        
	}

	@Override
	public Long criar(Avaliado avaliado) {
        addFields("id_avaliacao", "id_credito", "id_grupo_avaliacao", "nota",
        		  "descarte", "motivo_descarte", "id_mencao");
		
		addValues(avaliado.getIdAvaliacao(), avaliado.getIdCredito(), avaliado.getIdGrupoAvaliacao(), avaliado.getNota(),
				avaliado.isDescarte(), avaliado.getMotivoDescarte(), avaliado.getIdMencao());
		
		return (long) insertAuto("avaliado"); 
    }
		
	@Override
	public Long alterar(Avaliado avaliado) {
		addFields("id_avaliacao", "id_credito", "id_grupo_avaliacao", "nota",
      		  "descarte", "motivo_descarte", "id_mencao");
		
		addValues(avaliado.getIdAvaliacao(), avaliado.getIdCredito(), avaliado.getIdGrupoAvaliacao(), avaliado.getNota(),
				avaliado.isDescarte(), avaliado.getMotivoDescarte(), avaliado.getIdMencao(), avaliado.getId());
		
		return (long) update("avaliado", avaliado.getId());
	}
	
	@Override
	public Avaliado buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select a.id_avaliacao, a.id_credito, a.id_grupo_avaliacao, a.nota, ");
            query.append("a.descarte, a.motivo_descarte, a.id_mencao ");
            query.append("from avaliado a where 1=1 ");
            query = andEqual(query, "a.id", id);
			Avaliado avaliado = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new AvaliadoMapper());
			return avaliado;
		} catch (EmptyResultDataAccessException e) {
			log.info("Avaliado não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Avaliado> listar(FiltroAvaliado filtroAvaliado) {
		StringBuilder query = createQuery("select a.id_avaliacao, a.id_credito, a.id_grupo_avaliacao, a.nota, ");
        query.append("a.descarte, a.motivo_descarte, a.id_mencao ");
        query.append("from avaliado a where 1=1 ");
        query = andEqual(query, "a.id", filtroAvaliado.getId());  
        query = andEqual(query, "a.id_avaliacao", filtroAvaliado.getIdAvaliacao());
        query = andEqual(query, "a.id_credito", filtroAvaliado.getIdCredito());
        query = andEqual(query, "a.id_grupo_avaliacao", filtroAvaliado.getIdGrupoAvaliacao());
        query = orderBy(query, Order.ASC, "a.id_credito");
		query = limit(query, filtroAvaliado.getQtdTotal(), filtroAvaliado.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AvaliadoMapper());
	}
	
	@Override
	public int contar(FiltroAvaliado filtroAvaliado) {
		 StringBuilder query = createQuery("select count(a.id) from avaliado a where 1=1 ");
		 query = andEqual(query, "a.id", filtroAvaliado.getId());  
	        query = andEqual(query, "a.id_avaliacao", filtroAvaliado.getIdAvaliacao());
	        query = andEqual(query, "a.id_credito", filtroAvaliado.getIdCredito());
	        query = andEqual(query, "a.id_grupo_avaliacao", filtroAvaliado.getIdGrupoAvaliacao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("avaliado", id);
		return true;
	}
	
	@Override
	public boolean excluirPorAvaliacao(Long idAvaliacao) {
		addFields("id_avaliacao");
		addValues(idAvaliacao);
		delete("avaliado");
		return true;
	}
	
	private class AvaliadoMapper implements RowMapper<Avaliado> {

		@Override
		public Avaliado mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Avaliado.builder()
					.id(rs.getLong("id"))
					.idAvaliacao(rs.getLong("id_avaliacao"))
                    .idCredito(rs.getLong("id_credito"))
                    .idGrupoAvaliacao(rs.getLong("id_grupo_avaliacao"))
                    .nota(rs.getDouble("nota"))
                    .descarte(rs.getBoolean("descarte"))
                    .motivoDescarte(rs.getString("motivo_descarte"))
                    .idMencao(rs.getLong("id_mencao"))
					.build();
		}
	}
	
	@Override
	public List<AvaliadoDTO> listarParaSelecaoNaAvaliacao(FiltroAvaliado filtroAvaliado) {
		StringBuilder query = createQuery("select cast(null as bigint) as id, cast(null as bigint) as id_avaliacao, c.id as id_credito, p.numero_registro, ");
        query.append("p.nome, cast(false as boolean) as descarte, cast(null as bigint) as id_grupo_avaliacao ");
        query.append("from credito c ");
        query.append("left join contrato ct on (c.id_contrato = ct.id) ");
        query.append("left join matricula m on (ct.id_matricula = m.id) ");
        query.append("left join pessoa p on (m.id_pessoa = p.id) where 1=1 ");
        query = andEqual(query, "c.id_turma", filtroAvaliado.getIdTurma());
        query = andEqual(query, "c.id_disciplina", filtroAvaliado.getIdDisciplina());
        query = andEqual(query, "ct.ano", filtroAvaliado.getAnoTurma());
        query.append(" and not exists ( ");
        query.append("select a.id_credito from avaliado a where 1=1 ");
        query = andEqual(query, "a.id_avaliacao", filtroAvaliado.getIdAvaliacao());
        query.append(" and (a.id_credito = c.id)) ");
        query.append("union ");
        query.append("select a.id, a.id_avaliacao, a.id_credito, p.numero_registro, p.nome, a.descarte, a.id_grupo_avaliacao ");
        query.append("from avaliado a ");
        query.append("left join credito c on (a.id_credito = c.id) ");
        query.append("left join contrato ct on (c.id_contrato = ct.id) ");
        query.append("left join matricula m on (ct.id_matricula = m.id) ");
        query.append("left join pessoa p on (m.id_pessoa = p.id) where 1=1 ");
        query = andEqual(query, "a.id_avaliacao", filtroAvaliado.getIdAvaliacao());
        query = andEqual(query, "ct.ano", filtroAvaliado.getAnoTurma());
        query.append(" order by 5 ");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AvaliadoDTOMapper());
	}
	
	@Override
	public int contarParaSelecaoNaAvaliacao(FiltroAvaliado filtroAvaliado) {
		StringBuilder query = createQuery("select c.id ");
        query.append("from credito c ");
        query.append("left join contrato ct on (c.id_contrato = ct.id) ");
        query.append("left join matricula m on (ct.id_matricula = m.id) ");
        query.append("left join pessoa p on (m.id_pessoa = p.id) where 1=1 ");
        query = andEqual(query, "c.id_turma", filtroAvaliado.getIdTurma());
        query = andEqual(query, "c.id_disciplina", filtroAvaliado.getIdDisciplina());
        query = andEqual(query, "ct.ano", filtroAvaliado.getAnoTurma());
        query.append(" and not exists ( ");
        query.append("select a.id_credito from avaliado a where 1=1 ");
        query = andEqual(query, "a.id_avaliacao", filtroAvaliado.getIdAvaliacao());
        query.append(" and (a.id_credito = c.id)) ");
        query.append("union ");
        query.append("select a.id ");
        query.append("from avaliado a ");
        query.append("left join credito c on (a.id_credito = c.id) ");
        query.append("left join contrato ct on (c.id_contrato = ct.id) ");
        query.append("left join matricula m on (ct.id_matricula = m.id) ");
        query.append("left join pessoa p on (m.id_pessoa = p.id) where 1=1 ");
        query = andEqual(query, "a.id_avaliacao", filtroAvaliado.getIdAvaliacao());
        query = andEqual(query, "ct.ano", 2022);
		List<Integer> ids = this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Integer.class);
		return ids != null && !ids.isEmpty() ? ids.size() : 0;
	}
	
	private class AvaliadoDTOMapper implements RowMapper<AvaliadoDTO> {

		@Override
		public AvaliadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AvaliadoDTO.builder()
					.id(rs.getLong("id"))
					.idAvaliacao(rs.getLong("id_avaliacao"))
                    .idCredito(rs.getLong("id_credito"))
                    .idGrupoAvaliacao(rs.getLong("id_grupo_avaliacao"))
                    .descarte(rs.getBoolean("descarte"))
                    .nomeAvaliado(rs.getString("nome"))
                    .numeroRegistro(rs.getInt("numero_registro"))
					.build();
		}
	}
	
	@Override
	public List<AvaliadoDTO> listarResultadoHabiliadeDoAvaliado(FiltroAvaliado filtroAvaliado) {
		StringBuilder query = createQuery("select a.id, p.numero_registro, p.nome,  ");
		
		adicionarCaseSituacao(filtroAvaliado, query);
		
		adicionarCaseIdMencao(filtroAvaliado, query);
		
		
        query.append("array(select m.simbolo from resultado_habilidade rh ");
        query.append("left join mencao m on (rh.id_mencao = m.id) ");
        query.append("where rh.id_avaliado = a.id ");
        query.append("order by rh.id_avaliacao_habilidade) as mencoes ");
        query.append("from avaliado a ");
        query.append("left join credito c on (a.id_credito = c.id) ");
        query.append("left join contrato ct on (c.id_contrato = ct.id) ");
        query.append("left join matricula m on (ct.id_matricula = m.id) ");
        query.append("left join pessoa p on (m.id_pessoa = p.id) where 1=1 ");
        query = andEqual(query, "a.id_avaliacao", filtroAvaliado.getIdAvaliacao());
        query = andEqual(query, "ct.ano", filtroAvaliado.getAnoTurma());
        query = orderBy(query, Order.ASC, "p.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AvaliadoDTOComMensoesMapper());
	}

	private void adicionarCaseIdMencao(FiltroAvaliado filtroAvaliado, StringBuilder query) {
		query.append("CASE ");
		query.append("WHEN (select tf.sigla ");
		query.append("from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) ");
		query.append("where at.id_tipo_atestado = 2 ");
		query.append("and at.id_pessoa = p.id ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(filtroAvaliado.getDataAvaliacao().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append("between at.data_inicio_vigencia and at.data_final_vigencia) is not null THEN 6 ");
		
		query.append("WHEN (select tf.sigla ");
		query.append("from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) ");
		query.append("where at.id_tipo_atestado = 4 ");
		query.append("and at.id_pessoa = p.id ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(filtroAvaliado.getDataAvaliacao().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append("between at.data_inicio_vigencia and at.data_final_vigencia) is not null THEN 5 ");
		
		query.append(" ELSE Null END AS id_mencao,");
	}

	private void adicionarCaseSituacao(FiltroAvaliado filtroAvaliado, StringBuilder query) {
		query.append("CASE ");
		query.append("WHEN (select tf.sigla ");
		query.append("from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) ");
		query.append("where at.id_tipo_atestado = 2 ");
		query.append("and at.id_pessoa = p.id ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(filtroAvaliado.getDataAvaliacao().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append("between at.data_inicio_vigencia and at.data_final_vigencia) is not null THEN 'Transferido' ");
		
		
		query.append("WHEN (select tf.sigla ");
		query.append("from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) ");
		query.append("where at.id_tipo_atestado = 4 ");
		query.append("and at.id_pessoa = p.id ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(filtroAvaliado.getDataAvaliacao().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append("between at.data_inicio_vigencia and at.data_final_vigencia) is not null THEN 'Cancelamanto' ");
		
		query.append("WHEN (select tf.sigla ");
		query.append("from atestado at ");
		query.append("left join tipo_falta tf on (at.id_tipo_falta = tf.id) ");
		query.append("where at.id_tipo_atestado = 1 ");
		query.append("and at.id_pessoa = p.id ");
		
		query.append(" and ");
		query.append("to_date('");
		query.append(filtroAvaliado.getDataAvaliacao().toString());
		query.append("', 'yyyy-MM-dd') ");
		
		query.append("between at.data_inicio_vigencia and at.data_final_vigencia) is not null THEN 'Atestado médico' ");
		
		query.append(" ELSE 'Ativo' END AS situacao,");
	}
	
	@Override
	public int contarResultadoHabiliadeDoAvaliado(FiltroAvaliado filtroAvaliado) {
		 StringBuilder query = createQuery("select count(a.id) ");
		 query.append("from avaliado a ");
	     query.append("left join credito c on (a.id_credito = c.id) ");
	     query.append("left join contrato ct on (c.id_contrato = ct.id) ");
	     query.append("left join matricula m on (ct.id_matricula = m.id) ");
	     query.append("left join pessoa p on (m.id_pessoa = p.id) where 1=1 ");
	     query = andEqual(query, "a.id_avaliacao", filtroAvaliado.getIdAvaliacao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class AvaliadoDTOComMensoesMapper implements RowMapper<AvaliadoDTO> {

		@Override
		public AvaliadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AvaliadoDTO.builder()
					.id(rs.getLong("id"))
                    .nomeAvaliado(rs.getString("nome"))
                    .numeroRegistro(rs.getInt("numero_registro"))
                    .mencoes(rs.getArray("mencoes") != null ? (String[])rs.getArray("mencoes").getArray() : null)
                    .situacao(rs.getString("situacao"))
                    .idMencao(rs.getLong("id_mencao"))
					.build();
		}
	}
}
