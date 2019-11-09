plugins {
    java
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClassName = "com.example.wordcounter.Main"
}

dependencies {
    testCompile("junit", "junit", "4.12")
    testCompile("com.google.guava", "guava", "28.1-jre")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
