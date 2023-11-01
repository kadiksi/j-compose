rootProject.name = "JCourier-compose"
include(":app")
pluginManagement {
    buildscript {
        repositories {
            mavenCentral()
            maven {
                url = uri("https://storage.googleapis.com/r8-releases/raw")
            }
        }
        dependencies {
            classpath("com.android.tools:r8:8.1.56")
            classpath("com.google.guava:guava:30.1.1-jre")  // <-- THIS IS REQUIRED UNTIL R8 3.2.4-dev
        }
    }
}