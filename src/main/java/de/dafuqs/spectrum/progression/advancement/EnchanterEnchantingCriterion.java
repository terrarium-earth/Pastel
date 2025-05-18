package de.dafuqs.spectrum.progression.advancement;

import com.mojang.serialization.Codec;
import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class EnchanterEnchantingCriterion extends SimpleCriterionTrigger<EnchanterCraftingCriterion.Conditions> {

	public static final ResourceLocation ID = SpectrumCommon.locate("enchanter_enchanting");

	public void trigger(ServerPlayer player, ItemStack itemStack, int spentExperience) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack, spentExperience));
	}

	@Override
	public Codec<EnchanterCraftingCriterion.Conditions> codec() {
		return EnchanterCraftingCriterion.Conditions.CODEC;
	}
}
