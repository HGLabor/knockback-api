plugins {
    id("io.papermc.paperweight.userdev") version "1.3.9-SNAPSHOT"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("java")
    id("java-library")
    id("maven-publish")
    id("signing")
}

group = "de.hglabor"
version = "1.19.0"

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.19-R0.1-SNAPSHOT")
}

java {
    withSourcesJar()
    withJavadocJar()
}

signing {
    sign(publishing.publications)
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    processResources {
        val props = mapOf(
            "version" to project.version
        )
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}

publishing {
    kotlin.runCatching {
        repositories {
            maven("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/") {
                name = "ossrh"
                credentials(PasswordCredentials::class) {
                    username = (property("ossrhUsername") ?: return@credentials) as String
                    password = (property("ossrhPassword") ?: return@credentials) as String
                }
            }
        }
    }.onFailure {
        println("Unable to add publishing repositories: ${it.message}")
    }
    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            artifact(tasks.jar.get().outputs.files.single())
            this.groupId = project.group.toString()
            this.artifactId = project.name.toLowerCase()
            this.version = project.version.toString()
            pom {
                name.set(project.name)
                description.set("A simple and easy to use api to configure the knockback behaivor of entities")
                developers {
                    developer {
                        name.set("mooziii")
                    }
                }
                licenses {
                    license {
                        name.set("GNU General Public License, Version 3")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
                    }
                }
                url.set("https://github.com/mooziii/alert")
                scm {
                    connection.set("scm:git:git://github.com/HGLabor/knockback-api.git")
                    url.set("https://github.com/HGLabor/knockback-api/tree/main")
                }
            }
        }
    }
}
