package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.items.armor.CrystalArmorItem;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
    Slot.class
)
public abstract class SlotMixin {
    @Shadow
    public abstract ItemStack getItem();

    @Shadow
    @Final
    public Container container;

    @Inject(
        method = "setChanged", at = @At(
            "HEAD"
        )
    )
    private void setChangedMixin(CallbackInfo ci) {
        if (this.container instanceof Inventory) return;
        var item = this.getItem();
        if (item.has(PastelDataComponentTypes.CRYSTAL_ARMOR_EMPOWERED)) CrystalArmorItem.removeEmpowered(item);
    }
}
