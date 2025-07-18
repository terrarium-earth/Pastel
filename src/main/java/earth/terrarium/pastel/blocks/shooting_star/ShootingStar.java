package earth.terrarium.pastel.blocks.shooting_star;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelLootTables;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public interface ShootingStar {

    enum Variant implements StringRepresentable {
        GLISTERING("glistering", PastelLootTables.GLISTERING_SHOOTING_STAR),
        FIERY("fiery", PastelLootTables.FIERY_SHOOTING_STAR),
        COLORFUL("colorful", PastelLootTables.COLORFUL_SHOOTING_STAR),
        PRISTINE("pristine", PastelLootTables.PRISTINE_SHOOTING_STAR),
        GEMSTONE("gemstone", PastelLootTables.GEMSTONE_SHOOTING_STAR);

        public static Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);
        public static final StreamCodec<ByteBuf, Variant> STREAM_CODEC = PacketCodecHelper.enumOf(Variant::values);

        private final String name;
        private final ResourceKey<LootTable> lootTable;

        Variant(String name, ResourceKey<LootTable> lootTable) {
            this.name = name;
            this.lootTable = lootTable;
        }

        public static Variant getWeightedRandomType(@NotNull RandomSource random) {
            int r = random.nextInt(8);
            if (r == 0) {
                return FIERY;
            } else if (r == 1) {
                return PRISTINE;
            } else if (r < 3) {
                return GLISTERING;
            } else if (r < 5) {
                return COLORFUL;
            } else {
                return GEMSTONE;
            }
        }

        public static Variant getType(int type) {
            Variant[] types = values();
            if (type < 0 || type >= types.length) {
                type = 0;
            }

            return types[type];
        }

        public static Variant getType(String name) {
            Variant[] types = values();

            for (Variant type : types) {
                if (type.getName()
                        .equals(name)) {
                    return type;
                }
            }

            return types[0];
        }

        @Contract("_ -> new")
        public static @NotNull ResourceKey<LootTable> getLootTable(int index) {
            return values()[index].getLootTable();
        }

        public @NotNull ResourceKey<LootTable> getLootTable() {
            return this.lootTable;
        }

        public String getName() {
            return this.name;
        }

        public Block getBlock() {
            switch (this) {
                case PRISTINE -> {
                    return PastelBlocks.PRISTINE_SHOOTING_STAR.get();
                }
                case GEMSTONE -> {
                    return PastelBlocks.GEMSTONE_SHOOTING_STAR.get();
                }
                case FIERY -> {
                    return PastelBlocks.FIERY_SHOOTING_STAR.get();
                }
                case COLORFUL -> {
                    return PastelBlocks.COLORFUL_SHOOTING_STAR.get();
                }
                default -> {
                    return PastelBlocks.GLISTERING_SHOOTING_STAR.get();
                }
            }
        }

        public @NotNull Vector3f getRandomParticleColor(RandomSource random) {
            switch (this) {
                case GLISTERING -> {
                    int r = random.nextInt(5);
                    if (r == 0) {
                        return ColorHelper.getRGBVec(DyeColor.YELLOW);
                    } else if (r == 1) {
                        return ColorHelper.getRGBVec(DyeColor.WHITE);
                    } else if (r == 2) {
                        return ColorHelper.getRGBVec(DyeColor.ORANGE);
                    } else if (r == 3) {
                        return ColorHelper.getRGBVec(DyeColor.LIME);
                    } else {
                        return ColorHelper.getRGBVec(DyeColor.BLUE);
                    }
                }
                case COLORFUL -> {
                    return ColorHelper.getRGBVec(
                        ColorHelper.VANILLA_DYE_COLORS.get(random.nextInt(ColorHelper.VANILLA_DYE_COLORS.size())));
                }
                case FIERY -> {
                    int r = random.nextInt(2);
                    if (r == 0) {
                        return ColorHelper.getRGBVec(DyeColor.ORANGE);
                    } else {
                        return ColorHelper.getRGBVec(DyeColor.RED);
                    }
                }
                case PRISTINE -> {
                    int r = random.nextInt(3);
                    if (r == 0) {
                        return ColorHelper.getRGBVec(DyeColor.BLUE);
                    } else if (r == 1) {
                        return ColorHelper.getRGBVec(DyeColor.LIGHT_BLUE);
                    } else {
                        return ColorHelper.getRGBVec(DyeColor.CYAN);
                    }
                }
                default -> {
                    int r = random.nextInt(4);
                    if (r == 0) {
                        return ColorHelper.getRGBVec(DyeColor.CYAN);
                    } else if (r == 1) {
                        return ColorHelper.getRGBVec(DyeColor.MAGENTA);
                    } else if (r == 2) {
                        return ColorHelper.getRGBVec(DyeColor.WHITE);
                    } else {
                        return ColorHelper.getRGBVec(DyeColor.YELLOW);
                    }
                }
            }
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

    }
}
