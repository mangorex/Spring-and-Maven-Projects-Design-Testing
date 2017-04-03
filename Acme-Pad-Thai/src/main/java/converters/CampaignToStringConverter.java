
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Campaign;

@Component
@Transactional
public class CampaignToStringConverter implements Converter<Campaign, String> {
	
	@Override
	public String convert(Campaign campaign) {
		Assert.notNull(campaign);
		
		String result;

		result = String.valueOf(campaign.getId());
		
		return result;
	}

}
