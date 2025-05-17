plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}
group = "ru.fridrock"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}

}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security:3.4.4")
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.4.4")
	implementation("org.postgresql:postgresql:42.7.5")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.0")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	compileOnly("org.projectlombok:lombok:1.18.30")
	annotationProcessor("org.projectlombok:lombok:1.18.30")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
}

dependencies {
	developmentOnly("org.springframework.boot:spring-boot-devtools")
}
tasks.withType<Test> {
	useJUnitPlatform()
}


tasks.withType<JavaCompile> {
	options.compilerArgs.addAll(listOf(
			"-Amapstruct.suppressGeneratorTimestamp=true",
			"-Amapstruct.suppressGeneratorVersionInfoComment=true",
			"-Amapstruct.defaultComponentModel=spring" // Optional
	))
}