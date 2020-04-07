import org.gradle.api.tasks.bundling.Jar
import org.springframework.boot.gradle.tasks.bundling.BootJar

apply(plugin = "kotlin-jpa")
apply(from = "${project.rootDir}/gradle/flyway.gradle")
val jar: Jar by tasks
jar.enabled = true

val bootJar: BootJar by tasks
bootJar.enabled = false

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-couchbase")
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.querydsl:querydsl-jpa:4.2.1")
    implementation("com.querydsl:querydsl-sql:4.2.1")
    kapt("com.querydsl:querydsl-apt:4.2.1:jpa")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("org.apache.commons:commons-lang3:3.9")
    runtimeOnly("mysql:mysql-connector-java")

    testRuntimeOnly("com.h2database:h2")

    // https://mvnrepository.com/artifact/au.com.console/kassava
    implementation("au.com.console:kassava:2.0.0")
}