package br.com.kronos.backend.aplicacao.funcionario;

import java.util.Arrays;
import java.util.List;

public enum EnumTipoCategoriaFuncao {
	
    DIRECAO(1, "Direção"),
    COORDENACAO(2, "Coordenação"),
    PROFESSORES(3, "Professores"),
    CONSELHEIROS(4,"Conselheiros"),
    ADMINISTRACAO(5, "Administração"),
    ALUNOS(6, "Alunos"),
    SERVICOS_GERAIS(7, "Serviços Gerais");

private int id;
private String label;


EnumTipoCategoriaFuncao(int id, String label) {
    this.label = label;
    this.id = id;
}

public List<EnumTipoCategoriaFuncao> listarTodos() {
    return Arrays.asList(EnumTipoCategoriaFuncao.values());
}

public int id() {
    return this.id;
}

public String label() {
    return this.label;
}

}