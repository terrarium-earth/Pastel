package earth.terrarium.pastel.items.magic_items;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.api.block.ColorableBlock;
import earth.terrarium.pastel.api.block.PaintbrushInformed;
import earth.terrarium.pastel.api.block.PaintbrushTriggered;
import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.interaction.EntityColorProcessorRegistry;
import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.compat.claims.GenericClaimModsCompat;
import earth.terrarium.pastel.components.PaintbrushComponent;
import earth.terrarium.pastel.entity.entity.InkProjectileEntity;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.helpers.level.BlockVariantHelper;
import earth.terrarium.pastel.helpers.level.MobEffectHelper;
import earth.terrarium.pastel.inventories.PaintbrushScreenHandler;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.util.thread.EffectiveSide;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PaintbrushItem extends Item implements SignApplicator {

    public static final int COOLDOWN_DURATION_TICKS = 10;
    public static final int BLOCK_COLOR_COST = 25;
    public static final int INK_SLING_COST = 100;
    public static final int CANTRIP_COST = 50;
    public static final int ITEM_VACUUM_RANGE = 16; // todo move to config?
    public static final float PINK_HEAL_AMOUNT = 4f;
    public static final TagKey<Block>[] MUTANDIS_TAGS = new TagKey[]{
        BlockTags.FLOWERS, BlockTags.SAPLINGS, BlockTags.LEAVES, BlockTags.CROPS, BlockTags.CORALS,
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
        if (data.mode() != PaintbrushComponent.PaintbrushMode.INFO && data.color()
                                                                          .isPresent()) {
            tooltip.add(Component.translatable("item.pastel.paintbrush.tooltip.color.selected")
                                 .append(data.color()
                                             .get()
                                             .getColoredName()));
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
        stack.set(
            PastelDataComponentTypes.PAINTBRUSH, new PaintbrushComponent(
                stack.getOrDefault(PastelDataComponentTypes.PAINTBRUSH, PaintbrushComponent.DEFAULT)
                     .mode(), Optional.ofNullable(color)
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
                var color = getColor(context.getItemInHand());
                if (context.getPlayer() != null && canTrip(context.getPlayer()) && color.isPresent() &&
                    InkPowered.hasAvailableInk(
                        context.getPlayer(), new InkCost(color.get(), CANTRIP_COST)) && tryBlockCantrip(context)) {
                    if (context.getPlayer() != null && getColor(context.getItemInHand()).isPresent())
                        InkPowered.tryDrainEnergy(
                            context.getPlayer(), getColor(context.getItemInHand()).get(), CANTRIP_COST);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return super.useOn(context);
    }

    private boolean tryBlockCantrip(UseOnContext context) {
        Optional<InkColor> inkColor = getColor(context.getItemInHand());
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
        var block = state.getBlock();
        var blockRegistry = context.getLevel()
                                   .registryAccess()
                                   .registryOrThrow(Registries.BLOCK);
        switch (dyeColor.get()) { // note: because java is fucked up and evil the stargazer colors will not be able
            // to be in here
            case DyeColor.MAGENTA -> { // "World’s worst tick acceleration"
                if (state.isRandomlyTicking()) {
                    if (level instanceof ServerLevel serverLevel) {
                        state.randomTick(serverLevel, pos, serverLevel.getRandom());
                    }
                    return true;
                }
                return false;
            }
            case DyeColor.BLACK -> { // "Item Vacuum"
                if (level instanceof ServerLevel serverLevel) {
                    for (var i : serverLevel.getEntitiesOfClass(
                        ItemEntity.class, new AABB(pos).inflate(ITEM_VACUUM_RANGE))) {
                        i.moveTo(player.position());
                    }
                }
                return true;
            }
            case DyeColor.BLUE -> { // "Temporary Blocks"
                // todo actually impl this
                return true;
            }
            case DyeColor.ORANGE -> { // "Flint and Steel"
                Items.FLINT_AND_STEEL.useOn(context);
                return true;
            }
            case DyeColor.CYAN -> { // "Falling Block"
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
                    serverLevel.registryAccess(), PastelEnchantments.RESONANCE, context.getItemInHand())) {
                    return false;
                }
                FallingBlockEntity.fall(serverLevel, pos, state);
                return true;
            }
            case DyeColor.YELLOW -> { // "Lightning"
                var offsetPos = pos.relative(context.getClickedFace());
                if (level.getBlockState(offsetPos)
                         .canBeReplaced()) {
                    level.setBlockAndUpdate(
                        offsetPos, PastelBlocks.ENERGETIC_MOTE.get()
                                                              .defaultBlockState()
                    );
                    return true;
                }
                return false;
            }
            case DyeColor.LIME -> { // "Mutandis from the hit mod Witchery Resurrected"
                if (!(level instanceof ServerLevel sl)) return false;
                if (state.is(PastelBlockTags.MUTANDIS_BLACKLIST)) return false;
                for (TagKey<Block> tag : MUTANDIS_TAGS) {
                    if (state.is(tag)) {
                        var tagEntries = blockRegistry.getTag(tag);
                        if (tagEntries.isEmpty()) continue;
                        var list = tagEntries.get().contents;
                        var index = list.indexOf(state.getBlockHolder()) + 1;
                        if (index >= list.size()) index = 0;
                        if (list.get(index)
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
            case DyeColor.PINK -> { // "Touch Heal"
                if (!state.is(PastelBlockTags.CRACKED_BLOCKS) || state.hasBlockEntity()) return false;
                // fucked up and evil
                var blockRegistryResult = level.registryAccess()
                                               .registry(Registries.BLOCK);
                if (blockRegistryResult.isEmpty()) return false;
                var blocks = blockRegistryResult.get();
                var blockKey = blocks.getResourceKey(block);
                if (blockKey.isEmpty()) return false;
                var blockLoc = blockKey.get()
                                       .location();
                var newBlock = blocks.get(ResourceLocation.fromNamespaceAndPath(
                    blockLoc.getNamespace(), blockLoc.getPath()
                                                     .replaceFirst("cracked_", "")
                ));
                if (newBlock == null) return false;
                level.setBlock(pos, newBlock.withPropertiesOf(state), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                return true;
            }
            case DyeColor.LIGHT_GRAY, DyeColor.LIGHT_BLUE -> { // These don't do anything on blocks
                return false;
            }
            case null, default -> throw new IllegalStateException(
                "Unimplemented color, yell at lily (unless this is from an addon in which case yell at them): " +
                inkColor.get());
        }
    }

    private void mutate(BlockPos pos, Block block, ServerLevel level) {
        if (block instanceof DoublePlantBlock doublePlantBlock) {
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
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (user.isShiftKeyDown()) {
            if (user instanceof ServerPlayer serverPlayerEntity) {
                if (canPaint(serverPlayerEntity)) {
                    serverPlayerEntity.openMenu(createScreenHandlerFactory(user.getItemInHand(hand)));
                }
            }
            return InteractionResultHolder.pass(user.getItemInHand(hand));
        } else if (canTrip(user)) {
            Optional<InkColor> optionalInkColor = getColor(user.getItemInHand(hand));
            if (optionalInkColor.isPresent()) {

                InkColor inkColor = optionalInkColor.get();
                if (user.isCreative() || InkPowered.tryDrainEnergy(user, inkColor, INK_SLING_COST)) {
                    user.getCooldowns()
                        .addCooldown(this, COOLDOWN_DURATION_TICKS);

                    if (!world.isClientSide) {
                        InkProjectileEntity.shoot(world, user, inkColor);
                    }
                    // cause the slightest bit of knockback (more if Red)
                    if (!user.isCreative()) {
                        if (inkColor == InkColors.RED) {
                            causeKnockback(user, user.getYRot(), user.getXRot(), 0.1F, 0.5F);
                        } else {
                            causeKnockback(user, user.getYRot(), user.getXRot(), 0, 0.3F);
                        }
                    }
                } else {
                    if (world.isClientSide) {
                        user.playSound(PastelSounds.USE_FAIL, 1.0F, 1.0F);
                    }
                }

                return InteractionResultHolder.pass(user.getItemInHand(hand));
            }
        }
        return super.use(world, user, hand);
    }

    private void causeKnockback(Player user, float yaw, float pitch, float roll, float multiplier) {
        float f = Mth.sin(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F) * multiplier;
        float g = Mth.sin((pitch + roll) * 0.017453292F) * multiplier;
        float h = -Mth.cos(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F) * multiplier;
        user.push(f, g, h);
    }

    @Override
    public InteractionResult interactLivingEntity(
        ItemStack stack, Player player, LivingEntity entity,
        InteractionHand hand
    ) {
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
                if (canTrip(player) && getColor(stack).isPresent() && InkPowered.hasAvailableInk(
                    player, getColor(stack).get(), CANTRIP_COST) && tryEntityCantrip(stack, player, entity, hand)) {
                    InkPowered.tryDrainEnergy(player, getColor(stack).get(), CANTRIP_COST);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return super.interactLivingEntity(stack, player, entity, hand);
    }

    private boolean tryEntityCantrip(
        ItemStack stack, Player player, LivingEntity entity,
        InteractionHand hand
    ) {
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
                      .playSound(null, entity.blockPosition(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F,
                                 entity.level()
                                       .getRandom()
                                       .nextFloat() * 0.4F + 0.8F
                      );
                return true;
            }
            case PURPLE -> {
                return entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 30 * 20, 0, false, false));
            }
            case YELLOW -> {
                // lightning damage, todo
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
                            null, signBlockEntity.getBlockPos(), PastelSounds.PAINTBRUSH_PAINT,
                            SoundSource.BLOCKS, 1.0F, 1.0F
                        );
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
