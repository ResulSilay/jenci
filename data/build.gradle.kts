plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

android {
    compileSdk = Android.DefaultConfig.compileSdk

    defaultConfig {
        minSdk = Android.DefaultConfig.minSdk
        targetSdk = Android.DefaultConfig.targetSdk

        testInstrumentationRunner = Android.Test.instrumentationRunner
        consumerProguardFile(Android.Proguard.consumerRules)

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
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
    implementation(Deps.AndroidX.liveDataKtx)
    implementation(Deps.AndroidX.liveDataExt)
    implementation(Deps.AndroidX.cryptoKtx)
    implementation(Deps.AndroidX.datastore) //*
    implementation(Deps.AndroidX.roomKtx)

    kapt(Deps.AndroidX.roomCompilerProcessor)
    api(Deps.AndroidX.roomRuntimeApi)

    implementation(Deps.Google.gson)

    implementation(Deps.Square.retrofit)
    implementation(Deps.Square.moshi)

    implementation(Deps.Other.coroutines)
    implementation(Deps.Other.javax)

    implementation(project(Project.common))
    implementation(project(Project.domain))

    testImplementation(Deps.Test.jUnit)
    testImplementation(Deps.Test.jUnitExt)
}