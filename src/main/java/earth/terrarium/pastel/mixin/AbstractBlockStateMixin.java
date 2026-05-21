package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import earth.terrarium.pastel.api.interaction.ResonanceProcessor;
import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class AbstractBlockStateMixin {

    @ModifyVariable(method = "spawnAfterBreak", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public boolean preventXPDropsWhenUsingResonance(
        boolean dropExperience, ServerLevel world, BlockPos pos, ItemStack stack) {
        if (ResonanceProcessor.preventNextXPDrop && EnchantmentHelper.hasTag(
            stack, PastelEnchantmentTags.RESONANT_BLOCK_DROPS)) {
            ResonanceProcessor.preventNextXPDrop = false;
            return false;
        }
        return dropExperience;
    }

    @ModifyReturnValue(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At(
        "RETURN"))
    public VoxelShape getFluidloggedCollisionShape(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
        FluidState fluidState = level.getFluidState(pos);
        int fluidLevel = fluidState.getAmount();
        if (fluidLevel == 0) return original;
        VoxelShape fluidShape = FLUID_LEVEL_SHAPES[fluidLevel];
        if (!context.canStandOnFluid(level.getFluidState(pos.above()), fluidState)) return original;
        return Shapes.or(original, fluidShape);
    }

    // https://github.com/apace100/water-walking-fix
    @Unique
    private static final VoxelShape[] FLUID_LEVEL_SHAPES;
    static {
        FLUID_LEVEL_SHAPES = new VoxelShape[16];
        for (int i = 0; i < 16; i++) {
            FLUID_LEVEL_SHAPES[i] = Block.box(0.0, 0.0, 0.0, 16.0, i, 16.0);
        }
    }
}
