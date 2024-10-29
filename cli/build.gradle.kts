plugins {
    java
    application
    id("io.freefair.lombok") version "6.6.1"
}

group = "ru.mxmztsv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // JAX-WS для SOAP
    implementation("javax.xml.ws:jaxws-api:2.3.1")
    implementation("com.sun.xml.ws:jaxws-rt:2.3.2")

    // Lombok для упрощенного создания моделей
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.0.1")

    // Для генерации клиента через wsimport
    implementation("com.sun.xml.ws:jaxws-tools:2.3.3")

    // JUnit для тестирования
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("com.example.Main") // Основной класс приложения
}

tasks.test {
    useJUnitPlatform()
}

val wsdlUrl = "http://localhost:9090/ClientService?wsdl"

val generateSoapClient by tasks.registering(JavaExec::class) {
    group = "code generation"
    description = "Generates SOAP client from WSDL"

    mainClass.set("com.sun.tools.ws.WsImport")
    args = listOf("-keep", "-s", "src/main/java", wsdlUrl)

    classpath = sourceSets["main"].compileClasspath

    standardOutput = System.out
    errorOutput = System.err
    isIgnoreExitValue = false
}

tasks.named("build") {
    dependsOn(generateSoapClient)
}

tasks.register("generateCodeSoapClient") {
    group="generateSoapClient"
    description="Generates Soap client from WSDL"
    dependsOn(generateSoapClient)
}
