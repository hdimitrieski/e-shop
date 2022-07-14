plugins {
  id("spring-conventions")
}

dependencies {
  implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("io.projectreactor:reactor-core:3.4.19")
  implementation("org.springframework.cloud:spring-cloud-starter-gateway")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.security:spring-security-oauth2-client")
  implementation("org.springframework.security:spring-security-oauth2-resource-server")
  implementation("org.springframework.security:spring-security-oauth2-jose")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
  implementation("org.springframework.cloud:spring-cloud-starter-config")
  implementation("org.springframework.cloud:spring-cloud-starter-bus-kafka")
  implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
  implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
  implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j:2.1.3")
  implementation("net.logstash.logback:logstash-logback-encoder")
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  implementation("org.apache.commons:commons-lang3")
  implementation("org.apache.commons:commons-collections4")
}

description = "gateway"
