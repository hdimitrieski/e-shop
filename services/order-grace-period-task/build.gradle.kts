plugins {
  id("spring-conventions")
}

description = "order-grace-period-task"

dependencies {
  implementation(project(":event-handling"))

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
  implementation("org.springframework.cloud:spring-cloud-starter-config")
  implementation("org.springframework.cloud:spring-cloud-starter-bus-kafka")
  implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
  implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
  implementation("org.springframework.kafka:spring-kafka")
  implementation("net.logstash.logback:logstash-logback-encoder")

  runtimeOnly("org.postgresql:postgresql")
}
