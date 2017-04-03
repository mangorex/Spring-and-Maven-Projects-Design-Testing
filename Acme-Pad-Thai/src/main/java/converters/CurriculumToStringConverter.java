
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curriculum;

@Component
@Transactional
public class CurriculumToStringConverter implements Converter<Curriculum, String> {
	
	@Override
	public String convert(Curriculum actor) {
		Assert.notNull(actor);
		
		String result;

		result = String.valueOf(actor.getId());
		
		return result;
	}

}
