package de.dafuqs.spectrum.mixin;

import com.google.common.collect.*;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.*;
import com.mojang.serialization.*;
import de.dafuqs.spectrum.api.enchantment.*;
import net.minecraft.recipe.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.*;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {
	
	//TODO this isn't quite working
	@WrapOperation(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;parse(Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;"))
	private DataResult<?> injectAdditionalRecipes(Codec<?> instance, DynamicOps<?> dynamicOps, Object data, Operation<DataResult<?>> original, @Local LocalRef<Identifier> identifier, @Local ImmutableMultimap.Builder<RecipeType<?>, RecipeEntry<?>> builder, @Local ImmutableMap.Builder<Identifier, RecipeEntry<?>> builder2) {
		DataResult<?> result = original.call(instance, dynamicOps, data);
		if (result.result().isPresent() && result.result().get() instanceof IRecipeGenerator recipeGenerator) {
			List<? extends Recipe<?>> additionalRecipes = recipeGenerator.getAdditionalRecipes();
			
			for (int i = 0; i < additionalRecipes.size(); i++) {
				Identifier transformedId = recipeGenerator.transformRecipeId(identifier.get(), i + 1);
				Recipe<?> recipe = additionalRecipes.get(i);
				RecipeEntry<?> recipeEntry = new RecipeEntry<>(transformedId, recipe);
				builder.put(recipe.getType(), recipeEntry);
				builder2.put(transformedId, recipeEntry);
			}
			
			identifier.set(recipeGenerator.transformRecipeId(identifier.get(), 0));
		}
		
		return result;
	}
	
}
