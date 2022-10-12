package br.com.kronos.backend.adaptadores.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DoubleCommaSerializer extends JsonSerializer<Double> {

	@Override
	public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (null == value) {
			gen.writeNull();
        } else {
            gen.writeString(value.toString().replace(".", ","));
        }
		
	}

}