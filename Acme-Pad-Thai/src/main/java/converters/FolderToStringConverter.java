
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Folder;

@Component
@Transactional
public class FolderToStringConverter implements Converter<Folder, String> {
	
	@Override
	public String convert(Folder actor) {
		Assert.notNull(actor);
		
		String result;

		result = String.valueOf(actor.getId());
		
		return result;
	}

}
