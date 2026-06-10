package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;
import earth.terrarium.pastel.registries.PastelDamageTypes;

public class PastelDeathMessageLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        provider.add("death.attack.ripping", "%1$s got ripped!");
        provider.add("death.attack.pastel_bristle_sprouts", "%1$s was pricked to death");
        provider.add("death.attack.pastel_deadly_poison", "%1$s succumbed to deadly poison");
        provider.add("death.attack.pastel_deadly_poison.item", "%1$s was deadly poisoned by %2$s using %3$s");
        provider.add("death.attack.pastel_deadly_poison.player", "%1$s was deadly poisoned by %2$s");
        provider.add("death.attack.pastel_decay", "%1$s was eaten alive");
        provider.add("death.attack.pastel_dike_gate", "%1$s was not invited, but understood too late");
        provider.add("death.attack.pastel_dragonrot", "%1$s came to nothing");
        provider.add("death.attack.pastel_dragonrot.item", "%2$s made %1$s come to nothing using %3$s");
        provider.add("death.attack.pastel_dragonrot.player", "%2$s made %1$s come to nothing");
        provider.add("death.attack.pastel_evisceration", "%1$s was skinned by a thousand cuts");
        provider.add(
            "death.attack.pastel_evisceration.item",
            "%2$s threw %1$s into the meat grinder (%3$s) . Only their eyeball came out ;^;"
        );
        provider.add("death.attack.pastel_evisceration.player", "%1$s was turned to a fine red mist by %2$s.");
        provider.add("death.attack.pastel_floatblock", "%1$s was smashed by a Floating Block");
        provider.add("death.attack.pastel_impaling", "%1$s was pierced clean through!");
        provider.add("death.attack.pastel_impaling.item", "%2$s impaled %1$s - Ouch!");
        provider.add("death.attack.pastel_impaling.player", "%1$s was made into a skewer by %2$s");
        provider.add("death.attack.pastel_impaling.projectile", "%1$s was made into a skewer by  %2$s");
        provider.add("death.attack.pastel_incandescence", "%1$s became grossly incandescent");
        provider.add(
            "death.attack.pastel_incandescence.item",
            "%1$s used %3$s to help %2$s become a star in bright vermilion - more kindling for the flame!"
        );
        provider.add(
            "death.attack.pastel_incandescence.player",
            "%2$s helped %1$s become a star in bright vermilion - more kindling for the flame!"
        );
        provider.add("death.attack.pastel_ink_projectile", "%2$s was taught %1$s the joy of painting");
        provider.add("death.attack.pastel_ink_projectile.item", "%2$s taught %1$s the joy of painting using %3$s");
        provider.add("death.attack.pastel_ink_projectile.player", "%2$s taught %1$s the joy of painting");
        provider.add("death.attack.pastel_irradiance", "%1$s was skewered by solid light");
        provider.add("death.attack.pastel_irradiance.player", "%2$s turned %1$s into a shimmering pincushion");
        provider.add("death.attack.pastel_irradiance.projectile", "%2$s turned %1$s into a shimmering pincushion");
        provider.add("death.attack.pastel_kindling_cough", "%1$s got grilled by %2$s");
        provider.add("death.attack.pastel_midnight_solution", "%1$s was lost in Translation");
        provider.add(
            "death.attack.pastel_midnight_solution.player", "%1$s was lost in Translation being chased by %2$s");
        provider.add("death.attack.pastel_moonstone_blast", "%1$s was disintegrated");
        provider.add("death.attack.pastel_moonstone_blast.item", "%1$s was disintegrated by %2$s using %3$s");
        provider.add("death.attack.pastel_moonstone_blast.player", "%1$s was disintegrated by %2$s");
        provider.add("death.attack.pastel_moonstone_strike", "%1$s had a blast. Literally.");
        provider.add("death.attack.pastel_moonstone_strike.item", "%2$s used %3$s to give %1$s a blast. Literally.");
        provider.add("death.attack.pastel_moonstone_strike.player", "%2$s made %1$s have a blast. Literally.");
        provider.add("death.attack.pastel_primordial_fire", "%1$s had their soul turned to smouldering soot");
        provider.add("death.attack.pastel_primordial_fire.item", "%2$s used %3$s to rend %1$s's spirit to ash");
        provider.add("death.attack.pastel_primordial_fire.player", "%2$s rended %1$s's spirit to ash");
        provider.add("death.attack.pastel_ripper", "%1$s wanted to play a game");
        provider.add("death.attack.pastel_set_health", "%1$s was sent to the shadow realm");
        provider.add("death.attack.pastel_set_health.item", "%1$s used %3$s to send %2$s to the shadow realm");
        provider.add("death.attack.pastel_set_health.player", "%1$s was sent to the shadow realm by %2$s");
        provider.add("death.attack.pastel_shooting_star", "%1$s was smitten by a Shooting Star. Make a Wish!");
        provider.add("death.attack.pastel_sleep", "%1$s was lost to a bottomless dream");
        provider.add(
            "death.attack.pastel_sleep.item",
            "%2$s used %3$s to send %1$s on, unto the bottomless sea, accepting of all that is and could be."
        );
        provider.add(
            "death.attack.pastel_sleep.player",
            "%2$s sent %1$s on, unto the bottomless sea, accepting of all that is and could be."
        );
        provider.add("death.attack.pastel_dark_stake", "%1$s was unable to ward off death.");
        provider.add("death.attack.pastel_dark_stake.item", "%2$s used %3$s to disrupt %1$s's hold on life.");
        provider.add("death.attack.pastel_dark_stake.player", "%1$s thought they could escape %2$s's knives.");
        provider.add("death.attack.pastel_electric", "%1$s couldn't handle the wattage.");
        provider.add("death.attack.pastel_electric.item", "%2$s orders in perfect %3$s, shocks %1$s!");
        provider.add("death.attack.pastel_electric.player", "%1$s found %2$s quite shocking.");
        provider.add("death.attack.pastel_snapping_ivy", "%1$s was held back a bit");
        provider.add("death.attack.pastel_whip", "%1$s succumbed to their lacerations.");
        provider.add("death.attack.pastel_whip.item", "%2$s used %3$s to show %1$s that power is meaningless without purpose!");
        provider.add("death.attack.pastel_whip.player", "%1$s couldn't handle the depths of %2$s's devotion.");
    }
}
