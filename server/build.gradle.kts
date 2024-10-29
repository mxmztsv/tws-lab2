plugins {
    id("java")
    id("war")
    id("org.gretty") version "4.0.3"
}

group = "ru.mxmztsv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.3") // API для JAXB
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.3") // Реализация JAXB

    implementation("com.sun.xml.ws:jaxws-rt:3.0.0") // JAX-WS Runtime
    implementation("jakarta.jws:jakarta.jws-api:3.0.0") // JAX-WS API
    implementation("jakarta.xml.ws:jakarta.xml.ws-api:3.0.0") // JAX-WS API

    implementation("jakarta.platform:jakarta.jakartaee-api:9.1.0") // Jakarta EE API
    implementation("javax.annotation:javax.annotation-api:1.3.2") // Java EE Annotations
    implementation("jakarta.servlet:jakarta.servlet-api:4.0.4") // Servlet API

    implementation("org.apache.tomcat:tomcat-jdbc:10.0.0") // JDBC для Tomcat

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    implementation("org.flywaydb:flyway-core:10.19.0")
    implementation("org.flywaydb:flyway-database-postgresql:10.19.0")
    implementation("org.postgresql:postgresql:42.7.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<War>("war") {
    archiveFileName.set("Clients-service.war") // Устанавливаем имя WAR-файла
    archiveBaseName.set("Clients-service")
}

gretty {
    contextPath = "/" // Указываем контекст для развертывания
    servletContainer = "tomcat10" // Используем Tomcat 10 для деплоя
    httpPort = 9091 // Порт, на котором будет доступен сервер
    jvmArgs = listOf("--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED")
    enableNaming = true
}
