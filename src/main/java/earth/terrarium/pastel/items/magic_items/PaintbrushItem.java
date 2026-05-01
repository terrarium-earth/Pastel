package earth.terrarium.pastel.items.magic_items;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.block.ColorableBlock;
import earth.terrarium.pastel.api.block.PaintbrushInformed;
import earth.terrarium.pastel.api.block.PaintbrushTriggered;
import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.interaction.EntityColorProcessorRegistry;
import earth.terrarium.pastel.api.item.PickBlockActivated;
import earth.terrarium.pastel.compat.claims.GenericClaimModsCompat;
import earth.terrarium.pastel.components.PaintbrushComponent;
import earth.terrarium.pastel.entity.entity.InkProjectileEntity;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.helpers.level.BlockVariantHelper;
import earth.terrarium.pastel.inventories.PaintbrushScreenHandler;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.networking.c2s_payloads.PaintbrushModeSwitchPayload;
import earth.terrarium.pastel.recipe.cantrip.DegradingRecipe;
import earth.terrarium.pastel.recipe.cantrip.HealingRecipe;
import earth.terrarium.pastel.registries.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PaintbrushItem extends Item implements SignApplicator {
    public static final int BLOCK_COLOR_COST = 25;
    public static final int CANTRIP_COST = 50;
    public static final int ITEM_VACUUM_RANGE = 16; // todo move to config? also this is a DIAMETER not a radius!
    public static final float PINK_HEAL_AMOUNT = 4f;
    public static final TagKey<Block>[] MUTANDIS_TAGS = new TagKey[]{
        BlockTags.SMALL_FLOWERS, BlockTags.SAPLINGS, BlockTags.LEAVES, BlockTags.CROPS, BlockTags.CORALS,
        BlockTags.WART_BLOCKS
    };

    public PaintbrushItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        if (EffectiveSide.get()
                         .isClient()) {
            appendClientTooltips(stack, tooltip);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void appendClientTooltips(ItemStack stack, List<Component> tooltip) {
        if (DatabankUtils.hasAdvancementClient(PastelAdvancements.COLLECT_PIGMENT) ||
            DatabankUtils.hasAdvancementClient(PastelAdvancements.Midgame.FILL_INK_CONTAINER)) {
            tooltip.add(Component.translatable("key.pickItem")
                                 .append(Component.translatable("item.pastel.paintbrush.tooltip.menu")));
        }
        PaintbrushComponent data = stack.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT);
        switch (data.mode()) {
            case INFO -> tooltip.add(Component.translatable("item.pastel.paintbrush.tooltip.info"));
            case PAINT -> tooltip.add(Component.translatable("item.pastel.paintbrush.tooltip.paint"));
            case SPELL -> tooltip.add(Component.translatable("item.pastel.paintbrush.tooltip.spell"));
        }
        if (data.mode() == PaintbrushComponent.PaintbrushMode.PAINT && data.color()
                                                                           .isPresent()) {
            tooltip.add(Component.translatable("item.pastel.paintbrush.tooltip.color.selected")
                                 .append(data.color()
                                             .get()
                                             .getColoredName()));
        }
        if (data.color()
                .isPresent() && data.mode() == PaintbrushComponent.PaintbrushMode.SPELL && data.color()
                                                                                               .get()
                                                                                               .getDyeColor()
                                                                                               .isPresent()) {
            tooltip.add(Component.translatable("item.pastel.paintbrush.tooltip.cantrip." + data.color()
                                                                                               .get()
                                                                                               .toString()
                                                                                               .substring(7))
                                 .withColor(data.color()
                                                .get()
                                                .getTextColorInt()));
        }
    }

    public static boolean canPaint(Player player) {
        return DatabankUtils.hasAdvancement(player, PastelAdvancements.COLLECT_PIGMENT);
    }

    public static boolean canTrip(Player player) {
        return DatabankUtils.hasAdvancement(player, PastelAdvancements.Midgame.FILL_INK_CONTAINER);
    }

    public MenuProvider createScreenHandlerFactory(ItemStack itemStack) {
        return new SimpleMenuProvider(
            (syncId, inventory, player) -> new PaintbrushScreenHandler(syncId, inventory, itemStack),
            Component.translatable("item.pastel.paintbrush")
        );
    }

    @Override
    public Component getName(ItemStack stack) {
        MutableComponent name = Component.translatable(this.getDescriptionId(stack));
        getColor(stack).ifPresent(inkColor -> name.setStyle(Style.EMPTY.withColor(inkColor.getTextColorInt())));
        return name;
    }

    public static void setColor(ItemStack stack, @Nullable InkColor color) {
        var component = stack.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT);
        stack.set(
            PastelDataComponentTypes.PAINTBRUSH, new PaintbrushComponent(
                component.mode(), Optional.ofNullable(color), component.brown(), component.greenPos(),
                component.greenDim()
            )
        );
    }

    public static Optional<InkColor> getColor(ItemStack stack) {
        return stack.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT)
                    .color();
    }

    public PaintbrushComponent.PaintbrushMode getMode(ItemStack stack) {
        return stack.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT)
                    .mode();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        var state = level.getBlockState(context.getClickedPos());
        if (state.getBlock() instanceof PaintbrushTriggered && context.getPlayer() != null && !context.getPlayer()
                                                                                                      .isShiftKeyDown()) {
            return InteractionResult.PASS; // paintbrush triggering is handled on the block itself so we no-op here
        }
        switch (getMode(context.getItemInHand())) {
            case INFO -> {
                var player = context.getPlayer();
                if (state.getBlock() instanceof PaintbrushInformed infoBlock && player != null) {
                    var message = infoBlock.getStatusBarInfo(level, context.getClickedPos(), player);
                    if (message != null) player.displayClientMessage(message, true);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            case PAINT -> {
                if (canPaint(context.getPlayer()) && tryColorBlock(context)) {
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
            case SPELL -> {
                var paintbrush = context.getItemInHand();
                var color = getColor(paintbrush);
                if (context.getPlayer() != null && canTrip(context.getPlayer()) && color.isPresent() && hasCost(
                    paintbrush, context.getPlayer(), color.get()) && tryBlockCantrip(context)) {
                    if (context.getPlayer() != null && getColor(paintbrush).isPresent() && tryDrainCost(
                        paintbrush, context.getPlayer(), getColor(paintbrush).get())) {

                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
        }
        return super.useOn(context);
    }

    private boolean tryBlockCantrip(UseOnContext context) {
        var paintbrush = context.getItemInHand();
        Optional<InkColor> inkColor = getColor(paintbrush);
        if (inkColor.isEmpty()) {
            return false;
        }
        var dyeColor = inkColor.get()
                               .getDyeColor();
        if (dyeColor.isEmpty()) return false;
        var player = context.getPlayer();
        if (player == null || !InkPowered.hasAvailableInk(player, inkColor.get(), CANTRIP_COST)) return false;
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var state = level.getBlockState(pos);
        var component = paintbrush.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT);
        var blockRegistry = context.getLevel()
                                   .registryAccess()
                                   .registryOrThrow(Registries.BLOCK);
        switch (dyeColor.get()) { // note: because java is fucked up and evil the stargazer colors will not be able
            // to be in here
            case MAGENTA -> { // "World’s worst tick acceleration"
                if (state.is(PastelBlockTags.TICK_ACCEL_BLACKLIST) || !(level instanceof ServerLevel sl)) return false;
                if (state.isRandomlyTicking()) {
                    state.randomTick(sl, pos, sl.getRandom());
                }
                state.tick(sl, pos, level.random);
                return false;
            }
            case BLUE -> { // "Temporary Blocks"
                var toPlace = pos.relative(context.getClickedFace());
                if (level.getBlockState(toPlace)
                         .canBeReplaced()) {
                    level.setBlock(
                        toPlace, PastelBlocks.TEMPORARY_PLATFORM.get()
                                                                .defaultBlockState(), Block.UPDATE_ALL
                    );
                    level.scheduleTick(toPlace, PastelBlocks.TEMPORARY_PLATFORM.get(), 600);
                    return true;
                }
                return false;
            }
            case ORANGE -> { // "Flint and Steel"
                Items.FLINT_AND_STEEL.useOn(context);
                return true;
            }
            case CYAN -> { // "Falling Block"
                var destroySpeed = state.getDestroySpeed(level, pos);
                if (!(level instanceof ServerLevel serverLevel) || destroySpeed == -1 || destroySpeed >= 50 ||
                    state.hasBlockEntity() || state.is(PastelBlockTags.REALLY_FALLING_BLOCK_BLACKLISTED)) {
                    return false;
                }
                var enchReg = level.registryAccess()
                                   .registry(Registries.ENCHANTMENT);
                if (enchReg.isEmpty()) return false;
                var resonance = enchReg.get()
                                       .getHolder(PastelEnchantments.RESONANCE);
                if (resonance.isEmpty()) return false;
                if (state.is(PastelBlockTags.FALLING_BLOCK_BLACKLISTED) && !Ench.hasEnchantment(
                    serverLevel.registryAccess(), PastelEnchantments.RESONANCE, paintbrush)) {
                    return false;
                }
                FallingBlockEntity.fall(serverLevel, pos, state);
                return true;
            }
            case YELLOW -> { // "Lightning"
                var offsetPos = pos.relative(context.getClickedFace());
                if (level.getBlockState(offsetPos)
                         .canBeReplaced()) {
                    level.setBlockAndUpdate(
                        offsetPos, PastelBlocks.ENERGETIC_MOTE.get()
                                                              .defaultBlockState()
                    );
                    level.scheduleTick(offsetPos, PastelBlocks.ENERGETIC_MOTE.get(), 2);
                    return true;
                }
                return false;
            }
            case LIME -> { // "Mutandis from the hit mod Witchery Resurrected"
                if (!(level instanceof ServerLevel sl)) return false;
                if (state.is(PastelBlockTags.MUTANDIS_BLACKLIST)) return false;
                for (TagKey<Block> tag : MUTANDIS_TAGS) {
                    if (state.is(tag)) {
                        var tagEntries = blockRegistry.getTag(tag);
                        if (tagEntries.isEmpty()) continue;
                        var list = tagEntries.get().contents;
                        var index = list.indexOf(state.getBlockHolder()) + 1;
                        if (index >= list.size()) index = 0;
                        // if you blacklist an entire tag and cause an infinite loop here: i hate you so much
                        while (list.get(index)
                                   .is(PastelBlockTags.MUTANDIS_BLACKLIST)) index = (index + 1) % list.size();
                        var holder = list.get(index)
                                         .unwrap();
                        // waugh. this is needed so that mutating from a double block (like peonies) to a single block
                        // (like poppies) doesn't leave floating half-flowers
                        BlockPos finalPos = (state.getBlock() instanceof DoublePlantBlock && state.getValue(
                            DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) ? pos.below() : pos;
                        if (state.getBlock() instanceof DoublePlantBlock) level.setBlock(
                            pos.above(), Blocks.AIR.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS);
                        holder.ifLeft(blockResourceKey -> {
                                  var newBlock = blockRegistry.get(blockResourceKey);
                                  if (newBlock != null) mutate(finalPos, newBlock, sl);
                              })
                              .ifRight(block1 -> {
                                  mutate(finalPos, block1, sl);
                              });
                        return true;
                    }
                }
                return false;
            }
            case PINK -> { // "Touch Heal"
                if (!HealingRecipe.processBlock(level, pos, state)) {
                    var ret = false;
                    var items = level.getEntitiesOfClass(
                        ItemEntity.class, AABB.encapsulatingFullBlocks(
                            pos.relative(context.getClickedFace()), pos.relative(context.getClickedFace()))
                    );
                    for (var i : items) {
                        ret |= HealingRecipe.processItemEntity(level, i);
                    }
                    return ret;
                }
                return true;
            }
            case GRAY -> { // “Degrades” blocks into similar weaker blocks
                if (!DegradingRecipe.processBlock(level, pos, state)) {
                    var ret = false;
                    var items = level.getEntitiesOfClass(
                        ItemEntity.class, AABB.encapsulatingFullBlocks(
                            pos.relative(context.getClickedFace()), pos.relative(context.getClickedFace()))
                    );
                    for (var i : items) {
                        ret |= DegradingRecipe.processItemEntity(level, i);
                    }
                    return ret;
                }
                return true;
            }
            case GREEN -> {
                if (component.greenPos()
                             .isPresent() && component.greenPos()
                                                      .get()
                                                      .equals(pos))
                    return false; // no charge if you try to set it to the same block
                paintbrush.set(
                    PastelDataComponentTypes.PAINTBRUSH, new PaintbrushComponent(
                        component.mode(), component.color(), component.brown(), Optional.of(pos), level.dimension()
                                                                                                       .location()
                                                                                                       .toString()
                    )
                );
                return true;
            }
            case LIGHT_GRAY, LIGHT_BLUE, BLACK, PURPLE, RED, BROWN -> { // These don't do anything on blocks
                return false;
            }
            case null, default -> throw new IllegalStateException(
                "Unimplemented color, yell at lily (unless this is from an addon in which case yell at them): " +
                inkColor.get());
        }
    }

    private void mutate(BlockPos pos, Block block, ServerLevel level) {
        if (block instanceof DoublePlantBlock) {
            DoublePlantBlock.placeAt(
                level, block.defaultBlockState(), pos, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
        } else {
            level.setBlock(pos, block.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
        }
    }

    private boolean tryColorBlock(UseOnContext context) {
        Optional<InkColor> inkColor = getColor(context.getItemInHand());
        if (inkColor.isEmpty()) {
            return false;
        }
        Optional<DyeColor> dyeColor = inkColor.get()
                                              .getDyeColor();

        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof ColorableBlock colorableBlock) {
            if (!colorableBlock.isColor(world, pos, dyeColor)) {
                if (payBlockColorCost(context.getPlayer(), inkColor.get()) && colorableBlock.color(
                    world, pos, dyeColor, context.getPlayer())) {
                    context.getLevel()
                           .playSound(
                               null, context.getClickedPos(), PastelSounds.PAINTBRUSH_PAINT, SoundSource.BLOCKS, 1.0F,
                               1.0F
                           );
                } else {
                    context.getLevel()
                           .playSound(
                               null, context.getClickedPos(), PastelSounds.USE_FAIL, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            return false;
        }

        return cursedColor(context);
    }

    private boolean cursedColor(UseOnContext context) {
        Level world = context.getLevel();
        if (context.getPlayer() == null) {
            return false;
        }

        Optional<InkColor> optionalInkColor = getColor(context.getItemInHand());
        if (optionalInkColor.isEmpty()) {
            return false;
        }

        InkColor inkColor = optionalInkColor.get();
        Optional<DyeColor> optionalDyeColor = inkColor.getDyeColor();
        if (optionalDyeColor.isEmpty()) {
            return false;
        }
        DyeColor dyeColor = optionalDyeColor.get();

        BlockState newBlockState = BlockVariantHelper.getCursedBlockColorVariant(
            context.getLevel(), context.getClickedPos(), dyeColor);
        if (newBlockState.isAir()) {
            return false;
        }

        if (payBlockColorCost(context.getPlayer(), inkColor)) {
            if (!world.isClientSide) {
                world.setBlockAndUpdate(context.getClickedPos(), newBlockState);
                world.playSound(
                    null, context.getClickedPos(), PastelSounds.PAINTBRUSH_PAINT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return true;
        } else {
            if (world.isClientSide) {
                context.getPlayer()
                       .playSound(PastelSounds.USE_FAIL, 1.0F, 1.0F);
            }
        }
        return false;
    }

    private boolean payBlockColorCost(Player player, InkColor inkColor) {
        if (player == null) {
            return false;
        }
        if (player.isCreative()) {
            return true;
        }
        if (InkPowered.tryDrainEnergy(player, inkColor, BLOCK_COLOR_COST)) {
            return true;
        }
        Optional<DyeColor> dyeColor = inkColor.getDyeColor();
        if (dyeColor.isEmpty()) {
            return false;
        }
        return InventoryHelper.removeFromInventoryWithRemainders(
            player, PigmentItem.byColor(inkColor)
                               .getDefaultInstance()
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var paintbrush = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (player instanceof ServerPlayer serverPlayerEntity) {
                if (canPaint(serverPlayerEntity)) {
                    serverPlayerEntity.openMenu(createScreenHandlerFactory(player.getItemInHand(hand)));
                }
            }
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        } else if (canTrip(player) && getColor(paintbrush).isPresent() && hasCost(
            paintbrush, player, getColor(paintbrush).get()) && tryAirCantrip(paintbrush, level, player, hand)) {
            tryDrainCost(paintbrush, player, getColor(paintbrush).get());
            return InteractionResultHolder.sidedSuccess(paintbrush, level.isClientSide);
        }
        return super.use(level, player, hand);
    }

    private void causeKnockback(Player user, float yaw, float pitch, float roll, float multiplier) {
        float f = Mth.sin(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F) * multiplier;
        float g = Mth.sin((pitch + roll) * 0.017453292F) * multiplier;
        float h = -Mth.cos(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F) * multiplier;
        user.push(f, g, h);
    }

    private boolean hasCost(ItemStack stack, Player player, InkColor color) {
        if (color == InkColors.BROWN) {
            return true; // brown doesn't drain anything since it's just a toggle
        }
        var inkCost = CANTRIP_COST;
        var brownCost = 0;
        if (stack.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT)
                 .brown()) {
            inkCost = inkCost / 2;
            brownCost = inkCost;
        }
        return InkPowered.hasAvailableInk(player, color, inkCost) && (brownCost == 0 || InkPowered.hasAvailableInk(
            player, InkColors.BROWN, inkCost / 4));
    }

    private boolean tryDrainCost(ItemStack stack, Player player, InkColor color) {
        if (color == InkColors.BROWN) {
            return true; // brown doesn't drain anything since it's just a toggle
        }
        var inkCost = CANTRIP_COST;
        var brownCost = 0;
        if (stack.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT)
                 .brown()) {
            inkCost = inkCost / 2;
            brownCost = inkCost / 2;
        }
        // this is only called on a successful cantrip cast soooo
        player.level()
              .playSound(null, BlockPos.containing(player.position()), PastelSounds.CAST_RADIANCE, SoundSource.PLAYERS);
        return InkPowered.tryDrainEnergy(player, color, inkCost) && InkPowered.tryDrainEnergy(
            player, InkColors.BROWN, brownCost);
    }

    private boolean tryAirCantrip(ItemStack stack, Level level, Player player, InteractionHand hand) {
        Optional<InkColor> inkColor = getColor(stack);
        if (inkColor.isEmpty()) {
            return false;
        }
        var dyeColor = inkColor.get()
                               .getDyeColor();
        if (dyeColor.isEmpty()) return false;
        switch (dyeColor.get()) {
            case RED -> {
                if (!level.isClientSide) {
                    InkProjectileEntity.shoot(level, player, inkColor.get());
                }
                if (!player.isCreative()) {
                    causeKnockback(player, player.getYRot(), player.getXRot(), 0.1F, 0.5F);
                }
                return true;
            }
            case PINK -> {
                if (player.getHealth() == player.getMaxHealth()) return false;
                player.heal(PINK_HEAL_AMOUNT);
                return true;
            }
            case BLACK -> {
                var done = false;
                for (var item : level.getEntitiesOfClass(
                    ItemEntity.class,
                    AABB.ofSize(
                        player.position(), ITEM_VACUUM_RANGE, ITEM_VACUUM_RANGE,
                        ITEM_VACUUM_RANGE
                    )
                )) {
                    item.teleportTo(player.getX(), player.getY(), player.getZ());
                    done = true;
                }
                return done;
            }
            case BROWN -> {
                var component = stack.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT);
                stack.set(
                    PastelDataComponentTypes.PAINTBRUSH, new PaintbrushComponent(
                        component.mode(), component.color(), !component.brown(), component.greenPos(),
                        component.greenDim()
                    )
                );
                return true;
            }
            case GREEN -> {
                if (!(player instanceof ServerPlayer serverPlayer)) return false;
                var component = stack.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT);
                var optionalPos = component.greenPos();
                if (optionalPos.isEmpty() || component.greenDim()
                                                      .isEmpty()) return false;
                var pos = optionalPos.get();
                if (!PastelCommon.isSameDimension(level, component.greenDim()) || !level.isLoaded(pos)) return false;
                // we are in the same "lore dimension" but not the same level, so we need to get the right level
                if (!level.dimension()
                          .location()
                          .toString()
                          .equals(component.greenDim())) {
                    if (level.getServer() == null) return false;
                    switch (component.greenDim()) {
                        case "minecraft:overworld" -> level = level.getServer()
                                                                   .getLevel(Level.OVERWORLD);
                        case "pastel:imbrifer" -> level = level.getServer()
                                                               .getLevel(PastelLevels.DIMENSION_KEY);
                    }
                }
                if (level == null) return false;
                serverPlayer.gameMode.useItemOn(
                    serverPlayer, level, stack, hand,
                    new BlockHitResult(pos.getCenter(), player.getNearestViewDirection(), pos, false)
                );
                return true;
            }
            case BLUE -> {
                BlockHitResult hitResult = raycast(level, player, stack, hand);
                if (hitResult == null || !level.getBlockState(hitResult.getBlockPos())
                                               .canBeReplaced()) return false;
                var pos = hitResult.getBlockPos()
                                   .relative(player.getNearestViewDirection()
                                                   .getOpposite());
                level.setBlock(
                    pos, PastelBlocks.TEMPORARY_PLATFORM.get()
                                                        .defaultBlockState(),
                    Block.UPDATE_ALL
                );
                level.scheduleTick(pos, PastelBlocks.TEMPORARY_PLATFORM.get(), 600);
                return false;
            }
            case LIGHT_BLUE -> {
                if (!(level instanceof ServerLevel serverLevel) || !(player instanceof ServerPlayer serverPlayer))
                    return false;
                BlockHitResult hitResult = raycast(level, player, stack, hand);
                if (hitResult == null) return false;
                // shouldn't be possible, but hey, you never know
                if (!serverLevel.isLoaded(hitResult.getBlockPos())) return false;
                serverPlayer.gameMode.useItemOn(serverPlayer, level, stack, hand, hitResult);
                return true;
            }
            // these need an actual target
            case CYAN, MAGENTA, YELLOW, ORANGE, LIME, LIGHT_GRAY, GRAY, PURPLE -> {
                return false;
            }
            default -> {
                throw new IllegalStateException(
                    "Unimplemented color, yell at lily (unless this is from an addon in which case yell at them): " +
                    inkColor.get());
            }
        }
    }

    private BlockHitResult raycast(Level level, Player player, ItemStack stack, InteractionHand hand) {
        if (!(level instanceof ServerLevel serverLevel) || !(player instanceof ServerPlayer serverPlayer)) return null;
        int reachDistance = (serverLevel.getChunkSource().distanceManager.simulationDistance - 1) * 16;
        return level.clip(new ClipContext(
            player.getEyePosition(), player.getEyePosition()
                                           .add(player.getViewVector(0f)
                                                      .normalize()
                                                      .scale(reachDistance)), ClipContext.Block.OUTLINE,
            ClipContext.Fluid.NONE, player
        ));
    }

    @Override
    public InteractionResult interactLivingEntity(
        ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        Level level = player.level();
        switch (getMode(stack)) {
            case INFO -> {
                if (entity instanceof PaintbrushInformed infoBlock) {
                    var message = infoBlock.getStatusBarInfo(level, entity.blockPosition(), player);
                    if (message != null) player.displayClientMessage(message, true);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            case PAINT -> {
                if (canPaint(player) && GenericClaimModsCompat.canInteract(entity.level(), entity, player)) {
                    Optional<InkColor> color = getColor(stack);

                    if (color.isPresent() && payBlockColorCost(player, color.get()) &&
                        EntityColorProcessorRegistry.colorEntity(
                            entity, color.get()
                                         .getDyeColor(), entity instanceof Player targetPlayer ? targetPlayer : null
                        )) {
                        entity.level()
                              .playSound(null, entity, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
            case SPELL -> {
                if (canTrip(player) && getColor(stack).isPresent() && hasCost(stack, player, getColor(stack).get()) &&
                    tryEntityCantrip(stack, player, entity, hand)) {
                    tryDrainCost(stack, player, getColor(stack).get());
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return super.interactLivingEntity(stack, player, entity, hand);
    }

    private boolean tryEntityCantrip(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        Optional<InkColor> inkColor = getColor(stack);
        if (inkColor.isEmpty()) {
            return false;
        }
        var dyeColor = inkColor.get()
                               .getDyeColor();
        if (dyeColor.isEmpty()) return false;
        switch (dyeColor.get()) {
            case LIGHT_GRAY -> {
                if (entity instanceof AgeableMob mob) {
                    mob.ageUp(AgeableMob.getSpeedUpSecondsWhenFeeding(-mob.getAge()), true);
                    return true;
                }
            }
            case PINK -> {
                entity.heal(PINK_HEAL_AMOUNT);
                return true;
            }
            case ORANGE -> {
                entity.igniteForSeconds(5);
                entity.level()
                      .playSound(
                          null, entity.blockPosition(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F,
                          entity.level()
                                .getRandom()
                                .nextFloat() * 0.4F + 0.8F
                      );
                return true;
            }
            case PURPLE -> {
                return entity.addEffect(
                    new MobEffectInstance(PastelMobEffects.TRUE_INVISIBILITY, 30 * 20, 0, false, false));
            }
            case YELLOW -> {
                entity.hurt(PastelDamageTypes.electric(entity.level()), 2f);
            }
            // these don't do anything to entities
            case CYAN, MAGENTA, BLACK, BLUE, LIME, LIGHT_BLUE, RED, BROWN, GREEN, GRAY -> {
                return false;
            }
            default -> {
                throw new IllegalStateException(
                    "Unimplemented color, yell at lily (unless this is from an addon in which case yell at them): " +
                    inkColor.get());
            }
        }
        return false;
    }

    @Override
    public boolean tryApplyToSign(Level world, SignBlockEntity signBlockEntity, boolean front, Player player) {
        if (tryUseOnSign(world, signBlockEntity, front, player, player.getItemInHand(InteractionHand.MAIN_HAND)))
            return true;
        if (tryUseOnSign(world, signBlockEntity, front, player, player.getItemInHand(InteractionHand.OFF_HAND)))
            return true;

        player.playSound(PastelSounds.USE_FAIL, 1.0F, 1.0F);
        return false;
    }

    // TODO: can this be moved to ColorableBlock / as a block color processor?
    private boolean tryUseOnSign(
        Level world, SignBlockEntity signBlockEntity, boolean front, Player player, ItemStack stack) {
        if (stack.is(PastelItems.PAINTBRUSH.get())) {
            Optional<InkColor> color = getColor(stack);
            if (color.isPresent()) {
                InkColor inkColor = color.get();
                Optional<DyeColor> dyeColor = inkColor.getDyeColor();

                if (canPaint(player) && payBlockColorCost(player, inkColor)) {
                    if (signBlockEntity.updateText(
                        signText -> {
                            if (dyeColor.isPresent()) {
                                return signText.setColor(dyeColor.get());
                            }
                            return signText;
                        }, front
                    )) {
                        world.playSound(
                            null, signBlockEntity.getBlockPos(), PastelSounds.PAINTBRUSH_PAINT, SoundSource.BLOCKS,
                            1.0F, 1.0F
                        );
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
