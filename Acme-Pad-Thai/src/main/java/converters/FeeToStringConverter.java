
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Fee;

@Component
@Transactional
public class FeeToStringConverter implements Converter<Fee, String> {
	
	@Override
	public String convert(Fee fee) {
		Assert.notNull(fee);
		
		String result;

		result = String.valueOf(fee.getId());
		
		return result;
	}

}
