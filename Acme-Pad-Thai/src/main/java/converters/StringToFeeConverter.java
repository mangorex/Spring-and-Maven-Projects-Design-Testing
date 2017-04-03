package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FeeRepository;
import domain.Fee;

@Component
@Transactional
public class StringToFeeConverter implements Converter<String, Fee> {

	@Autowired
	FeeRepository feeRepository;
	
	@Override
	public Fee convert(String text) {
		Fee result;
		int id;
		
		try{
			if( StringUtils.isEmpty(text) )
				result = null;
			else{
				id = Integer.valueOf(text);
				result = feeRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
		
	}

}