package earth.terrarium.pastel.recipe.pedestal;

import com.cmdpro.databank.DatabankUtils;
import com.mojang.serialization.Codec;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelMultiblocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public enum PedestalRecipeTier implements StringRepresentable {
    BASIC(
        PastelAdvancements.PLACED_PEDESTAL,
        new GemstoneColor[]{BuiltinGemstoneColor.CYAN, BuiltinGemstoneColor.MAGENTA, BuiltinGemstoneColor.YELLOW}
    ),
    SIMPLE(
        PastelAdvancements.BUILD_BASIC_PEDESTAL_STRUCTURE,
        new GemstoneColor[]{BuiltinGemstoneColor.CYAN, BuiltinGemstoneColor.MAGENTA, BuiltinGemstoneColor.YELLOW}
    ),
    ADVANCED(
        PastelAdvancements.BUILD_ADVANCED_PEDESTAL_STRUCTURE, new GemstoneColor[]{
        BuiltinGemstoneColor.CYAN, BuiltinGemstoneColor.MAGENTA, BuiltinGemstoneColor.YELLOW, BuiltinGemstoneColor.BLACK
    }
    ),
    COMPLEX(PastelAdvancements.BUILD_COMPLEX_PEDESTAL_STRUCTURE, BuiltinGemstoneColor.values());

    private final ResourceLocation unlockAdvancementId;
    private final GemstoneColor[] gemstoneColors;

    public static final Codec<PedestalRecipeTier> CODEC = StringRepresentable.fromEnum(PedestalRecipeTier::values);
    public static final StreamCodec<ByteBuf, PedestalRecipeTier> STREAM_CODEC = PacketCodecHelper.enumOf(
        PedestalRecipeTier::values);

    PedestalRecipeTier(ResourceLocation unlockAdvancementId, GemstoneColor[] gemstoneColors) {
        this.unlockAdvancementId = unlockAdvancementId;
        this.gemstoneColors = gemstoneColors;
    }

    @Contract(pure = true)
    public int getPowderSlotCount() {
        return this.gemstoneColors.length;
    }

    @Contract(pure = true)
    public GemstoneColor[] getAvailableGemstoneColors() {
        return gemstoneColors;
    }

    @Contract(pure = true)
    public static Optional<PedestalRecipeTier> getHighestUnlockedRecipeTier(Player playerEntity) {
        if (DatabankUtils.hasAdvancement(playerEntity, COMPLEX.unlockAdvancementId)) {
            return Optional.of(PedestalRecipeTier.COMPLEX);
        } else if (DatabankUtils.hasAdvancement(playerEntity, ADVANCED.unlockAdvancementId)) {
            return Optional.of(PedestalRecipeTier.ADVANCED);
        } else if (DatabankUtils.hasAdvancement(playerEntity, SIMPLE.unlockAdvancementId)) {
            return Optional.of(PedestalRecipeTier.SIMPLE);
        } else if (DatabankUtils.hasAdvancement(playerEntity, BASIC.unlockAdvancementId)) {
            return Optional.of(PedestalRecipeTier.BASIC);
        }
        return Optional.empty();
    }

    public boolean hasUnlocked(Player playerEntity) {
        return DatabankUtils.hasAdvancement(playerEntity, unlockAdvancementId);
    }

    public static Optional<PedestalRecipeTier> hasJustUnlockedANewRecipeTier(
        @NotNull ResourceLocation advancementIdentifier
    ) {
        if (advancementIdentifier.equals(BASIC.unlockAdvancementId)) {
            return Optional.of(PedestalRecipeTier.BASIC);
        } else if (advancementIdentifier.equals(SIMPLE.unlockAdvancementId)) {
            return Optional.of(PedestalRecipeTier.SIMPLE);
        } else if (advancementIdentifier.equals(ADVANCED.unlockAdvancementId)) {
            return Optional.of(PedestalRecipeTier.ADVANCED);
        } else if (advancementIdentifier.equals(COMPLEX.unlockAdvancementId)) {
            return Optional.of(PedestalRecipeTier.COMPLEX);
        }
        return Optional.empty();
    }


    @Contract(pure = true)
    public @Nullable ResourceLocation getStructureID(Player player) {
        switch (this) {
            case COMPLEX -> {
                if (DatabankUtils.hasAdvancement(
                    player, PastelAdvancements.BUILD_COMPLEX_PEDESTAL_STRUCTURE_WITHOUT_MOONSTONE)) {
                    return PastelMultiblocks.PEDESTAL_COMPLEX;
                } else {
                    return PastelMultiblocks.PEDESTAL_COMPLEX_WITHOUT_MOONSTONE;
                }
            }
            case ADVANCED -> {
                return PastelMultiblocks.PEDESTAL_ADVANCED;
            }
            case SIMPLE -> {
                return PastelMultiblocks.PEDESTAL_SIMPLE;
            }
            default -> {
                return null;
            }
        }
    }

    public @Nullable Component getStructureText() {
        switch (this) {
            case COMPLEX -> {
                return PastelMultiblocks.PEDESTAL_COMPLEX_TEXT;
            }
            case ADVANCED -> {
                return PastelMultiblocks.PEDESTAL_ADVANCED_TEXT;
            }
            case SIMPLE -> {
                return PastelMultiblocks.PEDESTAL_SIMPLE_TEXT;
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }
}
