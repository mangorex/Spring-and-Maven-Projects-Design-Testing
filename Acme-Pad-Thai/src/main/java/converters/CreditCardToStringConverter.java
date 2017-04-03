
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.CreditCard;

@Component
@Transactional
public class CreditCardToStringConverter implements Converter<CreditCard, String> {
	
	@Override
	public String convert(CreditCard creditCard) {
		Assert.notNull(creditCard);
		
		String result;

		result = String.valueOf(creditCard.getId());
		
		return result;
	}

}
