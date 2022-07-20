plugins {
  id("spring-conventions")
}

description = "rating"

dependencies {
  implementation(project(":rest"))
  implementation(project(":security"))
  implementation(project(":event-handling"))

  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
  implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
  implementation("org.springframework.cloud:spring-cloud-starter-config")
  implementation("org.springframework.cloud:spring-cloud-starter-bus-kafka")
  implementation("net.logstash.logback:logstash-logback-encoder")
  implementation("org.springframework.kafka:spring-kafka")
  implementation("org.hibernate:hibernate-jpamodelgen")
  implementation("org.flywaydb:flyway-core")

  runtimeOnly("org.postgresql:postgresql")
}
