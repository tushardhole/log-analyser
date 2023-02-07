import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.arrow-kt:arrow-core:1.1.5")

    testImplementation("io.kotest:kotest-assertions-core:5.5.4")
    testImplementation("io.kotest.extensions:kotest-assertions-arrow:1.2.5")
    testImplementation(kotlin("test"))
}


application {
    mainClass.set("app.Runner")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "app.Runner"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    doLast {
        copy {
            from("build/libs/app.jar")
            into("release")
        }
    }
    archiveName = "app.jar"
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}