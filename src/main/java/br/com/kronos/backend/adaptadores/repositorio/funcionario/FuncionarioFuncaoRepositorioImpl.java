package br.com.kronos.backend.adaptadores.repositorio.funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.FiltroFuncionarioFuncao;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioFuncao;
import br.com.kronos.backend.aplicacao.funcionario.FuncionarioFuncaoRepositorio;
import br.com.kronos.backend.aplicacao.funcionario.api.FuncionarioFuncaoDTO;

public class FuncionarioFuncaoRepositorioImpl extends SqlQueryRepositorio implements FuncionarioFuncaoRepositorio {
	
	public FuncionarioFuncaoRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(FuncionarioFuncao funcionarioFuncao) {
        addFields("id_funcionario", "id_tipo_funcao", "funcao_registro", "ativo");
		
		addValues(funcionarioFuncao.getIdFuncionario(), funcionarioFuncao.getIdTipoFuncao(), funcionarioFuncao.isFuncaoRegistro(),funcionarioFuncao.isAtivo()); 
		
		return (long) insertAuto("funcionario_funcao"); 
    }
		
	@Override
	public List<FuncionarioFuncaoDTO> listar(FiltroFuncionarioFuncao filtroFuncionarioFuncao) {
		  StringBuilder query = createQuery("select ff.id, ff.id_funcionario, tf.id_tipo_categoria_funcao, tc.nome as categoria, ff.id_tipo_funcao, ");
          query.append("tf.nome as funcao, ff.funcao_registro ");
          query.append("from funcionario_funcao ff ");
          query.append("left join tipo_funcao tf on (ff.id_tipo_funcao = tf.id) ");
          query.append("left join tipo_categoria_funcao tc on (tf.id_tipo_categoria_funcao = tc.id) where 1=1 ");
          query = andEqual(query, "ff.id_funcionario", filtroFuncionarioFuncao.getIdFuncionario());
          
          query.append(" union ");
          
          query.append("select  cast(null as bigint) as id, cast(null as bigint) as id_funcionario, tf.id_tipo_categoria_funcao, tc.nome as categoria, tf.id as id_tipo_funcao, ");
          query.append("tf.nome as funcao, cast(false as boolean) as funcao_registro ");
          query.append("from tipo_funcao tf ");
          query.append("left join tipo_categoria_funcao tc on (tf.id_tipo_categoria_funcao = tc.id) ");
          query.append("where not exists ");
          query.append("(select f.id_tipo_funcao from funcionario_funcao f ");
          query.append("where f.id_tipo_funcao = tf.id ");
          query = andEqual(query, "f.id_funcionario", filtroFuncionarioFuncao.getIdFuncionario());
          query.append(") ");
          query.append("order by 3,5 ");
		return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new FuncionarioFuncaoDTOMapper());
	}
	
	@Override
	public boolean excluirPorIdFuncionario(Long idFuncionario) {
		addFields("id_funcionario");
		addValues(idFuncionario);
		delete("funcionario_funcao");
		return true;
	}
	
	private class FuncionarioFuncaoDTOMapper implements RowMapper<FuncionarioFuncaoDTO> {

		@Override
		public FuncionarioFuncaoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return FuncionarioFuncaoDTO.builder()
					.id(rs.getLong("id"))
                    .idFuncionario(rs.getLong("id_funcionario"))
                    .idTipoFuncao(rs.getInt("id_tipo_funcao"))
                    .funcaoRegistro(rs.getBoolean("funcao_registro"))
                    .idTipoCategoriaFuncao(rs.getInt("id_tipo_categoria_funcao"))
                    .categoria(rs.getString("categoria"))
                    .funcao(rs.getString("funcao"))
					.build();
		}

	}

}

