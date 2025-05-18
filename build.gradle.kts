
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
}

cloche {
    minecraftVersion = "1.21.1"

    metadata {
        modId = "pastel"
    }

    singleTarget {
        neoforge {
            loaderVersion = "21.1.172"

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

dependencies {
    val curios = create(group = "top.theillusivec4.curios", name = "curios-neoforge", version = "9.5.1+1.21.1")

    compileOnly(curios.copy()) {
        artifact {
            classifier = "api"
        }
    }

    runtimeOnly(curios)
}
