plugins {
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
	id("org.springframework.boot") version "3.5.0-M3"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.openapi.generator") version "5.1.1"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

openApiGenerate {
	generatorName.set("spring")
	inputSpec.set("$rootDir/src/main/resources/swagger.yaml")
	outputDir.set("$buildDir/generated/")
//	apiPackage.set("com.example.api")
//	modelPackage.set("com.example.model")
//	invokerPackage.set("com.example.invoker")
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	implementation("javax.validation:validation-api:2.0.0.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("javax.servlet:javax.servlet-api:4.0.1")
	implementation("io.springfox:springfox-swagger2:2.9.2")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")
	implementation("org.openapitools:jackson-databind-nullable:0.2.4")
//	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

configure<SourceSetContainer> {
	named("main") {
		java.srcDir("$buildDir/generated/src/main/java")
	}
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
	dependsOn("openApiGenerate")
	kotlinOptions.jvmTarget = "21"
}

tasks.withType<Test> {
	useJUnitPlatform()
}
