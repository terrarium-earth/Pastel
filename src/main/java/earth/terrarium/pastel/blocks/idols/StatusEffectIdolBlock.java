package earth.terrarium.pastel.blocks.idols;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StatusEffectIdolBlock extends IdolBlock {

    protected final Holder<MobEffect> statusEffect;
    protected final int amplifier;
    protected final int duration;

    public StatusEffectIdolBlock(
        Properties settings, ParticleOptions particleEffect, Holder<MobEffect> statusEffect, int amplifier,
        int duration
    ) {
        super(settings, particleEffect);
        this.statusEffect = statusEffect;
        this.amplifier = amplifier;
        this.duration = duration;
    }

    @Override
    public MapCodec<? extends StatusEffectIdolBlock> codec() {
        //TODO: Make the codec
        return null;
    }

    @Override
    public void appendHoverText(
        ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable(
            "block.pastel.potion_effect_idol.tooltip", this.statusEffect.value()
                                                                        .getDisplayName()
        ));
    }

    @Override
    public boolean trigger(
        ServerLevel world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(statusEffect, duration, amplifier, true, true));

            // if entity is burning: put out fire
            if (statusEffect == MobEffects.FIRE_RESISTANCE && livingEntity.isOnFire()) {
                livingEntity.setRemainingFireTicks(0);
            }

            return true;
        }
        return false;
    }

}
