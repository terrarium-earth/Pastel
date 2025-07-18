package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.networking.s2c_payloads.PlayDivinityAppliedEffectsPayload;
import earth.terrarium.pastel.registries.PastelMobEffects;
import earth.terrarium.pastel.status_effects.DivinityStatusEffect;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CircletOfArroganceItem extends PastelTrinketItem {

    private static final int TRIGGER_EVERY_X_TICKS = 240;
    private static final int EFFECT_DURATION = TRIGGER_EVERY_X_TICKS + 10;

    public CircletOfArroganceItem(Properties settings) {
        super(settings, PastelCommon.locate("unlocks/trinkets/circlet_of_arrogance"));
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        LivingEntity entity = slotContext.entity();

        giveEffect(entity);
        if (entity instanceof ServerPlayer serverPlayerEntity) {
            PlayDivinityAppliedEffectsPayload.playDivinityAppliedEffects(serverPlayerEntity);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);

        Level world = slotContext.entity()
                                 .level();
        if (!world.isClientSide && world.getGameTime() % TRIGGER_EVERY_X_TICKS == 0) {
            giveEffect(slotContext.entity());
        }
    }

    private static void giveEffect(LivingEntity entity) {
        entity.addEffect(
            new MobEffectInstance(
                PastelMobEffects.DIVINITY, EFFECT_DURATION, DivinityStatusEffect.CIRCLET_AMPLIFIER,
                true, true
            ));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("item.pastel.circlet_of_arrogance.tooltip")
                             .withStyle(ChatFormatting.GRAY));
    }

}
