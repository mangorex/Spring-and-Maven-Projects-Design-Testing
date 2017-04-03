
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Property;

@Component
@Transactional
public class PropertyToStringConverter implements Converter<Property, String> {
	
	@Override
	public String convert(Property property) {
		Assert.notNull(property);
		
		String result;

		result = String.valueOf(property.getId());
		
		return result;
	}

}
