import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    kotlin("plugin.serialization") version "2.1.0"
//    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {

    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ktor-plugin"
            isStatic = true
        }
    }
    linuxX64()
    jvm()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            // 添加 Ktor 客户端核心依赖
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.utils)
//            implementation(libs.ktor.client.cio)
            // 添加日志支持
            implementation(libs.ktor.client.logging)
            // 添加序列化支持，这里以 Kotlinx Serialization 为例
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.content.negotiation)
            // 添加 Kotlinx Serialization JSON 格式的依赖
            implementation(libs.jetbrains.kotlinx.serialization.json)
            // 添加 Kotlinx Coroutines 的依赖
            implementation(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
        }
        jvmMain.dependencies {
            // 添加 JVM 平台的引擎依赖，这里使用 CIO 引擎

        }
        jsMain.dependencies {
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }

}

android {
    namespace = "hos.ktor.plugin"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

group = "io.github.cjfsss"
version = "1.0.0"
mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
//    publishToMavenCentral("DEFAULT", true)
    signAllPublications()
    coordinates("io.github.cjfsss", "hos-ktor-plugin", "1.0.0")
    pom {
        name = "Kotlinx datetime Plus"
        description =
            "A KMP library that provides extensions and helper functions for hos-coordinates."
        inceptionYear = "2025"
        url = "https://github.com/cjfsss/KMPCode/"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "cjfsss"
                name = "cjfsss"
                url = "https://github.com/cjfsss"
            }
        }
        scm {
            url = "https://github.com/cjfsss/KMPCode/"
            connection = "scm:git:git://github.com/cjfsss/KMPCode.git"
            developerConnection =
                "scm:git:ssh://git@github.com/cjfsss/KMPCode.git"
        }
    }
}
