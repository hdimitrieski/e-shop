plugins {
  id("spring-conventions")
}

description = "image-service"

dependencies {
  implementation(project(":rest"))

  implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("io.minio:minio:8.4.2")
  implementation("com.squareup.okhttp3:okhttp:4.10.0")
  implementation(project(":security"))
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
  implementation("org.springframework.cloud:spring-cloud-starter-config")
  implementation("org.springframework.cloud:spring-cloud-starter-bus-kafka")
  implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
  implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
  implementation("net.logstash.logback:logstash-logback-encoder")
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  implementation("org.apache.commons:commons-lang3")
  implementation("org.apache.commons:commons-collections4")
}
