plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

android {
    compileSdk = Android.DefaultConfig.compileSdk

    defaultConfig {
        minSdk = Android.DefaultConfig.minSdk
        targetSdk = Android.DefaultConfig.targetSdk

        testInstrumentationRunner = Android.Test.instrumentationRunner
        consumerProguardFile(Android.Proguard.consumerRules)
    }

    buildTypes {
        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = Properties.Debug.isMinifyEnabled
            proguardFiles(getDefaultProguardFile(Android.Proguard.optimize), Android.Proguard.rules)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Android.Options.jvmTarget
    }
}

dependencies {
    implementation(Deps.Other.coroutines)
    implementation(Deps.Other.javax)

    implementation(Deps.Square.retrofit)

    implementation(project(Project.common))
    implementation(Deps.AndroidX.annotation)

    testImplementation(Deps.Test.jUnit)
    testImplementation(Deps.Test.jUnitExt)
}