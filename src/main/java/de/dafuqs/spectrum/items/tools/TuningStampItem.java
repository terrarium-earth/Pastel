package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.Stampable;
import de.dafuqs.spectrum.api.item.TooltipExtensions;
import de.dafuqs.spectrum.helpers.BlockReference;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class TuningStampItem extends Item implements TooltipExtensions {

    public static final String DATA = Stampable.STAMPING_DATA_TAG;

    public TuningStampItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player miner) {
        return false;
    }

    /**
     * This is set up such that it can easily be extended for other uses later.
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        var stack = context.getItemInHand();
        var world = context.getLevel();
        var pos = context.getClickedPos();
        var player = Optional.ofNullable(context.getPlayer());
        var reference = BlockReference.of(world, pos);

        var potentialData = Optional.<Stampable.StampData>empty();

        var nbtComp = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (nbtComp.contains(DATA)) {
            potentialData = Stampable.loadStampingData(world, nbtComp.copyTag().getCompound(DATA));
        }

        if (potentialData.isPresent()) {
            var potentialTarget = getData(player, reference, world);

            if (potentialTarget.isEmpty())
                return InteractionResult.PASS;

            var source = potentialData.get();
            var target = potentialTarget.get();

            if (!source.verifyStampData(target) || !target.canUserStamp(player)) {
                tryPlaySound(player, SpectrumSoundEvents.SHATTER_LIGHT, 0.75F);
                return InteractionResult.FAIL;
            }
            var interactable = target.source();

            var targetChanged = interactable.handleImpression(source.stamper(), player, source.reference(), world);
            source.notifySourceOfChange(target, targetChanged);

            if (!targetChanged) {
                tryPlaySound(player, SpectrumSoundEvents.SHATTER_HEAVY, 0.45F);
                return InteractionResult.FAIL;
            }

            //Allow for 'rolling' linking for flow.
            player.ifPresent(user -> {
                if (!user.isShiftKeyDown()) {
                    var newSource = target.source().recordStampData(player, reference, world);
                    saveToNbt(stack, newSource);
                    tryPlaySound(player, SoundEvents.AMETHYST_BLOCK_CHIME, 0.825F);
                }
                else {
                    tryPlaySound(player, SpectrumSoundEvents.BLOCK_ONYX_BLOCK_CHIME, 0.825F);
                }
            });

            return InteractionResult.sidedSuccess(world.isClientSide());
        }
        else {
            var candidate = getData(player, reference, world);

            //Blank an interactable if shift clicking without a saved reference
            if (player.map(Entity::isShiftKeyDown).orElse(false)) {
                if (candidate.map(d -> d.canUserStamp(player)).orElse(false)) {
                    candidate.get().source().clearImpression();
                    tryPlaySound(player, SoundEvents.AMETHYST_BLOCK_BREAK, 0.825F);
                }
                return InteractionResult.sidedSuccess(world.isClientSide());
            }

            if (candidate.isPresent() && candidate.get().canUserStamp(player)) {
                saveToNbt(stack, candidate.get());
                tryPlaySound(player, SpectrumSoundEvents.CRYSTAL_STRIKE, 0.75F);
                return InteractionResult.sidedSuccess(world.isClientSide());
            }
        }

        return super.useOn(context);
    }

    public void clearData(Optional<Player> player, ItemStack stack) {
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY,
                comp -> comp.update(nbt -> nbt.remove(DATA)));
        tryPlaySound(player, SoundEvents.BRUSH_GENERIC, 1F);
    }

    private void tryPlaySound(Optional<Player> player, SoundEvent sound, float volume) {
        player.ifPresent(p -> p.level().playSound(null, p, sound, SoundSource.PLAYERS, volume, 0.9F + p.getRandom().nextFloat() / 5F));
    }

    private void saveToNbt(ItemStack stack, Stampable.StampData data) {
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY,
                comp -> comp.update(nbt -> nbt.put(DATA, Stampable.saveStampingData(data))));
    }

    private Optional<Stampable.StampData> getData(Optional<Player> player, BlockReference reference, Level world) {
        var data = Optional.<Stampable.StampData>empty();

        findData: {
            if (reference.getState().getBlock() instanceof Stampable interactable)
                data = Optional.ofNullable(interactable.recordStampData(player, reference, world));

            if (data.isPresent())
                break findData;

            data = reference.tryGetBlockEntity().map(be -> {
                if (be instanceof Stampable interactable)
                    return interactable.recordStampData(player, reference, world);
                return null;
            });
        }

        return data;
    }

    @Override
    public void appendTooltipWithPlayer(ItemStack stack, @Nullable Player player, List<Component> tooltip, TooltipContext context) {
        var nbtComp = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (player != null && nbtComp.contains(DATA)) {
            var data = Stampable.loadStampingData(player.level(), nbtComp.copyTag().getCompound(DATA));

            if (data.isEmpty()) {
                tooltip.add(Component.translatable("item.pastel.tuning_stamp.tooltip.missing").withStyle(style -> style.withColor(0xff757a)));
                return;
            }

            var stampData = data.get();
            var pos = stampData.reference().pos;

            tooltip.add(Component.translatable("item.pastel.tuning_stamp.tooltip.linked", stampData.reference().getState().getBlock().getName()).withStyle(style -> style.withColor(0xffc98c)));
            tooltip.add(Component.translatable("item.pastel.tuning_stamp.tooltip2", pos.getX(), pos.getY(), pos.getZ()).withStyle(style -> style.withColor(0xf99b89).withItalic(true)));
            return;
        }

        tooltip.add(Component.translatable("item.pastel.tuning_stamp.tooltip").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void expandTooltipPostStats(ItemStack stack, @Nullable Player player, List<Component> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("item.pastel.tuning_stamp.controls").withStyle(style -> style.withColor(0x66ff99)));
            tooltip.add(Component.translatable("item.pastel.tuning_stamp.controls2").withStyle(style -> style.withColor(0x66ff99)));
            tooltip.add(Component.translatable("item.pastel.tuning_stamp.controls3").withStyle(style -> style.withColor(0x66ff99)));
        } else {
            tooltip.add(Component.translatable("pastel.tooltip.press_shift_for_controls").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        }
    }
}
