package br.com.kronos.backend.aplicacao.desempenho.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.desempenho.Desempenho;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAtitude;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoAvaliacao;
import br.com.kronos.backend.aplicacao.desempenho.DesempenhoRepositorio;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenho;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAtitude;
import br.com.kronos.backend.aplicacao.desempenho.FiltroDesempenhoAvaliacao;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class DesempenhoServicoImpl implements DesempenhoServico {

	@NonNull
	private DesempenhoRepositorio desempenhoRepositorio;
	
	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(DesempenhoDTO DesempenhoDTO) throws ExcecaoDeNegocio {
			return desempenhoRepositorio.criar(Desempenho.builder()
											.idSubFaseExecucao(DesempenhoDTO.getIdSubFaseExecucao())
											.idCredito(DesempenhoDTO.getIdCredito())
											.dataAvaliacao(DesempenhoDTO.getDataAvaliacao())
											.parecer(DesempenhoDTO.getParecer())
											.dataParecer(DesempenhoDTO.getDataParecer())
											.consideracoes(DesempenhoDTO.getConsideracoes())
											.dataConsideracoes(DesempenhoDTO.getDataConsideracoes()).build());
			
    }
    
	@Override
	public Long alterar(DesempenhoDTO DesempenhoDTO) throws ExcecaoDeNegocio {
			return desempenhoRepositorio.alterar(Desempenho.builder()
											.id(DesempenhoDTO.getId())
											.idSubFaseExecucao(DesempenhoDTO.getIdSubFaseExecucao())
											.idCredito(DesempenhoDTO.getIdCredito())
											.dataAvaliacao(DesempenhoDTO.getDataAvaliacao())
											.parecer(DesempenhoDTO.getParecer())
											.dataParecer(DesempenhoDTO.getDataParecer())
											.consideracoes(DesempenhoDTO.getConsideracoes())
											.dataConsideracoes(DesempenhoDTO.getDataConsideracoes()).build());		
	}

	@Override
	public DesempenhoDTO buscarPorId(long id) {
		return modelMapper.map(desempenhoRepositorio.buscarPorId(id), DesempenhoDTO.class);
	}

	@Override
	public PaginacaoDTO<DesempenhoDTO> listar(FiltroDesempenho filtroDesempenho) throws ExcecaoDeNegocio {
			int total = desempenhoRepositorio.contar(filtroDesempenho);
			List<DesempenhoDTO> Desempenhos = modelMapper.map(desempenhoRepositorio.listar(filtroDesempenho),
					new TypeToken<List<DesempenhoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<DesempenhoDTO>builder().total(total).dados(Desempenhos).build();
	}

	@Override
	public boolean excluir(long id) {
		return desempenhoRepositorio.excluir(id);
	}
	
	@Override
	public void atribuirAtitudeDesempenho(DesempenhoAtitudeDTO dto) {
		this.desempenhoRepositorio.atribuirAtitudeDesempenho(DesempenhoAtitude.builder().idDesempenho(dto.getIdDesempenho()).idAtitude(dto.getIdAtitude()).
				nota(dto.getNota()).idMencao(dto.getIdMencao()).build());
	}

	@Override
	public void excluirAtitudeDesempenho(DesempenhoAtitudeDTO dto) {
		this.desempenhoRepositorio.excluirAtitudeDesempenho(DesempenhoAtitude.builder().idDesempenho(dto.getIdDesempenho()).idAtitude(dto.getIdAtitude()).build());
	}

	@Override
	public boolean verificarVinculoDesempenhoAtitude(FiltroDesempenhoAtitude filtro) {
		return this.desempenhoRepositorio.verificarVinculoDesempenhoAtitude(filtro);
	}
	
	@Override
	public void atribuirAvaliacaoDesempenho(DesempenhoAvaliacaoDTO dto) {
		this.desempenhoRepositorio.atribuirAvaliacaoDesempenho(DesempenhoAvaliacao.builder().idDesempenho(dto.getIdDesempenho()).idAvaliacao(dto.getIdAvaliacao()).build());
	}

	@Override
	public void excluirAvaliacaoDesempenho(DesempenhoAvaliacaoDTO dto) {
		this.desempenhoRepositorio.excluirAvaliacaoDesempenho(DesempenhoAvaliacao.builder().idDesempenho(dto.getIdDesempenho()).idAvaliacao(dto.getIdAvaliacao()).build());
	}

	@Override
	public boolean verificarVinculoDesempenhoAvaliacao(FiltroDesempenhoAvaliacao filtro) {
		return this.desempenhoRepositorio.verificarVinculoDesempenhoAvaliacao(filtro);
	}

}


