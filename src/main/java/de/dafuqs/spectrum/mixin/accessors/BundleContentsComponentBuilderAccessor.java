package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BundleContents.Mutable.class)
public interface BundleContentsComponentBuilderAccessor {
	
	@Accessor
	List<ItemStack> getItems();
	
}
