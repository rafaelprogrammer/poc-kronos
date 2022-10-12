package br.com.kronos.backend.aplicacao.pessoa.api;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.pessoa.Filiacao;
import br.com.kronos.backend.aplicacao.pessoa.FiliacaoRepositorio;
import br.com.kronos.backend.aplicacao.pessoa.FiltroFiliacao;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class FiliacaoServicoImpl implements FiliacaoServico {

	@NonNull
	private FiliacaoRepositorio filiacaoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public int criar(FiliacaoDTO dto) throws ExcecaoDeNegocio {
		try {
			return filiacaoRepositorio.criar(Filiacao.builder()
													.dataFiliacao(dto.getDataFiliacao())
													.filiacaoAtual(dto.isFiliacaoAtual())
													.idPessoaFilho(dto.getIdPessoaFilho())
													.idPessoaPais(dto.getIdPessoaPais())
													.idTipoFiliacao(dto.getIdTipoFiliacao())
													.build());
			
		} catch (RuntimeException e) {
			log.error("Erro ao criar filiacao idPais - " + dto.getNomePessoaPais(), e);
			throw new ExcecaoDeNegocio("Erro ao criar filiacao idPais - " + dto.getNomePessoaPais(), e);
		}
	}

	@Override
	public PaginacaoDTO<FiliacaoDTO> listar(FiltroFiliacao filtro) throws ExcecaoDeNegocio {
		try {
			
			int total = filiacaoRepositorio.contar(filtro);
			return PaginacaoDTO.<FiliacaoDTO>builder().total(total).dados(filiacaoRepositorio.listar(filtro)).build();
		} catch (RuntimeException e) {
			log.error("Erro ao listar filiacao", e);
			throw new ExcecaoDeNegocio("Erro ao listar filiacao", e);
		}
	}

	@Override
	public boolean excluir(int id) {
		try {
			return filiacaoRepositorio.excluir(id);
		} catch (RuntimeException e) {
			log.error("Erro ao excluir a filiacao de id - " + id, e);
			throw new ExcecaoDeNegocio("Erro ao excluir a filiacao de id - " + id, e);
		}
	}

}
