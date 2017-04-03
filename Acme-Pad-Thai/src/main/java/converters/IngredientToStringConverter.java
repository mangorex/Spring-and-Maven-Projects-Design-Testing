
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Ingredient;

@Component
@Transactional
public class IngredientToStringConverter implements Converter<Ingredient, String> {
	
	@Override
	public String convert(Ingredient actor) {
		Assert.notNull(actor);
		
		String result;

		result = String.valueOf(actor.getId());
		
		return result;
	}

}
