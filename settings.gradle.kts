pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        jcenter() // Старые библиотеки могут быть в JCenter
    }
}

rootProject.name = "ToDoList"
include(":app")
 