package com.marciobr.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.marciobr.serialization.converter.JsonToYamlConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	private static final MediaType MEDIA_TYPE_YAML = MediaType.valueOf("application/x-yaml");
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new JsonToYamlConverter());
	}

	public void addCorsMapping(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedMethods("GET","POST","PUT","DELETE","OPTIONS");
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false)
		.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json", MediaType.APPLICATION_JSON)
		.mediaType("xml", MediaType.APPLICATION_XML)
		.mediaType("x-yaml", MEDIA_TYPE_YAML);
	}
}
