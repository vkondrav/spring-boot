import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.cloud.tools.jib") version "3.4.2"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
    id("org.openapi.generator") version "7.8.0"
}

group = "com.micro"
version = "v1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")


    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

sourceSets {
    main {
        java {
            srcDir("${buildDir}/generated/src/main/java")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jib {
    from {
        image = "eclipse-temurin:17"
    }
    to {
        image = "vkondrav/${project.name}:${project.version}"
    }
}

val cleanGenerated by tasks.registering(Delete::class) {
    delete("$buildDir/generated")
}

val generateCardsApi by tasks.registering(GenerateTask::class) {
    generatorName.set("spring")
    library.set("spring-cloud")
    inputSpec.set("$projectDir/../cards/openapi/openapi-schema.json")
    outputDir.set("$buildDir/generated")
    apiPackage.set("com.micro.cards.api")
    invokerPackage.set("com.micro.cards.invoker")
    modelPackage.set("com.micro.cards.model")

    configOptions.putAll(
        mapOf(
            "useFeignClientUrl" to false,
            "useSpringBoot3" to true,
            "useTags" to true,
            "openApiNullable" to false
        ).mapValues { it.value.toString() }
    )
}

val generateLoansApi by tasks.registering(GenerateTask::class) {

    generatorName = "spring"
    library = "spring-cloud"
    inputSpec = "$projectDir/../loans/openapi/openapi-schema.json"
    outputDir = "$buildDir/generated"
    apiPackage = "com.micro.loans.api"
    invokerPackage = "com.micro.loans.invoker"
    modelPackage = "com.micro.loans.model"

    configOptions.putAll(
        mapOf(
            "useFeignClientUrl" to false,
            "useSpringBoot3" to true,
            "useTags" to true,
            "openApiNullable" to false
        ).mapValues { it.value.toString() }
    )
}

val generateAccountsApi by tasks.registering(GenerateTask::class) {

    generatorName = "spring"
    library = "spring-cloud"
    inputSpec = "$projectDir/../accounts/openapi/openapi-schema.json"
    outputDir = "$buildDir/generated"
    apiPackage = "com.micro.accounts.api"
    invokerPackage = "com.micro.accounts.invoker"
    modelPackage = "com.micro.accounts.model"

    configOptions.putAll(
        mapOf(
            "useFeignClientUrl" to false,
            "useSpringBoot3" to true,
            "useTags" to true,
            "openApiNullable" to false
        ).mapValues { it.value.toString() }
    )
}

val generateApi by tasks.registering {
    dependsOn(
        cleanGenerated,
        generateCardsApi,
        generateLoansApi,
        generateAccountsApi
    )
}

tasks.named("compileKotlin") {
    dependsOn(generateApi)
}
