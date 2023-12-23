plugins {
    id("java")
}

group = "edu.postech.csed332"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "edu.postech.csed332.homework1.GameUI"
    }
}

tasks.test {
    useJUnitPlatform()
}