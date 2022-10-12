package br.com.kronos.backend.aplicacao.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	public static LocalDateTime dataHoraAtual() {
		return LocalDateTime.now(recuperarZoneIdSaoPaulo());
	}
	
	public static String dataHoraAtualFormatada() {
		return LocalDateTime.now(recuperarZoneIdSaoPaulo()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
	
	public static LocalTime horaAtual() {
		return LocalTime.now(recuperarZoneIdSaoPaulo());
	}

	private static ZoneId recuperarZoneIdSaoPaulo() {
		return ZoneId.of("America/Sao_Paulo");
	}
	
	public static LocalDate dataAtual() {
		return LocalDate.now(recuperarZoneIdSaoPaulo());
	}
	
	public static LocalDate paraLocalDate(int ano, int mes, int dia) {
		return  LocalDate.of(ano, mes, dia);
	}
	
	public static LocalDateTime paraLocalDateTime(String data) {
		return  LocalDateTime.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static LocalDate paraLocalDate(String data) {
		return  LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public static LocalDate paraLocalDate(Timestamp data) {
		return LocalDate.parse(data.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	}

	public static String paraData(Timestamp data) {
		return data.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static String paraDataEHora(Timestamp data) {
		return data.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	public static String paraString(LocalDateTime data) {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public static String paraString(LocalDate data) {
		return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public static String paraString(LocalDateTime data, String pattern) {
		return data.format(DateTimeFormatter.ofPattern(pattern));
	}
	
	public static String paraString(LocalDate data, String pattern) {
		return data.format(DateTimeFormatter.ofPattern(pattern));
	}
	
}
