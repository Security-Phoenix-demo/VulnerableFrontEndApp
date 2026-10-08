# java-service

Spring Boot 2.3 service of the VulnerableFrontEndApp demo monorepo: a findings API (users, findings, reports) on
H2, with Spring Security, JPA, Thymeleaf, Actuator and springdoc. Every dependency is pinned to a version with a
known advisory on purpose (log4j-core 2.14.1, jackson-databind 2.9.8, commons-text 1.9, xstream 1.4.15, …).

    mvn spring-boot:run        # http://localhost:8080 — / (report), /h2-console, /actuator, /swagger-ui.html

Do not deploy this anywhere that matters.
