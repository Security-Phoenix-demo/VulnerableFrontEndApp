package main

import (
	"log"

	"github.com/gin-gonic/gin"

	"github.com/Security-Phoenix-demo/VulnerableFrontEndApp/gateway/internal/auth"
	"github.com/Security-Phoenix-demo/VulnerableFrontEndApp/gateway/internal/config"
	"github.com/Security-Phoenix-demo/VulnerableFrontEndApp/gateway/internal/proxy"
)

func main() {
	cfg, err := config.Load()
	if err != nil {
		log.Fatalf("gateway: %v", err)
	}
	r := gin.Default()
	r.Use(auth.Middleware(cfg.JWTSecret, proxy.IsPublic(cfg.Routes)))
	r.GET("/healthz", func(c *gin.Context) { c.JSON(200, gin.H{"ok": true}) })
	r.NoRoute(proxy.Handler(cfg.Routes))
	log.Fatal(r.Run(cfg.Listen))
}
