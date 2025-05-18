package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.neoforged.neoforge.common.Tags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(ItemPredicate.class)
public abstract class ItemPredicateMixin {
	
	@Shadow
	@Final
	private Optional<HolderSet<Item>> items;

	// FUCK THIS MIXIN
	// GO TO HELL - XOXO AZZYYPAARAS
	@ModifyExpressionValue(method = "test(Lnet/minecraft/world/item/ItemStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/core/HolderSet;)Z"))
	public boolean redirectShearsPredicates(boolean original, @Local(argsOnly = true) ItemStack stack) {
		if (original)
			return true;
		
		assert items.isPresent();
		var entries = items.get();
		
		if (entries.stream().anyMatch(e -> e.value().equals(Items.SHEARS))) {
			return stack.is(Tags.Items.TOOLS_SHEAR);
		}
		
		return false;
	}
	
}