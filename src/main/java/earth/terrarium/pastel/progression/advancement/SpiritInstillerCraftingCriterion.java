package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.SpectrumCommon;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class SpiritInstillerCraftingCriterion extends SimpleCriterionTrigger<FusionShrineCraftingCriterion.Conditions> {

	public static final ResourceLocation ID = SpectrumCommon.locate("crafted_with_spirit_instiller");

	public void trigger(ServerPlayer player, ItemStack itemStack, int experience) {
		this.trigger(player, (conditions) -> conditions.matches(itemStack, experience));
	}

	@Override
	public Codec<FusionShrineCraftingCriterion.Conditions> codec() {
		return FusionShrineCraftingCriterion.Conditions.CODEC;
	}
}