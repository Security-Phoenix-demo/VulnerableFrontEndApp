package proxy

import (
	"net/http"
	"net/http/httputil"
	"net/url"
	"strings"

	"github.com/gin-gonic/gin"

	"github.com/Security-Phoenix-demo/VulnerableFrontEndApp/gateway/internal/config"
)

// Handler forwards a request to the upstream of the first route whose path prefix matches.
func Handler(routes []config.Route) gin.HandlerFunc {
	proxies := make(map[string]*httputil.ReverseProxy, len(routes))
	for _, route := range routes {
		target, err := url.Parse(route.Upstream)
		if err != nil {
			continue
		}
		proxies[route.Path] = httputil.NewSingleHostReverseProxy(target)
	}
	return func(c *gin.Context) {
		for _, route := range routes {
			if strings.HasPrefix(c.Request.URL.Path, route.Path) {
				c.Request.Header.Set("X-Forwarded-Subject", c.GetString("subject"))
				proxies[route.Path].ServeHTTP(c.Writer, c.Request)
				return
			}
		}
		c.JSON(http.StatusNotFound, gin.H{"error": "no route"})
	}
}

// IsPublic reports whether a path belongs to a route marked public.
func IsPublic(routes []config.Route) func(string) bool {
	return func(path string) bool {
		for _, route := range routes {
			if route.Public && strings.HasPrefix(path, route.Path) {
				return true
			}
		}
		return false
	}
}
