package earth.terrarium.pastel.data.recipe.builder.anvil_crushing;

import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.recipe.anvil_crushing.AnvilCrushingRecipe;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.Optional;

public final class AnvilCrushingRecipeBuilder extends GatedRecipeBuilder<AnvilCrushingRecipeBuilder> {
    private final Ingredient input;
    private final float crushedItemsPerPointOfDamage;
    @Nullable
    private ResourceLocation particleEffectIdentifier;
    private int particleCount = 1;
    private final ResourceLocation soundEventIdentifier;
    private float experience = 0.0f;

    private AnvilCrushingRecipeBuilder(
            ItemStack result,
            Ingredient input,
            float crushedItemsPerPointOfDamage,
            ResourceLocation soundEventIdentifier) {
        super(result);
        this.input = input;
        this.crushedItemsPerPointOfDamage = crushedItemsPerPointOfDamage;
        this.soundEventIdentifier = soundEventIdentifier;
    }

    public static AnvilCrushingRecipeBuilder of(
            ItemStack result,
            Ingredient input,
            float crushedItemsPerPointOfDamage,
            ResourceLocation soundEventIdentifier
    ) {
        return new AnvilCrushingRecipeBuilder(result, input, crushedItemsPerPointOfDamage, soundEventIdentifier);
    }

    public static AnvilCrushingRecipeBuilder of(
            ItemStack result,
            Ingredient input,
            float crushedItemsPerPointOfDamage,
            SoundEvent soundEvent
    ) {
        return of(result, input, crushedItemsPerPointOfDamage, soundEvent.getLocation());
    }



    public AnvilCrushingRecipeBuilder particleCount(int particleCount) {
        this.particleCount = particleCount;
        return this;
    }

    public AnvilCrushingRecipeBuilder experience(float experience) {
        this.experience = experience;
        return this;
    }

    public AnvilCrushingRecipeBuilder particleEffect(@Nullable ResourceLocation identifier) {
        this.particleEffectIdentifier = identifier;
        return this;
    }

    public AnvilCrushingRecipeBuilder particleEffect(@Nullable ParticleType<?> particleType) {
        if (particleType == null) {
            this.particleEffectIdentifier = null;
        } else {
            this.particleEffectIdentifier = BuiltInRegistries.PARTICLE_TYPE.getKey(particleType);
        }
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var recipe = new AnvilCrushingRecipe(
                this.group,
                this.secret,
                this.getRequiredAdvancement(),
                this.input,
                this.result,
                this.crushedItemsPerPointOfDamage,
                this.experience,
                Optional.ofNullable(this.particleEffectIdentifier),
                this.particleCount,
                this.soundEventIdentifier
        );
        this.saveHelper(recipeOutput, id, recipe);
    }
}
