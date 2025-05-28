package earth.terrarium.pastel.blocks.pastel_network.nodes;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import java.util.List;

public enum PastelNodeType implements StringRepresentable {
	CONNECTION(List.of(Component.translatable("block.pastel.connection_node.tooltip")), false, false),
	STORAGE(List.of(Component.translatable("block.pastel.storage_node.tooltip").withStyle(ChatFormatting.WHITE), Component.translatable("block.pastel.pastel_network_nodes.tooltip.placing").withStyle(ChatFormatting.GRAY)), true, true),
	BUFFER(List.of(Component.translatable("block.pastel.buffer_node.tooltip").withStyle(ChatFormatting.WHITE), Component.translatable("block.pastel.pastel_network_nodes.tooltip.placing").withStyle(ChatFormatting.GRAY)), true, true),
	PROVIDER(List.of(Component.translatable("block.pastel.provider_node.tooltip").withStyle(ChatFormatting.WHITE), Component.translatable("block.pastel.pastel_network_nodes.tooltip.placing").withStyle(ChatFormatting.GRAY)), false, true),
	SENDER(List.of(Component.translatable("block.pastel.sender_node.tooltip").withStyle(ChatFormatting.WHITE), Component.translatable("block.pastel.pastel_network_nodes.tooltip.placing").withStyle(ChatFormatting.GRAY)), false, true),
	GATHER(List.of(Component.translatable("block.pastel.gather_node.tooltip").withStyle(ChatFormatting.WHITE), Component.translatable("block.pastel.pastel_network_nodes.tooltip.placing").withStyle(ChatFormatting.GRAY)), true, false);
	
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
