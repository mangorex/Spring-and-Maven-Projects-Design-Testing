
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.LearningMaterial;
import domain.Video;

@Component
@Transactional
public class VideoToStringConverter implements Converter<Video, String> {
	
	@Override
	public String convert(Video video) {
		Assert.notNull(video);
		
		String result;

		result = String.valueOf(video.getId());
		
		return result;
	}

}
