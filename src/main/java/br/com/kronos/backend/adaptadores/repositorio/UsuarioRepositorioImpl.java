package br.com.kronos.backend.adaptadores.repositorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.FiltroUsuario;
import br.com.kronos.backend.aplicacao.Usuario;
import br.com.kronos.backend.aplicacao.UsuarioPessoa;
import br.com.kronos.backend.aplicacao.UsuarioRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsuarioRepositorioImpl extends SqlQueryRepositorio implements UsuarioRepositorio {
	
	public UsuarioRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long criar(Usuario usuario) {
		addFields("id_pessoa", "ativo", "data_criacao", "hash_senha", 
				"data_ativacao", "id_instituicao", "bloqueado");
		
		addValues(usuario.getIdPessoa(), usuario.isAtivo(), usuario.getDataCriacao(), usuario.getSenha(), usuario.getDataAtivacao(),
				usuario.getIdInstituicao(), usuario.isBloqueado());
		
		long id = insertAuto("usuario");
		usuario.setId(id);
		if(usuario.getIdsPerfis() != null && !usuario.getIdsPerfis().isEmpty()) {
			adicionarPerfil(usuario, id);
			
		}
		return id;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void adicionarPerfil(Usuario usuario, long id) {
		usuario.getIdsPerfis().stream().forEach(idPerfil -> {
			addFields("id_usuario", "id_perfil");
			addValues(id, idPerfil);
			insert("usuario_perfil");
		});
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long alterar(Usuario usuario) {
		addFields("id_pessoa", "ativo", "data_criacao", "hash_senha", 
				"data_ativacao", "id_instituicao", "bloqueado");
		
		addValues(usuario.getIdPessoa(), usuario.isAtivo(), usuario.getDataCriacao(), usuario.getSenha(), usuario.getDataAtivacao(),
				usuario.getIdInstituicao(), usuario.isBloqueado(), usuario.getId());
		
		update("usuario", usuario.getId());
		if(usuario.getIdsPerfis() != null && !usuario.getIdsPerfis().isEmpty()) {
			adicionarPerfil(usuario, usuario.getId());
		}
		return usuario.getId();
	}
	
	@Override
	public String recuperarSenha(Long id) {
		try {
			
			StringBuilder query = createQuery("select hash_senha from usuario u where 1=1 ");
			query = andEqual(query, "u.id", id);
			return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), String.class);
			
		} catch (EmptyResultDataAccessException e) {
			log.info("Usuário não existe para recuperar a senha - " + id);
			return null;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario buscarPorId(Long id) {
		try {
			
			StringBuilder query = createQuery("select u.id, u.id_pessoa, u.ativo, u.data_criacao, u.data_ativacao, u.id_instituicao, u.bloqueado from usuario u where 1=1 ");
			query = andEqual(query, "u.id", id);
			Usuario usuario = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new UsuarioMapper(false));
			
			query = createQuery("select p.id from usuario u ");
			query.append("join usuario_perfil up on (u.id = up.id_usuario) ");
			query.append("join perfil p on (up.id_perfil = p.id) where 1=1 ");
			query = andEqual(query, "u.id", id);
			
			List<Integer> idsPerfis = this.getNamedParameterJdbcTemplate().queryForList(query.toString(),
					getMapSqlParameterSource(), Integer.class);
			
			usuario.addAuthorities(idsPerfis);
			
			return usuario;
		} catch (EmptyResultDataAccessException e) {
			log.info("Usuário não existe - " + id);
			return null;
		}
	}
	
	@Override
	public List<UsuarioPessoa> listar(FiltroUsuario filtroUsuario) {
		StringBuilder query = createQuery("select u.id as idUsuario, p.id as idPessoa, p.numero_registro, p.cpf, p.nome, p.data_nascimento, p.email, u.ativo, u.bloqueado ");
		query.append(" from usuario u ");
		query.append(" join pessoa p on (u.id_pessoa = p.id) where 1=1 ");
		query = andEqual(query, "u.id", filtroUsuario.getId());
		query = andLike(query, "p.nome", filtroUsuario.getNome());
		query = andEqual(query, "p.numero_registro", filtroUsuario.getNumeroRegistro());
		query = andEqual(query, "p.cpf", filtroUsuario.getCpf());
		query = orderBy(query, Order.ASC, "p.nome");
		query = limit(query, filtroUsuario.getQtdTotal(), filtroUsuario.getNumeroPagina());
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new UsuarioPessoaMapper());
	}
	
	@Override
	public int contar(FiltroUsuario filtroUsuario) {
		StringBuilder query = createQuery("select count(u.id) from usuario u ");
		query.append(" join pessoa p on (u.id_pessoa = p.id) where 1=1 ");
		query = andEqual(query, "u.id", filtroUsuario.getId());
		query = andLike(query, "p.nome", filtroUsuario.getNome());
		query = andEqual(query, "p.numero_registro", filtroUsuario.getNumeroRegistro());
		query = andEqual(query, "p.cpf", filtroUsuario.getCpf());
		return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	private class UsuarioPessoaMapper implements RowMapper<UsuarioPessoa> {
		

		@Override
		public UsuarioPessoa mapRow(ResultSet rs, int rowNum) throws SQLException {
			return UsuarioPessoa.builder()
					.id(rs.getInt("idUsuario"))
					.idPessoa(rs.getInt("idPessoa"))
					.nome(rs.getString("nome"))
					.cpf(rs.getString("cpf"))
					.email(rs.getString("email"))
					.dataNascimento(rs.getDate("data_nascimento").toLocalDate())
					.numeroRegistro(rs.getInt("numero_registro"))
					.ativo(rs.getBoolean("ativo"))
					.bloqueado(rs.getBoolean("bloqueado"))
					.build();
		}

	}
	
	private class UsuarioMapper implements RowMapper<Usuario> {
		
		private boolean comSenha;
		
		public UsuarioMapper(boolean comSenha) {
			this.comSenha = comSenha;
		}

		@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Usuario.builder()
					.id(rs.getLong("id"))
					.dataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime())
					.idPessoa(rs.getLong("id_pessoa"))
					.idInstituicao(rs.getLong("id_instituicao"))
					.ativo(rs.getBoolean("ativo"))
					.dataAtivacao(rs.getTimestamp("data_ativacao").toLocalDateTime())
					.bloqueado(rs.getBoolean("bloqueado"))
					.senha(comSenha ? rs.getString("hash_senha") : null)
					.build();
		}

	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean excluirUsuarioPerfil(Long idUsuario) {
		addFields("id_usuario");
		addValues(idUsuario);
		delete("usuario_perfil");
		return true;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean excluir(Long id) {
		excluirUsuarioPerfil(id);
		delete("usuario", id);
		return true;
	}
	
	@Override
	public Usuario buscarPorUsuario(String cpf) {
		StringBuilder query = createQuery("select u.id, u.id_pessoa, u.ativo, u.data_criacao, u.hash_senha, u.data_ativacao, u.id_instituicao, u.bloqueado ");
		query.append(" from usuario u ");
		query.append(" join pessoa p on (u.id_pessoa = p.id) where 1=1 ");
		query = andEqual(query, "p.cpf", cpf);
		
		Usuario usuario = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
				getMapSqlParameterSource(), new UsuarioMapper(true));
		
		query = createQuery("select p.id from usuario u ");
		query.append("join usuario_perfil up on (u.id = up.id_usuario) ");
		query.append("join perfil p on (up.id_perfil = p.id) where 1=1 ");
		query = andEqual(query, "u.id", usuario.getId());
		
		List<Integer> idsPerfis = this.getNamedParameterJdbcTemplate().queryForList(query.toString(),
				getMapSqlParameterSource(), Integer.class);
		
		usuario.addAuthorities(idsPerfis);
		
		return usuario;
	}

}
