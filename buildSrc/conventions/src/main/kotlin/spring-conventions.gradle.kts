plugins {
  id("java-conventions")
  id("org.springframework.boot")
}

tasks.bootJar {
  archiveFileName.set("${project.name}.jar")
  manifest {
    attributes(
      mapOf(
        "Implementation-Title" to project.name,
        "Implementation-Version" to project.version
      )
    )
  }
}
