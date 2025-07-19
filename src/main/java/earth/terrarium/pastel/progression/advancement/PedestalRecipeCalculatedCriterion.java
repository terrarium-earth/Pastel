package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class PedestalRecipeCalculatedCriterion extends SimpleCriterionTrigger<PedestalCraftingCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("pedestal_recipe_calculated");

    public void trigger(ServerPlayer player, ItemStack itemStack, int experience, int durationTicks) {
        this.trigger(player, (conditions) -> conditions.matches(itemStack, experience, durationTicks));
    }

    @Override
    public Codec<PedestalCraftingCriterion.Conditions> codec() {
        return PedestalCraftingCriterion.Conditions.CODEC;
    }
}
