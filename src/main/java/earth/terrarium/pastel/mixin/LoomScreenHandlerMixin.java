package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.api.item.LoomPatternProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LoomMenu.class)
public abstract class LoomScreenHandlerMixin extends AbstractContainerMenu {

    @Shadow
    @Final
    private Slot patternSlot;

    @Shadow
    @Final
    private HolderGetter<BannerPattern> patternGetter;

    private LoomScreenHandlerMixin() {
        super(null, 0);
    }

    @Inject(method = "getSelectablePatterns(Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;", at = @At("HEAD"),
            cancellable = true)
    private void getPatternsFor(ItemStack stack, CallbackInfoReturnable<List<Holder<BannerPattern>>> cir) {
        if (stack.getItem() instanceof LoomPatternProvider loomPatternProvider) {
            cir.setReturnValue(LoomPatternProvider.getPatterns(patternGetter, loomPatternProvider));
        }
    }

    @Inject(
        method = "quickMoveStack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;",
            ordinal = 0,
            shift = At.Shift.BEFORE
        ),
        cancellable = true
    )
    private void attemptPatternItemTransfer(Player player, int slotIdx, CallbackInfoReturnable<ItemStack> info) {
        ItemStack stack = this.slots.get(slotIdx)
                                    .getItem();

        if (stack.getItem() instanceof LoomPatternProvider) {
            if (!this.moveItemStackTo(stack, this.patternSlot.index, this.patternSlot.index + 1, false)) {
                info.setReturnValue(ItemStack.EMPTY);
            }
        }
    }

}
