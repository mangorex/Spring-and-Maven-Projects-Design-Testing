
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.MasterClass;

@Component
@Transactional
public class MasterClassToStringConverter implements Converter<MasterClass, String> {
	
	@Override
	public String convert(MasterClass masterClass) {
		Assert.notNull(masterClass);
		
		String result;

		result = String.valueOf(masterClass.getId());
		
		return result;
	}

}
