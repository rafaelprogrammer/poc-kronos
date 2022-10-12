package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.arquivo.Arquivo;
import br.com.kronos.backend.aplicacao.arquivo.ArquivoRepositorio;
import br.com.kronos.backend.aplicacao.arquivo.EnumTipoArquivo;
import br.com.kronos.backend.aplicacao.arquivo.EnumTipoConteudo;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroPessoa;
import br.com.kronos.backend.aplicacao.pessoa.Pessoa;
import br.com.kronos.backend.aplicacao.pessoa.PessoaRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor 
public class PessoaServicoImpl implements PessoaServico {

	@NonNull
	private PessoaRepositorio pessoaRepositorio;
	
	@NonNull
	private ArquivoRepositorio arquivoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(PessoaDTO pessoaDTO) throws ExcecaoDeNegocio {
			return pessoaRepositorio.criar(Pessoa.builder()
											.dataNascimento(pessoaDTO.getDataNascimento())
											.cpf(pessoaDTO.getCpf())
											.nome(pessoaDTO.getNome())
											.nomeSocial(pessoaDTO.getNomeSocial())
											.nomeUsual(pessoaDTO.getNomeUsual())
											.bancoTalento(pessoaDTO.isBancoTalento())
											.email(pessoaDTO.getEmail())
											.numeroRegistro(pessoaDTO.getNumeroRegistro())
											.grauComportamento(pessoaDTO.getGrauComportamento())
											.idArqAnexo(pessoaDTO.getIdArqAnexo())
											.idCidade(pessoaDTO.getIdCidade())
											.idGenero(pessoaDTO.getIdGenero())
											.idInstituicao(pessoaDTO.getIdInstituicao()).build());
			
	}

	@Override
	public Long alterar(PessoaDTO pessoaDTO) throws ExcecaoDeNegocio {
			return pessoaRepositorio.alterar(Pessoa.builder()
												.id(pessoaDTO.getId())
												.dataNascimento(pessoaDTO.getDataNascimento())
												.cpf(pessoaDTO.getCpf())
												.nome(pessoaDTO.getNome())
												.nomeSocial(pessoaDTO.getNomeSocial())
												.nomeUsual(pessoaDTO.getNomeUsual())
												.bancoTalento(pessoaDTO.isBancoTalento())
												.email(pessoaDTO.getEmail())
												.numeroRegistro(pessoaDTO.getNumeroRegistro())
												.grauComportamento(pessoaDTO.getGrauComportamento())
												.idArqAnexo(pessoaDTO.getIdArqAnexo())
												.idCidade(pessoaDTO.getIdCidade())
												.idGenero(pessoaDTO.getIdGenero())
												.idInstituicao(pessoaDTO.getIdInstituicao()).build());
		
	}

	@Override
	public PessoaDTO buscarPorId(long id) {
		return modelMapper.map(pessoaRepositorio.buscarPorId(id), PessoaDTO.class);
	}

	@Override
	public PaginacaoDTO<PessoaDTO> listar(FiltroPessoa filtroPessoa) throws ExcecaoDeNegocio {
			int total = pessoaRepositorio.contar(filtroPessoa);
			List<PessoaDTO> pessoas = modelMapper.map(pessoaRepositorio.listar(filtroPessoa),
					new TypeToken<List<PessoaDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<PessoaDTO>builder().total(total).dados(pessoas).build();
	}

	@Override
	public boolean excluir(long id) {
		return pessoaRepositorio.excluir(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void alterarFoto(FotoDTO dto) {
		Pessoa pessoa = pessoaRepositorio.buscarPorId(dto.getIdPessoa());
		int idArquivoAnexoAntigo = pessoa.getIdArqAnexo();
		int idArquivo = arquivoRepositorio.criar(Arquivo.builder()
										.bytes(dto.getBytes())
										.tamanho(dto.getTamanho())
										.contentType(dto.getContentType())
										.legenda(pessoa.getNome())
										.nome("" + pessoa.getNumeroRegistro())
										.idTipoArquivo(EnumTipoArquivo.IMAGEM.id())
										.idTipoConteudo(EnumTipoConteudo.FOTO.id())
										.build());
			pessoa.alterarFoto(idArquivo);
			pessoaRepositorio.alterar(pessoa);
			arquivoRepositorio.excluir(idArquivoAnexoAntigo);
		
	
	}

	@Override
	public PaginacaoDTO<PessoaResumoDTO> listarParaSelecionarAluno(FiltroPessoa filtroPessoa) {
		int total = pessoaRepositorio.contarParaSelecionarAluno(filtroPessoa);
		
		return PaginacaoDTO.<PessoaResumoDTO>builder().total(total).dados(pessoaRepositorio.listarParaSelecionarAluno(filtroPessoa)).build();
	}
	
	@Override
	public boolean verificarCPFCadastrado(Long id, String cpf) {
		if (id != null) {
			Pessoa pessoa = pessoaRepositorio.buscarPorId(id);
			if (pessoa != null && pessoa.getCpf().equals(cpf)) {
				return false;
			}
				
		}
		return pessoaRepositorio.contar(FiltroPessoa.builder().cpf(cpf).build()) > 0;
	}

}
