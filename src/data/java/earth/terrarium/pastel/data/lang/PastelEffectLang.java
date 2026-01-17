package earth.terrarium.pastel.data.lang;

import earth.terrarium.pastel.data.PastelLanguageProvider;
import earth.terrarium.pastel.registries.PastelMobEffects;

public class PastelEffectLang {
    public static void addTranslations(PastelLanguageProvider provider) {
        for (var effect : PastelMobEffects.REGISTER.getEntries()) {
            var prettifiedName = PastelLanguageProvider.prettifyRegisteredName(effect.getRegisteredName().substring(7));
            provider.addEffect(effect, prettifiedName);
        }
        provider.add(
            "effect.pastel.another_roll.desc",
            "Influences the drops of Fortune, Looting, and other luck-based enchantments."
        );
        provider.add(
            "effect.pastel.ascension.desc",
            "The call of heaven, or something like that. A glorious, gathering of forces before Divinity kicks in."
        );
        provider.add("effect.pastel.calming.desc", "Reduces enemy spawn rates and aggression.");
        provider.add("effect.pastel.deadly_poison.desc", "Like Poison, but lethal.");
        provider.add(
            "effect.pastel.density.desc",
            "Makes you heavier, preventing you from making long jumps and increasing fall damage taken."
        );
        provider.add("effect.pastel.divinity.desc", "Grants massive buffs... at great cost, should you die.");
        provider.add(
            "effect.pastel.effect_prolonging.desc", "Increases the duration of other status effects applied to you.");
        provider.add(
            "effect.pastel.eternal_slumber.desc",
            "Locks the target into a deep, dreamless sleep, from which few can escape."
        );
        provider.add("effect.pastel.eternal_slumber.duration", "everlasting | %1$s");
        provider.add("effect.pastel.eternal_slumber.duration_inf", "everlasting");
        provider.add("effect.pastel.fatal_slumber.desc", "Induces instant death upon timing out.");
        provider.add(
            "effect.pastel.frenzy.desc",
            "Buffs damage, attack speed, movement speed, and knockback resistance with each kill. Not scoring a kill " +
            "in 10 seconds will debuff you instead."
        );
        provider.add(
            "effect.pastel.immunity.desc",
            "Removes all negative status effects, and prevents you from getting new ones."
        );
        provider.add(
            "effect.pastel.lava_gliding.desc",
            "Increases your movement speed and visibility in Lava. However, does not provide Fire Resistance."
        );
        provider.add("effect.pastel.life_drain.desc", "Decreases your maximum health.");
        provider.add(
            "effect.pastel.lightweight.desc",
            "Makes you lighter, increasing jump height, slowing falls, and at high levels granting immunity to fall " +
            "damage."
        );
        provider.add("effect.pastel.magic_annulation.desc", "Reduces incoming magic damage by 1 point per level.");
        provider.add(
            "effect.pastel.projectile_rebound.desc", "Grants a small chance to defend against incoming projectiles.");
        provider.add(
            "effect.pastel.scarred.desc", "Stops natural health regeneration and prevents you from sprinting.");
        provider.add("effect.pastel.somnolence.desc", "Causes intense fatigue, but allows sleeping through the day.");
        provider.add("effect.pastel.stiffness.desc", "Decreases your attack speed.");
        provider.add("effect.pastel.swiftness.desc", "Increases your attack speed.");
        provider.add("effect.pastel.toughness.desc", "Weakens incoming powerful attacks.");
        provider.add("effect.pastel.vulnerability.desc", "Increases damage taken.");
        provider.add("effect.pastel.nourishing.desc", "Slowly refills food bar.");
    }
}
