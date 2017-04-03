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

import domain.Nutritionist;

@Component
@Transactional
public class NutritionistToStringConverter implements Converter<Nutritionist, String> {
	
	@Override
	public String convert(Nutritionist nutritionist) {
		String result;

		if (nutritionist == null)
			result = null;
		else
			result = String.valueOf(nutritionist.getId());

		return result;
	}

}
