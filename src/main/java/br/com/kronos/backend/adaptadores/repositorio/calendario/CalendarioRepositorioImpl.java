package br.com.kronos.backend.adaptadores.repositorio.calendario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.calendario.Calendario;
import br.com.kronos.backend.aplicacao.calendario.CalendarioRepositorio;
import br.com.kronos.backend.aplicacao.calendario.EnumTipoSituacaoCalendario;
import br.com.kronos.backend.aplicacao.calendario.FiltroCalendario;
import br.com.kronos.backend.aplicacao.calendario.api.CalendarioDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalendarioRepositorioImpl extends SqlQueryRepositorio implements CalendarioRepositorio {
	
	public CalendarioRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Calendario calendario) {
		addFields("ano", "data_inicio", "data_inicio_atrib_credito", 
                  "data_final_atrib_credito", "data_inicio_ano_letivo", "data_final_ano_letivo",  "data_final", 
                  "descricao", "data_aprovacao", "id_instituicao",
                  "id_tipo_situacao_calendario", "data_conclusao", "id_funcionario_elaboracao");
		
		addValues(calendario.getAno(), calendario.getDataInicio(),calendario.getDataInicioAtribuicaoCredito(), 
                  calendario.getDataFinalAtribuicaoCredito(), calendario.getDataInicioAnoLetivo(), calendario.getDataFinalAnoLetivo(), calendario.getDataFinal(),  
                  calendario.getDescricao(), calendario.getDataAprovacao(), calendario.getIdInstituicao(),
                  calendario.getIdTipoSituacaoCalendario(), calendario.getDataConclusao(), calendario.getIdFuncionarioElaboracao());
		
		return (long) insertAuto("calendario"); 
    }

	@Override
	public Long alterar(Calendario calendario) {
		addFields("ano", "data_inicio", "data_inicio_atrib_credito", 
                  "data_final_atrib_credito", "data_inicio_ano_letivo", "data_final_ano_letivo",  "data_final", 
                  "descricao", "data_aprovacao", "id_instituicao",
                  "id_tipo_situacao_calendario", "data_conclusao", "id_funcionario_elaboracao");
		
		addValues(calendario.getAno(), calendario.getDataInicio(),calendario.getDataInicioAtribuicaoCredito(), 
                calendario.getDataFinalAtribuicaoCredito(), calendario.getDataInicioAnoLetivo(), calendario.getDataFinalAnoLetivo(), calendario.getDataFinal(),  
                calendario.getDescricao(), calendario.getDataAprovacao(), calendario.getIdInstituicao(),
                calendario.getIdTipoSituacaoCalendario(), calendario.getDataConclusao(), calendario.getIdFuncionarioElaboracao(), calendario.getId());
		
		return (long) update("calendario", calendario.getId());
	}
	
	
	
	@Override
	public CalendarioDTO buscarPorId(Long id) {
		try {     
            StringBuilder query = createQuery("select c.id, c.numero, c.ano, c.data_inicio, c.data_inicio_atrib_credito, ");
            query.append("c.data_final_atrib_credito, c.data_inicio_ano_letivo, c.data_final_ano_letivo,  c.data_final, ");
            query.append("c.descricao, c.data_aprovacao, c.id_instituicao, c.id_funcionario_aprovacao, ");
            query.append("c.id_tipo_situacao_calendario, c.data_conclusao, c.id_funcionario_elaboracao, ");
            query.append("pessoaApro.nome as pessoa_aprovacao, pessoaElabo.nome as pessoa_elaboracao ");
            query.append("from calendario c ");
            query.append("left join funcionario funcApro on (funcApro.id = c.id_funcionario_aprovacao) ");
            query.append("left join pessoa pessoaApro on (pessoaApro.id = funcApro.id_pessoa) ");
            query.append("join funcionario funcElabo on (funcElabo.id = c.id_funcionario_elaboracao) ");
            query.append("join pessoa pessoaElabo on (pessoaElabo.id = funcElabo.id_pessoa) where 1=1 ");
            query = andEqual(query, "c.id", id);
            CalendarioDTO calendario = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new CalendarioDTOMapper());
			return calendario;
		} catch (EmptyResultDataAccessException e) {
			log.info("calendario não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<Integer> listarAnos() {
		StringBuilder query = createQuery("select distinct c.ano from calendario c ");
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<Integer> listarNumerosPorAno(Integer ano) {
		StringBuilder query = createQuery("select distinct c.numero from calendario c where 1=1 ");
		query = andEqual(query, "c.ano", ano);
		return this.getNamedParameterJdbcTemplate().queryForList(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public List<CalendarioDTO> listar(FiltroCalendario filtroCalendario) {
		StringBuilder query = createQuery("select c.id, c.numero, c.ano, c.data_inicio, c.data_inicio_atrib_credito, ");
        query.append("c.data_final_atrib_credito, c.data_inicio_ano_letivo, c.data_final_ano_letivo,  c.data_final, ");
        query.append("c.descricao, c.data_aprovacao, c.id_instituicao, c.id_funcionario_aprovacao, ");
        query.append("c.id_tipo_situacao_calendario, c.data_conclusao, c.id_funcionario_elaboracao, ");
        query.append("pessoaApro.nome as pessoa_aprovacao, pessoaElabo.nome as pessoa_elaboracao ");
        query.append("from calendario c ");
        query.append("left join funcionario funcApro on (funcApro.id = c.id_funcionario_aprovacao) ");
        query.append("left join pessoa pessoaApro on (pessoaApro.id = funcApro.id_pessoa) ");
        query.append("join funcionario funcElabo on (funcElabo.id = c.id_funcionario_elaboracao) ");
        query.append("join pessoa pessoaElabo on (pessoaElabo.id = funcElabo.id_pessoa) where 1=1 ");
        query = andEqual(query, "c.id", filtroCalendario.getId());
		query = andEqual(query, "c.numero", filtroCalendario.getNumero());
        query = andEqual(query, "c.ano", filtroCalendario.getAno());
        query = andEqual(query, "c.id_instituicao", filtroCalendario.getIdInstituicao());
        query = andEqual(query, "c.id_tipo_situacao_calendario", filtroCalendario.getIdTipoSituacaoCalendario());
        query = andLike(query, "c.descricao", filtroCalendario.getDescricao());
        query = orderBy(query, Order.DESC, "c.ano");
		query = limit(query, filtroCalendario.getQtdTotal(), filtroCalendario.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new CalendarioDTOMapper());
	}
	
	@Override
	public int contar(FiltroCalendario filtroCalendario) {
		StringBuilder query = createQuery("select count(c.id) from calendario c where 1=1 ");
		query = andEqual(query, "c.id", filtroCalendario.getId());
		query = andEqual(query, "c.numero", filtroCalendario.getNumero());
        query = andEqual(query, "c.ano", filtroCalendario.getAno());
        query = andEqual(query, "c.id_instituicao", filtroCalendario.getIdInstituicao());
        query = andEqual(query, "c.id_tipo_situacao_calendario", filtroCalendario.getIdTipoSituacaoCalendario());
        query = andLike(query, "c.descricao", filtroCalendario.getDescricao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public long recuperarIdPeriodoExecucao(Long idPeriodoExecucao) {
		StringBuilder query = createQuery("select pe.id_calendario ");
		query.append("from  periodo_execucao pe where 1=1 ");
		query = andEqual(query, "pe.id", idPeriodoExecucao);
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Long.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("calendario", id);
		return true;
	}
	
	private class CalendarioDTOMapper implements RowMapper<CalendarioDTO> {

		@Override
		public CalendarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return CalendarioDTO.builder()
					.id(rs.getLong("id"))
					.ano(rs.getInt("ano"))
					.numero(rs.getInt("numero"))
                    .dataInicio(rs.getDate("data_inicio").toLocalDate())
                    .dataInicioAtribuicaoCredito(rs.getDate("data_inicio_atrib_credito").toLocalDate())
                    .dataFinalAtribuicaoCredito(rs.getDate("data_final_atrib_credito").toLocalDate())
                    .dataInicioAnoLetivo(rs.getDate("data_inicio_ano_letivo").toLocalDate())
                    .dataFinalAnoLetivo(rs.getDate("data_final_ano_letivo").toLocalDate())
                    .dataFinal(rs.getDate("data_final").toLocalDate())
                    .descricao(rs.getString("descricao"))
                    .dataAprovacao(rs.getDate("data_aprovacao") != null ? rs.getDate("data_aprovacao").toLocalDate() : null)
                    .idInstituicao(rs.getLong("id_instituicao"))
                    .idFuncionarioAprovacao(rs.getLong("id_funcionario_aprovacao"))
                    .idTipoSituacaoCalendario(rs.getInt("id_tipo_situacao_calendario"))
                    .nomeFuncionarioAprovacao(rs.getString("pessoa_aprovacao"))
                    .dataConclusao(rs.getDate("data_conclusao") != null ? rs.getDate("data_conclusao").toLocalDate() : null)
                    .idFuncionarioElaboracao(rs.getLong("id_funcionario_elaboracao"))
                    .nomeFuncionarioElaboracao(rs.getString("pessoa_elaboracao"))
					.build();
		}
	}

	@Override
	public void alterarSituacao(Long id, EnumTipoSituacaoCalendario situacao, Long idFuncionarioElaboracao,
			Long idFuncionarioAprovacao) {
		
		if (idFuncionarioAprovacao == null && idFuncionarioElaboracao == null) {
			addFields("id_tipo_situacao_calendario");
			addValues(situacao.id(), id);
		}
		
		if (idFuncionarioAprovacao != null && idFuncionarioElaboracao == null) {
			addFields("id_tipo_situacao_calendario", "id_funcionario_aprovacao");
			addValues(situacao.id(), idFuncionarioAprovacao, id);
		}
		
		if (idFuncionarioElaboracao != null && idFuncionarioAprovacao == null) {
			addFields("id_tipo_situacao_calendario", "id_funcionario_elaboracao");
			addValues(situacao.id(), idFuncionarioElaboracao, id);
		}
		
		if (idFuncionarioElaboracao != null && idFuncionarioAprovacao != null) {
			addFields("id_tipo_situacao_calendario", "id_funcionario_elaboracao", "id_funcionario_aprovacao");
			addValues(situacao.id(), idFuncionarioElaboracao, idFuncionarioAprovacao, id);
		}
		
		update("calendario", id);
		
	}
}

