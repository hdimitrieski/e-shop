allprojects {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
}

dependencies {
  subprojects.map {
    project(it.path)
  }.forEach {
    println("Add custom plugin dependency: ${it.group + ":" + it.name}")
    implementation(it)
  }
}
