pluginManagement {
  repositories {
    gradlePluginPortal()
  }
}

rootProject.name = "e-shop"

// See: https://docs.gradle.org/current/userguide/platforms.html
dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("buildSrc/configuration/libs.versions.toml"))
    }
  }
}

// shared
include(":shared")
include(":platform")
include(":outbox")
include(":event-handling")
include(":rest")
include(":validation")
include(":security")
project(":platform").projectDir = file("shared/platform")
project(":security").projectDir = file("shared/security")
project(":validation").projectDir = file("shared/validation")
project(":rest").projectDir = file("shared/rest")
project(":event-handling").projectDir = file("shared/event-handling")
project(":outbox").projectDir = file("shared/outbox")

// infrastructure
include(":infrastructure")
include(":gateway")
include(":gql-gateway")
include(":discovery")
include(":config")
include(":image-service")
project(":gateway").projectDir = file("infrastructure/gateway")
project(":gql-gateway").projectDir = file("infrastructure/gql-gateway")
project(":discovery").projectDir = file("infrastructure/discovery")
project(":config").projectDir = file("infrastructure/config")
project(":image-service").projectDir = file("infrastructure/image-service")

// services
include(":services")
include(":catalog-command")
include(":catalog-shared")
include(":catalog-query")
include(":order-grace-period-task")
include(":order-notifications")
include(":payment")
include(":rating")
include(":basket")
include(":order-processing")
include(":analytics")
include(":catalog")
project(":catalog-command").projectDir = file("services/catalog/catalog-command")
project(":catalog-shared").projectDir = file("services/catalog/catalog-shared")
project(":catalog-query").projectDir = file("services/catalog/catalog-query")
project(":order-grace-period-task").projectDir = file("services/order-grace-period-task")
project(":order-notifications").projectDir = file("services/order-notifications")
project(":payment").projectDir = file("services/payment")
project(":rating").projectDir = file("services/rating")
project(":basket").projectDir = file("services/basket")
project(":order-processing").projectDir = file("services/order-processing")
project(":analytics").projectDir = file("services/analytics")
project(":catalog").projectDir = file("services/catalog")
