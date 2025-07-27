package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GleamingPinItem extends PastelTrinketItem {

    public static final int BASE_RANGE = 12;
    public static final int RANGE_BONUS_PER_LEVEL_OF_SNIPING = 4;
    public static final int EFFECT_DURATION = 240;
    public static final long COOLDOWN_TICKS = 160;

    public GleamingPinItem(Properties settings) {
        super(settings, PastelCommon.locate("unlocks/trinkets/gleaming_pin"));
    }

    public static void doGleamingPinEffect(
        @NotNull Player player, @NotNull ServerLevel world, ItemStack gleamingPinStack) {
        world.playSound(
            null, player.getX(), player.getY(), player.getZ(), PastelSounds.RADIANCE_PIN_TRIGGER,
            SoundSource.PLAYERS, 0.4F, 0.9F + world.getRandom()
                                                   .nextFloat() * 0.2F
        );
        PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
            world, player.position()
                         .add(
                             0, 0.75, 0), PastelParticleTypes.LIQUID_CRYSTAL_SPARKLE, 100, new Vec3(0, 0.5, 0),
            new Vec3(2.5, 0.1, 2.5)
        );

        world.getEntities(
                 player, player.getBoundingBox()
                               .inflate(getEffectRange(world, gleamingPinStack)),
                 EntitySelector.LIVING_ENTITY_STILL_ALIVE
             )
             .forEach((entity) -> {
                 if (entity instanceof LivingEntity livingEntity) {
                     livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, EFFECT_DURATION, 0, true, true));
                 }
             });
    }

    public static int getEffectRange(ServerLevel world, ItemStack stack) {
        return BASE_RANGE + RANGE_BONUS_PER_LEVEL_OF_SNIPING * Ench.getLevel(
            world.registryAccess(), PastelEnchantments.SNIPING, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("item.pastel.gleaming_pin.tooltip"));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 16;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(PastelEnchantments.SNIPING);
    }

}
