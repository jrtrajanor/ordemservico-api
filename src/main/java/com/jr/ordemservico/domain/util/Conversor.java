package com.jr.ordemservico.domain.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class Conversor implements AttributeConverter<Boolean, String> {
	
	    @Override
	    public String convertToDatabaseColumn(Boolean value) {
	        return value ? "S": "N";
	    }

	    @Override
	    public Boolean convertToEntityAttribute(String value) {
	        if( "S".equals(value) )
	            return true;
	        else if( "N".equals(value) )
	            return false;

	        return null;
	    }
		
}
