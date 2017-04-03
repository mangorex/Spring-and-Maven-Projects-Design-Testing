
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Message;

@Component
@Transactional
public class MessageToStringConverter implements Converter<Message, String> {
	
	@Override
	public String convert(Message message) {
		Assert.notNull(message);
		
		String result;

		result = String.valueOf(message.getId());
		
		return result;
	}

}
