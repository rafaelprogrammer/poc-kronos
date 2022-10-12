package br.com.kronos.backend.aplicacao;

import java.util.List;

public interface UsuarioRepositorio {
	
	Long criar(Usuario usuario);
	Long alterar(Usuario usuario);
	Usuario buscarPorId(Long id);
	List<UsuarioPessoa> listar(FiltroUsuario filtroUsuario);
	boolean excluir(Long id);
	Usuario buscarPorUsuario(String email);
	int contar(FiltroUsuario filtroUsuario);
	String recuperarSenha(Long id);
	boolean excluirUsuarioPerfil(Long idUsuario);

}
