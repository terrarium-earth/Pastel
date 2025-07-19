package earth.terrarium.pastel.blocks.conditional;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.mixin.accessors.ExperienceDroppingBlockAccessor;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;

public class GemstoneOreBlock extends CloakedOreBlock {

	public final MapCodec<GemstoneOreBlock> codec;
	private final GemstoneColor gemstoneColor;
	
	public GemstoneOreBlock(IntProvider experienceDropped, Properties settings, GemstoneColor gemstoneColor) {
		super(experienceDropped, settings);
		this.gemstoneColor = gemstoneColor;
		this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
				IntProvider.codec(0, 10).fieldOf("experience").forGetter(b -> ((ExperienceDroppingBlockAccessor) b).getXpRange()),
				propertiesCodec(),
				PastelRegistries.GEMSTONE_COLOR.byNameCodec().fieldOf("color").forGetter(b -> b.gemstoneColor)
		).apply(instance, GemstoneOreBlock::new));
	}

	@Override
	public MapCodec<? extends GemstoneOreBlock> codec() {
		return codec;
	}

}
