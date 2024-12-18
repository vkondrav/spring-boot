plugins {
	kotlin("jvm") version "1.9.25"
	id("org.jetbrains.kotlin.plugin.spring") version "2.0.21"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	id("com.google.cloud.tools.jib") version "3.4.2"
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
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.cloud:spring-cloud-config-server")
	implementation("org.springframework.cloud:spring-cloud-starter-bus-amqp")
	implementation("org.springframework.cloud:spring-cloud-config-monitor")
	implementation("io.micrometer:micrometer-registry-prometheus:1.13.6")
	implementation("io.opentelemetry.javaagent:opentelemetry-javaagent:2.9.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
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
