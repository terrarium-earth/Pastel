package de.dafuqs.fractal.mixin;

import com.google.common.collect.Lists;
import de.dafuqs.fractal.api.ItemSubGroup;
import de.dafuqs.fractal.interfaces.ItemGroupParent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;

@Mixin(CreativeModeTab.class)
public class MixinItemGroup implements ItemGroupParent {
	
	@Unique
	private final List<ItemSubGroup> fractal$children = Lists.newArrayList();
	@Unique
	private ItemSubGroup fractal$selectedChild = null;
	
	@Inject(method = "getDisplayItems", at = @At("HEAD"), cancellable = true)
	public void getDisplayItems(CallbackInfoReturnable<Collection<ItemStack>> cir) {
		if (fractal$selectedChild != null) {
			cir.setReturnValue(fractal$selectedChild.getDisplayItems());
		}
	}
	
	@Override
	public List<ItemSubGroup> fractal$getChildren() {
		return fractal$children;
	}
	
	@Override
	public ItemSubGroup fractal$getSelectedChild() {
		return fractal$selectedChild;
	}
	
	@Override
	public void fractal$setSelectedChild(ItemSubGroup group) {
		fractal$selectedChild = group;
	}
	
}
