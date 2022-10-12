package br.com.kronos.backend.aplicacao.api.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.kronos.backend.aplicacao.FiltroUsuario;
import br.com.kronos.backend.aplicacao.Usuario;
import br.com.kronos.backend.aplicacao.UsuarioRepositorio;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioDTO;
import br.com.kronos.backend.aplicacao.api.dto.UsuarioPessoaDTO;
import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.instituicao.Instituicao;
import br.com.kronos.backend.aplicacao.instituicao.InstituicaoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.Pessoa;
import br.com.kronos.backend.aplicacao.pessoa.PessoaRepositorio;
import br.com.kronos.backend.aplicacao.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class UsuarioServicoImpl implements UsuarioServico {

	@NonNull
	private UsuarioRepositorio usuarioRepositorio;
	
	@NonNull
	private PessoaRepositorio pessoaRepositorio;
	
	@NonNull
	private InstituicaoRepositorio instituicaoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@NonNull
    private PasswordEncoder passwordEncoder;

	@Override
	public Long criar(UsuarioDTO usuario) throws ExcecaoDeNegocio {
		try {
			//TODO implementar o envio de email para criacao de uma senha
			return usuarioRepositorio.criar(
					Usuario.builder()
					.dataCriacao(DateUtil.dataHoraAtual())
					.dataAtivacao(DateUtil.dataHoraAtual())
					.idPessoa(usuario.getIdPessoa())
					.idsPerfis(usuario.getIdsPerfis())
					.idInstituicao(usuario.getIdInstituicao())
					.ativo(usuario.isAtivo())
					.bloqueado(usuario.isBloqueado())
					.senha(passwordEncoder.encode(StringUtils.isEmpty(usuario.getSenha()) ? "P@z3B3m" : usuario.getSenha())).build());
		} catch (RuntimeException e) {
			log.error("Erro ao criar o usuario - " + usuario.getUsername(), e);
			throw new ExcecaoDeNegocio("Erro ao criar 0 usuario - " + usuario.getUsername(), e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Long alterar(UsuarioDTO usuario) throws ExcecaoDeNegocio {
		try {
			
			usuarioRepositorio.excluirUsuarioPerfil(usuario.getId());
			return usuarioRepositorio.alterar(Usuario.builder()
					.id(usuario.getId())
					.dataCriacao(DateUtil.dataHoraAtual())
					.dataAtivacao(usuario.getDataAtivacao() == null ? DateUtil.dataHoraAtual() : usuario.getDataAtivacao())
					.idPessoa(usuario.getIdPessoa())
					.idsPerfis(usuario.getIdsPerfis())
					.idInstituicao(usuario.getIdInstituicao())
					.ativo(usuario.isAtivo())
					.senha(usuarioRepositorio.recuperarSenha(usuario.getId()))
					.bloqueado(usuario.isBloqueado()).build());
		} catch (RuntimeException e) {
			log.error("Erro ao alterar o usuario - " + usuario.getId(), e);
			throw new ExcecaoDeNegocio("Erro ao alterar o usuario - " + usuario.getId(), e);
		}
	}

	@Override
	public UsuarioDTO buscarPorId(Long id) throws ExcecaoDeNegocio {
		try {
			return modelMapper.map(usuarioRepositorio.buscarPorId(id), UsuarioDTO.class);
		} catch (RuntimeException e) {
			log.error("Erro ao buscar um usuario por id - " + id, e);
			throw new ExcecaoDeNegocio("Erro ao buscar um usuario por id - " + id, e);
		}
	}

	@Override
	public PaginacaoDTO<UsuarioPessoaDTO> listar(FiltroUsuario filtroUsuario) throws ExcecaoDeNegocio {
		try {
			int total = usuarioRepositorio.contar(filtroUsuario);
			List<UsuarioPessoaDTO> usuariosPessoasDTOs = modelMapper.map(usuarioRepositorio.listar(filtroUsuario),
					new TypeToken<List<UsuarioPessoaDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<UsuarioPessoaDTO>builder().total(total).dados(usuariosPessoasDTOs).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar usuarios", e);
			throw new ExcecaoDeNegocio("Erro ao listar usuarios", e);
		}
	}

	@Override
	public boolean excluir(Long id) throws ExcecaoDeNegocio {
		try {
			return usuarioRepositorio.excluir(id);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir o usuario id - " + id, e);
			throw new ExcecaoDeNegocio("Erro ao excluir o usuario id - " + id, e);
		}
	}

	@Override
	// TODO otimizar essa pesquisa
	public UsuarioDTO buscarPorUsuario(String username) throws ExcecaoDeNegocio {
		try {
			UsuarioDTO usuarioDTO =  modelMapper.map(usuarioRepositorio.buscarPorUsuario(username), UsuarioDTO.class);
			Pessoa pessoa = pessoaRepositorio.buscarPorId(usuarioDTO.getIdPessoa());
			usuarioDTO.setCpf(pessoa.getCpf());
			usuarioDTO.setNome(pessoa.getNome());
			Instituicao instituicao = instituicaoRepositorio.buscarPorId(usuarioDTO.getIdInstituicao());
			usuarioDTO.setInstituicao(instituicao.getNome());
			usuarioDTO.setInstituicaoMantenedora(instituicao.isMantenedora());
			usuarioDTO.setInstituicaoAtiva(instituicao.isAtivo());
			return usuarioDTO;
		} catch (RuntimeException e) {
			log.error("Erro ao buscar o usuario - " + username, e);
			throw new ExcecaoDeNegocio("Erro ao buscar o usuario  - " + username, e);
		}
	}

}
