import groovy.json.StringEscapeUtils

plugins {
	id("maven-publish")
    id("earth.terrarium.cloche") version "0.11.10"
	id("com.teamresourceful.resourcefulgradle") version "0.0.+"
}

sourceSets.main {
    // TODO Delete once everything in oldGenerated is properly data-genned
    resources.srcDir("src/main/oldGenerated")
}

repositories {
    cloche.librariesMinecraft()

    mavenCentral()
	mavenLocal()

    cloche {
        mavenNeoforgedMeta()
        mavenNeoforged()
    }

    maven(url = "https://maven.resourcefulbees.com/repository/terrarium/")

    maven(url = "https://maven.shedaniel.me/") // Cloth config, REI

    maven(url = "https://api.modrinth.com/maven") // Additional Entity Attributes, ExclusionsLib, Databank (temporary)

    maven(url = "https://maven.terraformersmc.com/") // EMI

    // Curios
    maven(url = "https://maven.theillusivec4.top/") {
        name = "Illusive Soulworks maven"
    }

    // modonomicon
    maven(url = "https://dl.cloudsmith.io/public/klikli-dev/mods/maven/") {
        content {
            includeGroup("com.klikli_dev")
        }
    }

    maven(url = "https://repo.unascribed.com") // Ears API

	maven(url = "https://cursemaven.com") // xycraft
}

