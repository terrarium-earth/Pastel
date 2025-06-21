package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.items.trinkets.PastelTrinketItem;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmBlock.class)
public abstract class FarmlandBlockMixin extends Block {
    public FarmlandBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = {"fallOn"}, at = {@At("HEAD")}, cancellable = true)
    private void spectrum$onLandedUpon(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo info) {
        super.fallOn(world, state, pos, entity, fallDistance); // fall damage

        // if carrying puff circlet: no trampling
        if (entity instanceof LivingEntity livingEntity) {
            if (PastelTrinketItem.hasEquipped(livingEntity, PastelItems.PUFF_CIRCLET.get())) {
                info.cancel();
            }
        }
    }
}