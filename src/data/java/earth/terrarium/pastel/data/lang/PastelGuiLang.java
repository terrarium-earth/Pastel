package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;

public class PastelGuiLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        provider.add("gui.pastel.button.back", "Back");
        provider.add("gui.pastel.button.collisions", "Collisions");
        provider.add("gui.pastel.button.forward", "Forward");
        provider.add("gui.pastel.button.glowing", "Glowing");
        provider.add("gui.pastel.button.particles", "Particles");
        provider.add("gui.pastel.quick_navigation.controls1", "Movement Keys to move");
        provider.add("gui.pastel.quick_navigation.controls2", "Drop/Inventory to select");
        provider.add("message.pastel.needs_item_to_harvest","Harvesting requires a ");
    }
}
