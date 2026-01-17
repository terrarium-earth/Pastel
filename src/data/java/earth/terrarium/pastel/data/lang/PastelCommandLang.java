package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;

public class PastelCommandLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        provider.add("commands.pastel.primordial_fire.put_on.success", "Set %d entities on fire");
        provider.add("commands.pastel.primordial_fire.put_out.success", "Extinguished %d entities");
        provider.add(
            "commands.pastel.progression_sanity.success",
            "Printed the sanity checks findings to the servers " + "console"
        );
        provider.add("commands.pastel.spawn_shooting_star.success", "Spawned %d shooting stars for each player");
    }
}
