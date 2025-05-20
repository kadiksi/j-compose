buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.25")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath("com.google.gms:google-services:4.4.2")
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    configurations.all {
        resolutionStrategy {
            force ("com.google.guava:guava:32.1.2-jre")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildTreePath)
}