package br.com.kronos.backend.aplicacao.pessoa.api;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroTelefonePessoa;
import br.com.kronos.backend.aplicacao.pessoa.TelefonePessoa;
import br.com.kronos.backend.aplicacao.pessoa.TelefonePessoaRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class TelefonePessoaServicoImpl implements TelefonePessoaServico {

	@NonNull
	private TelefonePessoaRepositorio telefonePessoaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Integer criar(TelefonePessoaDTO dto) throws ExcecaoDeNegocio {
		try {
			return telefonePessoaRepositorio.criar(TelefonePessoa.builder()
													.numero(dto.getNumero())
													.idCidade(dto.getIdCidade())
													.idOperadora(dto.getIdOperadora())
													.idPessoa(dto.getIdPessoa())
													.idTipoTelefone(dto.getIdTipoTelefone())
													.idTipoUso(dto.getIdTipoUso())
													.principal(dto.isPrincipal())
													.whatApp(dto.isWhatApp())
													.build());
			
		} catch (RuntimeException e) {
			log.error("Erro ao criar telefone pessoa - " + dto.getNumero(), e);
			throw new ExcecaoDeNegocio("Erro ao criar telefone pessoa - " + dto.getNumero(), e);
		}
	}

	@Override
	public PaginacaoDTO<TelefonePessoaDTO> listar(FiltroTelefonePessoa filtro) throws ExcecaoDeNegocio {
		try {
			
			int total = telefonePessoaRepositorio.contar(filtro);
			
			return PaginacaoDTO.<TelefonePessoaDTO>builder().total(total).dados(telefonePessoaRepositorio.listar(filtro)).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar telefone pessoa", e);
			throw new ExcecaoDeNegocio("Erro ao listar telefone pessoa", e);
		}
	}

	@Override
	public boolean excluir(int id) {
		try {
			return telefonePessoaRepositorio.excluir(id);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir a pessoa endereco de id - " + id, e);
			throw new ExcecaoDeNegocio("Erro ao excluir a pessoa endereco de id - " + id, e);
		}
	}

}
