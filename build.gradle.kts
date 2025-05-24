
plugins {
    id("earth.terrarium.cloche") version "0.9.13"
}

repositories {
    cloche.librariesMinecraft()

    mavenCentral()

    cloche {
        mavenNeoforgedMeta()
        mavenNeoforged()
    }

    maven(url = "https://maven.shedaniel.me/") // Cloth config, REI

    maven(url = "https://api.modrinth.com/maven") // Additional Entity Attributes, Jade

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

    maven(url = "https://maven2.bai.lol") // wthit
}

cloche {
    minecraftVersion = "1.21.1"

    metadata {
        modId = "pastel"
    }

    singleTarget {
        neoforge {
            loaderVersion = "21.1.172"

            accessWideners.from(
                "src/main/spectrum.accessWidener",
                "src/main/spectrum.data.accessWidener",
            )

            mixins.from("src/main/mixins/spectrum.mixins.json")

            // TODO Remove these
            mixins.from(
                "src/main/mixins/arrowhead.mixins.json",
                "src/main/mixins/fractal.mixins.json",
                "src/main/mixins/reverb.mixins.json",
                "src/main/mixins/revelationary.mixins.json",
            )

            val additionalEntityAttributes = module(group = "maven.modrinth", name = "additionalentityattributes", version = "2.0.0+1.21.1-neoforge")
            val jgrapht = module(group = "org.jgrapht", name = "jgrapht-core", version = "1.5.2")
            val jheaps = module(group = "org.jheaps", name = "jheaps", version = "0.14")

            include(additionalEntityAttributes)
            include(jgrapht)
            include(jheaps)

            dependencies {
                compileOnly(module(group = "maven.modrinth", name = "jade", version = "15.10.0+neoforge"))

                modApi(module(group = "me.shedaniel.cloth", name = "cloth-config-neoforge", version = "15.0.140"))

                modImplementation(module(group = "com.klikli_dev", name = "modonomicon-1.21.1-neoforge", version = "1.114.1")) {
                    exclude(group = "com.klikli_dev")
                    exclude(group = "mezz.jei")
                }

                modCompileOnly(module(group = "me.shedaniel", name = "RoughlyEnoughItems-neoforge", version = "16.0.788"))

                modImplementation(additionalEntityAttributes)
                modImplementation(jgrapht)
                modImplementation(jheaps)

                modCompileOnly(module(group = "mcp.mobius.waila", name = "wthit-api", version = "neo-12.4.1"))

                modCompileOnly("dev.emi:emi-neoforge:1.1.19+1.21.1")

                modCompileOnly("maven.modrinth:colorful-hearts:10.3.8") { isTransitive = false }
                modCompileOnly("maven.modrinth:sodium:mc1.21.1-0.6.5-neoforge") { isTransitive = false }
                modCompileOnly("com.unascribed:ears-api:1.4.6")
                modCompileOnly("maven.modrinth:create:1.21.1-6.0.4") { isTransitive = false }
                modCompileOnly("maven.modrinth:lodestonelib:1.7.0") { isTransitive = false }
                modCompileOnly("maven.modrinth:malum:1.20.1-1.6.5") { isTransitive = false }
                modCompileOnly("maven.modrinth:travelersbackpack:1.21.1-10.1.20")
                modCompileOnly("maven.modrinth:botania:1.20.1-448-forge")
                modCompileOnly("maven.modrinth:vanity:xWfEA0yC")


            }

            dependencies {
                val curios = module(group = "top.theillusivec4.curios", name = "curios-neoforge", version = "9.5.1+1.21.1")

                compileOnly(curios.copy()) {
                    artifact {
                        classifier = "api"
                    }
                }

                runtimeOnly(curios)
            }

            data()
            test()

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

