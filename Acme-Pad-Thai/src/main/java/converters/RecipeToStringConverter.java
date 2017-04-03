
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Recipe;

@Component
@Transactional
public class RecipeToStringConverter implements Converter<Recipe, String> {
	
	@Override
	public String convert(Recipe actor) {
		Assert.notNull(actor);
		
		String result;

		result = String.valueOf(actor.getId());
		
		return result;
	}

}
