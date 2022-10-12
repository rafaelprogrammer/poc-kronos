package br.com.kronos.backend.adaptadores.repositorio.pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroPessoa;
import br.com.kronos.backend.aplicacao.pessoa.Pessoa;
import br.com.kronos.backend.aplicacao.pessoa.PessoaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.api.PessoaResumoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PessoaRepositorioImpl extends SqlQueryRepositorio implements PessoaRepositorio {
	
	public PessoaRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Pessoa pessoa) {
		addFields("data_nascimento", "email", "nome", 
				"nome_usual", "nome_social", "cpf", "banco_talento", 
				"grau_comportamento", "id_genero", "id_instituicao", "id_cidade");
		
		addValues(pessoa.getDataNascimento(), pessoa.getEmail(), pessoa.getNome(),
				pessoa.getNomeUsual(), pessoa.getNomeSocial(), pessoa.getCpf(), pessoa.isBancoTalento(), 
				pessoa.getGrauComportamento(), pessoa.getIdGenero(), pessoa.getIdInstituicao(), pessoa.getIdCidade());
		
		return (long) insertAuto("pessoa");
	}
	
	@Override
	public Long alterar(Pessoa pessoa) {
		addFields("numero_registro", "data_nascimento", "email", "nome", 
				"nome_usual", "nome_social", "cpf", "banco_talento", 
				"grau_comportamento", "id_genero", "id_instituicao", "id_cidade", "id_arq_anexo");
		
		addValues(pessoa.getNumeroRegistro(), pessoa.getDataNascimento(), pessoa.getEmail(), pessoa.getNome(),
				pessoa.getNomeUsual(), pessoa.getNomeSocial(), pessoa.getCpf(), pessoa.isBancoTalento(), 
				pessoa.getGrauComportamento(), pessoa.getIdGenero(), pessoa.getIdInstituicao(), pessoa.getIdCidade(), pessoa.getIdArqAnexo(), pessoa.getId());
		
		return (long) update("pessoa", pessoa.getId());
	}
	
	@Override
	public Long alterarGrauComportamento(Long id, Float grauComportamento) {
		addFields("grau_comportamento");
		
		addValues(grauComportamento, id);
		
		return (long) update("pessoa", id);
	}
	
	@Override
	public Pessoa buscarPorId(long id) {
		try {
			StringBuilder query = createQuery("select p.id, p.numero_registro, p.data_nascimento, p.email, p.nome, p.nome_usual, p.id_arq_anexo, ");
			query.append("p.nome_social, p.cpf, p.banco_talento, p.grau_comportamento, p.id_genero, p.id_instituicao, p.id_cidade from pessoa p where 1=1 ");
			query = andEqual(query, "p.id", id);
			Pessoa pessoa = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new PessoaMapper());
			return pessoa;
		} catch (EmptyResultDataAccessException e) {
			log.info("Pessoa não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Pessoa> listar(FiltroPessoa filtroPessoa) {
		StringBuilder query = createQuery("select p.id, p.numero_registro, p.data_nascimento, p.email, p.nome, p.nome_usual, p.id_arq_anexo, ");
		query.append("p.nome_social, p.cpf, p.banco_talento, p.grau_comportamento, p.id_genero, p.id_instituicao, p.id_cidade from pessoa p where 1=1 ");
		query = andEqual(query, "p.id", filtroPessoa.getId());
		query = andLike(query, "p.nome", filtroPessoa.getNome());
		query = andEqual(query, "p.cpf", filtroPessoa.getCpf());
		query = andEqual(query, "p.id_instituicao", filtroPessoa.getIdInstituicao());
		query = andEqual(query, "p.numero_registro", filtroPessoa.getNumeroRegistro());
		query = orderBy(query, Order.ASC, "p.nome");
		query = limit(query, filtroPessoa.getQtdTotal(), filtroPessoa.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PessoaMapper());
	}
	
	@Override
	public int contar(FiltroPessoa filtroPessoa) {
		StringBuilder query = createQuery("select count(p.id) from pessoa p where 1=1 ");
		query = andEqual(query, "p.id", filtroPessoa.getId());
		query = andLike(query, "p.nome", filtroPessoa.getNome());
		query = andEqual(query, "p.numero_registro", filtroPessoa.getNumeroRegistro());
		query = andEqual(query, "p.cpf", filtroPessoa.getCpf());
		query = andEqual(query, "p.id_instituicao", filtroPessoa.getIdInstituicao());
		query = andEqual(query, "p.numero_registro", filtroPessoa.getNumeroRegistro());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(long id) {
		delete("pessoa", id);
		return true;
	}
	
	private class PessoaMapper implements RowMapper<Pessoa> {

		@Override
		public Pessoa mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Pessoa.builder()
					.id(rs.getInt("id"))
					.nome(rs.getString("nome"))
					.numeroRegistro(rs.getInt("numero_registro"))
					.dataNascimento(rs.getDate("data_nascimento").toLocalDate())
					.email(rs.getString("email"))
					.nomeUsual(rs.getString("nome_usual"))
					.nomeSocial(rs.getString("nome_social"))
					.idArqAnexo(rs.getInt("id_arq_anexo"))
					.cpf(rs.getString("cpf"))
					.bancoTalento(rs.getBoolean("banco_talento"))
					.grauComportamento(rs.getFloat("grau_comportamento"))
					.idGenero(rs.getInt("id_genero"))
					.idInstituicao(rs.getInt("id_instituicao"))
					.idCidade(rs.getInt("id_cidade"))
					.build();
		}

	}
	
	@Override
	public List<PessoaResumoDTO> listarParaSelecionarAluno(FiltroPessoa filtroPessoa) {
		StringBuilder query = createQuery("select distinct p.id, m.id_curso, m.id as id_matricula, p.numero_registro, p.cpf, p.nome, p.id_genero, p.grau_comportamento, p.data_nascimento ");
		query.append("from credito c ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) ");
		query.append("left join matricula m on (ct.id_matricula = m.id) ");
		query.append("left join pessoa p on (m.id_pessoa = p.id) ");
		query.append("left join turma t on (c.id_turma = t.id) where 1=1 ");
		
		query = andEqual(query, "m.id_curso", filtroPessoa.getIdCurso());
		query = andEqual(query, "t.ano", filtroPessoa.getAno());
		query = andEqual(query, "t.id", filtroPessoa.getIdTurma());
		query = andLike(query, "p.nome", filtroPessoa.getNome());
		query = andEqual(query, "p.numero_registro", filtroPessoa.getNumeroRegistro());
		query = andEqual(query, "p.cpf", filtroPessoa.getCpf());
		query = orderBy(query, Order.ASC, "p.nome");
		query = limit(query, filtroPessoa.getQtdTotal(), filtroPessoa.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new PessoaResumoDTOMapper());
	}
	
	@Override
	public int contarParaSelecionarAluno(FiltroPessoa filtroPessoa) {
		StringBuilder query = createQuery("select count(distinct p.id) ");
		query.append("from credito c ");
		query.append("left join contrato ct on (c.id_contrato = ct.id) ");
		query.append("left join matricula m on (ct.id_matricula = m.id) ");
		query.append("left join pessoa p on (m.id_pessoa = p.id) ");
		query.append("left join turma t on (c.id_turma = t.id) where 1=1 ");
		query = andEqual(query, "m.id_curso", filtroPessoa.getIdCurso());
		query = andEqual(query, "t.ano", filtroPessoa.getAno());
		query = andEqual(query, "t.id", filtroPessoa.getIdTurma());
		query = andLike(query, "p.nome", filtroPessoa.getNome());
		query = andEqual(query, "p.numero_registro", filtroPessoa.getNumeroRegistro());
		query = andEqual(query, "p.cpf", filtroPessoa.getCpf());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class PessoaResumoDTOMapper implements RowMapper<PessoaResumoDTO> {

		@Override
		public PessoaResumoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return PessoaResumoDTO.builder()
					.id(rs.getInt("id"))
					.nome(rs.getString("nome"))
					.numeroRegistro(rs.getInt("numero_registro"))
					.cpf(rs.getString("cpf"))
					.idCurso(rs.getLong("id_curso"))
					.idMatricula(rs.getLong("id_matricula"))
					.idGenero(rs.getInt("id_genero"))
					.grauComportamento(rs.getFloat("grau_comportamento"))
					.dataNascimento(rs.getDate("data_nascimento").toLocalDate())
					.build();
		}

	}
	
}
