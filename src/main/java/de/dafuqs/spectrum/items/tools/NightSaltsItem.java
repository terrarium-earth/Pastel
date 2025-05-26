package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.SleepAlteringItem;
import de.dafuqs.spectrum.attachments.data.MiscPlayerData;
import de.dafuqs.spectrum.registries.SpectrumStatusEffects;
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

import java.util.List;

public class NightSaltsItem extends Item implements SleepAlteringItem {

    private static final MutableComponent TOOLTIP = Component.translatable("item.pastel.night_salts.tooltip");

    public NightSaltsItem(Properties settings) {
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

            component.setSleepTimers(20 * 10, 20 * 10, 0);
            component.setLastSleepItem(this);
			
			player.addEffect(new MobEffectInstance(SpectrumStatusEffects.CALMING, 20 * 20, 2)); // TODO: this should probs be moved to a food component, so the effect shows up as tooltip
            if (!player.getAbilities().instabuild)
                stack.shrink(1);
        }
        else {
            user.addEffect(new MobEffectInstance(SpectrumStatusEffects.SOMNOLENCE, 20 * 15));
            user.startSleeping(user.blockPosition());
            stack.shrink(1);
        }

        world.playSound(null, user, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1F, 1.2F);
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
        player.addEffect(new MobEffectInstance(SpectrumStatusEffects.VULNERABILITY, 20 * 30));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 30));
    }
}
