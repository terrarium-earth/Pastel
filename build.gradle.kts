
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

    maven(url = "https://maven.theillusivec4.top/") {
        name = "Illusive Soulworks maven"
    }

    maven(url = "https://dl.cloudsmith.io/public/klikli-dev/mods/maven/") {
        content {
            includeGroup("com.klikli_dev")
        }
    }
}

cloche {
    minecraftVersion = "1.21.1"

    metadata {
        modId = "pastel"
    }

    singleTarget {
        neoforge {
            loaderVersion = "21.1.172"

            accessWideners.from("src/main/spectrum.accesswidener")

            mixins.from("src/main/mixins/spectrum.mixins.json")

            // TODO Remove these
            mixins.from(
                "src/main/mixins/arrowhead.mixins.json",
                "src/main/mixins/fractal.mixins.json",
                "src/main/mixins/revelationary.mixins.json",
            )

            dependencies {
                val curios = module(group = "top.theillusivec4.curios", name = "curios-neoforge", version = "9.5.1+1.21.1")

                compileOnly(curios.copy()) {
                    artifact {
                        classifier = "api"
                    }
                }

                runtimeOnly(curios)

                modImplementation(module(group = "com.klikli_dev", name = "modonomicon-1.21.1-neoforge", version = "1.114.1")) {
                    exclude(group = "com.klikli_dev")
                    exclude(group = "mezz.jei")
                }
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

