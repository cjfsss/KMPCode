import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
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
            baseName = "datetime"
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
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

}

android {
    namespace = "hos.datetime"
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
version = "1.0.2"
mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
//    publishToMavenCentral("DEFAULT", true)
    signAllPublications()
    coordinates("io.github.cjfsss", "hos-datetime", "1.0.2")
    pom {
        name = "Kotlinx datetime Plus"
        description =
            "A KMP library that provides extensions and helper functions for kotlinx-datetime."
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
