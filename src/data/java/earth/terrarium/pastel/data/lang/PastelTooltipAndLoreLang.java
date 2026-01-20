package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;

public class PastelTooltipAndLoreLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        provider.add("info.pastel.tooltip.adulterated.info", " [only you can see this]");
        provider.add("info.pastel.tooltip.adulterated.effect", "Tainted with %s");

        provider.add("pastel.tooltip.able_to_summon_warden", "Calls out to the Warden");
        provider.add("pastel.tooltip.biomemakeover_cursed", "Got cursed by a wicked altar");
        provider.add("pastel.tooltip.coming_soon", "Not available in Survival yet");
        provider.add("pastel.tooltip.dragon_and_wither_immune", "§7Ender Dragon and Wither proof");
        provider.add("pastel.tooltip.ink_cost", "§7 [%s %s]");
        provider.add("pastel.tooltip.ink_drain.tooltip.ink_for_next_step", "%d %s - %d until next improvement");
        provider.add("pastel.tooltip.ink_drain.tooltip.maxed_out", "§f⭐§r MAXED OUT §f⭐§r");
        provider.add("pastel.tooltip.ink_powered.bullet_amount", "◆ %d %s");
        provider.add("pastel.tooltip.ink_powered.consume", "Consumes %s");
        provider.add("pastel.tooltip.ink_powered.empty", "Empty");
        provider.add("pastel.tooltip.ink_powered.percent_filled", "%d Ink (%d %%)");
        provider.add("pastel.tooltip.ink_powered.prefix", "Consumes the following ink:");
        provider.add("pastel.tooltip.ink_powered.stored", "Stored:");
        provider.add("pastel.tooltip.ink_powered.unselect_color", "Unselect Color");
        provider.add("pastel.tooltip.crystal_armor_empowered","+I");
        provider.add("pastel.tooltip.press_shift_for_controls", "Press Shift to view controls");
        provider.add("pastel.tooltip.press_shift_for_more", "Press Shift to view additional stats");

        provider.add("lore.pastel.probably_sugar", "Sugar... probably?");
        provider.add("lore.pastel.time_travel_tap", "Worthy of a Time Traveller");
        provider.add("lore.pastel.evernectar", "...Evernectar?");

        provider.add("pastel.food.withChance", "%s [%d%%]");
        provider.add("pastel.food.whenEaten", "When Eaten:");
        provider.add("pastel.food.whenDrunk", "When Drunk:");

    }
}
