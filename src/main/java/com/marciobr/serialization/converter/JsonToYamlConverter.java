package com.marciobr.serialization.converter;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public final class JsonToYamlConverter extends AbstractJackson2HttpMessageConverter {

	public JsonToYamlConverter() {
		super(new YAMLMapper(),MediaType.parseMediaType("application/x-yaml"));
	}

}
