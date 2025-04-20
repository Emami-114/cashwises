import com.android.build.api.dsl.ManagedVirtualDevice
import org.gradle.kotlin.dsl.register
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.hotReload)
}


kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
            freeCompilerArgs.add("-Xexpect-actual-classes")
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
//            export("com.mohamedrejeb.calf:calf-ui:0.4.1")
        }
    }

    sourceSets {
        val desktopMain by getting

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
            implementation(libs.androidx.appcompat)
            implementation(libs.kotlinx.coroutines.android)
            implementation(compose.preview)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.material3AdaptiveNavigationSuite)
            // navigation
            implementation(libs.compose.navigation)
            implementation(libs.compose.navigation2)
            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.kotlinx.datetime)
            // MOKO
            // implementation(libs.moko.mvvm)
            // settings
            implementation(libs.multiplatformSettings)
            implementation(libs.multiplatformSettings.noArg)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:3.5.6"))
            // file picker
            implementation(libs.calf.filepicker)
            // rich text editor
            implementation(libs.rich.text)
            // Coil3
            implementation(libs.coil.compose)
            implementation(libs.coil)
            implementation(libs.coil.network.ktor)
            // material adaptive
            implementation(libs.compose.material3.adaptive.adaptive)
            implementation(libs.compose.material3.adaptive.layout)
            implementation(libs.compose.material3.adaptive.navigation)
            implementation(libs.compose.material3.windowsSize)
            implementation(project.dependencies.platform("org.kotlincrypto.macs:bom:0.7.0"))
            implementation("org.kotlincrypto.macs:hmac-sha2")
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(compose.desktop.common)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "org.emami.cashwises"
    compileSdk = 36

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/composeResources")

    defaultConfig {
        applicationId = "org.emami.cashwises"
        minSdk = 24
        targetSdk = 36
        //noinspection OldTargetApi
        versionCode = 1
        versionName = "1.0"
    }
    @Suppress("UnstableApiUsage")
    testOptions {
        managedDevices.devices {
            maybeCreate<ManagedVirtualDevice>("pixel5").apply {
                device = "Pixel 5"
                apiLevel = 34
                systemImageSource = "aosp"
            }
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
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
//            linux {
//                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
//            }
//            windows {
//                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
//            }
//            macOS {
//                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
//                bundleID = "org.company.app.desktopApp"
//            }
        }
    }
}

composeCompiler {
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
}
tasks.register<ComposeHotRun>("runHot") {
    mainClass.set("MainKt")
}

buildConfig {
    buildConfigField("String", "API_SECRET_KEY", "\"${project.findProperty("API_SECRET_KEY")}\"")
}
