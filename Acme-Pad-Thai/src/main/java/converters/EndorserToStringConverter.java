/* CurriculumToStringConverter.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import domain.Endorser;

@Component
@Transactional
public class EndorserToStringConverter implements Converter<Endorser, String> {
	
	@Override
	public String convert(Endorser endorser) {
		String result;

		if (endorser == null)
			result = null;
		else
			result = String.valueOf(endorser.getId());

		return result;
	}

}
