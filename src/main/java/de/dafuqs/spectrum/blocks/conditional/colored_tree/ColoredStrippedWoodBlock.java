package de.dafuqs.spectrum.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;
import java.util.Map;

public class ColoredStrippedWoodBlock extends RotatedPillarBlock implements RevelationAware, ColoredTree {

	public static final MapCodec<ColoredStrippedWoodBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			propertiesCodec(),
			InkColor.CODEC.fieldOf("color").forGetter(ColoredStrippedWoodBlock::getColor)
	).apply(instance, ColoredStrippedWoodBlock::new));
	
	private static final Map<InkColor, ColoredStrippedWoodBlock> WOOD = new Object2ObjectArrayMap<>();
	protected final InkColor color;
	
	public ColoredStrippedWoodBlock(Properties settings, InkColor color) {
		super(settings);
		this.color = color;
		WOOD.put(color, this);
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends ColoredStrippedWoodBlock> codec() {
		return CODEC;
	}
	
	@Override
	public ResourceLocation getCloakAdvancementIdentifier() {
		return ColoredTree.getTreeCloakAdvancementIdentifier(TreePart.STRIPPED_WOOD, this.color);
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		return Map.of(this.defaultBlockState(), Blocks.STRIPPED_OAK_WOOD.defaultBlockState());
	}
	
	@Override
	public Tuple<Item, Item> getItemCloak() {
		return new Tuple<>(this.asItem(), Blocks.STRIPPED_OAK_WOOD.asItem());
	}
	
	@Override
	public InkColor getColor() {
		return this.color;
	}
	
	public static ColoredStrippedWoodBlock byColor(InkColor color) {
		return WOOD.get(color);
	}
	
	public static Collection<ColoredStrippedWoodBlock> all() {
		return WOOD.values();
	}
	
}
