
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
