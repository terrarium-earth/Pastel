package earth.terrarium.pastel.blocks.pastel_network.ink.nodes;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import java.util.List;

public enum PastelInkNodeType implements StringRepresentable {
    CONNECTION(List.of(Component.translatable("block.pastel.connection_node.tooltip")), false, false),
    PROVIDER(
        List.of(
            Component.translatable("block.pastel.ink_provider_node.tooltip")
                     .withStyle(ChatFormatting.WHITE),
            Component.translatable("block.pastel.pastel_ink_network_nodes.tooltip.placing")
                     .withStyle(ChatFormatting.GRAY)
        ), false, false
    ),
    GATHER(
        List.of(
            Component.translatable("block.pastel.ink_gather_node.tooltip")
                     .withStyle(ChatFormatting.WHITE),
            Component.translatable("block.pastel.pastel_ink_network_nodes.tooltip.placing")
                     .withStyle(ChatFormatting.GRAY)
        ), false, false
    );

    private final List<Component> tooltips;

    PastelInkNodeType(List<Component> tooltips, boolean usesFilters, boolean hasOuterRing) {
        this.tooltips = tooltips;
    }

    public List<Component> getTooltips() {
        return this.tooltips;
    }

    @Override
    public String getSerializedName() {
        return name();
    }
}
