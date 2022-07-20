pluginManagement {
  repositories {
    gradlePluginPortal()
  }
}

// See: https://docs.gradle.org/current/userguide/platforms.html
dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("configuration/libs.versions.toml"))
    }
  }
}

include("conventions")
