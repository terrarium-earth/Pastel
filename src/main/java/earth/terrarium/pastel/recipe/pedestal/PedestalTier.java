package earth.terrarium.pastel.recipe.pedestal;

import com.cmdpro.databank.DatabankUtils;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.mojang.serialization.Codec;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntity;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.progression.PastelCriteria;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelMultiblocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Rotation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public enum PedestalTier implements StringRepresentable {
    BASIC(
        PastelAdvancements.PLACE_PEDESTAL, null,
        new GemstoneColor[]{PastelGemstoneColor.CYAN, PastelGemstoneColor.MAGENTA, PastelGemstoneColor.YELLOW}
    ),

    SIMPLE(
        PastelAdvancements.BUILD_BASIC_PEDESTAL_STRUCTURE, PastelMultiblocks.PEDESTAL_SIMPLE,
        new GemstoneColor[]{PastelGemstoneColor.CYAN, PastelGemstoneColor.MAGENTA, PastelGemstoneColor.YELLOW}
    ),

    ADVANCED(
        PastelAdvancements.Midgame.BUILD_ADVANCED_PEDESTAL_STRUCTURE, PastelMultiblocks.PEDESTAL_ADVANCED,
        new GemstoneColor[]{
            PastelGemstoneColor.CYAN, PastelGemstoneColor.MAGENTA,
            PastelGemstoneColor.YELLOW, PastelGemstoneColor.BLACK
        }
    ),

    COMPLEX(
        PastelAdvancements.Lategame.BUILD_COMPLEX_PEDESTAL_STRUCTURE, PastelMultiblocks.PEDESTAL_COMPLEX,
        PastelGemstoneColor.values()
    );

    public final ResourceLocation unlockAdvancementId;
    public final @Nullable ResourceLocation structure;
    private final GemstoneColor[] gemstoneColors;

    public static final Codec<PedestalTier> CODEC = StringRepresentable.fromEnum(PedestalTier::values);
    public static final StreamCodec<ByteBuf, PedestalTier> STREAM_CODEC = PacketCodecHelper.enumOf(
        PedestalTier::values);

    PedestalTier(ResourceLocation unlockAdvancementId, ResourceLocation structure, GemstoneColor[] gemstoneColors) {
        this.unlockAdvancementId = unlockAdvancementId;
        this.structure = structure;
        this.gemstoneColors = gemstoneColors;
    }

    @Contract(pure = true)
    public int getPowderSlotCount() {
        return this.gemstoneColors.length;
    }

    @Contract(pure = true)
    public GemstoneColor[] gemstoneColors() {
        return gemstoneColors;
    }

    @Contract(pure = true)
    public static Optional<PedestalTier> getHighestUnlockedRecipeTier(Player playerEntity) {
        if (DatabankUtils.hasAdvancement(playerEntity, COMPLEX.unlockAdvancementId)) {
            return Optional.of(PedestalTier.COMPLEX);
        } else if (DatabankUtils.hasAdvancement(playerEntity, ADVANCED.unlockAdvancementId)) {
            return Optional.of(PedestalTier.ADVANCED);
        } else if (DatabankUtils.hasAdvancement(playerEntity, SIMPLE.unlockAdvancementId)) {
            return Optional.of(PedestalTier.SIMPLE);
        } else if (DatabankUtils.hasAdvancement(playerEntity, BASIC.unlockAdvancementId)) {
            return Optional.of(PedestalTier.BASIC);
        }
        return Optional.empty();
    }

    public boolean hasUnlocked(Player playerEntity) {
        return DatabankUtils.hasAdvancement(playerEntity, unlockAdvancementId);
    }

    public static Optional<PedestalTier> hasJustUnlockedANewRecipeTier(
        @NotNull ResourceLocation advancementIdentifier
    ) {
        if (advancementIdentifier.equals(BASIC.unlockAdvancementId)) {
            return Optional.of(PedestalTier.BASIC);
        } else if (advancementIdentifier.equals(SIMPLE.unlockAdvancementId)) {
            return Optional.of(PedestalTier.SIMPLE);
        } else if (advancementIdentifier.equals(ADVANCED.unlockAdvancementId)) {
            return Optional.of(PedestalTier.ADVANCED);
        } else if (advancementIdentifier.equals(COMPLEX.unlockAdvancementId)) {
            return Optional.of(PedestalTier.COMPLEX);
        }
        return Optional.empty();
    }

    public static PedestalTier getTier(Optional<Player> player, PedestalBlockEntity pedestal) {
        Multiblock multiblock;
        var level = pedestal.getLevel();
        var pos = pedestal.getBlockPos();
        var maxTier = pedestal.getVariant()
                              .getRecipeTier();

        if (maxTier == BASIC)
            return BASIC;

        var tier = BASIC;
        for (int i = 1; i <= maxTier.ordinal(); i++) {
            var proposal = values()[i];
            multiblock = getStructureFor(player, proposal);

            if (multiblock.validate(level, pos.below(), Rotation.NONE)) {
                if (level instanceof ServerLevel sl) {
                    Support.mbCriterion(sl, pos, multiblock);
                }
                tier = proposal;
            }
        }

        return tier;
    }

    private static Multiblock getStructureFor(Optional<Player> player, PedestalTier proposal) {
        if (player.isPresent() && proposal == COMPLEX &&
            !DatabankUtils.hasAdvancement(
                player.get(), PastelAdvancements.Lategame.BUILD_COMPLEX_PEDESTAL_STRUCTURE_WITHOUT_MOONSTONE)) {

            return PastelMultiblocks.get(PastelMultiblocks.PEDESTAL_COMPLEX_WITHOUT_MOONSTONE);
        }

        return PastelMultiblocks.get(proposal.structure);
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }
}
