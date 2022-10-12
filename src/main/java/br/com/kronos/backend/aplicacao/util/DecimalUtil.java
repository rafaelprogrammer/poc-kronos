package br.com.kronos.backend.aplicacao.util;

import java.text.DecimalFormat;

public class DecimalUtil {

	public static Double converterDoubleDoisDecimais(double precoDouble) {
	    DecimalFormat fmt = new DecimalFormat("0.00");      
	    String string = fmt.format(precoDouble);
	    String[] part = string.split("[,]");
	    if (part == null || part.length == 1) {
	    	return Double.parseDouble(part[0]);
	    }
	    String string2 = part[0]+"."+part[1];
	        double preco = Double.parseDouble(string2);
	    return preco;
	}
}
