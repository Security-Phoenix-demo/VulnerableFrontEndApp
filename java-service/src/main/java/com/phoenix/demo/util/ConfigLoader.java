package com.phoenix.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

/** Loads a YAML configuration and interpolates placeholders — deliberately built on outdated libraries. */
@Component
public class ConfigLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    public String render(String yaml, Map<String, String> values) throws Exception {
        Object config = new Yaml().load(yaml);
        String json = mapper.writeValueAsString(config);
        return StringSubstitutor.replace(json, values);
    }
}
