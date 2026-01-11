plugins {
    id("java")
    kotlin("jvm") version "2.2.20"
    id("dev.sayaya.gwt") version "2.2.7"
    id("com.adarshr.test-logger") version "4.0.0"
    id("org.jetbrains.kotlinx.kover") version "0.9.2"
}

group = "dev.sayaya"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.jboss.elemento:elemento-core:2.3.2")
    implementation("dev.sayaya:ui:2.4.1.1")
    implementation("dev.sayaya:rx:2.2.1")
    implementation("com.google.dagger:dagger-gwt:2.57.2")
    annotationProcessor("com.google.dagger:dagger-compiler:2.57.2")
    implementation("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("com.google.dagger:dagger-compiler:2.57.2")
    testImplementation("dev.sayaya:gwt-test:2.2.7")
    testImplementation("io.kotest:kotest-runner-junit5:6.0.5")
    testImplementation("io.mockk:mockk:1.14.6")
}
tasks {
    gwt {
        gwtVersion = "2.12.2"
        sourceLevel = "auto"
        modules = listOf("dev.sayaya.Todo")
        war = file("src/main/webapp")
        devMode {
            modules = listOf(
                "dev.sayaya.Todo",
                "dev.sayaya.CryptoTest",
                "dev.sayaya.TodoListStoreTest",
                "dev.sayaya.TodoFilteredTest",
                "dev.sayaya.NewTodoInputElementTest",
                "dev.sayaya.TodoCardElementTest",
                "dev.sayaya.TodoCardListElementTest",
                "dev.sayaya.TodoToolbarElementTest",
            )
            war = file("src/test/webapp")
        }
        generateJsInteropExports = true
        compiler {
            strict = true
        }
    }
    test {
        useJUnitPlatform()
    }
}

