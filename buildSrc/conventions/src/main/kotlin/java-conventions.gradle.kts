import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  // See: https://docs.gradle.org/current/userguide/java_library_plugin.html#java_library_plugin
  `java-library`
}

java {
  // See: https://docs.gradle.org/current/userguide/toolchains.html#toolchains
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
  withSourcesJar()
}

val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
dependencies {
  api(platform(project(":platform")))

  versionCatalog.findLibrary("lombok").ifPresent {
    compileOnly(it)
    // Not work when use annotationProcessor("org.projectlombok:lombok")
    annotationProcessor(it)
  }

  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testImplementation("org.junit.jupiter:junit-jupiter-engine")
}

tasks.compileJava {
  @Suppress("SpellCheckingInspection")
  options.compilerArgs.addAll(
    listOf(
      // Deprecated API details
      "-Xlint:deprecation",
      // Enables all recommended warnings.
      // "-Xlint:all",
      // Terminates compilation when warnings occur.
      // "-Werror"
    )
  )
  options.encoding = "UTF-8"
}

tasks.test {
  useJUnitPlatform()
  testLogging {
    events = setOf(TestLogEvent.FAILED)
    exceptionFormat = TestExceptionFormat.FULL
  }
}
