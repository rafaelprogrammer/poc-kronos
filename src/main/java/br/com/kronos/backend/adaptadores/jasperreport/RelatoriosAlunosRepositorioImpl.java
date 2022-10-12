package br.com.kronos.backend.adaptadores.jasperreport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.comum.FiltroRelatorio;
import br.com.kronos.backend.aplicacao.relatorio.RelatoriosAlunosRepositorio;
import br.com.kronos.backend.aplicacao.relatorio.api.AlunoRelatorioDTO;

public class RelatoriosAlunosRepositorioImpl extends SqlQueryRepositorio implements RelatoriosAlunosRepositorio {
	
	public RelatoriosAlunosRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<AlunoRelatorioDTO> listarPorTurmaR(FiltroRelatorio filtro) {
		StringBuilder query = createQuery("select ROW_NUMBER() OVER (ORDER BY p.nome) AS nr, ");
		query.append("p.numero_registro, p.cpf, p.nome, ts.nome as situacao, p.email ");
		query.append("from credito c ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) ");
		query.append("left join matricula m on (ct.id_matricula = m.id) ");
		query.append("left join pessoa p on (m.id_pessoa = p.id) ");
		query.append("left join tipo_situacao_contrato ts on (ct.id_tipo_situacao_contrato = ts.id) where 1=1 ");
		query = andEqual(query, "c.id_turma", filtro.getIdTurma());
		query = andIn(query, "ct.id_tipo_situacao_contrato", filtro.getIdsTipoSituacaoContrato());
		query = groupBy(query, "p.numero_registro, p.cpf, p.nome, p.email, ts.nome ");
		query = orderBy(query, Order.ASC, "p.nome");
		
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AlunoRelatorioTurmaRDTOMapper());
	}
	
	@Override
	public int contarPorTurmaR(FiltroRelatorio filtro) {
		StringBuilder query = createQuery("select count(distinct p.numero_registro) ");
		query.append("from credito c ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) ");
		query.append("left join matricula m on (ct.id_matricula = m.id) ");
		query.append("left join pessoa p on (m.id_pessoa = p.id) ");
		query.append("left join tipo_situacao_contrato ts on (ct.id_tipo_situacao_contrato = ts.id) where 1=1 ");
		query = andEqual(query, "c.id_turma", filtro.getIdTurma());
		query = andIn(query, "ct.id_tipo_situacao_contrato", filtro.getIdsTipoSituacaoContrato());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class AlunoRelatorioTurmaRDTOMapper implements RowMapper<AlunoRelatorioDTO> {

		@Override
		public AlunoRelatorioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AlunoRelatorioDTO.builder()
					.nr(rs.getInt("nr"))
					.nome(rs.getString("nome"))
					.numeroRegistro(rs.getInt("numero_registro"))
					.cpf(rs.getString("cpf"))
					.situacao(rs.getString("situacao"))
					.email(rs.getString("email"))
					.build();
		}

	}
	
	@Override
	public List<AlunoRelatorioDTO> listarPorPeriodoR(FiltroRelatorio filtro) {
		StringBuilder query = createQuery("select ROW_NUMBER() OVER (ORDER BY t.sigla, p.nome) AS seq, ");
		query.append("ROW_NUMBER () OVER ( PARTITION BY t.sigla ORDER BY t.sigla, p.nome) as nr, ");
		query.append("t.sigla as turma, p.numero_registro, p.cpf, p.nome, ts.nome as situacao, p.email, cast(null as varchar(50)) as assinatura ");
		query.append("from credito c ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) ");
		query.append("left join matricula m on (ct.id_matricula = m.id) ");
		query.append("left join pessoa p on (m.id_pessoa = p.id) ");
		query.append("left join turma t on (c.id_turma = t.id) ");
		query.append("left join tipo_situacao_contrato ts on (ct.id_tipo_situacao_contrato = ts.id) where 1=1 ");
		query = andEqual(query, "t.id_periodo_execucao", filtro.getIdPeriodoExecucao());
		query = andIn(query, "ct.id_tipo_situacao_contrato", filtro.getIdsTipoSituacaoContrato());
		query = groupBy(query, "t.sigla, p.numero_registro, p.cpf, p.nome, p.email, ts.nome");
		query = orderBy(query, Order.ASC, "t.sigla, p.nome");
		
		query = limit(query, filtro.getQtdTotal(), filtro.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new AlunoRelatorioPeriodoRDTOMapper());
	}
	
	@Override
	public int contarPorPeriodoR(FiltroRelatorio filtro) {
		StringBuilder query = createQuery("select count(distinct p.numero_registro) ");
		query.append("from credito c ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) ");
		query.append("left join matricula m on (ct.id_matricula = m.id) ");
		query.append("left join pessoa p on (m.id_pessoa = p.id) ");
		query.append("left join turma t on (c.id_turma = t.id) ");
		query.append("left join tipo_situacao_contrato ts on (ct.id_tipo_situacao_contrato = ts.id) where 1=1 ");
		query = andEqual(query, "t.id_periodo_execucao", filtro.getIdPeriodoExecucao());
		query = andIn(query, "ct.id_tipo_situacao_contrato", filtro.getIdsTipoSituacaoContrato());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class AlunoRelatorioPeriodoRDTOMapper implements RowMapper<AlunoRelatorioDTO> {

		@Override
		public AlunoRelatorioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return AlunoRelatorioDTO.builder()
					.seq(rs.getInt("seq"))
					.nr(rs.getInt("nr"))
					.nome(rs.getString("nome"))
					.numeroRegistro(rs.getInt("numero_registro"))
					.cpf(rs.getString("cpf"))
					.situacao(rs.getString("situacao"))
					.email(rs.getString("email"))
					.turma(rs.getString("turma"))
					.build();
		}

	}

}
