plugins {
  id("spring-conventions")
}

dependencies {
  implementation(project(":event-handling"))

  implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
  implementation("org.springframework.cloud:spring-cloud-starter-config")
  implementation("org.springframework.cloud:spring-cloud-starter-bus-kafka")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
  implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
  implementation("net.logstash.logback:logstash-logback-encoder")
  implementation("org.springframework.kafka:spring-kafka")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  runtimeOnly("org.postgresql:postgresql")
}

description = "order-grace-period-task"
