package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Bill;

@Component
@Transactional
public class BillToStringConverter implements Converter<Bill, String> {

	@Override
	public String convert(Bill bill) {
		Assert.notNull(bill);

		String result;

		result = String.valueOf(bill.getId());

		return result;
	}

}
