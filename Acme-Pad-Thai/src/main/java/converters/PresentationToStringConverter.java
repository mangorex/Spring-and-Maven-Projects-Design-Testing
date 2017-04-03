
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Presentation;

@Component
@Transactional
public class PresentationToStringConverter implements Converter<Presentation, String> {
	
	@Override
	public String convert(Presentation presentation) {
		Assert.notNull(presentation);
		
		String result;

		result = String.valueOf(presentation.getId());
		
		return result;
	}

}
