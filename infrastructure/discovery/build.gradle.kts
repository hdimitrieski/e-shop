plugins {
  id("spring-conventions")
}

description = "discovery"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
  implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
  implementation("org.springframework.cloud:spring-cloud-starter-config")
  implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
  implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
  implementation("org.springframework.cloud:spring-cloud-starter-bus-kafka")
  implementation("net.logstash.logback:logstash-logback-encoder")
}
