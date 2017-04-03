package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RecipeRepository;
import domain.Recipe;

@Component
@Transactional
public class StringToRecipeConverter implements Converter<String, Recipe> {

	@Autowired
	RecipeRepository recipeRepository;

	@Override
	public Recipe convert(String text) {
		Recipe result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = recipeRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
