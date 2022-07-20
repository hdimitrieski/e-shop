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
  api(enforcedPlatform(libs.spring.boot.dependencies))
  api(enforcedPlatform(libs.spring.cloud.dependencies))

  constraints {
    api(libs.okhttp)
    api(libs.minio)
    api(libs.reactor.core)
    api(libs.dgs.spring.boot.starter)
    api(libs.dgs.extended.validation)
    api(libs.dgs.extended.scalars)
    api(libs.dgs.codegen.client.core)
    api(libs.ben.manes.caffeine)
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
