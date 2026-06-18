package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;

public class PastelTodoUnusedLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        provider.add("advancements.pastel.craft_energy_collector.title", "Roger That");
        provider
            .add(
                "advancements.pastel.craft_energy_collector.description",
                "Construct a collector powerful enough to harvest energy from a geodes"
            );
        provider.add("advancements.pastel.place_energy_collector.title", "A new Form of Energy");
        provider
            .add(
                "advancements.pastel.place_energy_collector.description",
                "Place the collector in the center of a geode and watch it work"
            );
        provider.add("advancements.pastel.unused2.title", "Having a Blast");
        provider.add("advancements.pastel.meet_paintlich.title", "Marked by Time");
        provider.add("advancements.pastel.meet_paintlich.description", "Meet a Certain Someone");
        provider.add("advancements.pastel.defeat_paintlich.title", "Crossed Paintbrushes");
        provider
            .add(
                "advancements.pastel.defeat_paintlich.description",
                "Win a light-hearted discussion with an Aging Celebrity"
            );

        provider.add("book.pastel.guidebook.mysterious_locket.page4.title", "The Locket's Contents");
        provider
            .add(
                "book.pastel.guidebook.mysterious_locket.page4.text",
                "There also was a little note inside... who could it have belonged to?"
            );
        provider
            .add(
                "book.pastel.guidebook.mysterious_locket.page5.text",
                "Now that I understand the Dreiton language, it is easy for me to decipher the inscription on the " + "back:\\\n\\\n*Don't be silly, Asteras - the moon cannot shine, it can only reflect the light of the sun.*"
            );
        provider.add("book.pastel.guidebook.mysterious_locket.page5.title", "The unidentified Note");
    }
}
