package br.com.kronos.backend.adaptadores.repositorio.funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.EnumTipoFuncao;
import br.com.kronos.backend.aplicacao.funcionario.FiltroFuncionario;
import br.com.kronos.backend.aplicacao.funcionario.Funcionario;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FuncionarioRepositorioImpl extends SqlQueryRepositorio implements FuncionarioRepositorio {

	public FuncionarioRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Funcionario funcionario) {
		addFields("data_admissao", "data_demissao", "id_instituicao", "id_pessoa");

		addValues(funcionario.getDataAdmissao(), funcionario.getDataDemissao(), funcionario.getIdInstituicao(),
				funcionario.getIdPessoa());

		return (long) insertAuto("funcionario");
	}

	@Override
	public Long alterar(Funcionario funcionario) {
		addFields("data_admissao", "data_demissao", "id_instituicao", "id_pessoa");

		addValues(funcionario.getDataAdmissao(), funcionario.getDataDemissao(), funcionario.getIdInstituicao(),
				funcionario.getIdPessoa(), funcionario.getId());

		return (long) update("funcionario", funcionario.getId());
	}

	@Override
	public FuncionarioDTO buscarPorId(Long id) {
		try {
			StringBuilder query = createQuery(
					"select f.id, f.data_admissao, f.data_demissao, f.id_instituicao, f.id_pessoa, p.nome, p.numero_registro, p.cpf,  ");
			query.append("p.data_nascimento, p.email ");
			query.append("from funcionario f ");
			query.append("left join pessoa p on (f.id_pessoa = p.id)  ");
			query.append("left join funcionario_funcao ff on (f.id = ff.id_funcionario)  ");
			query.append("left join tipo_funcao tf on (ff.id_tipo_funcao = tf.id) where 1=1 ");
			query = andEqual(query, "f.id", id);
			FuncionarioDTO funcionario = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new FuncionarioDTOMapper());
			return funcionario;
		} catch (EmptyResultDataAccessException e) {
			log.info("funcionario não existe - " + id);
			return null;
		}
	}

	@Override
	public FuncionarioDTO buscarPorIdPessoa(Long idPessoa) {
		try {
			StringBuilder query = createQuery(
					"select f.id, f.data_admissao, f.data_demissao, f.id_instituicao, f.id_pessoa, p.nome, p.numero_registro, p.cpf, p.data_nascimento, p.email  ");
			query.append("from funcionario f ");
			query.append("join pessoa p on (f.id_pessoa = p.id) where f.data_demissao is null ");
			query = andEqual(query, "f.id_pessoa", idPessoa);
			FuncionarioDTO funcionario = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new FuncionarioDTOMapper());
			return funcionario;
		} catch (EmptyResultDataAccessException e) {
			log.info("funcionario não existe com id pessoa - " + idPessoa);
			return null;
		}
	}

	@Override
	public List<FuncionarioDTO> listarPorDisciplina(long idDisciplina) {
		StringBuilder query = createQuery(
				"select f.id, f.data_admissao, f.data_demissao, f.id_instituicao, f.id_pessoa, p.nome, p.numero_registro, p.cpf, p.data_nascimento, p.email  ");
		query.append("from funcionario f ");
		query.append("left join disciplina_funcionario df on (f.id = df.id_funcionario) ");
		query.append("left join pessoa p on (f.id_pessoa = p.id) where 1=1 ");
		query = andEqual(query, "df.id_disciplina", idDisciplina);
		query = orderBy(query, Order.ASC, "p.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new FuncionarioDTOMapper());
	}

	@Override
	public List<FuncionarioDTO> listar(FiltroFuncionario filtroFuncionario) {
		StringBuilder query = createQuery(
				"select distinct f.id, f.data_admissao, f.data_demissao, f.id_instituicao, f.id_pessoa, p.nome, p.numero_registro, p.cpf,  ");
		query.append("p.data_nascimento, p.email ");
		query.append("from funcionario f ");
		query.append("left join pessoa p on (f.id_pessoa = p.id)  ");
		query.append("left join funcionario_funcao ff on (f.id = ff.id_funcionario)  ");
		query.append("left join tipo_funcao tf on (ff.id_tipo_funcao = tf.id) where 1=1 ");
		if (filtroFuncionario.getAtivo() != null) {
			if (filtroFuncionario.getAtivo()) {
				query.append("and f.data_demissao is null ");
			} else {
				query.append(
						"and (select count(f1.id) from funcionario f1 where f1.data_demissao is null and f1.id_pessoa = p.id ) = 0 ");
			}
		}
		query = andEqual(query, "f.id", filtroFuncionario.getId());
		query = andEqual(query, "f.id_pessoa", filtroFuncionario.getIdPessoa());
		query = andEqual(query, "f.id_instituicao", filtroFuncionario.getIdInstituicao());
		query = andEqual(query, "p.numero_registro", filtroFuncionario.getNumeroRegistro());
		query = andEqual(query, "p.cpf", filtroFuncionario.getCpf());
		query = andLike(query, "p.nome", filtroFuncionario.getNome());
		query = andEqual(query, "tf.id", filtroFuncionario.getIdTipoFuncao());
		query = andEqual(query, "tf.id_tipo_categoria_funcao", filtroFuncionario.getIdTipoCategoriaFuncao());

		query = orderBy(query, Order.DESC, "p.nome, f.data_admissao");
		query = limit(query, filtroFuncionario.getQtdTotal(), filtroFuncionario.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new FuncionarioDTOMapper());
	}

	@Override
	public int contar(FiltroFuncionario filtroFuncionario) {
		StringBuilder query = createQuery("select count(f.id) ");
		query.append("from funcionario f ");
		query.append("left join pessoa p on (f.id_pessoa = p.id)  ");
		query.append("left join funcionario_funcao ff on (f.id = ff.id_funcionario)  ");
		query.append("left join tipo_funcao tf on (ff.id_tipo_funcao = tf.id) where 1=1 ");
		if (filtroFuncionario.getAtivo() != null) {
			if (filtroFuncionario.getAtivo()) {
				query.append("and f.data_demissao is null ");
			} else {
				query.append(
						"and (select count(f1.id) from funcionario f1 where f1.data_demissao is null and f1.id_pessoa = p.id ) = 0 ");
			}
		}
		query = andEqual(query, "f.id", filtroFuncionario.getId());
		query = andEqual(query, "f.id_pessoa", filtroFuncionario.getIdPessoa());
		query = andEqual(query, "f.id_instituicao", filtroFuncionario.getIdInstituicao());
		query = andEqual(query, "p.numero_registro", filtroFuncionario.getNumeroRegistro());
		query = andEqual(query, "p.cpf", filtroFuncionario.getCpf());
		query = andLike(query, "p.nome", filtroFuncionario.getNome());
		query = andEqual(query, "tf.id", filtroFuncionario.getIdTipoFuncao());
		query = andEqual(query, "tf.id_tipo_categoria_funcao", filtroFuncionario.getIdTipoCategoriaFuncao());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(),
				Integer.class);
	}

	@Override
	public boolean excluir(Long id) {
		delete("funcionario", id);
		return true;
	}

	private class FuncionarioDTOMapper implements RowMapper<FuncionarioDTO> {

		@Override
		public FuncionarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return FuncionarioDTO.builder()
					.id(rs.getLong("id"))
					.dataAdmissao(rs.getDate("data_admissao").toLocalDate())
					.dataDemissao(
							rs.getDate("data_demissao") != null ? rs.getDate("data_demissao").toLocalDate() : null)
					.dataNascimento(rs.getDate("data_nascimento") != null ? rs.getDate("data_nascimento").toLocalDate() : null)
					.email(rs.getString("email"))
					.idInstituicao(rs.getLong("id_instituicao"))
					.idPessoa(rs.getLong("id_pessoa"))
					.nome(rs.getString("nome")).cpf(rs.getString("cpf"))
					.numeroRegistro(rs.getString("numero_registro")).build();
		}

	}

	@Override
	public List<FuncionarioDTO> listarParaOcorrencia(LocalDate dataOcorrencia, Long idInstituicao) {
		StringBuilder query = createQuery("select distinct f.id, p.nome  ");
		query.append("from funcionario f ");
		query.append("left join pessoa p on (f.id_pessoa = p.id) ");
		query.append("left join funcionario_funcao ff on (f.id = ff.id_funcionario) where 1=1 ");
		query = andEqual(query, "f.id_instituicao", idInstituicao);
		query = andIn(query, "ff.id_tipo_funcao", EnumTipoFuncao.DIRETOR.id(), EnumTipoFuncao.VICE_DIRETOR.id(),
				EnumTipoFuncao.COORDENADOR.id(), EnumTipoFuncao.PROFESSOR.id(), EnumTipoFuncao.INSPETOR.id());

		query.append(" and f.data_admissao <= ");
		query.append("to_date('");
		query.append(dataOcorrencia.toString());
		query.append("', 'yyyy-MM-dd') ");

		query.append(" and (f.data_demissao is null or f.data_demissao > ");
		query.append("to_date('");
		query.append(dataOcorrencia.toString());
		query.append("', 'yyyy-MM-dd')) ");

		query = orderBy(query, Order.ASC, "p.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new FuncionarioDTOParaOcorrenciaMapper());
	}

	private class FuncionarioDTOParaOcorrenciaMapper implements RowMapper<FuncionarioDTO> {

		@Override
		public FuncionarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return FuncionarioDTO.builder().id(rs.getLong("id")).nome(rs.getString("nome")).build();
		}

	}

	@Override
	public List<FuncionarioDTO> listarParaOcorrenciaResponsavelAnulacao(LocalDate dataOcorrencia, Long idInstituicao) {
		StringBuilder query = createQuery("select distinct f.id, p.nome  ");
		query.append("from funcionario f ");
		query.append("left join pessoa p on (f.id_pessoa = p.id) ");
		query.append("left join funcionario_funcao ff on (f.id = ff.id_funcionario) where 1=1 ");
		query = andEqual(query, "f.id_instituicao", idInstituicao);
		query = andEqual(query, "ff.id_tipo_funcao", EnumTipoFuncao.COORDENADOR.id());

		query.append(" and f.data_admissao <= ");
		query.append("to_date('");
		query.append(dataOcorrencia.toString());
		query.append("', 'yyyy-MM-dd') ");

		query.append(" and (f.data_demissao is null or f.data_demissao > ");
		query.append("to_date('");
		query.append(dataOcorrencia.toString());
		query.append("', 'yyyy-MM-dd')) ");

		query = orderBy(query, Order.ASC, "p.nome");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(),
				new FuncionarioDTOParaOcorrenciaMapper());
	}

}
