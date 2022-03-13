plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.daggerHilt)
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

kapt {
    correctErrorTypes = Properties.correctErrorTypes
}

dependencies {
    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appCompat)

    implementation(Deps.Google.material)
    implementation(Deps.Square.retrofit)
    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompilerKapt)

    implementation(Deps.Other.timber)

    testImplementation(Deps.Test.jUnit)
    testImplementation(Deps.Test.jUnitExt)
    testImplementation(Deps.Test.espresso)
}