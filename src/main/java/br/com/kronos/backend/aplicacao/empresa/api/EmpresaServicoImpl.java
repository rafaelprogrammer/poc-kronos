package br.com.kronos.backend.aplicacao.empresa.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import br.com.kronos.backend.aplicacao.api.dto.comum.PaginacaoDTO;
import br.com.kronos.backend.aplicacao.empresa.Empresa;
import br.com.kronos.backend.aplicacao.empresa.EmpresaRepositorio;
import br.com.kronos.backend.aplicacao.empresa.FiltroEmpresa;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class EmpresaServicoImpl implements EmpresaServico {

	@NonNull
	private EmpresaRepositorio empresaRepositorio;

	@NonNull
	private ModelMapper modelMapper;

	@Override
	public int criar(EmpresaDTO dto) {
		return empresaRepositorio.criar(Empresa.builder()
										.cnpj(dto.getCnpj())
										.emailContato(dto.getEmailContato())
										.inscricaoEstadual(dto.getInscricaoEstadual())
										.nomeContato(dto.getNomeContato())
										.nomeFantasia(dto.getNomeFantasia())
										.razaoSocial(dto.getRazaoSocial())
										.site(dto.getSite())
										.build());
		
	}

	@Override
	public PaginacaoDTO<EmpresaDTO> listar(FiltroEmpresa filtro) {
		int total = empresaRepositorio.contar(filtro);
		
		List<EmpresaDTO> talentos = modelMapper.map(empresaRepositorio.listar(filtro),
				new TypeToken<List<EmpresaDTO>>() {
				}.getType());
		
		return PaginacaoDTO.<EmpresaDTO>builder().total(total).dados(talentos).build();
	}

	@Override
	public boolean excluir(int id) {
		return empresaRepositorio.excluir(id);
	}

}
