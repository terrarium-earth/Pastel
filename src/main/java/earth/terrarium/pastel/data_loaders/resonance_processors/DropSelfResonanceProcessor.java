package earth.terrarium.pastel.data_loaders.resonance_processors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.interaction.ResonanceProcessor;
import earth.terrarium.pastel.api.predicate.block.BrokenBlockPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.List;

public class DropSelfResonanceProcessor extends ResonanceProcessor {
	
	public static final MapCodec<DropSelfResonanceProcessor> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			BrokenBlockPredicate.CODEC.fieldOf("block")
					.validate(block -> block.test(Blocks.AIR.defaultBlockState()) ? DataResult.error(() -> "Registering a Resonance Drop that matches on everything!") : DataResult.success(block))
					.forGetter(c -> c.blockPredicate),
			Codec.STRING.listOf().optionalFieldOf("state_properties_to_copy", List.of()).forGetter(c -> c.statePropertiesToCopy),
			Codec.STRING.listOf().optionalFieldOf("nbt_to_copy", List.of()).forGetter(c -> c.nbtToCopy),
			Codec.BOOL.optionalFieldOf("include_default_state_properties", false).forGetter(c -> c.includeDefaultStateProperties)
	).apply(i, DropSelfResonanceProcessor::new));
	
	public List<String> statePropertiesToCopy;
	public List<String> nbtToCopy;
	public boolean includeDefaultStateProperties;
	
	public DropSelfResonanceProcessor(BrokenBlockPredicate blockTarget, List<String> statePropertiesToCopy, List<String> nbtToCopy, boolean includeDefaultStateProperties) {
		super(blockTarget);
		this.statePropertiesToCopy = statePropertiesToCopy;
		this.nbtToCopy = nbtToCopy;
		this.includeDefaultStateProperties = includeDefaultStateProperties;
	}
	
	@Override
	public boolean process(BlockState state, BlockEntity blockEntity, List<ItemStack> droppedStacks) {
		if (blockPredicate.test(state)) {
			dropSelf(state, blockEntity, droppedStacks);
			ResonanceProcessor.preventNextXPDrop = true;
			return true;
		}
		return false;
	}
	
	public void copyBlockStateTags(BlockState minedState, ItemStack convertedStack) {
		BlockItemStateProperties component = BlockItemStateProperties.EMPTY;
		for (Property<?> blockProperty : minedState.getProperties()) {
			if (statePropertiesToCopy.contains(blockProperty.getName())) {
				if (!includeDefaultStateProperties && minedState.getBlock().defaultBlockState().getValue(blockProperty) == minedState.getValue(blockProperty)) {
					// do not copy if the value matches the default to make it stack with others
					continue;
				}
				
				component = component.with(blockProperty, minedState);
			}
		}
		convertedStack.set(DataComponents.BLOCK_STATE, component);
	}
	
	public void copyNbt(BlockEntity blockEntity, ItemStack convertedStack) {
		CompoundTag newNbt = new CompoundTag();
		
		CompoundTag BlockEntityNbt = blockEntity.saveCustomAndMetadata(blockEntity.getLevel().registryAccess());
		for (String s : nbtToCopy) {
			if (BlockEntityNbt.contains(s)) {
				newNbt.put(s, BlockEntityNbt.get(s));
			}
		}
		
		if (!newNbt.isEmpty()) {
			BlockItem.setBlockEntityData(convertedStack, blockEntity.getType(), newNbt);
		}
	}
	
	private void dropSelf(BlockState minedState, BlockEntity blockEntity, List<ItemStack> droppedStacks) {
		ItemStack selfStack = minedState.getBlock().asItem().getDefaultInstance();
		
		if (!statePropertiesToCopy.isEmpty()) {
			copyBlockStateTags(minedState, selfStack);
		}
		if (!nbtToCopy.isEmpty()) {
			copyNbt(blockEntity, selfStack);
		}
		
		droppedStacks.clear();
		droppedStacks.add(selfStack);
	}
	
	public MapCodec<? extends ResonanceProcessor> getCodec() {
		return CODEC;
	}
	
	public static Builder builder(BrokenBlockPredicate blockTarget) {
		return new Builder(blockTarget);
	}
	
	public static class Builder {
		private final BrokenBlockPredicate blockTarget;
		private final List<String> nbtToCopy = new ArrayList<>();
		private final List<String> statePropertiesToCopy = new ArrayList<>();
		private boolean includeDefaultStateProperties = false;
		
		private Builder(BrokenBlockPredicate blockTarget) {
			this.blockTarget = blockTarget;
		}
		
		public Builder copyNbt(String... tags) {
			this.nbtToCopy.addAll(List.of(tags));
			return this;
		}
		
		public Builder copyState(String... states) {
			this.statePropertiesToCopy.addAll(List.of(states));
			return this;
		}
		
		public Builder includeDefaultState() {
			this.includeDefaultStateProperties = true;
			return this;
		}
		
		public DropSelfResonanceProcessor build() {
			return new DropSelfResonanceProcessor(blockTarget, statePropertiesToCopy, nbtToCopy, includeDefaultStateProperties);
		}
		
	}
	
}
