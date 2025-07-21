package earth.terrarium.pastel.items.magic_items.ampoules;

import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.InkPoweredStatusEffectInstance;
import earth.terrarium.pastel.api.item.InkPoweredPotionFillable;
import earth.terrarium.pastel.entity.entity.LightMineEntity;
import earth.terrarium.pastel.entity.entity.LightShardBaseEntity;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MalachiteGlassAmpouleItem extends GlassAmpouleItem implements InkPoweredPotionFillable {

    public MalachiteGlassAmpouleItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean trigger(
        Level world, ItemStack stack, @Nullable LivingEntity attacker, @Nullable LivingEntity target, Vec3 position) {
        List<MobEffectInstance> e = new ArrayList<>();
        if (attacker instanceof Player player) {
            List<InkPoweredStatusEffectInstance> effects = InkPoweredPotionFillable.getEffects(stack);
            for (InkPoweredStatusEffectInstance effect : effects) {
                if (InkPowered.tryDrainEnergy(player, effect.getInkCost())) {
                    e.add(effect.getStatusEffectInstance());
                }
            }
        }

        if (e.isEmpty()) {
            return false;
        }

        world.playLocalSound(
            BlockPos.containing(position), PastelSoundEvents.LIGHT_CRYSTAL_RING, SoundSource.PLAYERS, 0.35F, 0.9F +
                                                                                                             world.getRandom()
                                                                                                                  .nextFloat() *
                                                                                                             0.334F,
            true
        );
        LightMineEntity.summonBarrage(
            world, attacker, target, LightShardBaseEntity.MONSTER_TARGET, e, position,
            LightShardBaseEntity.DEFAULT_COUNT_PROVIDER
        );
        return true;
    }

    @Override
    public int maxEffectCount() {
        return 1;
    }

    @Override
    public int maxEffectAmplifier() {
        return 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("item.pastel.malachite_glass_ampoule.tooltip")
                             .withStyle(ChatFormatting.GRAY));
        appendPotionFillableTooltip(
            stack, tooltip, Component.translatable("item.pastel.malachite_glass_ampoule.tooltip.when_hit"), false,
            context.tickRate()
        );
    }

}
