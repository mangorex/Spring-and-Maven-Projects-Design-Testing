
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.SocialIdentity;

@Component
@Transactional
public class SocialIdentityToStringConverter implements Converter<SocialIdentity, String> {
	
	@Override
	public String convert(SocialIdentity actor) {
		Assert.notNull(actor);
		
		String result;

		result = String.valueOf(actor.getId());
		
		return result;
	}

}
