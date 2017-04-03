
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Text;

@Component
@Transactional
public class TextToStringConverter implements Converter<Text, String> {
	
	@Override
	public String convert(Text text) {
		Assert.notNull(text);
		
		String result;

		result = String.valueOf(text.getId());
		
		return result;
	}

}
