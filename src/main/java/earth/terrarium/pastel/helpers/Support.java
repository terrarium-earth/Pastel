package earth.terrarium.pastel.helpers;

import com.cmdpro.databank.DatabankUtils;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.progression.PastelCriteria;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastBufferedInputStream;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Support {

    public static float varFloat(RandomSource random, float variance) {
        return (1F - variance) + random.nextFloat() * variance;
    }

    public static float varFloatCentered(RandomSource random, float variance) {
        return 1F + random.nextFloat() * variance - variance / 2F;
    }

    public static HitResult playerBlockInteractionRaycast(Level world, LivingEntity user, Player player) {
        double maxDistance = getBlockReachDistance(player);
        Vec3 eyePos = user.getEyePosition();
        Vec3 rotationVec = user.getViewVector(0F);
        Vec3 vec3d3 = eyePos.add(rotationVec.x * maxDistance, rotationVec.y * maxDistance, rotationVec.z * maxDistance);
        return world.clip(new ClipContext(eyePos, vec3d3, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }

    public static float getBlockReachDistance(Player player) {
        return (player.isCreative() ? 5.0F : 4.5F) + (float) player
            .getAttributeValue(
                Attributes.BLOCK_INTERACTION_RANGE
            );
    }

    public static float refineDamage(float damage) {
        damage = Math.clamp(damage, 0, Float.MAX_VALUE / 2F);
        damage = Math.round(damage);
        return damage;
    }

    public static final DecimalFormat DF = new DecimalFormat("0");

    public static final DecimalFormat DF1 = new DecimalFormat("0.0");

    public static final DecimalFormat DF2 = new DecimalFormat("0.00");

    static {
        DF.setRoundingMode(RoundingMode.DOWN);
        DF1.setRoundingMode(RoundingMode.DOWN);
        DF2.setRoundingMode(RoundingMode.DOWN);
    }

    @Nullable @SuppressWarnings(
        "unchecked"
    )
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(
        BlockEntityType<A> givenType,
        BlockEntityType<E> expectedType,
        BlockEntityTicker<? super E> ticker
    ) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    public static @NotNull Optional<TagKey<Block>> getFirstMatchingBlockTag(
        @NotNull BlockState blockState,
        @NotNull List<TagKey<Block>> tags
    ) {
        return blockState
            .getTags()
            .filter(tags::contains)
            .findFirst();
    }

    public static String getWithOneDecimalAfterComma(float number) {
        return DF1.format(number);
    }

    public static String getShortenedNumberString(double number) {
        if (number > 1000000000D) {
            return DF2.format(number / 1000000000D) + "G";
        } else if (number > 1000000D) {
            return DF2.format(number / 1000000D) + "M";
        } else if (number > 1000D) {
            return DF2.format(number / 1000D) + "K";
        } else {
            return DF.format(number);
        }
    }

    public static String getShortenedNumberString(long number) {
        if (number > 1000000000L) {
            return DF2.format(number / 1000000000D) + "G";
        } else if (number > 1000000L) {
            return DF2.format(number / 1000000D) + "M";
        } else if (number > 1000L) {
            return DF2.format(number / 1000D) + "K";
        } else {
            return DF.format(number);
        }
    }

    /**
     * Calculates the percentage of x / y from 0-100, but in a way it feels logical to players
     * If x > 0 the result is always at least 1%,
     * If it approaches 100%, but is not exactly 100%, returns 99
     */
    public static String getSensiblePercentString(long x, long y) {
        if (y == 0) {
            return "0";
        }

        double result = (double) x / y;
        if (result < 0.01 && x > 0) {
            return "1";
        } else if (result > 0.99 && x != y) {
            return "99";
        } else {
            return DF.format(Math.round(result * 100L));
        }
    }

    public static int getSensiblePercent(long x, long y, int max) {
        if (y == 0) {
            return 0;
        }

        int result = (int) Mth.clampedLerp(0, max, (double) x / y);
        if (result < 1 && x > 0) {
            return 1;
        } else if (result == max && x != y) {
            return max - 1;
        } else {
            return result;
        }
    }

    public static int chanceRound(double d, @NotNull RandomSource random) {
        boolean roundUp = (random.nextFloat() < d % 1);
        if (roundUp) {
            return ((int) d) + 1;
        } else {
            return (int) d;
        }
    }

    public static final int HH_RANGE = 20;

    public static final int H_RANGE = 16;

    public static final int M_RANGE = 12;

    public static final int L_RANGE = 8;

    public static void areaCriterion(
        ServerLevel level,
        int radius,
        BlockPos center,
        Optional<ResourceLocation> prerequisite,
        Consumer<ServerPlayer> trigger
    ) {
        for (
            Entity proposal : level
                .getEntities(
                    null,
                    AABB.ofSize(center.getCenter(), radius + 0.5, radius + 0.5, radius + 0.5)
                )
        ) {

            if (!(proposal instanceof ServerPlayer player) || prerequisite
                .map(a -> !DatabankUtils.hasAdvancement(player, a))
                .orElse(false))
                continue;

            trigger.accept(player);
        }
    }

    public static void areaCriterion(
        ServerLevel level,
        int radius,
        BlockPos center,
        ResourceLocation prerequisite,
        Consumer<ServerPlayer> trigger
    ) {
        areaCriterion(level, radius, center, Optional.of(prerequisite), trigger);
    }

    public static void mbCriterion(ServerLevel level, BlockPos center, Multiblock multiblock) {
        Support
            .areaCriterion(
                level,
                Support.HH_RANGE,
                center,
                Optional.empty(),
                p -> PastelCriteria.COMPLETED_MULTIBLOCK.trigger(p, multiblock)
            );
    }

    /**
     * Returns a relative new BlockPos based on a facing direction and a vector
     *
     * @param origin           the source position
     * @param forwardUpRight   a vector specifying the amount of blocks forward, up and right
     * @param horizontalFacing the facing direction
     * @return the blockpos with forwardUpRight offset from origin when facing horizontalFacing
     */
    public static BlockPos directionalOffset(
        BlockPos origin,
        Vec3i forwardUpRight,
        @NotNull Direction horizontalFacing
    ) {
        switch (horizontalFacing) {
            case NORTH -> {
                return origin.offset(forwardUpRight.getZ(), forwardUpRight.getY(), -forwardUpRight.getX());
            }
            case EAST -> {
                return origin.offset(forwardUpRight.getX(), forwardUpRight.getY(), forwardUpRight.getZ());
            }
            case SOUTH -> {
                return origin.offset(-forwardUpRight.getZ(), forwardUpRight.getY(), forwardUpRight.getX());
            }
            case WEST -> {
                return origin.offset(-forwardUpRight.getX(), forwardUpRight.getY(), -forwardUpRight.getZ());
            }
            default -> {
                PastelCommon
                    .logWarning(
                        "Called directionalOffset with facing" + horizontalFacing + " this is not supported."
                    );
                return origin;
            }
        }
    }

    public static void grantAdvancementCriterion(
        @NotNull ServerPlayer serverPlayerEntity,
        ResourceLocation advancementIdentifier,
        String criterion
    ) {
        if (serverPlayerEntity.getServer() == null) {
            return;
        }
        ServerAdvancementManager sal = serverPlayerEntity
            .getServer()
            .getAdvancements();
        PlayerAdvancements tracker = serverPlayerEntity.getAdvancements();

        AdvancementHolder advancement = sal.get(advancementIdentifier);
        if (advancement == null) {
            PastelCommon
                .logError(
                    "Trying to grant a criterion \"" + criterion + "\" for an advancement that does not exist: " + advancementIdentifier
                );
        } else {
            if (!tracker
                .getOrStartProgress(advancement)
                .isDone()) {
                tracker.award(advancement, criterion);
            }
        }
    }

    public static void grantAdvancementCriterion(
        @NotNull ServerPlayer serverPlayerEntity,
        String advancementString,
        String criterion
    ) {
        grantAdvancementCriterion(serverPlayerEntity, PastelCommon.locate(advancementString), criterion);
    }

    public static @NotNull String getReadableDimensionString(@NotNull String dimensionKeyString) {
        switch (dimensionKeyString) {
            case "minecraft:overworld" -> {
                return "Overworld";
            }
            case "minecraft:nether" -> {
                return "Nether";
            }
            case "minecraft:end" -> {
                return "End";
            }
            case "pastel:imbrifer" -> {
                return "Imbrifer";
            }
            default -> {
                if (dimensionKeyString.contains(":")) {
                    return dimensionKeyString.substring(dimensionKeyString.indexOf(":") + 1);
                } else {
                    return dimensionKeyString;
                }
            }
        }
    }

    @Contract(
        pure = true
    )
    public static Direction directionFromRotation(@NotNull Rotation blockRotation) {
        switch (blockRotation) {
            case NONE -> {
                return Direction.NORTH;
            }
            case CLOCKWISE_90 -> {
                return Direction.EAST;
            }
            case CLOCKWISE_180 -> {
                return Direction.SOUTH;
            }
            default -> {
                return Direction.WEST;
            }
        }
    }

    @Contract(
        pure = true
    )
    public static Rotation rotationFromDirection(@NotNull Direction direction) {
        switch (direction) {
            case EAST -> {
                return Rotation.CLOCKWISE_90;
            }
            case SOUTH -> {
                return Rotation.CLOCKWISE_180;
            }
            case WEST -> {
                return Rotation.COUNTERCLOCKWISE_90;
            }
            default -> {
                return Rotation.NONE;
            }
        }
    }

    public static Optional<BlockPos> getNexReplaceableBlockPosUpDown(Level world, BlockPos blockPos, int maxUpDown) {
        if (world
            .getBlockState(blockPos)
            .canBeReplaced()) {
            // search down
            for (
                int i = 0;
                i < maxUpDown;
                i++
            ) {
                if (!world
                    .getBlockState(blockPos.below(i + 1))
                    .canBeReplaced()) {
                    return Optional.of(blockPos.below(i));
                }
            }
        } else {
            // search up
            for (
                int i = 1;
                i <= maxUpDown;
                i++
            ) {
                if (world
                    .getBlockState(blockPos.above(i))
                    .canBeReplaced()) {
                    return Optional.of(blockPos.above(i));
                }
            }
        }
        return Optional.empty();
    }

    public static double logBase(double base, double logNumber) {
        return Math.log(logNumber) / Math.log(base);
    }

    private static boolean migrationActioned = false;

    /**
     * We have DFU at home
     */
    public static void migrateStructureNBT(MinecraftServer server) {
        if (false) {
            var man = server.getStructureManager();
            var sex = new FileToIdConverter("structure", ".nbt");
            man
                .listTemplates()
                .filter(
                    l -> l
                        .getNamespace()
                        .equals(PastelCommon.MOD_ID)
                )
                .forEach(l -> {
                    var opt = man.get(l);
                    if (opt.isEmpty())
                        return;

                    var res = server.getResourceManager();
                    var loc = sex.idToFile(l);
                    FileOutputStream write = null;
                    try {
                        var stream = new FastBufferedInputStream(res.open(loc));
                        var structTag = NbtIo.readCompressed(stream, NbtAccounter.unlimitedHeap());
                        replaceModId(structTag);
                        var dev = server.getWorldPath(new LevelResource("dev"));
                        var path = dev.resolve(loc.getPath());
                        Files.createDirectories(path.getParent());
                        write = new FileOutputStream(path.toFile());
                        NbtIo.writeCompressed(structTag, write);
                    } catch (Exception logged) {
                        PastelCommon.LOGGER.error("piss", logged);
                    }
                    try {
                        if (write != null) {
                            write.close();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            migrationActioned = true;
        }
    }

    private static void replaceModId(ListTag tag) {
        for (
            int i = 0;
            i < tag.size();
            i++
        ) {
            Tag element = tag.get(i);

            if (element instanceof StringTag) {
                tag
                    .setTag(
                        i,
                        StringTag
                            .valueOf(
                                element
                                    .getAsString()
                                    .replace("spectrum:", "pastel:")
                            )
                    );
            } else if (element instanceof CompoundTag compound) {
                replaceModId(compound);
            } else if (element instanceof ListTag list) {
                replaceModId(list);
            }
        }
    }

    private static void replaceModId(CompoundTag tag) {
        for (
            String key : tag.getAllKeys()
        ) {
            Tag element = tag.get(key);

            if (element instanceof StringTag) {
                tag
                    .putString(
                        key,
                        element
                            .getAsString()
                            .replace("spectrum:", "pastel:")
                    );
            } else if (element instanceof CompoundTag compound) {
                replaceModId(compound);
            } else if (element instanceof ListTag list) {
                replaceModId(list);
            }
        }
    }
}
