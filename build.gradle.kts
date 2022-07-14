plugins {
  id("idea")
}

idea {
  module.isDownloadJavadoc = false
  module.isDownloadSources = false
}

allprojects {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
