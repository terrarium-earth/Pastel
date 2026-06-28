package earth.terrarium.pastel.data.recipe.builder.titration_barrel;

import net.minecraft.world.effect.MobEffect;

public class StaticFermentationStatusEffectEntryBuilder extends FermentationStatusEffectEntryBuilder<StaticFermentationStatusEffectEntryBuilder> {
    public StaticFermentationStatusEffectEntryBuilder(MobEffect statusEffect, int baseDuration) {
        super(statusEffect, baseDuration);
    }
}
