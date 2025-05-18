package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.energy.InkCost;
import de.dafuqs.spectrum.api.energy.InkPowered;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.api.render.ExtendedItemBarProvider;
import de.dafuqs.spectrum.api.render.SlotBackgroundEffectProvider;
import de.dafuqs.spectrum.helpers.Support;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import de.dafuqs.spectrum.sound.OverchargingSoundInstance;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

// right click ability: able to overload an already loaded arrow
public class GlassCrestCrossbowItem extends MalachiteCrossbowItem implements ExtendedItemBarProvider, SlotBackgroundEffectProvider, InkPowered {
    
    private static final InkCost OVERCHARGE_COST = new InkCost(InkColors.WHITE, 1000);
    private static final int OVERCHARGE_DURATION_MAX_TICKS = 20 * 6; // 6 seconds
    
    public GlassCrestCrossbowItem(Properties settings) {
        super(settings);
    }
	
	@Override
	public List<InkColor> getUsedColors() {
		return List.of(OVERCHARGE_COST.color());
	}
	
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (user.isShiftKeyDown() && isCharged(itemStack) && !isOvercharged(itemStack) && InkPowered.tryDrainEnergy(user, OVERCHARGE_COST)) {
            if (world.isClientSide) {
                startSoundInstance(user);
            }
            return ItemUtils.startUsingInstantly(world, user, hand);
        }
        return super.use(world, user, hand);
    }
    
    @Environment(EnvType.CLIENT)
    public void startSoundInstance(Player user) {
        Minecraft.getInstance().getSoundManager().play(new OverchargingSoundInstance(user));
    }
    
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return isCharged(stack) ? OVERCHARGE_DURATION_MAX_TICKS : super.getUseDuration(stack, user);
    }
    
    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (isCharged(stack) && remainingUseTicks <= 0) {
            if (remainingUseTicks % 4 == 0) {
                world.playSound(null, user, SpectrumSoundEvents.BLOCK_MOONSTONE_CLUSTER_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        } else {
            super.onUseTick(world, user, stack, remainingUseTicks);
        }
    }
    
    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (isCharged(stack)) {
            if (!world.isClientSide) {
                if (remainingUseTicks > 0) {
                    float overcharge = 1 - (float) remainingUseTicks / OVERCHARGE_DURATION_MAX_TICKS;
                    overcharge(stack, overcharge);
                    if (user instanceof ServerPlayer serverPlayerEntity) {
                        serverPlayerEntity.displayClientMessage(Component.translatable("item.spectrum.glass_crest_crossbow.message.charge", Support.DF.format(overcharge * 100)), true);
                    }
                }
            }
            return;
        }
        super.releaseUsing(stack, world, user, remainingUseTicks);
    }
    
    public static boolean isOvercharged(ItemStack stack) {
        return getOvercharge(stack) > 0;
    }
    
    public static float getOvercharge(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.OVERCHARGED, 0f);
    }
    
    public static void overcharge(ItemStack stack, float percent) {
		stack.set(SpectrumDataComponentTypes.OVERCHARGED, percent);
    }
    
    public static void unOvercharge(ItemStack stack) {
		stack.remove(SpectrumDataComponentTypes.OVERCHARGED);
    }
    
    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        float overcharge = getOvercharge(stack);
        if (overcharge == 0) {
            tooltip.add(Component.translatable("item.spectrum.glass_crest_crossbow.tooltip.how_to_overcharge").withStyle(ChatFormatting.GRAY));
			addInkPoweredTooltip(tooltip);
        } else {
            tooltip.add(Component.translatable("item.spectrum.glass_crest_crossbow.tooltip.overcharged", Support.DF.format(overcharge * 100)).withStyle(ChatFormatting.GRAY));
        }
    }
    
    @Override
    public float getProjectileVelocityModifier(ItemStack stack) {
        float parent = super.getProjectileVelocityModifier(stack);
        float overcharge = getOvercharge(stack);
		return overcharge == 0 ? parent : parent * (1 + overcharge * 0.75F);
    }
    
    @Override
    public float getDivergenceMod(ItemStack stack) {
        float parent = super.getDivergenceMod(stack);
        float overcharge = getOvercharge(stack);
        return overcharge == 0 ? parent : parent * (1 - overcharge * 0.5F);
    }
	
	@Override
	public SlotBackgroundEffectProvider.SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
		var usable = InkPowered.hasAvailableInk(player, OVERCHARGE_COST);
		return usable ? SlotEffect.BORDER_FADE : SlotBackgroundEffectProvider.SlotEffect.NONE;
	}
	
	@Override
	public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
		return 0xFFFFFF;
	}
	
	@Override
	public int barCount(ItemStack stack) {
		return 1;
	}
	
	@Override
	public boolean allowVanillaDurabilityBarRendering(@Nullable Player player, ItemStack stack) {
		if (player == null || !isCharged(stack))
			return true;
		
		var usage = player.isUsingItem() && player.getItemInHand(player.getUsedItemHand()) == stack;
		
		return !(usage || isOvercharged(stack));
	}
	
	@Override
	public BarSignature getSignature(@Nullable Player player, @NotNull ItemStack stack, int index) {
		if (player == null || !isCharged(stack))
			return PASS;
		
		var usage = player.isUsingItem() && player.getItemInHand(player.getUsedItemHand()) == stack;
		
		if (!usage && !isOvercharged(stack))
			return PASS;
		
		var progress = (int) Math.floor(Mth.clampedLerp(0, 13, usage ? ((float) player.getTicksUsingItem() / OVERCHARGE_DURATION_MAX_TICKS) : getOvercharge(stack)));
		return new BarSignature(2, 13, 13, progress, 1, 0xFFFFFFFF, 2, ExtendedItemBarProvider.DEFAULT_BACKGROUND_COLOR);
	}
}
