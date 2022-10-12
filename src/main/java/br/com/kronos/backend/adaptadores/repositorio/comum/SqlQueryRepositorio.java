package br.com.kronos.backend.adaptadores.repositorio.comum;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import lombok.Getter;



public class SqlQueryRepositorio extends SqlRepositorio {
	
	@Getter
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Getter
	private MapSqlParameterSource mapSqlParameterSource;
	
	public enum Order {
		ASC,
		DESC
	}

	public SqlQueryRepositorio(DataSource dataSource) {
		super(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public StringBuilder createQuery(String sql) {
		this.mapSqlParameterSource = new MapSqlParameterSource();
		return new StringBuilder(sql);
	}
	
	public StringBuilder andEqual(StringBuilder query, String field, Number value) {
		if(value != null) {
			query.append(" and ");
			query.append(field);
			query.append(" = :");
			query.append(field);
			this.mapSqlParameterSource.addValue(field, value);
		}
		return query;
		
	}
	
	public StringBuilder andEqualDate(StringBuilder query, String field, String value) {
		if(!StringUtils.isEmpty(value)) {
			query.append(" and ");
			query.append(field);
			query.append(" = to_date(:");
			query.append(field);
			query.append(", 'yyyy-MM-dd')");
			this.mapSqlParameterSource.addValue(field, value);
		}
		return query;
		
	}
	
	public StringBuilder andEqual(StringBuilder query, String field, Object value) {
		if(value != null) {
			query.append(" and ");
			query.append(field);
			query.append(" = :");
			query.append(field);
			this.mapSqlParameterSource.addValue(field, value);
		}
		return query;
	}
	
	public StringBuilder and(StringBuilder query, String field, Object value, String operador) {
		if(!StringUtils.isEmpty(value)) {
			query.append(" and ");
			query.append(field);
			query.append(operador);
			query.append(" :");
			query.append(field);
			this.mapSqlParameterSource.addValue(field, value);
		}
		return query;
	}
	
	public StringBuilder or(StringBuilder query, String field, Object value, String operador) {
		if(!StringUtils.isEmpty(value)) {
			query.append(" or ");
			query.append(field);
			query.append(operador);
			query.append(" :");
			query.append(field);
			this.mapSqlParameterSource.addValue(field, value);
		}
		return query;
	}
	
	public StringBuilder groupBy(StringBuilder query, String fields) {
		if(!StringUtils.isEmpty(fields)) {
			query.append(" group by ");
			query.append(fields);
			query.append(" ");
		}
		return query;
	}
	
	public StringBuilder andLike(StringBuilder query, String field, String value) {
		if(!StringUtils.isEmpty(value)) {
			query.append(" and unaccent(upper(");
			query.append(field);
			query.append(")) like :");
			query.append(field);
			this.mapSqlParameterSource.addValue(field, "%" + normalize(value.toUpperCase()) + "%");
		}
		return query;
		
	}
	
	public StringBuilder orderBy(StringBuilder query, Order order, String... fields) {
		query.append(" order by ");
		List<String> atributos = Arrays.asList(fields);
		atributos.stream().forEach(a -> {
			query.append(a);
			query.append(",");
		});
		query.replace(query.length()-1, query.length(), " ");
		return query.append(order);
	}
	
	public StringBuilder limit(StringBuilder query, Integer total, Integer pagina) {
		if(total != null && total > 0) {
			query.append(" LIMIT :total ");
			mapSqlParameterSource.addValue("total", total);
			if(pagina != null && pagina > 0) {
				query.append(" OFFSET (:pagina - 1) * :total ");
				this.mapSqlParameterSource.addValue("pagina", pagina);
			}
		}
		return query;
	}
	
	public StringBuilder andIn(StringBuilder query, String field, Number... fields) {
		query.append(" and ");
		query.append(field);
		query.append(" in( ");
		List<Number> atributos = Arrays.asList(fields);
		atributos.stream().forEach(a -> {
			query.append(a);
			query.append(",");
		});
		query.replace(query.length()-1, query.length(), " ");
		query.append(" ) ");
		return query;
	}
	
	private String normalize(String value) {
		return Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}
