package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;

@Component
@Transactional
public class UserAccountToStringConverter implements
		Converter<UserAccount, String> {

	@Override
	public String convert(UserAccount userAccount) {
		Assert.notNull(userAccount);

		String result;

		result = String.valueOf(userAccount.getId());

		return result;
	}

}
