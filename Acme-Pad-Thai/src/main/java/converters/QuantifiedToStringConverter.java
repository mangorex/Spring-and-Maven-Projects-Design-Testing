package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Quantified;

@Component
@Transactional
public class QuantifiedToStringConverter implements Converter<Quantified, String> {

	@Override
	public String convert(Quantified quantified) {
		String result;
		
		if( quantified == null )
			result = null;
		else
			result = String.valueOf(quantified.getId());
		return result;
	}
}