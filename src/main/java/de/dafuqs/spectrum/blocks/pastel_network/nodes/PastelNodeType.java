package de.dafuqs.spectrum.blocks.pastel_network.nodes;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import java.util.List;

public enum PastelNodeType implements StringRepresentable {
	CONNECTION(List.of(Component.translatable("block.spectrum.connection_node.tooltip")), false, false),
	STORAGE(List.of(Component.translatable("block.spectrum.storage_node.tooltip").withStyle(ChatFormatting.WHITE), Component.translatable("block.spectrum.pastel_network_nodes.tooltip.placing").withStyle(ChatFormatting.GRAY)), true, true),
	BUFFER(List.of(Component.translatable("block.spectrum.buffer_node.tooltip").withStyle(ChatFormatting.WHITE), Component.translatable("block.spectrum.pastel_network_nodes.tooltip.placing").withStyle(ChatFormatting.GRAY)), true, true),
	PROVIDER(List.of(Component.translatable("block.spectrum.provider_node.tooltip").withStyle(ChatFormatting.WHITE), Component.translatable("block.spectrum.pastel_network_nodes.tooltip.placing").withStyle(ChatFormatting.GRAY)), false, true),
	SENDER(List.of(Component.translatable("block.spectrum.sender_node.tooltip").withStyle(ChatFormatting.WHITE), Component.translatable("block.spectrum.pastel_network_nodes.tooltip.placing").withStyle(ChatFormatting.GRAY)), false, true),
	GATHER(List.of(Component.translatable("block.spectrum.gather_node.tooltip").withStyle(ChatFormatting.WHITE), Component.translatable("block.spectrum.pastel_network_nodes.tooltip.placing").withStyle(ChatFormatting.GRAY)), true, false);
	
	private final List<Component> tooltips;
	private final boolean usesFilters, hasOuterRing;
	
	PastelNodeType(List<Component> tooltips, boolean usesFilters, boolean hasOuterRing) {
		this.tooltips = tooltips;
		this.usesFilters = usesFilters;
		this.hasOuterRing = hasOuterRing;
	}
	
	public List<Component> getTooltips() {
		return this.tooltips;
	}

	public boolean usesFilters() {
		return usesFilters;
	}

	public boolean hasOuterRing() {
		return hasOuterRing;
	}

	@Override
	public String getSerializedName() {
		return name();
	}
}
