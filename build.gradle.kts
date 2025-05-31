
plugins {
    id("earth.terrarium.cloche") version "0.9.20"
}

sourceSets.main {
    resources.srcDir("src/main/generated")
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
        name = "Pastel"

        description = "Do flowers dream of the moon?"

        license = "lGPL3"

        url = "https://www.curseforge.com/minecraft/mc-mods/pastel"
        sources = "https://github.com/terrarium-earth/Pastel"
        issues = "https://github.com/terrarium-earth/Pastel/issues"

        icon = "assets/pastel/icon.png"

        dependency {
            modId = "modonomicon"
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
    }

    singleTarget {
        neoforge {
            loaderVersion = "21.1.172"

            accessWideners.from(
                "src/main/pastel.accessWidener",
                "src/main/pastel.data.accessWidener",
                "src/main/pastel.todo.accessWidener",
            )

            mixins.from(
                "src/main/mixins/pastel.mixins.json",
                "src/main/mixins/pastel.client.mixins.json",
            )

            // TODO Remove these
            mixins.from(
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
                implementation(jgrapht)
                implementation(jheaps)

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

                modCompileOnly(curios.copy()) {
                    artifact {
                        classifier = "api"
                    }
                }

                modRuntimeOnly(curios)
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

