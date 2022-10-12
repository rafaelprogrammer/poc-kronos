package br.com.kronos.backend.aplicacao.pessoa.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.EnderecoPessoa;
import br.com.kronos.backend.aplicacao.pessoa.EnderecoPessoaRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroEnderecoPessoa;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class EnderecoPessoaServicoImpl implements EnderecoPessoaServico {

	@NonNull
	private EnderecoPessoaRepositorio enderecoPessoaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Integer criar(EnderecoPessoaDTO enderecoPessoaDTO) throws ExcecaoDeNegocio {
		try {
			return enderecoPessoaRepositorio.criar(EnderecoPessoa.builder()
													.cep(enderecoPessoaDTO.getCep())
													.localidade(enderecoPessoaDTO.getLocalidade())
													.bairro(enderecoPessoaDTO.getBairro())
													.complemento(enderecoPessoaDTO.getComplemento())
													.gia(enderecoPessoaDTO.getGia())
													.ibge(enderecoPessoaDTO.getIbge())
													.logradouro(enderecoPessoaDTO.getLogradouro())
													.idPessoa(enderecoPessoaDTO.getIdPessoa())
													.idTipoEndereco(enderecoPessoaDTO.getIdTipoEndereco())
													.numero(enderecoPessoaDTO.getNumero())
													.unidade(enderecoPessoaDTO.getUnidade())
													.uf(enderecoPessoaDTO.getUf())
													.build());
			
		} catch (RuntimeException e) {
			log.error("Erro ao criar pessoa endereco - " + enderecoPessoaDTO.getCep(), e);
			throw new ExcecaoDeNegocio("Erro ao criar pessoa endereco - " + enderecoPessoaDTO.getCep(), e);
		}
	}

	@Override
	public PaginacaoDTO<EnderecoPessoaDTO> listar(FiltroEnderecoPessoa filtroEnderecoPessoa) throws ExcecaoDeNegocio {
		try {
			
			int total = enderecoPessoaRepositorio.contar(filtroEnderecoPessoa);
			List<EnderecoPessoaDTO> pessoasEnderecos = modelMapper.map(enderecoPessoaRepositorio.listar(filtroEnderecoPessoa),
					new TypeToken<List<EnderecoPessoaDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<EnderecoPessoaDTO>builder().total(total).dados(pessoasEnderecos).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar pessoa endereco", e);
			throw new ExcecaoDeNegocio("Erro ao listar pessoa endereco", e);
		}
	}

	@Override
	public boolean excluir(Integer id) throws ExcecaoDeNegocio {
		try {
			return enderecoPessoaRepositorio.excluir(id);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir a pessoa endereco de id - " + id, e);
			throw new ExcecaoDeNegocio("Erro ao excluir a pessoa endereco de id - " + id, e);
		}
	}

}
