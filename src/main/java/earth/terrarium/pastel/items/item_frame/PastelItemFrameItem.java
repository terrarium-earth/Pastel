package earth.terrarium.pastel.items.item_frame;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemFrameItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public abstract class PastelItemFrameItem extends ItemFrameItem {

    public PastelItemFrameItem(EntityType<? extends HangingEntity> entityType, Item.Properties settings) {
        super(entityType, settings);
    }

    public abstract ItemFrame getItemFrameEntity(Level world, BlockPos blockPos, Direction direction);

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockPos2 = blockPos.relative(direction);
        Player playerEntity = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        if (playerEntity != null && !this.mayPlace(playerEntity, direction, itemStack, blockPos2)) {
            return InteractionResult.FAIL;
        } else {
            Level world = context.getLevel();
            ItemFrame invisibleItemFrameEntity = getItemFrameEntity(world, blockPos2, direction);

            var nbtComponent = itemStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
            if (!nbtComponent.isEmpty()) {
                EntityType.updateCustomEntityTag(world, playerEntity, invisibleItemFrameEntity, nbtComponent);
            }

            if (invisibleItemFrameEntity.survives()) {
                if (!world.isClientSide) {
                    invisibleItemFrameEntity.playPlacementSound();
                    world.gameEvent(playerEntity, GameEvent.ENTITY_PLACE, blockPos);
                    world.addFreshEntity(invisibleItemFrameEntity);
                }

                itemStack.shrink(1);
                return InteractionResult.sidedSuccess(world.isClientSide);
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

}
