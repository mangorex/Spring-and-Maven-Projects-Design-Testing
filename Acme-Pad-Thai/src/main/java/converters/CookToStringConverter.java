
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Cook;

@Component
@Transactional
public class CookToStringConverter implements Converter<Cook, String> {
	
	@Override
	public String convert(Cook cook) {
		Assert.notNull(cook);
		
		String result;

		result = String.valueOf(cook.getId());
		
		return result;
	}

}
