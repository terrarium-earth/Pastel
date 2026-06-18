package earth.terrarium.pastel.items.item_frame;

import com.mojang.authlib.GameProfile;
import earth.terrarium.pastel.components.EnderSpliceComponent;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.entity.entity.EnderCanvasEntity;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EnderCanvasItem extends HangingEntityItem {
    public EnderCanvasItem(Properties properties) {
        super(PastelEntityTypes.ENDER_CANVAS.get(), properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos clickedPos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();
        BlockPos relative = clickedPos.relative(clickedFace);
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        if (player != null && !this.mayPlace(player, clickedFace, stack, relative)) {
            return InteractionResult.FAIL;
        } else {
            Level level = context.getLevel();
            EnderCanvasEntity result;

            EnderSpliceComponent spliceComponent = stack
                .getOrDefault(
                    PastelDataComponentTypes.ENDER_SPLICE,
                    EnderSpliceComponent.DEFAULT
                );
            EnderCanvasEntity.EnderCanvasVariant variant = stack
                .getOrDefault(
                    PastelDataComponentTypes.ENDER_CANVAS_VARIANT,
                    EnderCanvasEntity.EnderCanvasVariant.LANDSCAPELARGE
                );

            if (spliceComponent
                .pos()
                .isEmpty() && spliceComponent
                    .targetGameProfile()
                    .isEmpty())
                return InteractionResult.PASS;

            Optional<EnderCanvasEntity> canvasEntity = EnderCanvasEntity
                .createNew(
                    level,
                    relative,
                    clickedFace,
                    spliceComponent,
                    variant,
                    EnchantmentHelper.hasTag(stack, PastelEnchantmentTags.DIMENSIONAL_TELEPORT)
                );

            if (canvasEntity.isEmpty()) {
                return InteractionResult.CONSUME;
            }
            result = (EnderCanvasEntity) canvasEntity.get();

            if (result.survives()) {
                if (!level.isClientSide) {
                    result.playPlacementSound();
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, result.position());
                    level.addFreshEntity(result);
                }

                stack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.CONSUME;
            }
        }
    }

    @Override
    @OnlyIn(
        Dist.CLIENT
    )
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        var teleportTargetPos = getTeleportTargetPos(stack);
        if (teleportTargetPos.isPresent()) {
            String dimensionDisplayString = Support
                .getReadableDimensionString(
                    teleportTargetPos
                        .get()
                        .getA()
                        .location()
                        .toString()
                );
            Vec3 pos = teleportTargetPos
                .get()
                .getB();
            tooltip
                .add(
                    Component
                        .translatable(
                            stack
                                .getOrDefault(
                                    PastelDataComponentTypes.ENDER_CANVAS_VARIANT,
                                    EnderCanvasEntity.EnderCanvasVariant.LANDSCAPELARGE
                                ) == EnderCanvasEntity.EnderCanvasVariant.LANDSCAPELARGE
                                    ? "item.pastel.ender_canvas.tooltip.large_landscape"
                                    : "item.pastel.ender_canvas.tooltip.small_landscape",
                            (int) pos.x,
                            (int) pos.y,
                            (int) pos.z,
                            dimensionDisplayString
                        )
                );
        } else {
            Optional<UUID> teleportTargetPlayerUUID = getTeleportTargetPlayerUUID(stack);
            if (teleportTargetPlayerUUID.isPresent()) {
                Optional<Component> teleportTargetPlayerName = getTeleportTargetPlayerName(stack);
                if (teleportTargetPlayerName.isPresent()) {
                    tooltip
                        .add(
                            Component
                                .translatable(
                                    "item.pastel.ender_canvas.tooltip.portrait",
                                    teleportTargetPlayerName.get()
                                )
                        );
                } else {
                    tooltip.add(Component.translatable("item.pastel.ender_canvas.tooltip.portrait", "???"));
                }
            }
        }
    }

    public Optional<Tuple<ResourceKey<Level>, Vec3>> getTeleportTargetPos(@NotNull ItemStack itemStack) {
        var component = itemStack.getOrDefault(PastelDataComponentTypes.ENDER_SPLICE, EnderSpliceComponent.DEFAULT);
        if (component
            .pos()
            .isPresent() && component
                .dimension()
                .isPresent()) return Optional
                    .of(
                        new Tuple<>(
                            component
                                .dimension()
                                .get(),
                            component
                                .pos()
                                .get()
                        )
                    );
        return Optional.empty();
    }

    public Optional<UUID> getTeleportTargetPlayerUUID(@NotNull ItemStack itemStack) {
        return itemStack
            .getOrDefault(PastelDataComponentTypes.ENDER_SPLICE, EnderSpliceComponent.DEFAULT)
            .targetGameProfile()
            .map(GameProfile::getId);
    }

    public Optional<Component> getTeleportTargetPlayerName(@NotNull ItemStack itemStack) {
        return itemStack
            .getOrDefault(PastelDataComponentTypes.ENDER_SPLICE, EnderSpliceComponent.DEFAULT)
            .targetName();
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.is(PastelEnchantmentTags.DIMENSIONAL_TELEPORT);
    }
}
