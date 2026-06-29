package earth.terrarium.pastel.data.recipe.builder.titration_barrel;

import earth.terrarium.pastel.recipe.titration_barrel.FermentationStatusEffectEntry;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class FermentationStatusEffectEntryBuilder<C extends FermentationStatusEffectEntryBuilder<C>> {
    private final Holder<MobEffect> statusEffect;
    private final int baseDuration;
    private final List<FermentationStatusEffectEntry.StatusEffectPotencyEntry> potencyEntries = new ArrayList<>();

    public FermentationStatusEffectEntryBuilder(
            Holder<MobEffect> statusEffect,
            int baseDuration
    ) {
        this.statusEffect = statusEffect;
        this.baseDuration = baseDuration;
    }

    public static final class Standalone extends FermentationStatusEffectEntryBuilder<Standalone> {

        public Standalone(Holder<MobEffect> statusEffect, int baseDuration) {
            super(statusEffect, baseDuration);
        }
    }

    public static Standalone of(Holder<MobEffect> statusEffect, int baseDuration) {
        return new Standalone(statusEffect, baseDuration);
    }

    public C simplePotencyEntry(int potency) {
        new FermentationStatusEffectPotencyEntryBuilder(potency).submit();
        return (C)this;
   }

    public FermentationStatusEffectEntryBuilder<C>.FermentationStatusEffectPotencyEntryBuilder potencyEntry(int potency) {
        return new FermentationStatusEffectEntryBuilder<C>.FermentationStatusEffectPotencyEntryBuilder(potency);
    }

    public C scaleOnAlc(int from, int to, float start, float perLevel) {
        float p = start;
        for (var x = from; x <= to; x++, p += perLevel) {
            potencyAlc(x, p);
        }
        return (C)this;
    }

    public C scaleOnAlc(int maxLevel, float start, float perLevel) {
        return scaleOnAlc(0, maxLevel, start, perLevel);
    }

    public C scaleOnThickness(int from, int to, float start, float perLevel) {
        float p = start;
        for (var x = from; x <= to; x++, p += perLevel) {
            potencyThickness(x, p);
        }
        return (C)this;
    }

    public C scaleOnThickness(int maxLevel, float start, float perLevel) {
        return scaleOnThickness(0, maxLevel, start, perLevel);
    }

    public C potencyAlc(int potency, float minAlc) {
        new FermentationStatusEffectPotencyEntryBuilder(potency).minAlc(minAlc).submit();
        return (C)this;
    }

    public C potencyThickness(int potency, float minThickness) {
        new FermentationStatusEffectPotencyEntryBuilder(potency).minThickness(minThickness).submit();
        return (C)this;
    }

    public C potencyFull(int potency, float minAlc, float minThickness) {
        this.potencyEntries.add(new FermentationStatusEffectEntry.StatusEffectPotencyEntry(minAlc, minThickness, potency));
        return (C)this;
    }

    public FermentationStatusEffectEntry build() {
        return new FermentationStatusEffectEntry(statusEffect, baseDuration, potencyEntries);
    }

    public final class FermentationStatusEffectPotencyEntryBuilder {
        private float minAlc = 0.0f;
        private float minThickness = 0.0f;
        private final int potency;

        public FermentationStatusEffectPotencyEntryBuilder(int potency) {
            this.potency = potency;
        }

        public FermentationStatusEffectPotencyEntryBuilder minAlc(float value) {
            this.minAlc = value;
            return this;
        }

        public FermentationStatusEffectPotencyEntryBuilder minThickness(float value) {
            this.minThickness = value;
            return this;
        }


        public C scaledUntil(int maxLevels, float perLevelAlc, float perLevelThickness) {
            float alc = this.minAlc;
            float thickness = this.minThickness;
            for (var x = this.potency; x <= maxLevels; x++, alc += perLevelAlc, thickness += perLevelThickness) {
                potencyEntries.add(new FermentationStatusEffectEntry.StatusEffectPotencyEntry(alc, thickness, x));
            }

            return (C)FermentationStatusEffectEntryBuilder.this;
        }


        public C submit() {
            potencyEntries.add(new FermentationStatusEffectEntry.StatusEffectPotencyEntry(this.minAlc, this.minThickness, this.potency));
            return (C)FermentationStatusEffectEntryBuilder.this;
        }
    }
}