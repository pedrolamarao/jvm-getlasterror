plugins {
    id("application")
}

dependencies {
    implementation("com.github.jnr:jnr-ffi:2.2.12")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}