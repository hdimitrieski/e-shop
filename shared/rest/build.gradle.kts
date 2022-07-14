plugins {
  id("lib-conventions")
}

description = "rest"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
}
