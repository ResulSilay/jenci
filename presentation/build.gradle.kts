plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.daggerHilt)
}

android {
    compileSdk = Android.DefaultConfig.compileSdk

    defaultConfig {
        applicationId = Android.DefaultConfig.applicationId
        minSdk = Android.DefaultConfig.minSdk
        targetSdk = Android.DefaultConfig.targetSdk
        versionCode = Android.DefaultConfig.versionCode
        versionName = Android.DefaultConfig.versionName
        setProperty(
            Android.DefaultConfig.Property.versionPropertyName,
            Android.DefaultConfig.Property.versionPropertyValue
        )

        testInstrumentationRunner = Android.Test.instrumentationRunner
        vectorDrawables {
            useSupportLibrary = Properties.useSupportLibrary
        }
        signingConfig = signingConfigs.getByName(BuildTypes.DEBUG)
    }

    buildTypes {
        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = Properties.Debug.isMinifyEnabled
            proguardFiles(getDefaultProguardFile(Android.Proguard.optimize), Android.Proguard.rules)
        }
        getByName(BuildTypes.RELEASE) {
            isShrinkResources = Properties.Release.isShrinkResources
            isMinifyEnabled = Properties.Release.isMinifyEnabled
            proguardFiles(getDefaultProguardFile(Android.Proguard.optimize), Android.Proguard.rules)
        }
    }

    kotlinOptions {
        jvmTarget = Android.Options.jvmTarget
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = Properties.compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    packagingOptions {
        resources {
            excludes += Android.Excludes.AL_LGPL
        }
    }
}

kapt {
    correctErrorTypes = Properties.correctErrorTypes
}

dependencies {
    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.lifecycle)
    implementation(Deps.AndroidX.appCompat)
    implementation(Deps.AndroidX.compose)
    implementation(Deps.AndroidX.composeFoundation)
    implementation(Deps.AndroidX.composeUtil)
    implementation(Deps.AndroidX.composePreview)
    implementation(Deps.AndroidX.composeActivity)
    implementation(Deps.AndroidX.composeMaterialIcon)
    implementation(Deps.AndroidX.composeMaterial3)
    implementation(Deps.AndroidX.composeNavigation)
    implementation(Deps.AndroidX.hiltNavigationCompose)
    implementation(Deps.AndroidX.splashScreen)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompilerKapt)

    implementation(Deps.Square.retrofit)
    implementation(Deps.Square.moshiConverter)
    implementation(Deps.Square.retrofitConverter)
    implementation(Deps.Square.okHttpInterceptor)
    implementation(Deps.Square.seismic)

    debugImplementation(Deps.Square.leakCanaryDebug)
    debugImplementation(Deps.Square.chuckerLibraryDebug)
    releaseImplementation(Deps.Square.chuckerLibraryRelease)

    implementation(Deps.Accompanist.accompanistPager)
    implementation(Deps.Accompanist.accompanistPagerIndicator)
    implementation(Deps.Accompanist.accompanistSystemUiController)
    implementation(Deps.Accompanist.accompanistFlowLayout)
    implementation(Deps.Accompanist.accompanistSwipeRefresh)
    implementation(Deps.Accompanist.accompanistInsetsUi)
    implementation(Deps.Accompanist.accompanistNavigationAnimation)

    implementation(platform(Deps.Firebase.bomPlatform))
    implementation(Deps.Firebase.crashlyticsKtx)
    implementation(Deps.Firebase.analyticsKtx)

    implementation(Deps.Other.mpAndroidChart)
    implementation(Deps.Other.xException)

    implementation(project(Project.common))
    implementation(project(Project.domain))
    implementation(project(Project.data))

    testImplementation(Deps.Test.jUnit)
    testImplementation(Deps.Test.jUnitExt)
    testImplementation(Deps.Test.espresso)
    testImplementation(Deps.Test.composeUiJUnit)
    debugImplementation(Deps.Test.composeUiJUnitTool)
}