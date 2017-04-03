package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.QuantifiedRepository;
import domain.Quantified;


@Component
@Transactional
public class StringToQuantifiedConverter implements Converter<String, Quantified> {

	@Autowired
	QuantifiedRepository quantifiedRepository;
	
	@Override
	public Quantified convert(String text) {
		Quantified result;
		int id;
		
		try{
			if( StringUtils.isEmpty(text) )
				result = null;
			else{
				id = Integer.valueOf(text);
				result = quantifiedRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
		
	}
}
