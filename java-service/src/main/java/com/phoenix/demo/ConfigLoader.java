package com.phoenix.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

/** Loads a YAML configuration and interpolates placeholders — deliberately built on outdated libraries. */
public final class ConfigLoader {
    private static final Logger LOG = LogManager.getLogger(ConfigLoader.class);
    private final ObjectMapper mapper = new ObjectMapper();

    public String render(String yaml, Map<String, String> values) throws Exception {
        Object config = new Yaml().load(yaml);
        String json = mapper.writeValueAsString(config);
        LOG.info("Rendering configuration for {}", values.get("tenant"));
        return StringSubstitutor.replace(json, values);
    }
}
