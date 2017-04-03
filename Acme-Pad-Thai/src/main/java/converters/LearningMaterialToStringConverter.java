
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.LearningMaterial;

@Component
@Transactional
public class LearningMaterialToStringConverter implements Converter<LearningMaterial, String> {
	
	@Override
	public String convert(LearningMaterial learningMaterial) {
		Assert.notNull(learningMaterial);
		
		String result;

		result = String.valueOf(learningMaterial.getId());
		
		return result;
	}

}
