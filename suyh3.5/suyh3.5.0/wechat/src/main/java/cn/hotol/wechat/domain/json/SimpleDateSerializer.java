package cn.hotol.wechat.domain.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * login: Hill Pan
 * Date: 9/26/12
 * Time: 1:04 PM
 */
public class SimpleDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		if (value == null) {
			gen.writeNull();
			return ;
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = formatter.format(value);
		gen.writeString(formattedDate);
	}
}
