package earth.terrarium.pastel.blocks;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.inventories.BedrockAnvilScreenHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BedrockAnvilBlock extends AnvilBlock {

    public static final MapCodec<BedrockAnvilBlock> CODEC = simpleCodec(BedrockAnvilBlock::new);

    private static final Component TITLE = Component.translatable("container.pastel.bedrock_anvil");

    public BedrockAnvilBlock(Properties settings) {
        super(settings);
    }

//	@Override
//	public MapCodec<? extends BedrockAnvilBlock> getCodec() {
//		//TODO: Make the codec
//		return CODEC;
//	}

    // Heavier => More damage
    @Override
    protected void falling(FallingBlockEntity entity) {
        entity.setHurtsEntities(3.0F, 64);
    }

    @Override
    @Nullable
    public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider(
            (syncId, inventory, player) -> new BedrockAnvilScreenHandler(
            syncId, inventory, ContainerLevelAccess.create(world, pos)), TITLE
        );
    }

    @Override
    public void appendHoverText(
        ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("container.pastel.bedrock_anvil.tooltip")
                             .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("container.pastel.bedrock_anvil.tooltip2")
                             .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("container.pastel.bedrock_anvil.tooltip3")
                             .withStyle(ChatFormatting.GRAY));
    }

}
