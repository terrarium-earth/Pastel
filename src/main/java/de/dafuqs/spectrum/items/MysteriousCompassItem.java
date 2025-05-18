package de.dafuqs.spectrum.items;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.spectrum.api.render.SlotBackgroundEffectProvider;
import de.dafuqs.spectrum.items.magic_items.StructureCompassItem;
import de.dafuqs.spectrum.registries.SpectrumAdvancements;
import de.dafuqs.spectrum.registries.SpectrumStructureTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MysteriousCompassItem extends StructureCompassItem implements SlotBackgroundEffectProvider {

	public MysteriousCompassItem(Properties settings) {
		super(settings, SpectrumStructureTags.MYSTERIOUS_COMPASS_LOCATED);
	}
	
	@Override
	public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, Entity entity, int slot, boolean selected) {
		if (!world.isClientSide && world.getGameTime() % 200 == 0 && entity instanceof Player player)
			if (AdvancementHelper.hasAdvancement(player, SpectrumAdvancements.MYSTERIOUS_LOCKET_SOCKETING)) {
				locateStructure(stack, world, entity);
			} else {
				removeStructurePos(stack);
		}
	}
	
	@Override
	public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
		return SlotEffect.FULL_PACKAGE;
	}
	
	@Override
	public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
		return 0xFFFFFF;
	}
}
