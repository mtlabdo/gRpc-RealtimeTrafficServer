import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*;
import com.google.protobuf.gradle.protoc

plugins {
    kotlin("jvm") version "1.4.10"
    id("com.google.protobuf") version "0.8.13"
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    google()
}

dependencies {
    testImplementation(kotlin("test-junit"))

    implementation("io.grpc:grpc-protobuf:1.33.1")
    implementation("io.grpc:grpc-stub:1.33.1")
    implementation("io.grpc:grpc-netty:1.33.1")
    api("com.google.protobuf:protobuf-java-util:3.13.0")
    implementation("io.grpc:grpc-all:1.33.1")
    api("io.grpc:grpc-kotlin-stub:0.2.1")
    implementation("io.grpc:protoc-gen-grpc-kotlin:0.1.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
    implementation("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
    implementation("com.google.protobuf:protoc:3.25.3")


    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


}
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.1"
    }
    generatedFilesBaseDir = "$projectDir/src/main/kotlin/com.realTimeTraffic/generated"


    plugins {
        id("grpc"){
            artifact = "io.grpc:protoc-gen-grpc-java:1.33.1"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:0.1.5"
        }

    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}


tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
