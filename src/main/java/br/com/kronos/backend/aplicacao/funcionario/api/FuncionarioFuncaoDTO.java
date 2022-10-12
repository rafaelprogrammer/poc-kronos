package br.com.kronos.backend.aplicacao.funcionario.api;

import java.io.Serializable;

import br.com.kronos.backend.aplicacao.funcionario.EnumTipoFuncao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FuncionarioFuncaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;     
    private Long id;
    private Long idFuncionario;
    private Integer idTipoCategoriaFuncao;
    private Integer idTipoFuncao;
    private Boolean funcaoRegistro;
    private String funcao;
    private String categoria;
    private Boolean ativo;

		
	public String getNomeTipoFuncao() {
		return this.idTipoFuncao != null ? EnumTipoFuncao.porId(this.idTipoFuncao).label() : null;
    }   
}
