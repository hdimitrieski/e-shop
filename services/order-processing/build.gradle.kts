plugins {
  id("spring-conventions")
}

description = "order-processing"

dependencies {
  implementation(project(":rest"))
  implementation(project(":outbox"))
  implementation(project(":validation"))
  implementation(project(":security"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
  implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
  implementation("org.springframework.cloud:spring-cloud-starter-config")
  implementation("org.springframework.cloud:spring-cloud-starter-bus-kafka")
  implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
  implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
  implementation("org.springframework.kafka:spring-kafka")
  implementation("an.awesome:pipelinr")
  implementation("org.apache.commons:commons-lang3")
  implementation("org.apache.commons:commons-collections4")
  implementation("org.flywaydb:flyway-core")
  implementation("net.logstash.logback:logstash-logback-encoder")

  runtimeOnly("org.postgresql:postgresql")
}
