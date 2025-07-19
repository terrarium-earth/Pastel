package earth.terrarium.pastel.recipe.pedestal;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.mojang.serialization.Codec;
import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntity;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.progression.PastelAdvancementCriteria;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelMultiblocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Rotation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public enum PedestalTier implements StringRepresentable {
	BASIC(PastelAdvancements.PLACED_PEDESTAL, null,
			new GemstoneColor[]{PastelGemstoneColor.CYAN, PastelGemstoneColor.MAGENTA, PastelGemstoneColor.YELLOW}),

	SIMPLE(PastelAdvancements.BUILD_BASIC_PEDESTAL_STRUCTURE, PastelMultiblocks.PEDESTAL_SIMPLE,
			new GemstoneColor[]{PastelGemstoneColor.CYAN, PastelGemstoneColor.MAGENTA, PastelGemstoneColor.YELLOW}),

	ADVANCED(PastelAdvancements.BUILD_ADVANCED_PEDESTAL_STRUCTURE, PastelMultiblocks.PEDESTAL_ADVANCED,
			new GemstoneColor[]{PastelGemstoneColor.CYAN, PastelGemstoneColor.MAGENTA,
					PastelGemstoneColor.YELLOW, PastelGemstoneColor.BLACK}),

	COMPLEX(PastelAdvancements.BUILD_COMPLEX_PEDESTAL_STRUCTURE, PastelMultiblocks.PEDESTAL_COMPLEX,
			PastelGemstoneColor.values());
	
	public final ResourceLocation unlockAdvancementId;
	public final @Nullable ResourceLocation structure;
	private final GemstoneColor[] gemstoneColors;
	
	public static final Codec<PedestalTier> CODEC = StringRepresentable.fromEnum(PedestalTier::values);
	public static final StreamCodec<ByteBuf, PedestalTier> STREAM_CODEC = PacketCodecHelper.enumOf(PedestalTier::values);
	
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
		if (AdvancementHelper.hasAdvancement(playerEntity, COMPLEX.unlockAdvancementId)) {
			return Optional.of(PedestalTier.COMPLEX);
		} else if (AdvancementHelper.hasAdvancement(playerEntity, ADVANCED.unlockAdvancementId)) {
			return Optional.of(PedestalTier.ADVANCED);
		} else if (AdvancementHelper.hasAdvancement(playerEntity, SIMPLE.unlockAdvancementId)) {
			return Optional.of(PedestalTier.SIMPLE);
		} else if (AdvancementHelper.hasAdvancement(playerEntity, BASIC.unlockAdvancementId)) {
			return Optional.of(PedestalTier.BASIC);
		}
		return Optional.empty();
	}
	
	public boolean hasUnlocked(Player playerEntity) {
		return AdvancementHelper.hasAdvancement(playerEntity, unlockAdvancementId);
	}
	
	public static Optional<PedestalTier> hasJustUnlockedANewRecipeTier(@NotNull ResourceLocation advancementIdentifier) {
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
	
	
	@Contract(pure = true)
	public @Nullable ResourceLocation getStructureID(Player player) {
		switch (this) {
			case COMPLEX -> {
				if (AdvancementHelper.hasAdvancement(player, PastelAdvancements.BUILD_COMPLEX_PEDESTAL_STRUCTURE_WITHOUT_MOONSTONE)) {
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

	public static PedestalTier getTier(PedestalBlockEntity pedestal) {
		Multiblock multiblock;
		var level = pedestal.getLevel();
		var pos = pedestal.getBlockPos();
		var owner = (ServerPlayer) pedestal.getOwnerIfOnline();
		var maxTier = pedestal.getVariant().getRecipeTier();

		if (maxTier == BASIC)
			return BASIC;

		var tier = BASIC;
		for (int i = 1; i <= maxTier.ordinal(); i++) {
			var proposal = values()[i];
			multiblock = PastelMultiblocks.get(proposal.structure);

			if (multiblock.validate(level, pos.below(), Rotation.NONE)) {
				if (owner != null)
					PastelAdvancementCriteria.COMPLETED_MULTIBLOCK.trigger(owner, multiblock);
				tier = proposal;
			}
		}

		return tier;
	}

	@Override
	public String getSerializedName() {
		return name().toLowerCase();
	}
}
