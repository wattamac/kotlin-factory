import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "2.2.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("org.asciidoctor.convert") version "1.5.8"

	val kotlinPluginVersion = "1.3.71"
	kotlin("jvm") version "$kotlinPluginVersion"
	kotlin("plugin.spring") version "$kotlinPluginVersion"
	kotlin("plugin.jpa") version "$kotlinPluginVersion"
	kotlin("kapt") version "$kotlinPluginVersion"

	id("org.flywaydb.flyway") version "5.2.4"
}

group = "org.uhafactory"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val bootJar: BootJar by tasks
bootJar.enabled = false

repositories {
	mavenCentral()
}

subprojects {
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")

	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	dependencies {
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
		implementation("org.springframework.boot:spring-boot-starter-logging")

		testImplementation("org.springframework.boot:spring-boot-starter-test") {
			exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		}
		testImplementation("io.projectreactor:reactor-test")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	java.sourceCompatibility = JavaVersion.VERSION_1_8
	java.targetCompatibility = JavaVersion.VERSION_1_8

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "1.8"
		}
	}

	sourceSets {
		create("integrationTest") {
			withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
				kotlin.srcDir("src/integrationTest/kotlin")
				resources.srcDir("src/integrationTest/resources")
				compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
				runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
			}
		}
	}

	task<Test>("integrationTest") {
		description = "Runs the integration tests"
		group = "verification"
		testClassesDirs = sourceSets["integrationTest"].output.classesDirs
		classpath = sourceSets["integrationTest"].runtimeClasspath
		mustRunAfter(tasks["test"])
		useJUnitPlatform()
	}

	repositories {
		mavenCentral()
		maven { setUrl("https://repo.spring.io/plugins-release/") }
	}
}
