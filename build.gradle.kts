// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl(Repositories.mavenGradle)
        }
        maven {
            setUrl(Repositories.mavenJitpack)
        }
    }

    dependencies {
        classpath(Deps.Classpath.gradle)
        classpath(Deps.Classpath.kotlinGradle)
        classpath(Deps.Classpath.daggerHilt)
        classpath(Deps.Classpath.ktlint)
        classpath(Deps.Classpath.gsm)
        classpath(Deps.Classpath.crashlytics)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl(Repositories.mavenGradle)
        }
        maven {
            setUrl(Repositories.mavenJitpack)
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}