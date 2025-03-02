package de.dafuqs.spectrum.blocks;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.inventories.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.screen.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class BedrockAnvilBlock extends AnvilBlock {

	public static final MapCodec<BedrockAnvilBlock> CODEC = createCodec(BedrockAnvilBlock::new);

	private static final Text TITLE = Text.translatable("container.spectrum.bedrock_anvil");

	public BedrockAnvilBlock(Settings settings) {
		super(settings);
	}

//	@Override
//	public MapCodec<? extends BedrockAnvilBlock> getCodec() {
//		//TODO: Make the codec
//		return CODEC;
//	}
	
	// Heavier => More damage
	@Override
	protected void configureFallingBlockEntity(FallingBlockEntity entity) {
		entity.setHurtEntities(3.0F, 64);
	}
	
	@Override
	@Nullable
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new BedrockAnvilScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), TITLE);
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("container.spectrum.bedrock_anvil.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("container.spectrum.bedrock_anvil.tooltip2").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("container.spectrum.bedrock_anvil.tooltip3").formatted(Formatting.GRAY));
	}
	
}
