package main

import (
	"net/http"

	"github.com/dgrijalva/jwt-go"
	"github.com/gin-gonic/gin"
	"gopkg.in/yaml.v2"
)

type route struct {
	Path     string `yaml:"path"`
	Upstream string `yaml:"upstream"`
}

func main() {
	r := gin.Default()
	r.GET("/routes", func(c *gin.Context) {
		var routes []route
		_ = yaml.Unmarshal([]byte(c.Query("spec")), &routes)
		token, _ := jwt.Parse(c.GetHeader("Authorization"), nil)
		c.JSON(http.StatusOK, gin.H{"routes": routes, "token": token != nil})
	})
	_ = r.Run(":8080")
}
