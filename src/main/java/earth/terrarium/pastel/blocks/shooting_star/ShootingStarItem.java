package earth.terrarium.pastel.blocks.shooting_star;

import earth.terrarium.pastel.components.ShootingStarComponent;
import earth.terrarium.pastel.entity.entity.ShootingStarEntity;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShootingStarItem extends BlockItem implements ShootingStar {

    private final Variant shootingStarType;

    public ShootingStarItem(ShootingStarBlock block, Properties settings) {
        super(block, settings);
        this.shootingStarType = block.shootingStarType;
    }

    public static @NotNull ItemStack getWithRemainingHits(
        @NotNull ShootingStarItem shootingStarItem,
        int remainingHits,
        boolean hardened
    ) {
        return getWithRemainingHits(shootingStarItem.getDefaultInstance(), remainingHits, hardened);
    }

    public static @NotNull ItemStack getWithRemainingHits(
        @NotNull ItemStack stack,
        int remainingHits,
        boolean hardened
    ) {
        ShootingStarComponent component = new ShootingStarComponent(remainingHits, hardened);
        stack.set(PastelDataComponentTypes.SHOOTING_STAR, component);
        return stack;
    }

    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        if (context
            .getPlayer()
            .isShiftKeyDown()) {
            // place as block
            return super.useOn(context);
        } else {
            // place as entity
            Level world = context.getLevel();

            if (!world.isClientSide) {
                ItemStack itemStack = context.getItemInHand();
                Vec3 hitPos = context.getClickLocation();
                Player user = context.getPlayer();

                ShootingStarEntity shootingStarEntity = getEntityForStack(context.getLevel(), hitPos, itemStack);
                shootingStarEntity.setYRot(user.getYRot());
                if (!world.noCollision(shootingStarEntity, shootingStarEntity.getBoundingBox())) {
                    return InteractionResult.FAIL;
                } else {
                    world.addFreshEntity(shootingStarEntity);
                    world.gameEvent(user, GameEvent.ENTITY_PLACE, context.getClickedPos());
                    if (!user.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }

                    user.awardStat(Stats.ITEM_USED.get(this));
                }
            }

            return InteractionResult.sidedSuccess(world.isClientSide);
        }
    }

    @NotNull public ShootingStarEntity getEntityForStack(@NotNull Level world, Vec3 pos, ItemStack stack) {
        return new ShootingStarEntity(
            world,
            pos.x,
            pos.y,
            pos.z,
            this.shootingStarType,
            true,
            getRemainingHits(stack),
            isHardened(stack)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        if (isHardened(stack)) {
            tooltip
                .add(
                    Component
                        .translatable("item.pastel.shooting_star.tooltip.hardened")
                        .withStyle(ChatFormatting.GRAY)
                );
        }
    }

    public Variant getShootingStarType() {
        return this.shootingStarType;
    }

    public static boolean isHardened(ItemStack stack) {
        return stack
            .getOrDefault(PastelDataComponentTypes.SHOOTING_STAR, ShootingStarComponent.DEFAULT)
            .hardened();
    }

    public static int getRemainingHits(@NotNull ItemStack stack) {
        return stack
            .getOrDefault(PastelDataComponentTypes.SHOOTING_STAR, ShootingStarComponent.DEFAULT)
            .remainingHits();
    }

    public static void setHardened(ItemStack stack) {
        ShootingStarComponent component = stack
            .getOrDefault(
                PastelDataComponentTypes.SHOOTING_STAR,
                ShootingStarComponent.DEFAULT
            );
        if (component == null) {
            component = new ShootingStarComponent(ShootingStarComponent.DEFAULT.remainingHits(), true);
        } else {
            component = new ShootingStarComponent(component.remainingHits(), true);
        }

        stack.set(PastelDataComponentTypes.SHOOTING_STAR, component);
    }

}
