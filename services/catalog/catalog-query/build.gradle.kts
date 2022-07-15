plugins {
  id("spring-conventions")
}

description = "catalog-query"

dependencies {
  implementation(project(":rest"))
  implementation(project(":validation"))
  implementation(project(":catalog-shared"))
  implementation(project(":security"))
  implementation(project(":event-handling"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
  implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
  implementation("org.springframework.cloud:spring-cloud-starter-config")
  implementation("org.springframework.cloud:spring-cloud-starter-bus-kafka")
  implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
  implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
  implementation("org.axonframework:axon-spring-boot-starter")
  implementation("org.axonframework.extensions.kafka:axon-kafka-spring-boot-starter")
  implementation("org.axonframework.extensions.mongo:axon-mongo")
  implementation("org.springframework.kafka:spring-kafka")
  implementation("org.apache.kafka:kafka-clients")
  implementation("org.hibernate:hibernate-jpamodelgen")
  implementation("org.apache.commons:commons-lang3")
  implementation("org.apache.commons:commons-collections4")
  implementation("net.logstash.logback:logstash-logback-encoder")
  implementation("org.flywaydb:flyway-core")

  runtimeOnly("org.postgresql:postgresql")
}
