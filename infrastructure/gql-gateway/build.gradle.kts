plugins {
  id("spring-conventions")
  id("com.netflix.dgs.codegen") version "5.2.4"
}

description = "gql-gateway"

dependencies {
  implementation(project(":security"))

  implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter:5.0.4")
  implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars:5.0.4")
  implementation("com.netflix.graphql.dgs:graphql-dgs-extended-validation:5.0.4")
  implementation("com.netflix.graphql.dgs.codegen:graphql-dgs-codegen-client-core:5.1.17")
  implementation("com.github.ben-manes.caffeine:caffeine:3.1.1")
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
  implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j:2.1.3")
  implementation("net.logstash.logback:logstash-logback-encoder")
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  implementation("org.apache.commons:commons-lang3")
  implementation("org.apache.commons:commons-collections4")
}

tasks.named<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask>("generateJava") {
  schemaPaths = mutableListOf("${project.projectDir}/src/main/resources/schema")
  packageName = "com.eshop.gqlgateway"
  generateClient = false
  language = "JAVA"
}

tasks.compileJava {
  dependsOn += "generateJava"
}
