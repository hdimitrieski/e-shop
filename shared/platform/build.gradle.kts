plugins {
  // See: https://docs.gradle.org/current/userguide/java_platform_plugin.html#java_platform_plugin
  `java-platform`
}

description = "platform"

// By default, in order to avoid the common mistake of adding a dependency in a platform instead of a constraint,
// Gradle will fail if you try to do so. If, for some reason, you also want to add dependencies in addition to
// constraints, you need to enable it explicitly
javaPlatform {
  allowDependencies()
}

// Dependency Management
println("Enabling Dependency Management in project ${project.name}...")
dependencies {
  // Import a BOM. The versions used in this file will override any other version found in the graph
  api(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:2.7.1"))
  api(enforcedPlatform("org.springframework.cloud:spring-cloud-dependencies:2021.0.3"))

  constraints {
    api(libs.spring.data.mongodb)
    api(libs.kafka.clients)
    api(libs.axon.mongo)
    api(libs.axon.kafka.spring.boot.starter)
    api(libs.axon.spring.boot.starter)
    api(libs.pipelinr)
    api(libs.commons.collections4)
    api(libs.commons.lang3)
    api(libs.hibernate.jpamodelgen)
    api(libs.flywaydb)
    api(libs.postgresql)
    api(libs.logstash.logback.encoder)
    api(libs.lombok)
    api(libs.bundles.junit5)
  }
}
