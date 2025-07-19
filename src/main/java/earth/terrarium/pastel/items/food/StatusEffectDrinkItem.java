package earth.terrarium.pastel.items.food;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class StatusEffectDrinkItem extends DrinkItem {

    public StatusEffectDrinkItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        Player playerEntity = user instanceof Player ? (Player) user : null;
        if (playerEntity instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerEntity, stack);
        }

        if (!world.isClientSide) {
            PotionContents potionContentsComponent = stack.getOrDefault(
                DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            potionContentsComponent.forEachEffect((effect) -> {
                if ((effect.getEffect()
                           .value()).isInstantenous()) {
                    (effect.getEffect()
                           .value()).applyInstantenousEffect(
                        playerEntity, playerEntity, user, effect.getAmplifier(), 1.0);
                } else {
                    user.addEffect(effect);
                }
            });
        }

        if (playerEntity != null) {
            playerEntity.awardStat(Stats.ITEM_USED.get(this));
        }

        user.gameEvent(GameEvent.DRINK);
        return super.finishUsingItem(stack, world, user);
    }

}
