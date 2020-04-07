apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")

val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks
bootJar.enabled = false