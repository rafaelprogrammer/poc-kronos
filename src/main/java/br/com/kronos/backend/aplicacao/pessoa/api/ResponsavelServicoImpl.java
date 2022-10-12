package br.com.kronos.backend.aplicacao.pessoa.api;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.matricula.MatriculaRepositorio;
import br.com.kronos.backend.aplicacao.matricula.api.MatriculaDTO;
import br.com.kronos.backend.aplicacao.pessoa.EnumTipoResponsavel;
import br.com.kronos.backend.aplicacao.pessoa.FiltroResponsavel;
import br.com.kronos.backend.aplicacao.pessoa.Responsavel;
import br.com.kronos.backend.aplicacao.pessoa.ResponsavelRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class ResponsavelServicoImpl implements ResponsavelServico {

	@NonNull
	private ResponsavelRepositorio responsavelRepositorio;
	
	@NonNull
	private MatriculaRepositorio matriculaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public int criar(ResponsavelDTO dto) {
		return responsavelRepositorio.criar(Responsavel.builder()
												.dataInicio(dto.getDataInicio())
												.dataFim(dto.getDataFim())
												.prioridade(dto.getPrioridade())
												.idPessoa(dto.getIdPessoa())
												.idPessoaResponsavel(dto.getIdPessoaResponsavel())
												.idTipoResponsavel(dto.getIdTipoResponsavel())
												.build());
			
	}
	
	@Override
	public int alterar(ResponsavelDTO dto) {
		return responsavelRepositorio.alterar(Responsavel.builder()
												.id(dto.getId())
												.dataInicio(dto.getDataInicio())
												.dataFim(dto.getDataFim())
												.prioridade(dto.getPrioridade())
												.idPessoa(dto.getIdPessoa())
												.idPessoaResponsavel(dto.getIdPessoaResponsavel())
												.idTipoResponsavel(dto.getIdTipoResponsavel())
												.build());
			
	}
	
	@Override
	public ResponsavelDTO buscarPorId (int id) {
		return modelMapper.map(responsavelRepositorio.buscarPorId(id), ResponsavelDTO.class);
	}

	@Override
	public PaginacaoDTO<ResponsavelDTO> listar(FiltroResponsavel filtro) {
		int total = responsavelRepositorio.contar(filtro);
		return PaginacaoDTO.<ResponsavelDTO>builder().total(total).dados(responsavelRepositorio.listar(filtro)).build();
	}

	@Override
	public boolean excluir(int id) {
		return responsavelRepositorio.excluir(id);
	}
	
	@Override
	public List<ResponsavelDTO> listarResponsaveisFinanceiros(FiltroResponsavel filtro) {
		MatriculaDTO matricula = matriculaRepositorio.buscarPorId(filtro.getIdMatricula());
		filtro.setIdTipoPessoaResponsavel(EnumTipoResponsavel.FINANCEIRO.id());
		filtro.setIdPessoa(matricula.getIdPessoa());
		return responsavelRepositorio.listar(filtro);
	}

	@Override
	public List<ResponsavelDTO> listarParaOcorrenciaResponsavelCiencia(LocalDate dataOcorrencia,
			Long idPessoaSelecionada) {
		return responsavelRepositorio.listarParaOcorrenciaResponsavelCiencia(dataOcorrencia, idPessoaSelecionada);
	}

}
