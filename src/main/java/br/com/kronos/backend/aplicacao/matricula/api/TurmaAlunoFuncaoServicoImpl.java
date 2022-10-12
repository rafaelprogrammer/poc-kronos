package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurmaAlunoFuncao;
import br.com.kronos.backend.aplicacao.matricula.TurmaAlunoFuncao;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaAlunoFuncaoServico;
import br.com.kronos.backend.aplicacao.matricula.TurmaAlunoFuncaoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TurmaAlunoFuncaoServicoImpl implements TurmaAlunoFuncaoServico {

	@NonNull
	private TurmaAlunoFuncaoRepositorio turmaAlunoFuncaoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(TurmaAlunoFuncaoDTO turmaAlunoFuncaoDTO) throws ExcecaoDeNegocio {

			return turmaAlunoFuncaoRepositorio.criar(TurmaAlunoFuncao.builder()
					                        .dataInicioVigencia(turmaAlunoFuncaoDTO.getDataInicioVigencia())                                
                                            .dataFinalVigencia(turmaAlunoFuncaoDTO.getDataFinalVigencia())
                                            .idMatricula(turmaAlunoFuncaoDTO.getIdMatricula())
                                            .idTurma(turmaAlunoFuncaoDTO.getIdTurma())
                                            .idTipoFuncao(turmaAlunoFuncaoDTO.getIdTipoFuncao()).build());	

    }
	
	@Override
	public Long alterar(TurmaAlunoFuncaoDTO turmaAlunoFuncaoDTO) throws ExcecaoDeNegocio {
			return turmaAlunoFuncaoRepositorio.alterar(TurmaAlunoFuncao.builder()
												.id(turmaAlunoFuncaoDTO.getId())
												.dataInicioVigencia(turmaAlunoFuncaoDTO.getDataInicioVigencia())                                
	                                            .dataFinalVigencia(turmaAlunoFuncaoDTO.getDataFinalVigencia())
	                                            .idMatricula(turmaAlunoFuncaoDTO.getIdMatricula())
	                                            .idTurma(turmaAlunoFuncaoDTO.getIdTurma())
	                                            .idTipoFuncao(turmaAlunoFuncaoDTO.getIdTipoFuncao()).build());
		
	}

	@Override
	public TurmaAlunoFuncaoDTO buscarPorId (long id) {
		return modelMapper.map(turmaAlunoFuncaoRepositorio.buscarPorId(id), TurmaAlunoFuncaoDTO.class);
	}

	@Override
	public PaginacaoDTO<TurmaAlunoFuncaoDTO> listar(FiltroTurmaAlunoFuncao filtroTurmaAlunoFuncao) throws ExcecaoDeNegocio {
			int total = turmaAlunoFuncaoRepositorio.contar(filtroTurmaAlunoFuncao);
			List<TurmaAlunoFuncaoDTO> funcoesalunos = modelMapper.map(turmaAlunoFuncaoRepositorio.listar(filtroTurmaAlunoFuncao),
					new TypeToken<List<TurmaAlunoFuncaoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.<TurmaAlunoFuncaoDTO>builder().total(total).dados(funcoesalunos).build();
	}

	@Override
	public boolean excluir(Long id) {
		return turmaAlunoFuncaoRepositorio.excluir(id);
	}

}
