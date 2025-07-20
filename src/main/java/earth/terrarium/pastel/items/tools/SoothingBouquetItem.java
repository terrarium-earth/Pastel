package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.item.SleepAlteringItem;
import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.registries.PastelMobEffects;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoothingBouquetItem extends Item implements SleepAlteringItem, SlotBackgroundEffect {

    private static final MutableComponent TOOLTIP = Component.translatable("item.pastel.soothing_bouquet.tooltip");

    public SoothingBouquetItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(TOOLTIP.withStyle(ChatFormatting.GRAY));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (user instanceof Player player) {
            var component = MiscPlayerData.get(player);

            component.setSleepTimers(50, 20 * 6, 0);
            component.setLastSleepItem(this);

            player.addEffect(new MobEffectInstance(
                PastelMobEffects.CALMING, 20 * 10,
                4
            )); // TODO: this should probably be a food component, so it shows up as tooltip
            player.addEffect(new MobEffectInstance(PastelMobEffects.SOMNOLENCE, 20 * 10, 4));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 50, 3));
        } else {
            user.addEffect(new MobEffectInstance(PastelMobEffects.SOMNOLENCE, 20 * 15, 2));
            user.startSleeping(user.blockPosition());
        }

        world.playSound(null, user, PastelSoundEvents.LIGHT_CRYSTAL_RING, SoundSource.PLAYERS, 1F, 1.2F);
        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        user.startUsingItem(hand);
        return InteractionResultHolder.consume(user.getItemInHand(hand));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 40;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.SNIFFER_SCENTING;
    }

    @Override
    public void applyPenalties(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 15));
    }

    @Override
    public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
        return SlotEffect.BORDER_FADE;
    }

    @Override
    public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
        return PastelMobEffects.ETERNAL_SLUMBER_COLOR;
    }
}
