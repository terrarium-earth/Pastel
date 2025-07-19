package earth.terrarium.pastel.compat.botania;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import vazkii.botania.api.item.ManaDissolvable;
import vazkii.botania.api.mana.ManaPool;
import vazkii.botania.common.handler.BotaniaSounds;
import vazkii.botania.common.helper.EntityHelper;
import vazkii.botania.network.EffectType;
import vazkii.botania.network.clientbound.BotaniaEffectPacket;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.List;

public class BlackestLotusItem extends Item implements ManaDissolvable {

    public BlackestLotusItem(Properties settings) {
        super(settings);
    }

    @Override
    public void onDissolveTick(ManaPool manaPool, ItemEntity itemEntity) {
        if (manaPool.isFull() || manaPool.getCurrentMana() == 0) {
            return;
        }

        BlockPos pos = manaPool.getManaReceiverPos();
        if (!itemEntity.level().isClientSide) {
            manaPool.receiveMana(manaPool.getMaxMana());
            EntityHelper.shrinkItem(itemEntity);
            XplatAbstractions.INSTANCE.sendToTracking(
                itemEntity, new BotaniaEffectPacket(
                    EffectType.BLACK_LOTUS_DISSOLVE, pos.getX(), pos.getY() + 0.5,
                    pos.getZ()
                )
            );
        }

        itemEntity.playSound(BotaniaSounds.blackLotus, 1F, 0.25F);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        tooltip.add(Component.translatable("item.pastel.blackest_lotus.tooltip")
                             .withStyle(ChatFormatting.GRAY));
    }

}
