package cn.edu.sdcet.api.Config;


import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

		FastJsonConfig config = new FastJsonConfig();
		config.setDateFormat("yyyy-MM-dd HH:mm:ss");
		config.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean);
		config.setWriterFeatures(JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.PrettyFormat);
		converter.setFastJsonConfig(config);
		converter.setDefaultCharset(StandardCharsets.UTF_8);
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
		converters.addFirst(converter);
	}

	@Bean
	public Random random() {
		return new Random();
	}

}