cloche {
    minecraftVersion = project.properties["minecraftVersion"] as String

    metadata {
        modId = "pastel"
        name = "Pastel"
		version = System.getenv("VERSION") ?: "1.1.5.2"

        description = "Do flowers dream of the moon?"

        license = "GNU LGPL v3 for code, ARR for assets"

        url = "https://www.curseforge.com/minecraft/mc-mods/pastel"
        sources = "https://github.com/terrarium-earth/Pastel"
        issues = "https://github.com/terrarium-earth/Pastel/issues"

        icon = "assets/pastel/icon.png"

        dependency {
            modId = "modonomicon"
            required = true
        }

        dependency {
            modId = "cloth_config"
            required = true
        }

        dependency {
            modId = "curios"
            required = true
        }

        dependency {
            modId = "databank"
            required = true
        }

        dependency {
            modId = "exclusions_lib"
            required = true
        }

        author("Azzyypaaras", "azzy@terrarium.earth")
        author("Dafuqs")

        contributor("Salad Cat (OST)")
        contributor("MsRandom", "ashley@terrarium.earth")
        contributor("Robotgiggle")
        contributor("pizzacalz0ne")
        contributor("Lily")
        contributor("HoneyHive (Illustrations)")
        contributor("Electro_593")
        contributor("800020h")
        contributor("Quarx_")
        contributor("Athebyne")
        contributor("Noaaaaaaaaan")
        contributor("Khloe Leclair")
    }

    singleTarget {
        @Suppress("UnstableApiUsage")
        neoforge {
            loaderVersion = "21.1.218"

            datagenDirectory = file("src/main/generated")

            accessWideners.from(
                "src/main/pastel.accessWidener",
                "src/main/pastel.data.accessWidener",
                "src/main/pastel.todo.accessWidener",
            )

            mixins.from(
                "src/main/mixins/pastel.mixins.json",
                "src/main/mixins/pastel.client.mixins.json",
            )

            val additionalEntityAttributes = module(group = "maven.modrinth", name = "additionalentityattributes", version = "2.0.0+1.21.1-neoforge")
            val jgrapht = module(group = "org.jgrapht", name = "jgrapht-core", version = "1.5.2")
            val jheaps = module(group = "org.jheaps", name = "jheaps", version = "0.14")

            include(additionalEntityAttributes)
            include(jgrapht)
            include(jheaps)

            include(module(group = "org.apfloat", name = "apfloat", version = "1.10.1"))

            dependencies {
                modApi(module(group = "me.shedaniel.cloth", name = "cloth-config-neoforge", version = "15.0.140"))

                modImplementation(module(group = "com.klikli_dev", name = "modonomicon-1.21.1-neoforge", version = "1.114.1")) {
                    exclude(group = "com.klikli_dev")
                    exclude(group = "mezz.jei")
                }

                modCompileOnly(module(group = "me.shedaniel", name = "RoughlyEnoughItems-neoforge", version = "16.0.788"))

                modImplementation("maven.modrinth:databank:1.2.2")
                modImplementation("maven.modrinth:exclusions-lib:1.1.0-NEO")
                modImplementation(additionalEntityAttributes)
                compileOnly(jgrapht)
                implementation(jheaps)

				modImplementation("dev.emi:emi-neoforge:1.1.19+1.21.1")

                modCompileOnly("maven.modrinth:colorful-hearts:10.3.8") { isTransitive = false }
                modCompileOnly("maven.modrinth:sodium:mc1.21.1-0.6.5-neoforge") { isTransitive = false }
                modCompileOnly("com.unascribed:ears-api:1.4.6")
                modCompileOnly("maven.modrinth:botania:1.20.1-448-forge")
				modCompileOnly("maven.modrinth:vanity:xWfEA0yC") // compile only cuz accessories

                modImplementation("maven.modrinth:create:1.21.1-6.0.4") { isTransitive = false }
                modImplementation("maven.modrinth:lodestonelib:1.7.1") { isTransitive = false }
                modImplementation("maven.modrinth:malum:1.7.3.1") { isTransitive = false }
                modImplementation("maven.modrinth:travelersbackpack:1.21.1-10.1.20")
				modRuntimeOnly("maven.modrinth:ae2:19.2.12") { isTransitive = false }
				modRuntimeOnly("maven.modrinth:guideme:21.1.13") { isTransitive = false }
				modRuntimeOnly("curse.maven:xycraft-653786:5601037") { isTransitive = false }
				modRuntimeOnly("curse.maven:xycraft-machines-653791:5601045") { isTransitive = false }
				modRuntimeOnly("curse.maven:xycraft-world-653789:5601038") { isTransitive = false }
                //modRuntimeOnly("maven.modrinth:mmmmmmmmmmmm:neoforge_1.21-2.0.8")  { isTransitive = false }
                //modRuntimeOnly("maven.modrinth:moonlight:1.21-2.20.3-neoforge")  { isTransitive = false }
				modRuntimeOnly("maven.modrinth:jei:zRGLFYRx") // cuz xycraft >:(
            }

            dependencies {
                val curios = module(group = "top.theillusivec4.curios", name = "curios-neoforge", version = "9.5.1+1.21.1")

                modCompileOnly(curios.copy()) {
                    artifact {
                        classifier = "api"
                    }
                }

                modRuntimeOnly(curios)
            }

            data()

            runs {
                server()
                client()

                data()
            }
        }
    }

    mappings {
        official()
        parchment("2024.11.17")
    }
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			val modId = cloche.metadata.modId.get()
			from(components["java"])

			pom {
				name.set("Pastel")
				url.set("https://github.com/terrarium-earth/$modId")

				scm {
					connection.set("git:https://github.com/terrarium-earth/$modId.git")
					developerConnection.set("git:https://github.com/terrarium-earth/$modId.git")
					url.set("https://github.com/terrarium-earth/$modId")
				}

				licenses {
					license {
						name.set("LGPL-3.0")
					}
				}
			}
		}
	}
	repositories {
		maven {
			setUrl("https://maven.resourcefulbees.com/repository/terrarium/")
			credentials {
				username = System.getenv("MAVEN_USER")
				password = System.getenv("MAVEN_PASS")
			}
		}
	}
}


resourcefulGradle {
	templates {
		register("embed") {

			source.set(file("templates/embed.json.template"))
			injectedValues.set(mapOf(
				"minecraft" to "1.21.1", // TODO: ask Ash (awawawa) how to fix this
				"version" to System.getenv("VERSION"),
				"changelog" to StringEscapeUtils.escapeJava(System.getenv("CHANGELOG")),
                "version" to System.getenv("VERSION"),
                "modrinth_link" to System.getenv("MODRINTH_RELEASE_URL"),
                "curseforge_link" to System.getenv("CURSEFORGE_RELEASE_URL"),
			))
		}
	}
}
