package de.dafuqs.spectrum.data_loaders.resonance_processors;

import java.util.*;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.interaction.*;
import de.dafuqs.spectrum.api.predicate.block.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.state.property.*;

public class DropSelfResonanceProcessor extends ResonanceProcessor {
	
	public static final MapCodec<DropSelfResonanceProcessor> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			BrokenBlockPredicate.CODEC.fieldOf("block")
					.validate(block -> block.test(Blocks.AIR.getDefaultState()) ? DataResult.error(() -> "Registering a Resonance Drop that matches on everything!") : DataResult.success(block))
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
		for (Property<?> blockProperty : minedState.getProperties()) {
			if (statePropertiesToCopy.contains(blockProperty.getName())) {
				if (!includeDefaultStateProperties && minedState.getBlock().getDefaultState().get(blockProperty) == minedState.get(blockProperty)) {
					// do not copy if the value matches the default to make it stack with others
					continue;
				}
				
				NbtComponent nbt = convertedStack.get(DataComponentTypes.CUSTOM_DATA);
				convertedStack.apply(DataComponentTypes.CUSTOM_DATA, nbt, nbtComponent -> nbtComponent.apply(nbtCompound -> nbtCompound.putString(blockProperty.getName(), getPropertyName(minedState, blockProperty))));
			}
		}
	}
	
	private static <T extends Comparable<T>> String getPropertyName(BlockState state, Property<T> property) {
		return property.name(state.get(property));
	}
	
	public void copyNbt(BlockEntity blockEntity, ItemStack convertedStack) {
		NbtCompound newNbt = new NbtCompound();
		
		NbtCompound BlockEntityNbt = blockEntity.createComponentlessNbtWithIdentifyingData(blockEntity.getWorld().getRegistryManager());
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
		ItemStack selfStack = minedState.getBlock().asItem().getDefaultStack();
		
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
