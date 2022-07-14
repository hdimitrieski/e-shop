plugins {
  id("java-conventions")
  id("org.springframework.boot")
}


tasks.bootJar {
  archiveFileName.set("${project.name}.jar")
}