package br.com.kronos.backend.aplicacao.matricula.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.exception.ExcecaoDeNegocio;
import br.com.kronos.backend.aplicacao.matricula.FiltroTurmaFuncionarioFuncao;
import br.com.kronos.backend.aplicacao.matricula. TurmaFuncionarioFuncao;
import br.com.kronos.backend.aplicacao.matricula.api.TurmaFuncionarioFuncaoServico;
import br.com.kronos.backend.aplicacao.matricula.TurmaFuncionarioFuncaoRepositorio;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class  TurmaFuncionarioFuncaoServicoImpl implements TurmaFuncionarioFuncaoServico {

	@NonNull
	private  TurmaFuncionarioFuncaoRepositorio turmaFuncionarioFuncaoRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public Long criar(TurmaFuncionarioFuncaoDTO turmaFuncionarioFuncaoDTO) throws ExcecaoDeNegocio {

			return turmaFuncionarioFuncaoRepositorio.criar( TurmaFuncionarioFuncao.builder()
					                        .dataInicioVigencia(turmaFuncionarioFuncaoDTO.getDataInicioVigencia())                                
                                            .dataFinalVigencia(turmaFuncionarioFuncaoDTO.getDataFinalVigencia())
                                            .idFuncionario(turmaFuncionarioFuncaoDTO.getIdFuncionario())
                                            .idTurma(turmaFuncionarioFuncaoDTO.getIdTurma())
                                            .idTipoFuncao(turmaFuncionarioFuncaoDTO.getIdTipoFuncao()).build());	

    }
	
	@Override
	public Long alterar(TurmaFuncionarioFuncaoDTO turmaFuncionarioFuncaoDTO) throws ExcecaoDeNegocio {
			return turmaFuncionarioFuncaoRepositorio.alterar( TurmaFuncionarioFuncao.builder()
												.id(turmaFuncionarioFuncaoDTO.getId())
												.dataInicioVigencia(turmaFuncionarioFuncaoDTO.getDataInicioVigencia())                                
	                                            .dataFinalVigencia(turmaFuncionarioFuncaoDTO.getDataFinalVigencia())
	                                            .idFuncionario(turmaFuncionarioFuncaoDTO.getIdFuncionario())
	                                            .idTurma(turmaFuncionarioFuncaoDTO.getIdTurma())
	                                            .idTipoFuncao(turmaFuncionarioFuncaoDTO.getIdTipoFuncao()).build());
		
	}

	@Override
	public  TurmaFuncionarioFuncaoDTO buscarPorId (long id) {
		return modelMapper.map(turmaFuncionarioFuncaoRepositorio.buscarPorId(id),  TurmaFuncionarioFuncaoDTO.class);
	}

	@Override
	public PaginacaoDTO< TurmaFuncionarioFuncaoDTO> listar(FiltroTurmaFuncionarioFuncao filtroTurmaFuncionarioFuncao) throws ExcecaoDeNegocio {
			int total = turmaFuncionarioFuncaoRepositorio.contar(filtroTurmaFuncionarioFuncao);
			List< TurmaFuncionarioFuncaoDTO> funcoesfucionarios = modelMapper.map(turmaFuncionarioFuncaoRepositorio.listar(filtroTurmaFuncionarioFuncao),
					new TypeToken<List< TurmaFuncionarioFuncaoDTO>>() {
					}.getType());
			
			return PaginacaoDTO.< TurmaFuncionarioFuncaoDTO>builder().total(total).dados(funcoesfucionarios).build();
	}

	@Override
	public boolean excluir(Long id) {
		return turmaFuncionarioFuncaoRepositorio.excluir(id);
	}

}
