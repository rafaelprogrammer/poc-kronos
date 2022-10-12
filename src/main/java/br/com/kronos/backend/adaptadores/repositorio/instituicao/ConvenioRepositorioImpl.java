package br.com.kronos.backend.adaptadores.repositorio.instituicao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import br.com.kronos.backend.adaptadores.repositorio.comum.SqlQueryRepositorio;
import br.com.kronos.backend.aplicacao.instituicao.FiltroConvenio;
import br.com.kronos.backend.aplicacao.instituicao.api.ConvenioContratoDTO;
import br.com.kronos.backend.aplicacao.instituicao.Convenio;
import br.com.kronos.backend.aplicacao.instituicao.ConvenioRepositorio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConvenioRepositorioImpl extends SqlQueryRepositorio implements ConvenioRepositorio {
	
	public ConvenioRepositorioImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Long criar(Convenio convenio) {
        addFields("percentual_desconto", "data_inicio_vigencia", "data_final_vigencia", "id_tipo_desconto", 
                  "id_empresa", "id_instituicao");
		
		addValues(convenio.getPercentualDesconto(), convenio.getDataInicioVigencia(), convenio.getDataFinalVigencia(), convenio.getIdTipoDesconto(), 
                  convenio.getIdEmpresa(), convenio.getIdInstitucicao()); 
		
		return (long) insertAuto("convenio"); 
    }

	@Override
	public Long alterar(Convenio convenio) {
		addFields("percentual_desconto", "data_inicio_vigencia", "data_final_vigencia", "id_tipo_desconto", 
                "id_empresa", "id_instituicao");
		
		addValues(convenio.getPercentualDesconto(), convenio.getDataInicioVigencia(), convenio.getDataFinalVigencia(), convenio.getIdTipoDesconto(), 
                convenio.getIdEmpresa(), convenio.getIdInstitucicao(),convenio.getId());
		
		return (long) update("convenio", convenio.getId());
	}
	
	@Override
	public Convenio buscarPorId(Long id) {
		try {
            StringBuilder query = createQuery("select c.percentual_desconto, c.data_inicio_vigencia, c.data_final_vigencia, c.id_tipo_desconto, ");
            query.append("c.id_empresa, c.id_instituicao ");
            query.append("from convenio c where 1=1 ");
            query = andEqual(query, "c.id", id);
			Convenio convenio = this.getNamedParameterJdbcTemplate().queryForObject(query.toString(),
					getMapSqlParameterSource(), new ConvenioMapper());
			return convenio;
		} catch (EmptyResultDataAccessException e) {
			log.info("convenio não existe - " + id);
			return null;
		}
	} 
	
	@Override
	public List<Convenio> listar(FiltroConvenio filtroConvenio) {
		  StringBuilder query = createQuery("select c.percentual_desconto, c.data_inicio_vigencia, c.data_final_vigencia, c.id_tipo_desconto, ");
          query.append("c.id_empresa, c.id_instituicao ");
          query.append("from convenio c where 1=1 ");
          query = andEqual(query, "c.id", filtroConvenio.getId());
	   	  query = andEqual(query, "c.id_empresa", filtroConvenio.getIdEmpresa());
          query = andEqual(query, "c.id_instituicao", filtroConvenio.getIdInstituicao());
          query = orderBy(query, Order.DESC, "c.data_inicio_vigencia");
		  query = limit(query, filtroConvenio.getQtdTotal(), filtroConvenio.getNumeroPagina());
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ConvenioMapper());
	}
	
	
	@Override
	public int contar(FiltroConvenio filtroConvenio) {
		StringBuilder query = createQuery("select count(c.id) from convenio c where 1=1 ");
		query = andEqual(query, "c.id", filtroConvenio.getId());
	   	query = andEqual(query, "c.id_empresa", filtroConvenio.getIdEmpresa());
        query = andEqual(query, "c.id_instituicao", filtroConvenio.getIdInstituicao());
        return this.getNamedParameterJdbcTemplate().queryForObject(query.toString(), getMapSqlParameterSource(), Integer.class);
	}
	
	@Override
	public boolean excluir(Long id) {
		delete("convenio", id);
		return true;
	}
	
	private class ConvenioMapper implements RowMapper<Convenio> {

		@Override
		public Convenio mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Convenio.builder()
					.id(rs.getLong("id"))
					.percentualDesconto(rs.getDouble("codigo"))
					.dataInicioVigencia(rs.getDate("data_inicio_vigencia").toLocalDate())
                    .dataFinalVigencia(rs.getDate("data_final_vigencia").toLocalDate())
                    .idTipoDesconto(rs.getInt("id_tipo_desconto"))
                    .idEmpresa(rs.getLong("id_empresa"))
                    .idInstitucicao(rs.getLong("id_instituicao"))
					.build();
		}
	}
	
	@Override
	public List<ConvenioContratoDTO> listarParaContrato(FiltroConvenio filtroConvenio) {
		  StringBuilder query = createQuery("select c.id, e.cnpj, e.nome_fantasia as empresa, t.nome as tipo_desconto, c.percentual_desconto, ");
          query.append("c.id_empresa, c.id_instituicao ");
          query.append("from convenio c ");
          query.append("left join empresa e on (c.id_empresa = e.id) ");
          query.append("left join tipo_desconto t on (c.id_tipo_desconto = t.id) ");
          if (filtroConvenio.getIdContrato() != null) {
        	  query.append(" join contrato_convenio cc on (cc.id_convenio = c.id) where 1=1 ");
        	  query = andEqual(query, "cc.id_contrato", filtroConvenio.getIdContrato());
          } else {
	          query.append(" where 1=1 ");
	          query = and(query, "c.data_inicio_vigencia", filtroConvenio.getDataContrato(), "<=");
	          query.append(" and (c.data_final_vigencia is null or c.data_final_vigencia >= current_date) ");
	          query = andEqual(query, "c.id_instituicao", filtroConvenio.getIdInstituicao());
          }
          
		  return this.getNamedParameterJdbcTemplate().query(query.toString(), getMapSqlParameterSource(), new ConvenioContratoDTOMapper());
	}
	
	private class ConvenioContratoDTOMapper implements RowMapper<ConvenioContratoDTO> {

		@Override
		public ConvenioContratoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			return ConvenioContratoDTO.builder()
					.id(rs.getLong("id"))
					.percentualDesconto(rs.getDouble("percentual_desconto"))
					.cnpj(rs.getString("cnpj"))
                    .tipoDesconto(rs.getString("tipo_desconto"))
                    .empresa(rs.getString("empresa"))
                    .idEmpresa(rs.getLong("id_empresa"))
                    .idInstitucicao(rs.getLong("id_instituicao"))
					.build();
		}
	}
}
