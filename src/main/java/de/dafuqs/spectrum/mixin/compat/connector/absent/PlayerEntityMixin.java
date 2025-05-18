package de.dafuqs.spectrum.mixin.compat.connector.absent;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @ModifyExpressionValue(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;distanceToSqr(Lnet/minecraft/world/entity/Entity;)D", shift = At.Shift.AFTER))
    protected double spectrum$increaseSweepMaxDistance(double original) {
        var stack = this.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.getItem() == SpectrumItems.DRACONIC_TWINSWORD) {
			int channeling = SpectrumEnchantmentHelper.getLevel(level().registryAccess(), Enchantments.CHANNELING, stack);
            return original * 3 * ((channeling + 1) * 1.5);
		}
        return original;
    }
}
