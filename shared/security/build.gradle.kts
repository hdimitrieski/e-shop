plugins {
  id("lib-conventions")
}

description = "security"

dependencies {
  api("org.springframework.boot:spring-boot-starter-security")
  api("org.springframework.security:spring-security-config")
  api("org.springframework.security:spring-security-oauth2-resource-server")
  api("org.springframework.security:spring-security-oauth2-jose")
}
