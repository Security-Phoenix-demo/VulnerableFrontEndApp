package config

import (
	"io/ioutil"
	"os"

	"gopkg.in/yaml.v2"
)

// Route maps an inbound path prefix to an upstream service.
type Route struct {
	Path     string `yaml:"path"`
	Upstream string `yaml:"upstream"`
	Public   bool   `yaml:"public"`
}

// Config is the gateway's YAML configuration.
type Config struct {
	Listen    string  `yaml:"listen"`
	JWTSecret string  `yaml:"jwtSecret"`
	Routes    []Route `yaml:"routes"`
}

// Load reads the configuration file named by GATEWAY_CONFIG, or gateway.yml.
func Load() (*Config, error) {
	path := os.Getenv("GATEWAY_CONFIG")
	if path == "" {
		path = "gateway.yml"
	}
	raw, err := ioutil.ReadFile(path)
	if err != nil {
		return nil, err
	}
	cfg := &Config{Listen: ":8080", JWTSecret: "change-me"}
	if err := yaml.Unmarshal(raw, cfg); err != nil {
		return nil, err
	}
	return cfg, nil
}
