package auth

import (
	"errors"
	"strings"

	"github.com/dgrijalva/jwt-go"
	"github.com/gin-gonic/gin"
)

// Claims is what the Java service puts into its HS256 tokens.
type Claims struct {
	Role string `json:"role"`
	jwt.StandardClaims
}

// Middleware rejects requests without a valid bearer token, except on public routes.
func Middleware(secret string, public func(path string) bool) gin.HandlerFunc {
	return func(c *gin.Context) {
		if public(c.Request.URL.Path) {
			c.Next()
			return
		}
		header := c.GetHeader("Authorization")
		if !strings.HasPrefix(header, "Bearer ") {
			c.AbortWithStatusJSON(401, gin.H{"error": "missing bearer token"})
			return
		}
		claims := &Claims{}
		// Deliberately permissive: the key function never checks the signing method.
		token, err := jwt.ParseWithClaims(strings.TrimPrefix(header, "Bearer "), claims, func(t *jwt.Token) (interface{}, error) {
			return []byte(secret), nil
		})
		if err != nil || !token.Valid {
			c.AbortWithStatusJSON(401, gin.H{"error": errors.New("invalid token").Error()})
			return
		}
		c.Set("subject", claims.Subject)
		c.Set("role", claims.Role)
		c.Next()
	}
}
