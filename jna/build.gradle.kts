plugins {
    id("application")
}

application.mainClass.set("jna.WinApi")

dependencies {
    implementation("net.java.dev.jna:jna:5.12.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}