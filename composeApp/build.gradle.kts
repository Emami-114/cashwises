import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildConfig)

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "${JavaVersion.VERSION_1_8}"
                freeCompilerArgs += "-Xjdk-release=${JavaVersion.VERSION_1_8}"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }

        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
            implementation(libs.androidx.appcompat)
            implementation(libs.kotlinx.coroutines.android)
        }
        iosMain.dependencies {
//            implementation(libs.ktor.client.darwin)
            implementation("io.ktor:ktor-client-darwin:2.3.7")


        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.voyager.navigation)
            implementation(libs.voyager.tab)
            implementation(libs.voyager.transition)
            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.ktor.client.serialization)
//            implementation(libs.ktor.client.cio)
            implementation(libs.kotlinx.datetime)
            implementation(libs.composeIcons.featherIcons)

            // MOKO
            implementation(libs.moko.mvvm)
            // settings
            implementation(libs.multiplatformSettings)
            implementation(libs.multiplatformSettings.noArg)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            // file picker
            implementation(libs.calf.filepicker)
            // image laoder
            api(libs.image.loader)
            // rich text editor
            implementation(libs.rich.text)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(compose.desktop.common)
            implementation(libs.ktor.client.okhttp)
//            api(libs.image.loader)

        }
    }
}

android {
    namespace = "org.emami.cashwises"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.emami.cashwises"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
dependencies {
    implementation(libs.androidx.media3.common)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.emami.cashwises"
            packageVersion = "1.0.0"
        }
    }
}
