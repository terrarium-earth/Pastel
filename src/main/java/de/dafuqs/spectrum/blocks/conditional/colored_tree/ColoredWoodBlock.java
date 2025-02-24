package de.dafuqs.spectrum.blocks.conditional.colored_tree;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.revelationary.api.revelations.*;
import de.dafuqs.spectrum.api.energy.color.*;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import java.util.*;

public class ColoredWoodBlock extends PillarBlock implements RevelationAware, ColoredTree {

	public static final MapCodec<ColoredWoodBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			createSettingsCodec(),
			InkColor.CODEC.fieldOf("color").forGetter(ColoredWoodBlock::getColor)
	).apply(instance, ColoredWoodBlock::new));
	
	private static final Map<InkColor, ColoredWoodBlock> WOOD = new Object2ObjectArrayMap<>();
	protected final InkColor color;
	
	public ColoredWoodBlock(Settings settings, InkColor color) {
		super(settings);
		this.color = color;
		WOOD.put(color, this);
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends ColoredWoodBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public Identifier getCloakAdvancementIdentifier() {
		return ColoredTree.getTreeCloakAdvancementIdentifier(TreePart.WOOD, this.color);
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		return Map.of(this.getDefaultState(), Blocks.OAK_WOOD.getDefaultState());
	}
	
	@Override
	public Pair<Item, Item> getItemCloak() {
		return new Pair<>(this.asItem(), Blocks.OAK_WOOD.asItem());
	}
	
	@Override
	public InkColor getColor() {
		return this.color;
	}
	
	public static ColoredWoodBlock byColor(InkColor color) {
		return WOOD.get(color);
	}
	
	public static Collection<ColoredWoodBlock> all() {
		return WOOD.values();
	}
	
}
